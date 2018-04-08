package betterwithmods.module.gameplay.miniblocks.blocks;

import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import betterwithmods.module.gameplay.miniblocks.orientations.PedestalOrientation;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Set;

public class BlockPedestals extends BlockMini {

    public BlockPedestals(Material material, Set<IBlockState> subtypes) {
        super(material, subtypes);
    }

    @Override
    public BaseOrientation getOrientationFromPlacement(EntityLivingBase placer, @Nullable EnumFacing face, ItemStack stack, float hitX, float hitY, float hitZ) {
        if (face != null)
            return PedestalOrientation.getFromVec(new Vec3d(hitX, hitY, hitZ), face);
        return BaseOrientation.DEFAULT;
    }

    @Override
    public BaseOrientation deserializeOrientation(int ordinal) {
        return PedestalOrientation.VALUES[ordinal];
    }
}
