package net.bikash.timerewind.rewind.world;

import java.util.ArrayList;
import java.util.List;

public class BlockChangeRecorder {
    private final WorldHistory history;
    private final List<BlockChange> currentTickChanges = new ArrayList<>();

private boolean recordingEnabled = true;

    public BlockChangeRecorder(WorldHistory history){
        this.history = history;
    }

    public void pauseRecording(){
        recordingEnabled = false;
    }

    public void resumeRecording(){
        recordingEnabled = true;
    }

    public void recordChange(BlockChange change){
        if(change != null && recordingEnabled){
            currentTickChanges.add(change);
           
        }
    }

    public void setRecordingEnabled(boolean enabled){
    recordingEnabled = enabled;
    } 

    public boolean isRecordingEnabled(){
        return recordingEnabled;
    }


     public List<BlockChange> finishTickAndGetChanges() {
        if (!recordingEnabled) {
            currentTickChanges.clear();
            return List.of();
        }

        List<BlockChange> changes = new ArrayList<>(currentTickChanges);
        currentTickChanges.clear();
        return changes;
    }


    public void finishTick(){
      history.record(finishTickAndGetChanges());
      
    }


    public void clear(){
        currentTickChanges.clear();
        history.clear();
        recordingEnabled = true;
    }
}
