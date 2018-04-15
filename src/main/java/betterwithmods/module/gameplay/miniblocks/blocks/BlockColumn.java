package betterwithmods.module.gameplay.miniblocks.blocks;

import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import betterwithmods.module.gameplay.miniblocks.orientations.ColumnOrientation;
import betterwithmods.module.gameplay.miniblocks.tiles.TileMini;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Function;

public class BlockColumn extends BlockMini {


    public BlockColumn(Material material, Function<Material, Collection<IBlockState>> subtypes) {
        super(material, subtypes);
    }

    @Override
    public BaseOrientation getOrientationFromPlacement(EntityLivingBase placer, @Nullable EnumFacing face, ItemStack stack, float hitX, float hitY, float hitZ) {
        if (face != null)
            return ColumnOrientation.getFromVec(new Vec3d(hitX, hitY, hitZ), face);
        return BaseOrientation.DEFAULT;
    }

    @Override
    public boolean rotates() {
        return false;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileColumn();
    }


    public static class TileColumn extends TileMini {
        @Override
        public BaseOrientation deserializeOrientation(int ordinal) {
            return ColumnOrientation.VALUES[ordinal];
        }
    }

}


