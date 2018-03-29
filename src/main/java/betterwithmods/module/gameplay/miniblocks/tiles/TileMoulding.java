package betterwithmods.module.gameplay.miniblocks.tiles;

import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import betterwithmods.module.gameplay.miniblocks.orientations.MouldingOrientation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class TileMoulding extends TileMini {
    @Override
    public BaseOrientation deserializeOrientation(NBTTagCompound tag) {
        int o = tag.getInteger("orientation");
        return MouldingOrientation.VALUES[o];
    }

    @Override
    public BaseOrientation getOrientationFromPlacement(EntityLivingBase placer, @Nullable EnumFacing face, ItemStack stack, float hitX, float hitY, float hitZ) {
        return MouldingOrientation.getFromVec(new Vec3d(hitX, hitY, hitZ),face);
    }
}
