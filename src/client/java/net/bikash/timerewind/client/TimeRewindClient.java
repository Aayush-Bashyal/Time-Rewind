package net.bikash.timerewind.client;

import net.bikash.timerewind.rewind.PlayerHistory;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TimeRewindClient implements ClientModInitializer {

public static final PlayerHistory PLAYER_HISTORY = new PlayerHistory();

	@Override
	public void onInitializeClient() {
		
ClientTickEvents.END_CLIENT_TICK.register(client -> {

	if(client.player != null){

		PLAYER_HISTORY.record(client.player);
	
	}
});

	}
}