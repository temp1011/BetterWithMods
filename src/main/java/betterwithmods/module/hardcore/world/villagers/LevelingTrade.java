package betterwithmods.module.hardcore.world.villagers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.MerchantRecipe;

public class LevelingTrade extends MerchantRecipe {
    public boolean levels;
    public LevelingTrade(NBTTagCompound tagCompound) {
        super(tagCompound);
    }

    public LevelingTrade(ItemStack buy1, ItemStack buy2, ItemStack sell) {
        super(buy1, buy2, sell);
    }

    public LevelingTrade(ItemStack buy1, ItemStack buy2, ItemStack sell, int toolUsesIn, int maxTradeUsesIn) {
        super(buy1, buy2, sell, toolUsesIn, maxTradeUsesIn);
    }

    public LevelingTrade(ItemStack buy1, ItemStack sell) {
        super(buy1, sell);
    }

    public LevelingTrade(ItemStack buy1, Item sellItem) {
        super(buy1, sellItem);
    }

    public boolean levels() {
        return levels;
    }

    public void setLevels(boolean levels) {
        this.levels = levels;
    }
}
