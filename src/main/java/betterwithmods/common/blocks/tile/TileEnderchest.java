package betterwithmods.common.blocks.tile;

import betterwithmods.module.hardcore.beacons.HCBeacons;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

public class TileEnderchest extends TileEntityEnderChest {

    public enum Type {
        //TODO drop contents if NONE
        NONE((tile, player) -> tile.getCapability(HCBeacons.ENDERCHEST_CAPABILITY, EnumFacing.UP).getInventory()),
        DIMENSION1((tile, player) -> {
            World world = tile.getWorld();
            if (world.hasCapability(HCBeacons.ENDERCHEST_CAPABILITY, EnumFacing.SOUTH)) {
                return world.getCapability(HCBeacons.ENDERCHEST_CAPABILITY, EnumFacing.SOUTH).getInventory();
            }
            return null;
        }),
        DIMENSION2((tile, player) -> tile.getWorld().getCapability(HCBeacons.ENDERCHEST_CAPABILITY, EnumFacing.NORTH).getInventory()),
        GLOBAL((tile, player) -> DimensionManager.getWorld(0).getCapability(HCBeacons.ENDERCHEST_CAPABILITY, EnumFacing.DOWN).getInventory()),
        PRIVATE((tile, player) -> player.getInventoryEnderChest());

        public static Type[] VALUES = values();

        private BiFunction<TileEntity, EntityPlayer, InventoryEnderChest> function;

        Type(BiFunction<TileEntity, EntityPlayer, InventoryEnderChest> function) {
            this.function = function;
        }

        public BiFunction<TileEntity, EntityPlayer, InventoryEnderChest> getFunction() {
            return function;
        }
    }

    @Nonnull
    private Type type = Type.NONE;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("type", type.ordinal());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("type"))
            this.type = Type.VALUES[compound.getInteger("type")];
        super.readFromNBT(compound);
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Nonnull
    public Type getType() {
        return type;
    }



}

