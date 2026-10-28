package net.bikash.timerewind.rewind.world;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemDropHistory {
    

    private final List<UUID> currentTickDrops = new ArrayList<>();

    public void record(UUID itemId){
       if(itemId != null){
        currentTickDrops.add(itemId);
       }
    }
public List<UUID> finishTick(){
    List<UUID> drops = new ArrayList<>(currentTickDrops);
    currentTickDrops.clear();
    return drops;
}


    public void clear(){
        currentTickDrops.clear();
    }
}
