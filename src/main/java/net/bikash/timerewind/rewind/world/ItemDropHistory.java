package net.bikash.timerewind.rewind.world;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemDropHistory {
    

    private final List<UUID> droppedItems = new ArrayList<>();

    public void record(UUID itemId){
        droppedItems.add(itemId);
    }

    public List<UUID> getDroppedItems(){
        return new ArrayList<>(droppedItems);
    }

    public void clear(){
        droppedItems.clear();
    }
}
