package betterwithmods.integration.jei.wrapper.bulk;

import betterwithmods.craft.bulk.BulkRecipe;
import mezz.jei.api.IJeiHelpers;

import javax.annotation.Nonnull;

public class CrucibleRecipeWrapper extends BulkRecipeWrapper {
    public CrucibleRecipeWrapper(IJeiHelpers helper, @Nonnull BulkRecipe recipe) {
        super(helper, recipe);
    }
}
