package net.bikash.timerewind.client;

import net.bikash.timerewind.rewind.PlayerHistory;
import net.bikash.timerewind.rewind.RewindConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.client.KeyMapping;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

import java.util.List;

import com.mojang.blaze3d.platform.InputConstants;
import net.bikash.timerewind.rewind.RewindManager;
import net.bikash.timerewind.rewind.world.BlockChange;
import net.bikash.timerewind.rewind.world.BlockChangeRecorder;
import net.bikash.timerewind.rewind.world.WorldHistory;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.bikash.timerewind.rewind.world.BlockRewindManager;
import net.bikash.timerewind.rewind.world.InventoryHistory;
import net.bikash.timerewind.rewind.world.ItemDropHistory;
import net.bikash.timerewind.rewind.world.ItemPickupChange;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.bikash.timerewind.rewind.world.ServerBlockChangeRecorder;
import net.bikash.timerewind.rewind.world.ItemPickupRecorder;


public class TimeRewindClient implements ClientModInitializer {

public static final PlayerHistory PLAYER_HISTORY = new PlayerHistory();

public static final RewindManager REWIND_MANAGER = new RewindManager(PLAYER_HISTORY);

public static final WorldHistory WORLD_HISTORY = ServerBlockChangeRecorder.getHistory();


public static final BlockChangeRecorder BLOCK_CHANGE_RECORDER = ServerBlockChangeRecorder.getRecorder();

public static final ItemPickupRecorder	 ITEM_PICKUP_RECORDER =  ServerBlockChangeRecorder.getItemPickupRecorder();

public static final ItemDropHistory ITEM_DROP_HISTORY =     ServerBlockChangeRecorder.getItemDropHistory();

public static final InventoryHistory INVENTORY_HISTORY = new InventoryHistory();

public static final BlockRewindManager BLOCK_REWIND_MANAGER = new BlockRewindManager(WORLD_HISTORY, BLOCK_CHANGE_RECORDER,INVENTORY_HISTORY);

private static KeyMapping rewindKey;
public static KeyMapping.Category rewindCategory;
	@Override
	public void onInitializeClient() {
		RewindConfig.load();
		rewindCategory = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("time-rewind", "rewind"));

		rewindKey = KeyMappingHelper.registerKeyMapping( new KeyMapping(
		"key.time-rewind.rewind",
		 InputConstants.Type.KEYBOARD ,
		  82,
		  rewindCategory
		)
		);
		
ClientTickEvents.END_CLIENT_TICK.register(client -> {

	

	if(client.player != null && !REWIND_MANAGER.isRewinding()){

		PLAYER_HISTORY.record(client.player);

		INVENTORY_HISTORY.record(client.player.getInventory());
	
	}

	if(rewindKey.consumeClick() ){
	
			REWIND_MANAGER.beginRewind(client);
				
	}

if (REWIND_MANAGER.isRewinding() && !BLOCK_REWIND_MANAGER.isRewindPending()) {

   BLOCK_REWIND_MANAGER.rewindOneTick(client,()->{
	if(REWIND_MANAGER.isRewinding()){
		REWIND_MANAGER.rewindOneTick(client);
	}
   });
}

	

	


});

ServerTickEvents.END_SERVER_TICK.register(server -> {

	if(server.isSingleplayer() && !REWIND_MANAGER.isRewinding()){

	List<BlockChange> blockChanges =	BLOCK_CHANGE_RECORDER.finishTickAndGetChanges();

		List<ItemPickupChange> pickupChanges = ITEM_PICKUP_RECORDER.finishTick();

		List<java.util.UUID> droppedItemIds = ITEM_DROP_HISTORY.finishTick();

		WORLD_HISTORY.record(
			blockChanges,
			pickupChanges,
			droppedItemIds
		);

		
	}
});

ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
	PLAYER_HISTORY.clear();
	REWIND_MANAGER.reset();
	ITEM_PICKUP_RECORDER.clear();
	ITEM_DROP_HISTORY.clear();
	 INVENTORY_HISTORY.clear();
	BLOCK_CHANGE_RECORDER.clear();
	BLOCK_REWIND_MANAGER.reset();
});

	}
}