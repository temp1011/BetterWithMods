package betterwithmods.common.registry.block.recipe;

import betterwithmods.api.recipe.IRecipeOutput;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class SawRecipe extends BlockRecipe {

    public SawRecipe(BlockIngredient input, List<ItemStack> outputs) {
        super(input, outputs);
    }

    public SawRecipe(BlockIngredient input, IRecipeOutput recipeOutput) {
        super(input, recipeOutput);
    }
}
