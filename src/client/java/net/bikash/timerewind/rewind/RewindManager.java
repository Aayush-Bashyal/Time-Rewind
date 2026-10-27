package net.bikash.timerewind.rewind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class RewindManager {

    
private boolean rewinding = false;
private final PlayerHistory history;
private int ticksRemaining = 0;
private int totalTicks = 0;


public boolean isRewinding(){
    return rewinding;
}


    
public void stopRewind(){
   reset();
}

public int getTicksRemaining(){
    return ticksRemaining;
}



public RewindManager(PlayerHistory history){
    this.history = history;
}

public boolean beginRewind(Minecraft client){

    if(rewinding || client.player == null || client.level == null){
return false;
    }

    int seconds = RewindConfig.getRewindSeconds();

    if(seconds <= 0){
        return false;
    }
    int ticks = seconds * 20;

    if(history.getRecordedTicks() <= ticks){
        return false;
    }
   totalTicks = ticks;
    ticksRemaining = ticks;
    rewinding = true;
    return true;
}

public void rewindOneTick(Minecraft client){

    if(!rewinding){
        return;
    }

    if(client.player == null || client.level == null){
        reset();
        return;
    }

    LocalPlayer player = client.player;
    PlayerHistory.Snapshot snapshot = history.getTicksAgo(totalTicks - ticksRemaining + 1);

    

    if(snapshot == null){
        reset();
        return ;
    }
    player.setPos(snapshot.position());
    player.setDeltaMovement(0,0,0);
    player.setYRot(snapshot.yaw());
    player.setXRot(snapshot.pitch());

    ticksRemaining--;

    if(ticksRemaining <= 0){
        history.discardNewest(totalTicks);
      reset();
    }
}

public void reset(){
    rewinding = false;
    ticksRemaining = 0;
    totalTicks = 0;
}

}
