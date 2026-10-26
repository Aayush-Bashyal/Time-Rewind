package net.bikash.timerewind.client.mixin;


import net.bikash.timerewind.rewind.world.BlockChangeRecorder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Level.class)
public class BlockChangeMixin {
    

    @Inject (
        method = "setBlock",
        at = @At("HEAD")
    )
    private void timeRewind$beforeSetBlock(
BlockPos pos,
BlockState newState,
int flags,
int updateLimit,
CallbackInfoReturnable<Boolean> cir
    ){

BlockChangeRecorder recorder = net.bikash.timerewind.client.TimeRewindClient.BLOCK_CHANGE_RECORDER;

if(recorder != null){
    Level level = (Level) (Object) this;

    if(level.isClientSide()){
        BlockState oldState = level.getBlockState(pos);

        if(oldState != newState){
            recorder.recordChange(new net.bikash.timerewind.rewind.world.BlockChange(pos.immutable(), oldState, newState));
        }
    }
}

    }
}
