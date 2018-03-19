package betterwithmods.module.compat.jei.wrapper;

import betterwithmods.common.registry.blockmeta.recipe.BlockRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 11/11/16
 */
public class BlockRecipeWrapper implements IRecipeWrapper {
    public final BlockRecipe recipe;
    private final IJeiHelpers helpers;

    public BlockRecipeWrapper(IJeiHelpers helpers, BlockRecipe recipe) {
        this.helpers = helpers;
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(@Nonnull  IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, helpers.getStackHelper().toItemStackList(recipe.getInput()));
        ingredients.setOutputs(ItemStack.class, recipe.getOutputs());
    }
}
