package betterwithmods.api.recipe.input.impl;

import betterwithmods.api.recipe.input.IInput;
import betterwithmods.api.recipe.input.IRecipeInputs;
import betterwithmods.api.recipe.matching.BlockMatchInfo;
import betterwithmods.common.registry.block.recipe.BlockIngredient;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.List;

public class BlockInputs implements IRecipeInputs<Boolean, BlockMatchInfo> {

    private BlockIngredient ingredient;
    private IngredientInput input;

    public BlockInputs(BlockIngredient ingredient) {
        this.ingredient = ingredient;
        this.input = new IngredientInput(ingredient);
    }

    @Override
    public NonNullList<Ingredient> getInputs() {
        return InvUtils.asNonnullList(ingredient);
    }

    @Override
    public BlockIngredient getInput() {
        return ingredient;
    }

    @Override
    public List<IInput> getDisplayInputs() {
        return Lists.newArrayList(this.input);
    }

    @Override
    public boolean isInvalid() {
        return !InvUtils.isIngredientValid(ingredient);
    }

    @Override
    public Boolean matches(BlockMatchInfo info) {
        return getInput().apply(info.getWorld(), info.getPos(), info.getState());
    }
}
