package net.bikash.timerewind.rewind.world;

public class ServerBlockChangeRecorder {
    
    private static final WorldHistory HISTORY = new WorldHistory();
    private static final BlockChangeRecorder RECORDER = new BlockChangeRecorder(HISTORY);

    public static BlockChangeRecorder getRecorder() {
        return RECORDER;
    }

    public static WorldHistory getHistory(){
        return HISTORY;
    }
}
