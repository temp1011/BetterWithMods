package betterwithmods.common.blocks.mechanical.cookingpot;

import betterwithmods.common.blocks.mechanical.tile.TileEntityCrucible;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCrucible extends BlockCookingPot {

    public BlockCrucible() {
        setSoundType(SoundType.GLASS);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityCrucible();
    }
}
