package betterwithmods.common.registry.bulk.manager;

import betterwithmods.common.registry.bulk.recipes.MillRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.List;

public class MillManager extends CraftingManagerBulk<MillRecipe> {
    public MillRecipe addMillRecipe(List<Ingredient> inputs, List<ItemStack> outputs, int type) {
        return addRecipe(new MillRecipe(inputs, outputs, type));
    }
}
