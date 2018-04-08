package betterwithmods.module.gameplay.miniblocks.blocks;

import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import betterwithmods.module.gameplay.miniblocks.orientations.CornerOrientation;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Set;

public class BlockCorner extends BlockMini {

    public BlockCorner(Material material, Set<IBlockState> subtypes) {
        super(material, subtypes);
    }

    @Override
    public BaseOrientation getOrientationFromPlacement(EntityLivingBase placer, @Nullable EnumFacing facing, ItemStack stack, float hitX, float hitY, float hitZ) {
        if (facing != null)
            return CornerOrientation.getFromVec(new Vec3d(hitX, hitY, hitZ), facing);
        return BaseOrientation.DEFAULT;
    }

    @Override
    public BaseOrientation deserializeOrientation(int ordinal) {
        return CornerOrientation.VALUES[ordinal];
    }


}
