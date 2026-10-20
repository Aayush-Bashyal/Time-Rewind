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
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;



public class TimeRewindClient implements ClientModInitializer {

public static final PlayerHistory PLAYER_HISTORY = new PlayerHistory();

public static final RewindManager REWIND_MANAGER = new RewindManager(PLAYER_HISTORY);

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
	REWIND_MANAGER.rewindOneTick(client);
});

ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
	PLAYER_HISTORY.clear();
	REWIND_MANAGER.reset();
});

	}
}