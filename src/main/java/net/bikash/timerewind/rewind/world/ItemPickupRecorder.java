package net.bikash.timerewind.rewind.world;

import java.util.ArrayList;
import java.util.List;

public class ItemPickupRecorder {
    private final List<ItemPickupChange> currentTickChanges = new ArrayList<>();
    private boolean recordingEnabled = true;

    public void recordPickup(ItemPickupChange    change){
        if(change != null){
            currentTickChanges.add(change);

        }

    }
    public List<ItemPickupChange> finishTick(){
        List<ItemPickupChange> changes = new ArrayList<>(currentTickChanges);
        currentTickChanges.clear();
        return changes;
    }

    public boolean isRecordingEnabled(){
        return recordingEnabled;
    }
    public void clear(){
        currentTickChanges.clear();
        recordingEnabled     = true;
    }
}
