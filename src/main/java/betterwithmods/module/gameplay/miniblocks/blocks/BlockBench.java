package betterwithmods.module.gameplay.miniblocks.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Collection;
import java.util.function.Function;

public class BlockBench extends BlockFurniture {

    public BlockBench(Material material, Function<Material, Collection<IBlockState>> subtypes) {
        super(material, subtypes);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    }

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return world.getBlockState(pos.offset(facing)).getBlock() instanceof BlockBench;
    }

    @Override
    public boolean canSit() {
        return true;
    }

    @Override
    public double getOffset() {
        return 0;
    }
}
