package net.bikash.timerewind.rewind.world;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import net.minecraft.world.ticks.TickAccess;



public class WorldHistory {
    
    private static final int MAX_HISTORY_TICKS = 20*15;

    private final Deque <TickChanges> history = new ArrayDeque<>();

   public void record(List<BlockChange> blockChanges){
    record(blockChanges,List.of());
   }

   public void record(List<BlockChange> blockChanges,
    List<ItemPickupChange> pickupChanges
   ){
    history.addLast(new TickChanges (
        blockChanges == null ? List.of() : new ArrayList<>(blockChanges),
        pickupChanges == null ? List.of() : new ArrayList<>(pickupChanges)
    ));

    while(history.size() > MAX_HISTORY_TICKS){
        history.removeFirst();
    }
   }
   public TickChanges removeLatestTick(){
    return history.pollLast();
   }

   public TickChanges peekLatestTick(){
    return history.peekLast();
   }
   public void clear(){
    history.clear();
   }

   public int getRecordedTicks(){
    return history.size();
   }

   public record TickChanges(
    List<BlockChange> blockChanges,
    List<ItemPickupChange> pickupChanges
   ){}
  
}
