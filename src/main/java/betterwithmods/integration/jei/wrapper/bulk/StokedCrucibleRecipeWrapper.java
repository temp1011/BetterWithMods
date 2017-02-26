package betterwithmods.integration.jei.wrapper.bulk;

import betterwithmods.craft.bulk.BulkRecipe;
import mezz.jei.api.IJeiHelpers;

import javax.annotation.Nonnull;

public class StokedCrucibleRecipeWrapper extends BulkRecipeWrapper {
    public StokedCrucibleRecipeWrapper(IJeiHelpers helper, @Nonnull BulkRecipe recipe) {
        super(helper, recipe);
    }
}
