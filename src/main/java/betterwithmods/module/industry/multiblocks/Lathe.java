package betterwithmods.module.industry.multiblocks;

import betterwithmods.api.tile.multiblock.IMultiblock;
import betterwithmods.api.tile.multiblock.TileEntityMultiblock;
import betterwithmods.api.tile.multiblock.TileEntityProxyBlock;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.blocks.BlockSteel;
import betterwithmods.util.WorldUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
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
                new IBlockState[][]{new IBlockState[]{BlockSteel.getBlock(0), null, null}},
                new IBlockState[][]{new IBlockState[]{BWMBlocks.STEEL_GEARBOX.getDefaultState(), BlockSteel.getBlock(8), BlockSteel.getBlock(0)}},
        };
    }

    @Override
    public BlockPos getOriginOffet(EnumFacing facing) {
        if (facing.getAxis().isVertical())
            return null;
        switch (facing) {
            case NORTH:
                return new BlockPos(-1, 0, 0);
            case SOUTH:
                return new BlockPos(1, 0, 0);
            case WEST:
                return new BlockPos(0, 0, -1);
            case EAST:
                return new BlockPos(0, 0, 1);
            default:
                return null;
        }
    }

    @Override
    public void createMultiBlock(World world, BlockPos pos, EnumFacing facing) {
        BlockPos controller = pos;
        BlockPos offset = getOriginOffet(facing);
        if (offset == null)
            return;
        BlockPos origin = pos.add(offset);
        IBlockState[][][] structure = getStructure();
        for (int i = 0; i < structure.length; i++) {
            int y = structure.length - i - 1;
            for (int z = 0; z < structure[i].length; z++)
                for (int x = 0; x < structure[i][z].length; x++) {
                    BlockPos placement = facing.getAxis() == EnumFacing.Axis.X ? origin.add(z, y, x) : origin.add(x, y, z);
                    IBlockState state = world.getBlockState(placement);
                    if (WorldUtils.matches(state, structure[i][z][x])) {
                        if (state.equals(Blocks.AIR.getDefaultState()))
                            continue;
                        if (placement.equals(controller)) {
                            world.setBlockState(controller, BWMBlocks.LATHE.getDefaultState());
                            TileEntity tile = world.getTileEntity(controller);
                            if (tile instanceof TileEntityMultiblock) {
                                ((TileEntityMultiblock) tile).setMultiblock(this);
                                ((TileEntityMultiblock) tile).setFacing(facing);
                            }
                        } else {
                            world.setBlockState(placement, BWMBlocks.DUMMY.getDefaultState());
                            TileEntity tile = world.getTileEntity(placement);
                            if (tile instanceof TileEntityProxyBlock) {
                                ((TileEntityProxyBlock) tile).setController(controller);
                            }

                        }
                    }
                }
        }
    }

    @Override
    public void destroyMultiblock(World world, BlockPos pos, EnumFacing facing) {
        BlockPos offset = getOriginOffet(facing);
        if (offset == null)
            return;
        BlockPos origin = pos.add(offset);
        IBlockState[][][] structure = getStructure();
        for (int i = 0; i < structure.length; i++) {
            int y = structure.length - i - 1;
            for (int z = 0; z < structure[i].length; z++)
                for (int x = 0; x < structure[i][z].length; x++) {
                    BlockPos placement = facing.getAxis() == EnumFacing.Axis.X ? origin.add(z, y, x) : origin.add(x, y, z);
                    if (!world.isAirBlock(placement) && structure[i][z][x] != null)
                        world.setBlockState(placement, structure[i][z][x]);
                }
        }
    }

}
