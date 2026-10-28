package net.bikash.timerewind.rewind.world;

import java.util.List;


import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class BlockRewindManager {
    
    private final WorldHistory history;
    private final BlockChangeRecorder recorder;
    private boolean rewindPending = false;

    public BlockRewindManager(
        WorldHistory history,
        BlockChangeRecorder recorder
    ){
        this.history = history;
        this.recorder = recorder;
    }

    public boolean rewindOneTick(Minecraft client){

        if(rewindPending){
            return false;
        }
       
        if(client.level == null || client.player == null){
            return false;
        }
MinecraftServer server = client.getSingleplayerServer();

if(server == null){
    return false;
}


       

        List<BlockChange> changes = history.removeLatestTick();

        if(changes == null){
            return false;
        }

        rewindPending = true;
server.execute(() -> {
        recorder.pauseRecording();

        try{
            for(int i=changes.size() -1 ; i>=0;i--){
                BlockChange change = changes.get(i);

                ServerLevel serverLevel = server.getLevel(change.getDimension());
 
                if(serverLevel == null){
                    continue;
                }

                BlockPos position = change.getPosition(); 

            
            serverLevel.setBlock(position, change.getOldState(), 3,512);
        } 
    } finally {
        recorder.resumeRecording();
           rewindPending = false;
    }
});
    return true;
    }

    public void reset() {
    history.clear();
    recorder.clear();
}

}
