package net.bikash.timerewind.rewind.world;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class InventorySnapshot {
    

    private final List<ItemStack> items = new ArrayList<>();

    public InventorySnapshot(Inventory inventory){
        for(int i=0 ; i<inventory.getContainerSize();i++){
            items.add(inventory.getItem(i).copy());
        }
    }
    public void restore(Inventory inventory){
        int size = Math.min(inventory.getContainerSize(), items.size());

        for(int i = 0 ; i<size ; i++){
            inventory.setItem(i, items.get(i).copy());
        }
    }
}
