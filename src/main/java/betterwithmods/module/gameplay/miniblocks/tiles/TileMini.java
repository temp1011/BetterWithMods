package betterwithmods.module.gameplay.miniblocks.tiles;

import betterwithmods.common.blocks.camo.TileCamo;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMini;
import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TileMini extends TileCamo {

    public BaseOrientation orientation;

    public TileMini() {
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound tag = super.writeToNBT(compound);
        if (orientation != null) {
            tag.setInteger("orientation", orientation.ordinal());
        } else {
            world.setBlockToAir(pos);
        }
        return tag;
    }

    public BaseOrientation deserializeOrientation(NBTTagCompound tag) {
        int o = tag.getInteger("orientation");
        if (getBlockType() instanceof BlockMini) {
            return ((BlockMini) getBlockType()).deserializeOrientation(o);
        }
        return BaseOrientation.DEFAULT;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        orientation = deserializeOrientation(compound);
        super.readFromNBT(compound);
    }

    @Override
    public void onPlacedBy(EntityLivingBase placer, @Nullable EnumFacing face, ItemStack stack, float hitX, float hitY, float hitZ) {
        super.onPlacedBy(placer, face, stack, hitX, hitY, hitZ);
        if (getBlockType() instanceof BlockMini)
            orientation = ((BlockMini) getBlockType()).getOrientationFromPlacement(placer, face, stack, hitX, hitY, hitZ);
    }

    public boolean changeOrientation(BaseOrientation newOrientation, boolean simulate) {
        if (orientation != newOrientation) {
            if (!simulate) {
                orientation = newOrientation;
                markBlockForUpdate();
                getWorld().notifyNeighborsRespectDebug(pos, getBlockType(), true);
            }
            return true;
        } else {
            return false;
        }
    }

    public BaseOrientation getOrientation() {
        if (orientation == null)
            return BaseOrientation.DEFAULT;
        return orientation;
    }

}
