package net.bikash.timerewind.rewind.world;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;



public class WorldHistory {
    
    private static final int MAX_HISTORY_TICKS = 20*15;

    private final Deque<List<BlockChange>> history = new ArrayDeque<>();

    public void record(List<BlockChange> changes){
        if(changes == null){
            return;
        }
        history.addLast(new ArrayList<>(changes));

        while(history.size() > MAX_HISTORY_TICKS){
            history.removeFirst();
        }
    }
    public List<BlockChange> removeLatestTick(){
        return history.pollLast();
    }

    public List<BlockChange> peekLatestTick(){
        List<BlockChange> changes = history.peekLast();

        if(changes == null){
            return null ;

        }

        return new ArrayList<>(changes);
        }

    public void clear(){
        history.clear();
    }

    public int getRecordedTicks(){
        return history.size();
    }
    
  
}
