package betterwithmods.integration.jei.wrapper.bulk;

import betterwithmods.craft.bulk.BulkRecipe;
import betterwithmods.integration.jei.wrapper.bulk.BulkRecipeWrapper;
import mezz.jei.api.IJeiHelpers;

import javax.annotation.Nonnull;

public class StokedCauldronRecipeWrapper extends BulkRecipeWrapper {
    public StokedCauldronRecipeWrapper(IJeiHelpers helper, @Nonnull BulkRecipe recipe) {
        super(helper, recipe);
    }
}
