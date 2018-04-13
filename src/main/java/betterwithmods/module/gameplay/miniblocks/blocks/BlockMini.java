package betterwithmods.module.gameplay.miniblocks.blocks;

import betterwithmods.api.block.IRenderRotationPlacement;
import betterwithmods.client.ClientEventHandler;
import betterwithmods.client.baking.UnlistedPropertyGeneric;
import betterwithmods.common.blocks.BlockRotate;
import betterwithmods.module.gameplay.miniblocks.MiniBlocks;
import betterwithmods.module.gameplay.miniblocks.client.MiniCacheInfo;
import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import betterwithmods.module.gameplay.miniblocks.tiles.TileMini;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.stream.Collectors;

public abstract class BlockMini extends BlockRotate implements IRenderRotationPlacement {

    public static final IUnlistedProperty<MiniCacheInfo> MINI_INFO = new UnlistedPropertyGeneric<>("mini", MiniCacheInfo.class);


    public BlockMini(Material material) {
        super(material);
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
        items.addAll(MiniBlocks.MATERIALS.get(blockMaterial).stream().sorted(this::compareBlockStates).map(state -> MiniBlocks.fromParent(this, state)).collect(Collectors.toList()));
    }

    private int compareBlockStates(IBlockState a, IBlockState b) {
        Block blockA = a.getBlock();
        Block blockB = b.getBlock();
        int compare = Integer.compare(Block.getIdFromBlock(blockA),Block.getIdFromBlock(blockB));
        if(compare == 0)
            return Integer.compare(blockA.getMetaFromState(a), blockB.getMetaFromState(b));
        else
            return compare;
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
    public abstract TileEntity createTileEntity(World world, IBlockState state);

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileMini tile = (TileMini) world.getTileEntity(pos);
        IExtendedBlockState extendedBS = (IExtendedBlockState) super.getExtendedState(state, world, pos);
        if (tile != null) {
            return extendedBS.withProperty(MINI_INFO, MiniCacheInfo.from(tile));
        }
        return extendedBS;
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
    public void nextState(World world, BlockPos pos, IBlockState state) {
        rotateBlock(world, pos, EnumFacing.UP);
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
    public final void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        getDrops(drops, world, pos, state, null, fortune, false);
    }



    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.005F);
        int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
        boolean silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0;

        NonNullList<ItemStack> items = NonNullList.create();
        getDrops(items, worldIn, pos, state, te, 0, false);
        float chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, fortuneLevel, 1.0f, silkTouch, player);

        harvesters.set(player);

        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
            for (ItemStack item : items)
                if (chance >= 1.0f || worldIn.rand.nextFloat() <= chance)
                    spawnAsEntity(worldIn, pos, item);
        }

        harvesters.set(null);
    }

    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, @Nullable TileEntity te, int fortune, boolean silkTouch) {
        if (te instanceof TileMini) {
            drops.add(((TileMini) te).getPickBlock(null, null, state));
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }



    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
            NonNullList<ItemStack> drops = NonNullList.create();
            getDrops(drops, worldIn, pos, state, worldIn.getTileEntity(pos), fortune, false);
            chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, fortune, chance, false, harvesters.get());

            for (ItemStack drop : drops) {
                if (worldIn.rand.nextFloat() <= chance) {
                    spawnAsEntity(worldIn, pos, drop);
                }
            }
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileMini) {
            TileMini mini = (TileMini) tile;
            return mini.getPickBlock(player, target, state);
        }
        return new ItemStack(this);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        TileMini tile = (TileMini) world.getTileEntity(pos);
        if (tile != null) {
            return tile.state.getBlock().getFireSpreadSpeed(world,pos,face);
        }
        return 5;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        TileMini tile = (TileMini) world.getTileEntity(pos);
        if (tile != null) {
            return tile.state.getBlock().getFlammability(world,pos,face);
        }
        return 10;
    }
}
