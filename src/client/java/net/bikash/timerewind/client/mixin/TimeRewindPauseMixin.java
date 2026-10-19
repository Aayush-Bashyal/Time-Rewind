
package net.bikash.timerewind.client.mixin;

import net.bikash.timerewind.client.RewindSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public abstract class TimeRewindPauseMixin extends Screen {

    protected TimeRewindPauseMixin() {
        super(Component.literal("Pause"));
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addTimeRewindButton(CallbackInfo info) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addRenderableWidget(
            Button.builder(
                Component.literal("Time Rewind"),
                button -> Minecraft.getInstance().setScreenAndShow(
                    new RewindSettingsScreen(this)
                )
            )
            .bounds(centerX - 100, centerY + 72, 200, 20)
            .build()
        );
    }
}
