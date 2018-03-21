package betterwithmods.common.blocks.mechanical.mech_machine;

import betterwithmods.BWMod;
import betterwithmods.common.blocks.mechanical.tile.TileEntityPulley;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;

public class BlockPulley extends BlockMechMachine {

    public static final ResourceLocation PULLEY = LootTableList.register(new ResourceLocation(BWMod.MODID, "block/pulley"));

    public BlockPulley() {
        super(Material.WOOD, PULLEY);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityPulley();
    }
}
