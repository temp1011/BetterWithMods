package betterwithmods.module.gameplay.miniblocks.blocks;

import betterwithmods.common.blocks.camo.BlockCamo;
import betterwithmods.common.blocks.camo.CamoInfo;
import betterwithmods.common.blocks.camo.TileCamo;
import betterwithmods.common.entity.EntitySitMount;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
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
        setDefaultState(getDefaultState().withProperty(SUPPORTED, true));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCamo();
    }

    @Override
    public IBlockState fromTile(IExtendedBlockState state, TileCamo tile) {
        return state.withProperty(CAMO_INFO, new CamoInfo(tile));
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

    public boolean canSit() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (canSit() && worldIn.getEntitiesWithinAABB(EntitySitMount.class, getBoundingBox(state, worldIn, pos).offset(pos)).isEmpty()) {

            EntitySitMount mount = new EntitySitMount(worldIn, getOffset());
            mount.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            worldIn.spawnEntity(mount);
            playerIn.startRiding(mount);
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }


    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        AxisAlignedBB box = getBoundingBox(state, worldIn, pos);
        worldIn.getEntitiesWithinAABB(EntitySitMount.class, box).forEach(EntitySitMount::dismountRidingEntity);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        AxisAlignedBB box = getBoundingBox(state, world, pos).offset(pos).grow(1);
        world.getEntitiesWithinAABB(EntitySitMount.class, box).forEach(EntitySitMount::dismountRidingEntity);
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    public double getOffset() {
        return 0;
    }
}
