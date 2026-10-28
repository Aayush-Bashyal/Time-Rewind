package net.bikash.timerewind.rewind.world;

public class ServerBlockChangeRecorder {
    
    private static final WorldHistory HISTORY = new WorldHistory();
    private static final BlockChangeRecorder RECORDER = new BlockChangeRecorder(HISTORY);
private static final ItemDropHistory ITEM_DROP_HISTORY = new ItemDropHistory();
private static final ItemPickupRecorder ITEM_PICKUP_RECORDER = new ItemPickupRecorder();

public static ItemDropHistory getItemDropHistory() {
    return ITEM_DROP_HISTORY;
}

public static ItemPickupRecorder getItemPickupRecorder() {
    return ITEM_PICKUP_RECORDER;
}

    public static BlockChangeRecorder getRecorder() {
        return RECORDER;
    }

    public static WorldHistory getHistory(){
        return HISTORY;
    }
}
