package betterwithmods.module.compat.jei.wrapper;

import betterwithmods.api.recipe.IOutput;
import betterwithmods.common.registry.bulk.recipes.BulkRecipe;
import com.google.common.collect.Lists;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class BulkRecipeWrapper<T extends BulkRecipe> implements IRecipeWrapper {

    protected final T recipe;
    private final IJeiHelpers helpers;

    public BulkRecipeWrapper(IJeiHelpers helpers, @Nonnull T recipe) {
        this.recipe = recipe;
        this.helpers = helpers;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, helpers.getStackHelper().expandRecipeItemStackInputs(recipe.getInputs()));
        ingredients.setOutputLists(IOutput.class, Lists.newArrayList(recipe.getRecipeOutput().getExpandedOutputs()));
    }

    @Nonnull
    public T getRecipe() {
        return recipe;
    }


}
