package net.bikash.timerewind.rewind.world;

import java.util.ArrayDeque;
import java.util.Deque;

import com.jcraft.jorbis.Block;

public class WorldHistory {
    
    private static final int MAX_HISTORY_TICKS = 20*15;

    private final Deque<BlockChange> history = new ArrayDeque<>();

    public void record(BlockChange change){
        if(change == null){
            return;
        }
        history.addLast(change);
        while(history.size() > MAX_HISTORY_TICKS){
            history.removeFirst();
        }
    }
    public BlockChange getLatestChange(){
        return history.peekLast();
    }
    public BlockChange removeLatestChange(){
        return history.pollLast();
    }

    public void clear(){
        history.clear();
    }

    public int size(){
        return history.size();
    }

}
