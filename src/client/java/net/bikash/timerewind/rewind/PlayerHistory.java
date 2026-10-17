package net.bikash.timerewind.rewind;

import java.util.ArrayDeque;
import java.util.Deque;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

public class PlayerHistory {

    private static final int MAX_HISTORY_TICKS = 20*15;

    private final Deque<Snapshot> history = new ArrayDeque<>();

    public void record(LocalPlayer player){
        if(player == null){
            return;
        }

        history.addLast(new Snapshot(
            player.position(),
            player.getYRot(),
            player.getXRot()

        ));

        while(history.size() > MAX_HISTORY_TICKS){
            history.removeFirst();
        }
    }

    public Snapshot getTicksAgo(int tickAgo){

        if(tickAgo < 0 || tickAgo >= history.size()){
            return null;
        }

        int index = history.size() -1 - tickAgo;
        int currentIndex = 0;

        for(Snapshot snapshot : history){
            if(currentIndex == index){
                return snapshot;
            }
            currentIndex++;
        }
        return null;
    }

        public int getRecordedTicks(){
            return history.size();

        }

        public static class Snapshot {
            private final Vec3 position;
            private final float yaw;
            private final float pitch;

            public Snapshot(Vec3 position , float yaw , float pitch){
                this.position = position;
                this.yaw = yaw;
                this.pitch = pitch;
            }

            public Vec3 position(){
                return position;
            }

            public float yaw(){
                return yaw;
            }
            public float pitch(){
                return  pitch;
            }
        }
    }

    

