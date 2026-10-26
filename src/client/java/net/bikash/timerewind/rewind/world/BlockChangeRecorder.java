package net.bikash.timerewind.rewind.world;

import java.util.ArrayList;
import java.util.List;

public class BlockChangeRecorder {
    private final WorldHistory history;
    private final List<BlockChange> currentTickChanges = new ArrayList<>();

    public BlockChangeRecorder(WorldHistory history){
        this.history = history;
    }

    public void recordChange(BlockChange change){
        if(change != null){
            currentTickChanges.add(change);
        }
    }


    public void finishTick(){
        if(!currentTickChanges.isEmpty()){
            history.record(currentTickChanges);
        }
        currentTickChanges.clear();
    }


    public void clear(){
        currentTickChanges.clear();
        history.clear();
    }
}
