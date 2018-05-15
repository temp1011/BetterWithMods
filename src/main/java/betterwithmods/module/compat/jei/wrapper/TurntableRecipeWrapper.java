package betterwithmods.module.compat.jei.wrapper;

import betterwithmods.common.registry.block.recipe.TurntableRecipe;
import com.google.common.collect.Lists;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 11/11/16
 */
public class TurntableRecipeWrapper extends BlockRecipeWrapper<TurntableRecipe> {
    public TurntableRecipeWrapper(IJeiHelpers helpers, TurntableRecipe recipe) {
        super(helpers, recipe);
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        TurntableRecipe turntableRecipe = recipe;
        ingredients.setInput(ItemStack.class, helpers.getStackHelper().toItemStackList(turntableRecipe.getInput()));
        ingredients.setOutputLists(ItemStack.class, Lists.newArrayList(Lists.newArrayList(turntableRecipe.getRepresentative()),turntableRecipe.getOutputs()));
    }
}
