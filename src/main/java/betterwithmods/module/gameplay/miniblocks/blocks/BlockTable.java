package betterwithmods.module.gameplay.miniblocks.blocks;

import betterwithmods.common.blocks.camo.BlockCamo;
import betterwithmods.common.blocks.camo.TileCamo;
import betterwithmods.common.blocks.mechanical.tile.TileTable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

import javax.annotation.Nullable;
import java.util.Set;

public class BlockTable extends BlockCamo {

    public BlockTable(Material material, Set<IBlockState> subtypes) {
        super(material, subtypes);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileTable();
    }

    @Override
    public IBlockState fromTile(IExtendedBlockState state, TileCamo tile) {
        return null;
    }

//    @Override
//    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
//        if (state.getBlock() instanceof BlockTable) {
//            BlockTable block = (BlockTable) state.getBlock();
//            state = block.getActualState(state, source, pos);
//            if (state.getValue(SUPPORTED))
//                return TABLE_AABB;
//        }
//        return FULL_BLOCK_AABB;
//    }

//
//    @Override
//    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean pass) {
//        addCollisionBoxToList(pos, entityBox, collidingBoxes, TABLE_AABB);
//        if (state.getBlock() instanceof BlockTable) {
//            BlockTable block = (BlockTable) state.getBlock();
//            state = block.getActualState(state, world, pos);
//            if (!state.getValue(SUPPORTED))
//                addCollisionBoxToList(pos, entityBox, collidingBoxes, TABLE_STAND_AABB);
//        }
//    }
//
//    @Override
//    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
//        return side == EnumFacing.UP;
//    }
//
//    @Override
//    public int damageDropped(IBlockState state) {
//        return state.getValue(BlockPlanks.VARIANT).getMetadata();
//    }
//
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return state.getValue(BlockPlanks.VARIANT).getMetadata();
//    }
//
//    @Override
//    public IBlockState getStateFromMeta(int meta) {
//        return this.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.byMetadata(meta));
//    }
//
//    @Override
//    protected BlockStateContainer createBlockState() {
//        return new BlockStateContainer(this, SUPPORTED, BlockPlanks.VARIANT);
//    }
//
//    @Override
//    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
//        return face == EnumFacing.DOWN ? BlockFaceShape.CENTER : (face == EnumFacing.UP ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED);
//    }
//
//    @Override
//    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
//        return world.getBlockState(pos.offset(facing)).getBlock() instanceof BlockTable;
//    }
}
