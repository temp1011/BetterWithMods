package betterwithmods.common.blocks;

import betterwithmods.common.BWMBlocks;
import betterwithmods.util.ColorUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import static net.minecraft.util.EnumFacing.UP;

public class BlockCandle extends BWMBlock {

    public BlockCandle() {
        super(Material.GROUND);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ColorUtils.COLOR, EnumDyeColor.WHITE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ColorUtils.COLOR);
    }

    public static ItemStack getStack(EnumDyeColor type) {
        return new ItemStack(BWMBlocks.CANDLE, 1, type.getMetadata());
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.27D * (double) UP.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * (double) UP.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.27D * (double) UP.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * (double) UP.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
    }

    @Override

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumDyeColor color : EnumDyeColor.values())
            items.add(getStack(color));
        super.getSubBlocks(itemIn, items);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ColorUtils.COLOR, EnumDyeColor.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ColorUtils.COLOR).getMetadata();
    }


    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.isSideSolid(pos.down(), UP);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return 15;
    }
}
