package net.bikash.timerewind.client;

import net.bikash.timerewind.rewind.PlayerHistory;
import net.bikash.timerewind.rewind.RewindConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.client.KeyMapping;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import com.mojang.blaze3d.platform.InputConstants;



public class TimeRewindClient implements ClientModInitializer {

public static final PlayerHistory PLAYER_HISTORY = new PlayerHistory();
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

	if(client.player != null){

		PLAYER_HISTORY.record(client.player);
	
	}

	if(rewindKey.consumeClick()){
		System.out.println("Time Rewind key pressed");
	}
});

	}
}