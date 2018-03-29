package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.common.blocks.tile.TileBasic;
import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class TileMini extends TileBasic {

    public ItemStack texture;
    public BaseOrientation orientation;

    public TileMini() { }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound tag = super.writeToNBT(compound);
        if (texture != null)
            tag.setTag("texture", texture.serializeNBT());
        if (orientation != null)
            tag.setInteger("orientation", orientation.ordinal());
        return tag;
    }

    public abstract BaseOrientation deserializeOrientation(NBTTagCompound tag);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("texture"))
            texture = new ItemStack((NBTTagCompound) compound.getTag("texture"));
        orientation = deserializeOrientation(compound);
        super.readFromNBT(compound);
    }

    @Override
    public void onPlacedBy(EntityLivingBase placer, @Nullable EnumFacing face, ItemStack stack, float hitX, float hitY, float hitZ) {
        loadFromStack(stack);
        orientation = getOrientationFromPlacement(placer, face, stack, hitX, hitY, hitZ);
    }

    public abstract BaseOrientation getOrientationFromPlacement(EntityLivingBase placer, @Nullable EnumFacing face, ItemStack stack, float hitX, float hitY, float hitZ);

    public void loadFromStack(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        texture = new ItemStack(tag.getCompoundTag("texture"));
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

    public ItemStack getTexture() {
        return texture;
    }

    public BaseOrientation getOrientation() {
        return orientation;
    }
}
