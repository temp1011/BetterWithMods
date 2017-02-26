package betterwithmods.integration.jei.handler;

import betterwithmods.craft.HopperInteractions;
import betterwithmods.integration.jei.wrapper.bulk.HopperRecipeWrapper;
import betterwithmods.integration.jei.wrapper.other.SoulUrnWrapper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Purpose:
 *
 * @author Tyler Marshall
 * @version 11/20/16
 */
public class HopperRecipeHandler implements IRecipeHandler<HopperInteractions.HopperRecipe> {
    @Override
    public Class<HopperInteractions.HopperRecipe> getRecipeClass() {
        return HopperInteractions.HopperRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return "bwm.hopper";
    }

    @Override
    public String getRecipeCategoryUid(HopperInteractions.HopperRecipe recipe) {
        if (recipe instanceof HopperInteractions.SoulUrn) {
            return "bwm.hopper.soul_urn";
        }
        return getRecipeCategoryUid();
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(HopperInteractions.HopperRecipe recipe) {
        if (recipe instanceof HopperInteractions.SoulUrn)
            return new SoulUrnWrapper((HopperInteractions.SoulUrn)recipe);
        return new HopperRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(HopperInteractions.HopperRecipe recipe) {
        return true;
    }
}
