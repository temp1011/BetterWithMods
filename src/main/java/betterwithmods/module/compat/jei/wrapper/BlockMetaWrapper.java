package betterwithmods.module.compat.jei.wrapper;

import betterwithmods.common.registry.blockmeta.recipe.BlockRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 11/11/16
 */
public class BlockMetaWrapper implements IRecipeWrapper {
    public final BlockRecipe recipe;

    public BlockMetaWrapper(BlockRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(@Nonnull  IIngredients ingredients) {
        ingredients.setInput(Ingredient.class, recipe.getInput());
        ingredients.setOutputs(ItemStack.class, recipe.getOutputs());
    }
}
