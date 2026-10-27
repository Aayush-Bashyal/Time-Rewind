package net.bikash.timerewind.rewind.world;

import java.util.List;


import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class BlockRewindManager {
    
    private final WorldHistory history;
    private final BlockChangeRecorder recorder;

    public BlockRewindManager(
        WorldHistory history,
        BlockChangeRecorder recorder
    ){
        this.history = history;
        this.recorder = recorder;
    }

    public boolean rewindOneTick(Minecraft client){
       
        if(client.level == null || client.player == null){
            return false;
        }
MinecraftServer server = client.getSingleplayerServer();

if(server == null){
    return false;
}

ServerLevel serverLevel = server.getLevel(Level.OVERWORLD);

if(serverLevel == null){
    return false;
}
       

        List<BlockChange> changes = history.removeLatestTick();

        if(changes == null){
            return false;
        }
server.execute(() -> {
        recorder.pauseRecording();

        try{
            for(int i=changes.size() -1 ; i>=0;i--){
                BlockChange change = changes.get(i);
                BlockPos position = change.getPosition(); 
            
            serverLevel.setBlock(position, change.getOldState(), 3,512);
        } 
    } finally {
        recorder.resumeRecording();
    }
});
    return true;
    }

}
