package betterwithmods.testing.base;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MockInventory extends ItemStackHandler {
    public static MockInventory createInventory(int startIndex, ItemStack... array) {
        MockInventory inventory = new MockInventory();

        for (int i = startIndex; i < Math.min(array.length, inventory.getSlots()); i++) {
            inventory.setStackInSlot(startIndex, array[i - startIndex]);
        }
        return inventory;
    }
}
