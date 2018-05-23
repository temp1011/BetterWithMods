package betterwithmods.api.recipe.input.impl;

import betterwithmods.api.recipe.input.IInput;
import net.minecraft.item.crafting.Ingredient;

public class IngredientInput implements IInput {
    private Ingredient ingredient;

    public IngredientInput(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public Ingredient getInput() {
        return ingredient;
    }

    @Override
    public String getTooltip() {
        return "";
    }

    @Override
    public boolean equals(IInput input) {
        return input.getInput().equals(ingredient);
    }

    @Override
    public IInput copy() {
        return new IngredientInput(ingredient);
    }
}
