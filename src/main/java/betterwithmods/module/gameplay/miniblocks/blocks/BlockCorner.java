package betterwithmods.module.gameplay.miniblocks.blocks;

import betterwithmods.module.gameplay.miniblocks.tiles.TileCorner;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCorner extends BlockMini {
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCorner();
    }
}
