package betterwithmods.module.gameplay.miniblocks.blocks;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.function.Function;

public class BlockTable extends BlockFurniture {

    private static final AxisAlignedBB TABLE_TOP = new AxisAlignedBB(0, 14d / 16d, 0, 1, 1, 1);
    private static final ImmutableList<AxisAlignedBB> TABLE = ImmutableList.of(new AxisAlignedBB(0, 14 / 16d, 0, 1, 1, 1), new AxisAlignedBB(6 / 16d, 0, 6 / 16d, 10 / 16d, 14 / 16d, 10 / 16d));

    public BlockTable(Material material, Function<Material, Collection<IBlockState>> subtypes) {
        super(material, subtypes);
        setDefaultState(getDefaultState().withProperty(SUPPORTED, true));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return TABLE_TOP;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return TABLE_TOP.offset(pos);
    }

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return world.getBlockState(pos.offset(facing)).getBlock() instanceof BlockTable;
    }

    @Override
    public ImmutableList<AxisAlignedBB> getBoxes() {
        return TABLE;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP || super.isSideSolid(base_state, world, pos, side);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.UP) {
            return BlockFaceShape.SOLID;
        }
        return super.getBlockFaceShape(worldIn, state, pos, face);
    }
}
