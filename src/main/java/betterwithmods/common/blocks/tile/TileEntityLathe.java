package betterwithmods.common.blocks.tile;

import betterwithmods.api.tile.multiblock.TileEntityMultiblock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileEntityLathe extends TileEntityMultiblock implements ITickable {

    @Override
    public boolean hasExternalCapability(BlockPos pos, Capability<?> capability, @Nullable EnumFacing facing) {
        return false;
    }

    @Override
    public <T> T getExternalCapability(BlockPos pos, Capability<?> capability, @Nullable EnumFacing facing) {
        return null;
    }

    @Override
    public void update() {

    }
}
