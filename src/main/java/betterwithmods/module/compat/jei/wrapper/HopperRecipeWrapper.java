package betterwithmods.module.compat.jei.wrapper;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWRegistry;
import betterwithmods.common.registry.HopperInteractions;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 11/20/16
 */
public class HopperRecipeWrapper implements IRecipeWrapper {

    public final HopperInteractions.HopperRecipe recipe;
    protected final List<ItemStack> input;
    protected final List<ItemStack> filter;
    protected final List<ItemStack> outputs;
    protected final List<ItemStack> secondaryOutputs;
    protected final List<ItemStack> container;

    public HopperRecipeWrapper(HopperInteractions.HopperRecipe recipe) {
        this.recipe = recipe;
        this.input = recipe.getInputs();
        this.outputs = recipe.getOutputs();
        this.secondaryOutputs = recipe.getSecondaryOutputs();
        this.filter = Lists.newArrayList(BWRegistry.HOPPER_FILTERS.getFilter(recipe.getFilterType()).getFilter().getMatchingStacks());
        this.container = recipe.getContainers();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, Lists.newArrayList(input, filter, container));
        ingredients.setOutputLists(ItemStack.class, Lists.newArrayList(outputs, secondaryOutputs));
    }

    public static class SoulUrn extends HopperRecipeWrapper {
        public SoulUrn(HopperInteractions.SoulUrnRecipe recipe) {
            super(recipe);
            if (recipe.hasUrn())
                secondaryOutputs.add(new ItemStack(BWMBlocks.SOUL_URN));
            else
                container.clear();
        }
    }
}
