package betterwithmods.common.blocks;

import betterwithmods.common.blocks.tile.TileEntityLathe;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLathe extends BWMBlock {
    public BlockLathe() {
        super(Material.IRON);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityLathe();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
}
