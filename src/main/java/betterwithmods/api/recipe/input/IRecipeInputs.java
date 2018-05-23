package betterwithmods.api.recipe.input;

import betterwithmods.api.recipe.IMatchInfo;
import betterwithmods.api.recipe.IMatcher;
import betterwithmods.util.InvUtils;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.stream.Collectors;

public interface IRecipeInputs<T, I extends IMatchInfo> extends IMatcher<T, I> {

    default Ingredient getInput() { return null; }

    NonNullList<Ingredient> getInputs();

    List<IInput> getDisplayInputs();

    default List<List<IInput>> getExpandedInputs(int boxes) {
        return InvUtils.splitIntoBoxes(getDisplayInputs(), boxes);
    }

    default List<IInput> cast(List<? extends IInput> list) {
        return list.stream().map(o -> (IInput) o).collect(Collectors.toList());
    }

    boolean isInvalid();

}
