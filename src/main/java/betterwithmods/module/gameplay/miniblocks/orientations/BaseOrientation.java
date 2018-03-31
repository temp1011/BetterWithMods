package betterwithmods.module.gameplay.miniblocks.orientations;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.model.TRSRTransformation;

public interface BaseOrientation extends IStringSerializable {
    BaseOrientation DEFAULT = new BaseOrientation() {
        @Override
        public TRSRTransformation toTransformation() {
            return new TRSRTransformation(EnumFacing.UP);
        }

        @Override
        public String getName() {
            return "default";
        }
    };


    default int ordinal() {
        return 0;
    }

    default AxisAlignedBB getBounds() {
        return Block.FULL_BLOCK_AABB;
    }

    TRSRTransformation toTransformation();

    default BaseOrientation next() {
        return DEFAULT;
    }

}
