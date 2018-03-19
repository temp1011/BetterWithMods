package betterwithmods.module.compat.jei.wrapper;

import betterwithmods.common.registry.blockmeta.recipe.TurntableRecipe;
import mezz.jei.api.IJeiHelpers;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 11/11/16
 */
public class TurntableRecipeWrapper extends BlockRecipeWrapper {
    public TurntableRecipeWrapper(IJeiHelpers helpers, TurntableRecipe recipe) {
        super(helpers, recipe);
    }
}
