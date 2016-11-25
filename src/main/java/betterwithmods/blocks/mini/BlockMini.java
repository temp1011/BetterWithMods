package betterwithmods.blocks.mini;

import betterwithmods.blocks.BWMBlock;
import betterwithmods.util.InvUtils;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class BlockMini extends BWMBlock {
    public static final Material MINI = new Material(MapColor.WOOD);
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 6);
    public static final PropertyInteger ORIENTATION = createOrientation();

    public BlockMini(Material material, String name) {
        super(material);
        //this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, 0).withProperty(ORIENTATION, 0));
        this.setSoundType(material == MINI ? SoundType.WOOD : SoundType.STONE);
        if (material == Material.CIRCUITS)
            this.setHarvestLevel("axe", 0);
    }

    public static PropertyInteger createOrientation() {
        return PropertyInteger.create("orientation", 0, 5);
    }

    public int getMaxOrientation() {
        return 5;
    }

    public boolean rotate(World world,BlockPos pos, IBlockState state,EntityPlayer player,PropertyInteger property) {
        boolean emptyHands = player.getHeldItem(EnumHand.MAIN_HAND) == null && player.getHeldItem(EnumHand.OFF_HAND) == null && player.isSneaking();
        if (world.isRemote && emptyHands)
            return true;
        else if (!world.isRemote && emptyHands) {
            int nextOrient = (state.getValue(property) + 1)%getMaxOrientation()+1;
            world.playSound(null, pos, this.getSoundType(state, world, pos, player).getPlaceSound(), SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
            world.setBlockState(pos, state.withProperty(property, nextOrient));
            world.notifyNeighborsOfStateChange(pos, this);
            world.scheduleBlockUpdate(pos, this, 10, 5);
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {//TODO: Maybe make this try to work with items that don't have a use action?
       return rotate(world,pos,state,player,ORIENTATION);
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
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float flX, float flY, float flZ, int meta, EntityLivingBase placer) {
        return metaBlockPlace(facing, flX, flY, flZ);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityMultiType) {
                int meta = stack.getItemDamage();
                ((TileEntityMultiType) world.getTileEntity(pos)).setCosmeticType(meta);
                world.setBlockState(pos, state.withProperty(TYPE, meta));
        }
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityMultiType) {
            return new ItemStack(this, 1, ((TileEntityMultiType) world.getTileEntity(pos)).getCosmeticType());
        }
        return new ItemStack(this, 1, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < 6; i++) {
            list.add( new ItemStack(this, 1, i));
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);

        stack = new ItemStack(this, 1, state.getValue(TYPE));
        InvUtils.ejectStackWithOffset(world, pos, stack);
    }

    public abstract IBlockState metaBlockPlace(EnumFacing facing, float flX, float flY, float flZ);

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityMultiType();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        int type = 0;
        if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityMultiType)
            type = ((TileEntityMultiType) world.getTileEntity(pos)).getCosmeticType();
        return state.withProperty(TYPE, type);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ORIENTATION, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ORIENTATION);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE, ORIENTATION);
    }
}
