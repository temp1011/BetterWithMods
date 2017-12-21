package betterwithmods.module.hardcore.world.villagers;

import betterwithmods.common.items.ItemMaterial;
import com.google.common.collect.Lists;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipeList;

import java.util.List;
import java.util.Random;

public class VillagerTrades {

    public static List<EntityVillager.ITradeList> CLERIC_1 = Lists.newArrayList(
            new ListItemForEmeralds(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.HEMP), new EntityVillager.PriceInfo(-18, -22)),
            new ListItemForEmeralds(new ItemStack(Blocks.RED_MUSHROOM), new EntityVillager.PriceInfo(-14, -16)),
            new ListItemForEmeralds(new ItemStack(Blocks.CACTUS), new EntityVillager.PriceInfo(-35, -62)),
            new ListItemForEmeralds(new ItemStack(Items.FLINT_AND_STEEL), new EntityVillager.PriceInfo(1, 1)),
            new ListItemForEmeralds(new ItemStack(Items.PAINTING), new EntityVillager.PriceInfo(2, 3)),
            new ItemForLevel(new ItemStack(Blocks.ENCHANTING_TABLE))
    );

    public static class ItemForLevel implements EntityVillager.ITradeList {
        public ItemStack input;

        public ItemForLevel(ItemStack input) {
            this.input = input;
        }

        @Override
        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            recipeList.add(new LevelingTrade(input, ItemStack.EMPTY).leveling());
        }
    }

    public static class ListItemForEmeralds implements EntityVillager.ITradeList {
        public ItemStack itemToBuy;

        public EntityVillager.PriceInfo priceInfo;

        public ListItemForEmeralds(ItemStack stack, EntityVillager.PriceInfo priceInfo) {
            this.itemToBuy = stack;
            this.priceInfo = priceInfo;
        }

        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            int i = 1;
            if (this.priceInfo != null) {
                i = this.priceInfo.getPrice(random);
            }
            ItemStack itemstack;
            ItemStack itemstack1;
            if (i < 0) {
                itemstack = new ItemStack(Items.EMERALD);
                itemstack1 = new ItemStack(this.itemToBuy.getItem(), -i, this.itemToBuy.getMetadata());
            } else {
                itemstack = new ItemStack(Items.EMERALD, i, 0);
                itemstack1 = new ItemStack(this.itemToBuy.getItem(), 1, this.itemToBuy.getMetadata());
            }
            recipeList.add(new LevelingTrade(itemstack, itemstack1));
        }
    }


}
