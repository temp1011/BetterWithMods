package betterwithmods.module.compat.jei.wrapper;

import betterwithmods.common.registry.block.recipe.BlockRecipe;
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
public class BlockRecipeWrapper<T extends BlockRecipe> implements IRecipeWrapper {
    public final T recipe;
    protected final IJeiHelpers helpers;

    public BlockRecipeWrapper(IJeiHelpers helpers, T recipe) {
        this.helpers = helpers;
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, helpers.getStackHelper().toItemStackList(recipe.getInput()));
        ingredients.setOutputLists(ItemStack.class, recipe.getRecipeOutput().getDisplayOutputs());
    }

    public T getRecipe() {
        return recipe;
    }
}
