package betterwithmods.module.industry.multiblocks;

import betterwithmods.api.tile.multiblock.IMultiblock;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.blocks.BlockSteel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Lathe implements IMultiblock {
    @Override
    public String getName() {
        return "Lathe";
    }

    @Override
    public IBlockState[][][] getStructure() {
        return new IBlockState[][][]{
                new IBlockState[][]{new IBlockState[]{Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), BlockSteel.getBlock(0)}},
                new IBlockState[][]{new IBlockState[]{BlockSteel.getBlock(0), BlockSteel.getBlock(8), BWMBlocks.STEEL_GEARBOX.getDefaultState()}},
        };
    }

    @Override
    public BlockPos getOriginOffet(EnumFacing facing) {
        if (facing.getAxis().isVertical())
            return null;
        return facing.getAxis() == EnumFacing.Axis.Z ? new BlockPos(-1, 0, 0) : new BlockPos(0, 0, -1);
    }

    @Override
    public void destroyMultiblock(World world, BlockPos pos, IBlockState blockState, EnumFacing facing) {
//        IBlockState[][][] structure = getStructure();
//        for (int i = 0; i < structure.length; i++) {
//            int y = structure.length - i - 1;
//            for (int z = 0; z < structure[i].length; z++)
//                for (int x = 0; x < structure[i][z].length; x++) {
//                    BlockPos placement = facing.getAxis() == EnumFacing.Axis.X ? origin.add(z, y, x) : origin.add(x, y, z);
//                    world.setBlockState(placement, structure[i][z][x]);
//                }
//        }

    }

}
