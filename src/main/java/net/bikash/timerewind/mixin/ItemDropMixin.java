package net.bikash.timerewind.mixin;



import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.bikash.timerewind.rewind.world.ServerBlockChangeRecorder;

@Mixin(ServerLevel.class)
public class ItemDropMixin {
 
    
    @Inject (
        method = "addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",
        at = @At("HEAD")
    )
    private void timeRewind$beforeAddEntity(
        Entity entity,
        CallbackInfoReturnable<Boolean> cir
    ){
        if(entity instanceof ItemEntity itemEntity){

             ServerBlockChangeRecorder.getItemDropHistory()
            .record(itemEntity.getUUID());
            }
        }
    }

