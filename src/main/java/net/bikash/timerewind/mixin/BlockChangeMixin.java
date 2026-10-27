package net.bikash.timerewind.mixin;

import java.util.ArrayDeque;
import java.util.Deque;

import net.bikash.timerewind.rewind.world.ServerBlockChangeRecorder;
import net.bikash.timerewind.client.TimeRewindClient;
import net.bikash.timerewind.rewind.world.BlockChange;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Level.class)
public class BlockChangeMixin {
    

       private static final ThreadLocal<Deque<BlockSnapshot>> SNAPSHOTS =
        ThreadLocal.withInitial(ArrayDeque::new);

    @Inject (
        method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
        at = @At("HEAD")
    )
    private void timeRewind$beforeSetBlock(
BlockPos pos,
BlockState newState,
int flags,
int updateLimit,
CallbackInfoReturnable<Boolean> cir
    ){






    Level level = (Level) (Object) this;

    if (level.isClientSide()) {
    return;
}

   
    if(!ServerBlockChangeRecorder.getRecorder().isRecordingEnabled()){
        return;
    }

    BlockState oldsState = level.getBlockState(pos);

    SNAPSHOTS.get().push(new BlockSnapshot(pos.immutable(),oldsState));


    }

    @Inject (
    method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
    at = @At("RETURN")
)
private void timeRewind$afterSetBlock(
    BlockPos pos,
    BlockState newState,
    int flags,
    int updateLimit,
    CallbackInfoReturnable<Boolean> cir
){

Level level = (Level) (Object) this;

if (level.isClientSide()) {
    return;
}

Deque<BlockSnapshot> snapshots = SNAPSHOTS.get();

if(snapshots.isEmpty()){
    return;
}

BlockSnapshot snapshot = snapshots.pop();

if(snapshots.isEmpty()){
SNAPSHOTS.remove();
}

if(!TimeRewindClient.BLOCK_CHANGE_RECORDER.isRecordingEnabled()){
    return;
}

if(!cir.getReturnValueZ()){
    return;
}

BlockState actualState = level.getBlockState(pos);

if(!snapshot.oldState().equals(actualState)){

    TimeRewindClient.BLOCK_CHANGE_RECORDER.recordChange(new BlockChange(snapshot.position(), snapshot.oldState(), actualState));
}

}

  private record BlockSnapshot(
        BlockPos position,
        BlockState oldState
    ) {
    }
}
