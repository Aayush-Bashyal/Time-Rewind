package net.bikash.timerewind.rewind;

public class RewindManager {

    
private boolean rewinding = false;
private int ticksRemaining = 0;

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
}
