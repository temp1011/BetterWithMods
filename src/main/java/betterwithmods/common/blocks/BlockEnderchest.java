package betterwithmods.common.blocks;

import betterwithmods.common.blocks.tile.TileEnderchest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnderchest extends BlockEnderChest {

    public BlockEnderchest() {
        setUnlocalizedName("enderChest");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.getBlockState(pos).getBlock().equals(Blocks.ENDER_CHEST)) {
            if (!world.isRemote && world.getTileEntity(pos) instanceof TileEnderchest) {
                TileEnderchest tile = (TileEnderchest) world.getTileEntity(pos);
                InventoryEnderChest chest = tile.getType().getFunction().apply(tile, player);
                if (!world.getBlockState(pos.up()).isNormalCube()) {
                    chest.setChestTileEntity(tile);
                    player.displayGUIChest(chest);
                    player.addStat(StatList.ENDERCHEST_OPENED);
                }
            }
        }
        return true;
    }


    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEnderchest();
    }
}
