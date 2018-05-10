package betterwithmods.api.recipe;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IRecipeOutput {
    List<ItemStack> getOutputs();

    default List<List<ItemStack>> getDisplayOutputs() {
        return Lists.<List<ItemStack>>newArrayList(getOutputs());
    }

    boolean matches(List<ItemStack> outputs);

    boolean matchesFuzzy(List<ItemStack> outputs);
}
