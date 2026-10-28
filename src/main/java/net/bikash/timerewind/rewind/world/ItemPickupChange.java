package net.bikash.timerewind.rewind.world;

import java.util.UUID;

import net.minecraft.world.item.ItemStack;

public class ItemPickupChange {
    

    private final UUID playerId;
    private final ItemStack itemStack;
    private final UUID itemEntityId;

    public ItemPickupChange(UUID playerId, ItemStack itemStack, UUID itemEntityId){

        this.playerId = playerId;
        this.itemStack = itemStack.copy();
        this.itemEntityId = itemEntityId;
    }

    public UUID getPlayerId(){
        return playerId;
    }

public ItemStack getItemStack(){
    return itemStack.copy();
}

public UUID getItemEntityId(){
    return itemEntityId;
}

}
