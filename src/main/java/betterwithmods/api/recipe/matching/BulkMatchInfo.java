package betterwithmods.api.recipe.matching;

import betterwithmods.api.recipe.IMatchInfo;
import net.minecraftforge.items.ItemStackHandler;

public class BulkMatchInfo implements IMatchInfo {
    private ItemStackHandler inventory;

    public BulkMatchInfo(ItemStackHandler inventory) {
        this.inventory = inventory;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }
}
