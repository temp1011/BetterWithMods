package betterwithmods.module.gameplay.miniblocks.blocks;

import betterwithmods.api.block.IRenderRotationPlacement;
import betterwithmods.client.ClientEventHandler;
import betterwithmods.client.baking.UnlistedPropertyGeneric;
import betterwithmods.common.blocks.camo.BlockCamo;
import betterwithmods.common.blocks.camo.TileCamo;
import betterwithmods.module.gameplay.miniblocks.MiniBlocks;
import betterwithmods.module.gameplay.miniblocks.client.MiniInfo;
import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import betterwithmods.module.gameplay.miniblocks.tiles.TileMini;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import javax.annotation.Nullable;
import java.util.Set;

public abstract class BlockMini extends BlockCamo implements IRenderRotationPlacement {

    public static final IUnlistedProperty<MiniInfo> MINI_INFO = new UnlistedPropertyGeneric<>("mini", MiniInfo.class);

    public BlockMini(Material material, Set<IBlockState> subtypes) {
        super(material, subtypes);
    }


    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof TileMini) {
            TileMini mini = (TileMini) tile;
            return mini.getState().getBlockHardness(worldIn, pos);
        }
        return super.getBlockHardness(blockState, worldIn, pos);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileMini) {
            TileMini mini = (TileMini) tile;
            return mini.getState().getBlock().getExplosionResistance(world, pos, exploder, explosion);
        }
        return super.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (IBlockState state : MiniBlocks.MATERIALS.get(blockMaterial)) {
            items.add(MiniBlocks.fromParent(this, state));
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{MINI_INFO});
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileMini();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public IBlockState fromTile(IExtendedBlockState state, TileCamo tile) {
        return state.withProperty(MINI_INFO, new MiniInfo((TileMini) tile));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        TileEntity tile = source.getTileEntity(pos);
        if (tile instanceof TileMini) {
            TileMini mini = (TileMini) tile;
            if (mini.getOrientation() != null)
                return mini.getOrientation().getBounds();
        }
        return Block.FULL_BLOCK_AABB;
    }


    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileMini) {
            TileMini mini = (TileMini) tile;
            return mini.changeOrientation(mini.getOrientation().next(), false);
        }
        return false;
    }

    @Override
    public RenderFunction getRenderFunction() {
        return ClientEventHandler::renderMiniBlock;
    }

    public abstract BaseOrientation getOrientationFromPlacement(EntityLivingBase placer, @Nullable EnumFacing face, ItemStack stack, float hitX, float hitY, float hitZ);

    @Override
    public AxisAlignedBB getBounds(World world, BlockPos pos, EnumFacing facing, float flX, float flY, float flZ, ItemStack stack, EntityLivingBase placer) {
        return getOrientationFromPlacement(placer, facing, stack, flX, flY, flZ).getBounds();
    }

    @Override
    public IBlockState getRenderState(World world, BlockPos pos, EnumFacing facing, float flX, float flY, float flZ, ItemStack stack, EntityLivingBase placer) {
        return getDefaultState();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof TileMini) {
            TileMini mini = (TileMini) tile;
            return mini.getPickBlock(null, null, state);
        }
        return new ItemStack(this);
    }

    @Override
    public void nextState(World world, BlockPos pos, IBlockState state) {
        rotateBlock(world, pos, null);
    }

    @Override
    public boolean rotates() {
        return true;
    }

    public abstract BaseOrientation deserializeOrientation(int ordinal);

}
