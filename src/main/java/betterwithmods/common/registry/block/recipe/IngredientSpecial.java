package betterwithmods.common.registry.block.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.Predicate;

public class IngredientSpecial extends Ingredient {
    ItemStack[] matchingStacks = new ItemStack[0];
    boolean matchingStacksCached;
    Predicate<ItemStack> matcher;

    public IngredientSpecial(Predicate<ItemStack> matcher) {
        super(0);
        this.matcher = matcher;
    }

    @Override
    public boolean apply(@Nullable ItemStack stack) {
        if (stack == null)
            stack = ItemStack.EMPTY;

        return matcher.test(stack);
    }

    @Override
    public ItemStack[] getMatchingStacks() {
        if (!matchingStacksCached)
            cacheMatchingStacks();
        return matchingStacks;
    }

    public void cacheMatchingStacks() {
        ArrayList<ItemStack> matches = new ArrayList<>();
        for (Item item : ForgeRegistries.ITEMS) {
            ItemStack testItem = new ItemStack(item);
            if (matcher.test(testItem))
                matches.add(testItem);
        }
        matchingStacks = matches.toArray(matchingStacks);
        matchingStacksCached = true;
    }

}

