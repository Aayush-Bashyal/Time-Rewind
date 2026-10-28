package net.bikash.timerewind.client;

import net.bikash.timerewind.rewind.PlayerHistory;
import net.bikash.timerewind.rewind.RewindConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.client.KeyMapping;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.bikash.timerewind.rewind.RewindManager;
import net.bikash.timerewind.rewind.world.BlockChangeRecorder;
import net.bikash.timerewind.rewind.world.WorldHistory;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.bikash.timerewind.rewind.world.BlockRewindManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.bikash.timerewind.rewind.world.ServerBlockChangeRecorder;


public class TimeRewindClient implements ClientModInitializer {

public static final PlayerHistory PLAYER_HISTORY = new PlayerHistory();

public static final RewindManager REWIND_MANAGER = new RewindManager(PLAYER_HISTORY);

public static final WorldHistory WORLD_HISTORY = ServerBlockChangeRecorder.getHistory();


public static final BlockChangeRecorder BLOCK_CHANGE_RECORDER = ServerBlockChangeRecorder.getRecorder();

public static final BlockRewindManager BLOCK_REWIND_MANAGER = new BlockRewindManager(WORLD_HISTORY, BLOCK_CHANGE_RECORDER);

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
	
	}

	if(rewindKey.consumeClick() ){
	
			REWIND_MANAGER.beginRewind(client);
				
	}

boolean rewinding = REWIND_MANAGER.isRewinding();


  if (rewinding) {
        BLOCK_REWIND_MANAGER.rewindOneTick(client);
    }

	

	REWIND_MANAGER.rewindOneTick(client);


});

ServerTickEvents.END_SERVER_TICK.register(server -> {
	if(server.isSingleplayer() && !REWIND_MANAGER.isRewinding()){
		BLOCK_CHANGE_RECORDER.finishTick();
	}
});

ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
	PLAYER_HISTORY.clear();
	REWIND_MANAGER.reset();
	BLOCK_CHANGE_RECORDER.clear();
	BLOCK_REWIND_MANAGER.reset();
});

	}
}