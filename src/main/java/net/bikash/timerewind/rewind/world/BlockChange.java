package net.bikash.timerewind.rewind.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class BlockChange {

    private final BlockPos position;
    private final BlockState oldState;
    private final BlockState newState;
    private final  ResourceKey<Level> dimension;

public BlockChange(BlockPos position, BlockState oldState, BlockState newState, ResourceKey<Level> dimension){
    this.position = position;
    this.oldState = oldState;
    this.newState = newState;
    this.dimension = dimension;
    
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

    public ResourceKey<Level> getDimension() {
    return dimension;
}
}
