package betterwithmods.module.compat.jei.wrapper;

import betterwithmods.common.registry.block.recipe.BlockRecipe;
import com.google.common.collect.Lists;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 11/11/16
 */
public class BlockRecipeWrapper implements IRecipeWrapper {
    public final BlockRecipe recipe;
    protected final IJeiHelpers helpers;

    public BlockRecipeWrapper(IJeiHelpers helpers, BlockRecipe recipe) {
        this.helpers = helpers;
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, helpers.getStackHelper().toItemStackList(recipe.getInput()));
        ArrayList<List<ItemStack>> newList = new ArrayList<>();
        newList.add(recipe.getOutputs());
        ingredients.setOutputLists(ItemStack.class, newList);
    }
}
