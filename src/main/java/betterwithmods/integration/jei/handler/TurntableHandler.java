package betterwithmods.integration.jei.handler;

import betterwithmods.craft.TurntableInteraction;
import betterwithmods.integration.jei.wrapper.other.TurntableRecipeWrapper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Purpose:
 *
 * @author Tyler Marshall
 * @version 11/11/16
 */
public class TurntableHandler implements IRecipeHandler<TurntableInteraction.TurntableRecipe> {
    @Override
    public Class<TurntableInteraction.TurntableRecipe> getRecipeClass() {
        return TurntableInteraction.TurntableRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return "bwm.turntable";
    }

    @Override
    public String getRecipeCategoryUid(TurntableInteraction.TurntableRecipe recipe) {
        return getRecipeCategoryUid();
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(TurntableInteraction.TurntableRecipe recipe) {
        return new TurntableRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(TurntableInteraction.TurntableRecipe recipe) {
        return "turntable".equals(recipe.getType());
    }
}
