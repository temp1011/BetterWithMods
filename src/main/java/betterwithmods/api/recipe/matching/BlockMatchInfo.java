package betterwithmods.api.recipe.matching;

import betterwithmods.api.recipe.IMatchInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMatchInfo implements IMatchInfo {
    private World world;
    private BlockPos pos;
    private IBlockState state;

    public BlockMatchInfo(World world, BlockPos pos, IBlockState state) {
        this.world = world;
        this.pos = pos;
        this.state = state;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public IBlockState getState() {
        return state;
    }
}
