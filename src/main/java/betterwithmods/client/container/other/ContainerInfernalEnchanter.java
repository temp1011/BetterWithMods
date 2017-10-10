package betterwithmods.client.container.other;

import betterwithmods.common.blocks.tile.TileEntityInfernalEnchanter;
import betterwithmods.common.items.ItemArcaneScroll;
import betterwithmods.util.InvUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Arrays;

/**
 * Created by tyler on 9/11/16.
 */
public class ContainerInfernalEnchanter extends Container {
    public int[] enchantLevels;
    public int xpSeed;
    private TileEntityInfernalEnchanter tile;
    private ItemStackHandler handler;

    public ContainerInfernalEnchanter(EntityPlayer player, TileEntityInfernalEnchanter tile) {
        this.tile = tile;
        this.enchantLevels = new int[5];
        handler = new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                onContextChanged(this);
            }
        };
        this.xpSeed = player.getXPSeed();
        IItemHandler playerInv = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        addSlotToContainer(new SlotItemHandler(handler, 0, 17, 37));
        addSlotToContainer(new SlotItemHandler(handler, 1, 17, 75));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new SlotItemHandler(playerInv, j + i * 9 + 9, 8 + j * 18, 129 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new SlotItemHandler(playerInv, i, 8 + i * 18, 187));
        }

    }


    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        if (id > 0 && id < 3) {
            enchantLevels[id] = data;
        } else if (id == 3) {
            xpSeed = data;
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : this.listeners) {
            this.broadcastData(listener);
        }
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        broadcastData(listener);
    }

    public void broadcastData(IContainerListener listener) {
        for (int i = 0; i < this.enchantLevels.length; i++) {
            listener.sendWindowProperty(this, 0, this.enchantLevels[i]);
        }
        listener.sendWindowProperty(this, 3, this.xpSeed & -16);
    }

    public boolean areValidItems(ItemStack scroll, ItemStack item) {
        if (!scroll.isEmpty() && !item.isEmpty()) {
            Enchantment enchantment = ItemArcaneScroll.getEnchantment(scroll);
            if (enchantment != null && enchantment.canApply(item)) {
                return true;
            }
        }
        return false;
    }

    public void onContextChanged(IItemHandler handler) {

        ItemStack scroll = handler.getStackInSlot(0);
        ItemStack item = handler.getStackInSlot(1);

        Enchantment enchantment = null;
        Arrays.fill(enchantLevels, 0);
        int enchantCount = 1;
        int maxBookcase = tile.getBookcaseCount();
        if (areValidItems(scroll, item)) {
            enchantment = ItemArcaneScroll.getEnchantment(scroll);
            enchantCount = EnchantmentHelper.getEnchantments(item).size() + 1;
            //1,2,3,4
            //8,15,23,30
//            System.out.println(enchantment.getTranslatedName(-1) + "," + enchantCount + "," + maxBookcase + "," + enchantment.getMaxLevel());
        }
        for (int i = 1; i <= enchantLevels.length; i++) {
            if (enchantment == null || i > enchantment.getMaxLevel())
                enchantLevels[i - 1] = 0;
            else
                enchantLevels[i - 1] = (int) Math.ceil(30 / Math.min(enchantLevels.length, enchantment.getMaxLevel())) * i * enchantCount;
        }
        detectAndSendChanges();
    }

    public static final int INV_FIRST = 0, INV_LAST = 1, HOT_LAST = 37;

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(index > INV_LAST) {
                if(itemstack1.getItem() instanceof ItemArcaneScroll) {
                    if(!mergeItemStack(itemstack1, 0,1,true))
                        return ItemStack.EMPTY;
                } else {
                    if(!mergeItemStack(itemstack1, 1,2,true))
                        return ItemStack.EMPTY;
                }
            } else {
                if(!mergeItemStack(itemstack1, 2, 37,true))
                    return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);

            if (!stack.isEmpty() && !playerIn.getEntityWorld().isRemote)
                InvUtils.ejectStack(playerIn.getEntityWorld(), playerIn.posX, playerIn.posY, playerIn.posZ, stack);
        }
    }

}
