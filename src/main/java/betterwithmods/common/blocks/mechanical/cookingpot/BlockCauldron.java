package betterwithmods.common.blocks.mechanical.cookingpot;

import betterwithmods.common.blocks.mechanical.tile.TileCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCauldron extends BlockCookingPot{
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCauldron();
    }
}
