package betterwithmods.api.tile.multiblock;

import betterwithmods.util.WorldUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiblock<T extends TileEntityMultiblock> {
    String getName();


    IBlockState[][][] getStructure();

    /*
        Give the position offset of the getStructure()[0][0][0] from the controller block.
     */
    BlockPos getOriginOffet(EnumFacing facing);

    default boolean isValidStructure(World world, BlockPos pos, EnumFacing facing) {
        BlockPos offset = getOriginOffet(facing);
        if (offset == null)
            return false;
        BlockPos origin = pos.add(offset);
        IBlockState[][][] structure = getStructure();
        for (int i = 0; i < structure.length; i++) {
            int y = structure.length - i - 1;
            for (int z = 0; z < structure[i].length; z++)
                for (int x = 0; x < structure[i][z].length; x++) {
                    BlockPos placement = facing.getAxis() == EnumFacing.Axis.X ? origin.add(z, y, x) : origin.add(x, y, z);
                    if (!WorldUtils.matches(world.getBlockState(placement), structure[i][z][x])) {
                        return false;
                    }
                }
        }
        return true;
    }

    void createMultiBlock(World world, BlockPos pos, EnumFacing facing);

    void destroyMultiblock(World world, BlockPos pos, EnumFacing facing);
}
