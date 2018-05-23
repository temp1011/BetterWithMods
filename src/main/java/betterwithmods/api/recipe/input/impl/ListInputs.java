package betterwithmods.api.recipe.input.impl;

import betterwithmods.api.recipe.IMatchInfo;
import betterwithmods.api.recipe.input.IInput;
import betterwithmods.api.recipe.input.IRecipeInputs;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ListInputs<T, I extends IMatchInfo> implements IRecipeInputs<T, I> {

    private List<IngredientInput> inputs;
    private List<Ingredient> ingredientList;

    public ListInputs(Collection<Ingredient> inputs) {
        this.inputs = inputs.stream().map(IngredientInput::new).collect(Collectors.toList());
        this.ingredientList = Lists.newArrayList(inputs);
    }

    public ListInputs(List<IngredientInput> inputs) {
        this.inputs = inputs;
        this.ingredientList = inputs.stream().map(IngredientInput::getInput).collect(Collectors.toList());
    }

    @Override
    public NonNullList<Ingredient> getInputs() {
        return InvUtils.asNonnullList(ingredientList);
    }

    @Override
    public List<IInput> getDisplayInputs() {
        return cast(inputs);
    }

    @Override
    public boolean isInvalid() {
        return ingredientList.isEmpty() || ingredientList.stream().anyMatch(InvUtils::isIngredientValid);
    }
}
