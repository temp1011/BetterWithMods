package betterwithmods.client.container.anvil;

import betterwithmods.blocks.tile.TileEntitySteelAnvil;
import betterwithmods.client.container.SlotCraftingItemHandler;
import betterwithmods.craft.steelanvil.SteelCraftingManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class ContainerSteelCrafting extends Container {
    private static final int INV_FIRST = 17;
    private static final int INV_LAST = 44;
    private static final int HOT_LAST = 53;

    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    private TileEntitySteelAnvil te;
    private IItemHandler handler;

    public ContainerSteelCrafting(InventoryPlayer player, TileEntitySteelAnvil te) {
        this.te = te;
        handler = te.inventory;
        craftMatrix = new InventorySteelCrafting(this, te);
        craftResult = new InventorySteelCraftResult(te);
        this.addSlotToContainer(new SlotCraftingItemHandler(player.player, this.craftMatrix, this.craftResult, 0, 124, 44));
        int wy;
        int ex;

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                this.addSlotToContainer(new Slot(craftMatrix, j + i * 4, 12 + j * 18, 17 + i * 18));
            }
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 102 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 160));
        }

        this.onCraftMatrixChanged(craftMatrix);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory matrix) {
        this.craftResult.setInventorySlotContents(0, SteelCraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, te.getWorld()));
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return te.isUseableByPlayer(player);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 16) {
                if (!this.mergeItemStack(itemstack1, INV_FIRST, HOT_LAST, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= INV_FIRST && index < INV_LAST) {
                if (!this.mergeItemStack(itemstack1, INV_LAST, HOT_LAST, false)) {
                    return null;
                }
            } else if (index >= INV_LAST && index < HOT_LAST) {
                if (!this.mergeItemStack(itemstack1, INV_FIRST, INV_LAST, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, INV_FIRST, HOT_LAST, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}