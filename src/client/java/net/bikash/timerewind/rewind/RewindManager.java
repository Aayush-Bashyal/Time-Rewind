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

public void startRewind(){
    rewinding = true;
}
    
public void stopRewind(){
    rewinding = false;
}

public int getTicksRemaining(){
    return ticksRemaining;
}

public void setTickRemaining(int ticks){
    ticksRemaining = Math.max(0,ticks);
}

public RewindManager(PlayerHistory history){
    this.history = history;
}

public void beginRewind(Minecraft client){

    if(rewinding || client.player == null || client.level == null){
return;
    }

    int seconds = RewindConfig.getRewindSeconds();

    if(seconds <= 0){
        return;
    }
    int ticks = seconds * 20;

    if(history.getRecordedTicks() <= ticks){
        return;
    }
   totalTicks = ticks;
    ticksRemaining = ticks;
    rewinding = true;
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
        stopRewind();
        ticksRemaining = 0;
        totalTicks = 0;
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
