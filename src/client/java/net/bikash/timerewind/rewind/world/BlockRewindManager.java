package net.bikash.timerewind.rewind.world;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;


import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;

public class BlockRewindManager {
    
    private final WorldHistory history;
    private final BlockChangeRecorder recorder;
    private final InventoryHistory inventoryHistory;
    private boolean rewindPending = false;

    public BlockRewindManager(
        WorldHistory history,
        BlockChangeRecorder recorder,
         InventoryHistory inventoryHistory
    ){
        this.history = history;
        this.recorder = recorder;
          this.inventoryHistory = inventoryHistory;
    }

    public boolean isRewindPending() {
    return rewindPending;
}

    public boolean rewindOneTick(Minecraft client , Runnable onComplete){

        if(rewindPending){
            return false;
        }
       
        if(client.level == null || client.player == null){
            return false;
        }
MinecraftServer server = client.getSingleplayerServer();
var player  = client.player;

if(server == null){
    return false;
}


       

      WorldHistory.TickChanges   tickChanges = history.peekLatestTick();

    

        if(tickChanges == null){
            return false;
        }

     
        rewindPending = true;


server.execute(() -> {
        recorder.pauseRecording();

        try{

            for (UUID itemId : tickChanges.droppedItemIds()) {
    for (ServerLevel level : server.getAllLevels()) {
        var entity = level.getEntity(itemId);

        if (entity instanceof ItemEntity itemEntity) {
            itemEntity.discard();
            break;
        }
    }
}

 List<BlockChange> changes = tickChanges.blockChanges();

            for(int i=changes.size() -1 ; i>=0;i--){
                BlockChange change = changes.get(i);

                ServerLevel level = server.getLevel(change.getDimension());
 
                  if (level != null) {
                    level.setBlock(change.getPosition(), change.getOldState(), 3, 512);
                }
        } 

        client.execute(() -> {
    try {

         if (client.player != player) {
                        return;
                    }

                       InventorySnapshot snapshot = inventoryHistory.removeLatest();

         if (snapshot != null) {
                        snapshot.restore(player.getInventory());
                    }


        history.removeLatestTick();

if(onComplete != null){
    onComplete.run();
}

    } finally {
        rewindPending = false;
    }
});

        

    } catch (RuntimeException    exception){
        client.execute(() -> rewindPending = false);
        throw exception;
    }
    
    finally {
        recorder.resumeRecording();
          
    }
});
    return true;
    }

    public void reset() {
        rewindPending = false;
    history.clear();
    recorder.clear();
    inventoryHistory.clear();
}

}
