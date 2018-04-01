package betterwithmods.module.gameplay.miniblocks.tiles;

import betterwithmods.common.blocks.tile.TileBasic;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMini;
import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class TileMini extends TileBasic {

    public IBlockState state;
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

        if (state != null) {
            NBTTagCompound texture = new NBTTagCompound();
            NBTUtil.writeBlockState(texture, state);
            tag.setTag("texture", texture);
        }
        if (orientation != null)
            tag.setInteger("orientation", orientation.ordinal());
        return tag;
    }

    public abstract BaseOrientation deserializeOrientation(NBTTagCompound tag);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("texture"))
            state = NBTUtil.readBlockState(compound.getCompoundTag("texture"));
        orientation = deserializeOrientation(compound);
        super.readFromNBT(compound);
    }

    @Override
    public void onPlacedBy(EntityLivingBase placer, @Nullable EnumFacing face, ItemStack stack, float hitX, float hitY, float hitZ) {
        loadFromStack(stack);
        if (getBlockType() instanceof BlockMini)
            orientation = ((BlockMini) getBlockType()).getOrientationFromPlacement(placer, face, stack, hitX, hitY, hitZ);
    }

    public void loadFromStack(ItemStack stack) {
        NBTTagCompound tag = stack.getSubCompound("texture");
        if (tag != null) {
            state = NBTUtil.readBlockState(tag);
        }
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

    public void markBlockForRenderUpdate() {
        world.markBlockRangeForRenderUpdate(pos, pos);
    }

    public void markBlockForUpdate() {
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    public BaseOrientation getOrientation() {
        return orientation;
    }

    public IBlockState getState() {
        return state;
    }

    public ItemStack getPickBlock(EntityPlayer player, RayTraceResult target, IBlockState state) {
        if (this.state != null) {
            ItemStack stack = new ItemStack(state.getBlock());
            NBTTagCompound tag = new NBTTagCompound();
            NBTTagCompound texture = new NBTTagCompound();
            NBTUtil.writeBlockState(texture, this.state);
            tag.setTag("texture", texture);
            stack.setTagCompound(tag);
        }
        return ItemStack.EMPTY;
    }

}
