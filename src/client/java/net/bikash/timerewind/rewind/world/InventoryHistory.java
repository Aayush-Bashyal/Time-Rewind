package net.bikash.timerewind.rewind.world;

import java.util.ArrayDeque;
import java.util.Deque;

import net.minecraft.world.entity.player.Inventory;



public class InventoryHistory {
    
    private static final int MAX_HISTORY_TICKS = 20*15;

    private final Deque<InventorySnapshot> history = new ArrayDeque<>();

    public void record(Inventory inventory){
        history.addLast(new InventorySnapshot(inventory));

        while (history.size() > MAX_HISTORY_TICKS) {
            history.removeFirst();
            
        }
    }
    public InventorySnapshot removeLatest(){
        return history.pollLast();
    }
    public void clear(){
        history.clear();
    }
    public int getRecordedTicks(){
        return history.size();
    }
}
