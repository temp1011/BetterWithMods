package betterwithmods.common.blocks.mechanical;

import betterwithmods.common.blocks.mechanical.tile.TileWaterwheel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWaterwheel extends BlockAxleGenerator {

    public BlockWaterwheel() {
        super(Material.WOOD);
    }


    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 1;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileWaterwheel();
    }

}
