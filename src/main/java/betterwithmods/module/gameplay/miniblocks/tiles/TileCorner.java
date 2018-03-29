package betterwithmods.module.gameplay.miniblocks.tiles;

import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import betterwithmods.module.gameplay.miniblocks.orientations.SidingOrientation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

public class TileCorner extends TileMini {
    @Override
    public BaseOrientation deserializeOrientation(NBTTagCompound tag) {
        int o = tag.getInteger("orientation");
        return SidingOrientation.VALUES[o];
    }

    @Override
    public BaseOrientation getOrientationFromPlacement(EntityLivingBase placer, @Nullable EnumFacing face, ItemStack stack, float hitX, float hitY, float hitZ) {
        if (face != null)
            return SidingOrientation.VALUES[face.getIndex()];
        return BaseOrientation.DEFAULT;
    }
}
