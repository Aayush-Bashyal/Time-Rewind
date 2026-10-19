package net.bikash.timerewind.client;

import net.bikash.timerewind.rewind.PlayerHistory;
import net.bikash.timerewind.rewind.RewindConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;


public class TimeRewindClient implements ClientModInitializer {

public static final PlayerHistory PLAYER_HISTORY = new PlayerHistory();

	@Override
	public void onInitializeClient() {
		RewindConfig.load();
		
ClientTickEvents.END_CLIENT_TICK.register(client -> {

	if(client.player != null){

		PLAYER_HISTORY.record(client.player);
	
	}
});

	}
}