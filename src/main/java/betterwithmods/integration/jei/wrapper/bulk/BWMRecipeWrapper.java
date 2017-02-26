package betterwithmods.integration.jei.wrapper.bulk;

import betterwithmods.craft.bulk.BulkRecipe;
import mezz.jei.api.recipe.BlankRecipeWrapper;

import javax.annotation.Nonnull;

public abstract class BWMRecipeWrapper extends BlankRecipeWrapper {
    @Nonnull
    protected final BulkRecipe recipe;

    public BWMRecipeWrapper(@Nonnull BulkRecipe recipe) {
        this.recipe = recipe;
    }

    @Nonnull
    public BulkRecipe getRecipe() {
        return recipe;
    }
}
