package net.bikash.timerewind.rewind.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockChange {

    private final BlockPos position;
    private final BlockState oldState;
    private final BlockState newState;

public BlockChange(BlockPos position, BlockState oldState, BlockState newState){
    this.position = position;
    this.oldState = oldState;
    this.newState = newState;

}
    

    public BlockPos getPosition(){
        return position;
    }

    public BlockState getOldState(){
        return oldState;
    }

    public BlockState getNewState(){
        return newState;
    }
}
