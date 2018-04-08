package betterwithmods.module.gameplay.miniblocks.blocks;

import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import betterwithmods.module.gameplay.miniblocks.orientations.SidingOrientation;
import betterwithmods.module.gameplay.miniblocks.tiles.TileSiding;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class BlockSiding extends BlockMini {

    public BlockSiding(Material material, Set<IBlockState> subtypes) {
        super(material, subtypes);
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileSiding();
    }

    @Override
    public BaseOrientation getOrientationFromPlacement(EntityLivingBase placer, @Nullable EnumFacing facing, ItemStack stack, float hitX, float hitY, float hitZ) {
        if (facing != null)
            return SidingOrientation.getFromVec(new Vec3d(hitX, hitY, hitZ), facing);
        return BaseOrientation.DEFAULT;
    }
}
