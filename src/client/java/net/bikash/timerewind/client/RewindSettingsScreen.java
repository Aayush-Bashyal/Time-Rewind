package net.bikash.timerewind.client;

import net.bikash.timerewind.rewind.RewindConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class RewindSettingsScreen extends Screen {

    private final Screen parent;
    private int selectedSeonds;

    public RewindSettingsScreen(Screen parent){
        super(Component.literal("Time Rewind"));
        this.parent = parent;
        this.selectedSeonds = RewindConfig.getRewindSeconds();
    }

    @Override 
    protected  void init(){
        int centerX = this.width / 2;
        int centerY = this.height /2 ;

        addRenderableWidget(Button.builder(
            Component.literal("-"),
            button -> {
                selectedSeonds = Math.max(0, selectedSeonds -1);
                this.rebuildWidgets();
            }
            ).bounds(centerX - 100, centerY -10,  40,  20).build()
    ); 

    addRenderableWidget(Button.builder(Component.literal("+"),
button -> {
    
    selectedSeonds = Math.min(15, selectedSeonds +1);
    this.rebuildWidgets();
}
).bounds( centerX + 60, centerY - 10, 40, 20).build()
);

addRenderableWidget(Button.builder(Component.literal("Done"), 
button -> {
    RewindConfig.setRewindSeconds(selectedSeonds);
    Minecraft.getInstance().setScreenAndShow(parent);
}
).bounds(centerX - 100, centerY + 35, 200 , 20).build()
);


addRenderableWidget(Button.builder(Component.literal("Cancel"),
 button ->  
    Minecraft.getInstance().setScreenAndShow(parent)
).bounds(centerX-100, centerY+60, 200, 20).build()
);

    }

@Override 
public void extractRenderState(
    GuiGraphicsExtractor graphics,
    int mouseX,
    int mouseY,
    float delta
) {
    super.extractRenderState(graphics, mouseX, mouseY, delta);

    graphics.centeredText(this.font, 
                        this.title,
                        this.width / 2,
                        90,
                        -1
    );

    graphics.centeredText(
        this.font,
        Component.literal(selectedSeonds + " seconds"),
        this.width / 2,
        120,
        -1
    );

    graphics.centeredText(this.font,
         Component.literal("Choose how many seconds to rewind"),
          this.width/2,
          140,
        0xAAAAAA);


}
    
    
}
