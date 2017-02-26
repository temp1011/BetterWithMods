package betterwithmods.blocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by blueyu2 on 11/21/16.
 */
public class TileEntitySteelAnvil extends TileBasicInventory {

    public TileEntitySteelAnvil() {
    }

    public String getName() {
        return "inv.steel_anvil.name";
    }

    @Override
    public SimpleItemStackHandler createItemStackHandler() {
        return new SimpleItemStackHandler(this, true, 17);
    }

    private ItemStack result;

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setStackInSlot(slot, stack);
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.getWorld().getTileEntity(this.getPos()) == this
                && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
    }
}
