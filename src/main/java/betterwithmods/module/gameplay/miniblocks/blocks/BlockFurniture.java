package betterwithmods.module.gameplay.miniblocks.blocks;

import betterwithmods.common.blocks.camo.BlockCamo;
import betterwithmods.common.blocks.camo.CamoInfo;
import betterwithmods.common.blocks.camo.TileCamo;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Function;

public abstract class BlockFurniture extends BlockCamo {

    private static final PropertyBool SUPPORTED = PropertyBool.create("supported");

    public BlockFurniture(Material material, Function<Material, Collection<IBlockState>> subtypes) {
        super(material, subtypes);
        setDefaultState(getDefaultState().withProperty(SUPPORTED,true));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCamo();
    }

    @Override
    public IBlockState fromTile(IExtendedBlockState state, TileCamo tile) {
        return state.withProperty(CAMO_INFO,new CamoInfo(tile));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{SUPPORTED}, new IUnlistedProperty[]{CAMO_INFO});
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.CENTER : (face == EnumFacing.UP ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        boolean connected = isConnected(state, world, pos, EnumFacing.NORTH) || isConnected(state, world, pos, EnumFacing.WEST);
        return state.withProperty(SUPPORTED, !connected);
    }

    private boolean isConnected(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        EnumFacing opp = facing.getOpposite();
        IBlockState state1 = world.getBlockState(pos.offset(facing));
        IBlockState state2 = world.getBlockState(pos.offset(opp));
        return state1.getBlock().canBeConnectedTo(world, pos.offset(facing), opp) && state2.getBlock().canBeConnectedTo(world, pos.offset(opp), facing);
    }

    @Override
    public abstract boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing);
//        return world.getBlockState(pos.offset(facing)).getBlock() instanceof BlockFurniture;
//    }
}
