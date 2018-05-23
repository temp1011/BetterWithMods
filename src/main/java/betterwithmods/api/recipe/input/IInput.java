package betterwithmods.api.recipe.input;

import net.minecraft.item.crafting.Ingredient;

public interface IInput {

    Ingredient getInput();

    String getTooltip();

    boolean equals(IInput input);

    IInput copy();

}
