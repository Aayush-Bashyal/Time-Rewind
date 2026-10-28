package net.bikash.timerewind.mixin;

import java.lang.annotation.Inherited;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.bikash.timerewind.rewind.world.ItemPickupRecorder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;

import net.minecraft.world.item.ItemStack;
import net.bikash.timerewind.rewind.world.ItemPickupChange;


@Mixin (ItemEntity.class)
public class ItemPickupMixin {
    
    private  ItemStack timeRewind$stackBeforePickup;

    @Inject(method = "playerTouch", at = @At("HEAD"))
    private void timeRewind$beforePickup(Player player, CallbackInfo ci){

        ItemEntity itemEntity = (ItemEntity) (Object) this;

        if(itemEntity.level().isClientSide()){
            return;
        }

        timeRewind$stackBeforePickup = itemEntity.getItem().copy();
    }

    @Inject (method="playerTouch", at = @At("RETURN"))
    private void timeRewind$afterPickup(Player player, CallbackInfo ci){
        ItemEntity itemEntity = (ItemEntity) (Object) this;

        if(itemEntity.level().isClientSide() || timeRewind$stackBeforePickup == null){
            return;
        }

        ItemStack stackAfter = itemEntity.getItem();

        int pickedUpCount = timeRewind$stackBeforePickup.getCount() - (stackAfter.isEmpty()? 0 : stackAfter.getCount());

        if(pickedUpCount > 0){
            ItemStack pickedUp = timeRewind$stackBeforePickup.copy();
            pickedUp.setCount(pickedUpCount);

            ItemPickupRecorder recorder = net.bikash.timerewind.client.TimeRewindClient.ITEM_PICKUP_RECORDER;

            recorder.recordPickup(
                new ItemPickupChange(
                    player.getUUID(),
                    pickedUp,
                    itemEntity.getUUID()
                )
            );
        }
        timeRewind$stackBeforePickup = null;
    }
}
