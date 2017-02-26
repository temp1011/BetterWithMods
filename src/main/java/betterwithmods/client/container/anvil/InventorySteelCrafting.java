package betterwithmods.client.container.anvil;

import betterwithmods.blocks.tile.TileEntitySteelAnvil;
import betterwithmods.craft.steelanvil.SteelCraftingManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;

public class InventorySteelCrafting extends InventoryCrafting implements ISidedInventory {

    public TileEntitySteelAnvil craft;
    private IItemHandler handler;
    public Container container;

    public InventorySteelCrafting(Container container, TileEntitySteelAnvil te) {
        super(container, SteelCraftingManager.WIDTH, SteelCraftingManager.HEIGHT);
        craft = te;
        handler = te.inventory;
        this.container = container;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot >= this.getSizeInventory() ? null : handler.getStackInSlot(slot);
    }

    @Override
    public ItemStack getStackInRowAndColumn(int row, int column) {
        if (row >= 0 && row < SteelCraftingManager.WIDTH) {
            int x = row + column * SteelCraftingManager.HEIGHT;
            return this.getStackInSlot(x);
        } else {
            return null;
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int decrement) {
        ItemStack stack = handler.getStackInSlot(slot);
        this.container.onCraftMatrixChanged(this);
        if (stack != null) {
            ItemStack itemstack;
            if (stack.stackSize <= decrement) {
                itemstack = stack.copy();
                stack = null;
                craft.setInventorySlotContents(slot, null);
                this.container.onCraftMatrixChanged(this);
                return itemstack;
            } else {
                itemstack = stack.splitStack(decrement);
                if (stack.stackSize == 0) {
                    stack = null;
                    craft.setInventorySlotContents(slot, null);
                }
                this.container.onCraftMatrixChanged(this);
                return itemstack;
            }
        } else {
            return null;
        }
    }

    public void craft()
    {
        for (int i = 0; i < handler.getSlots(); i++) {
            if(handler.getStackInSlot(i) != null)
            {
                handler.extractItem(i, 1, false);
            }
        }
        this.container.onCraftMatrixChanged(this);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        craft.setInventorySlotContents(slot, itemstack);
        this.container.onCraftMatrixChanged(this);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
    }
}