package betterwithmods.api.recipe;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface IRecipeOutputs {
    NonNullList<ItemStack> getOutputs();

    List<IOutput> getDisplayOutputs();

    boolean matches(List<ItemStack> outputs);

    boolean matchesFuzzy(List<ItemStack> outputs);

    boolean isInvalid();


    default List<List<IOutput>> getExpandedOutputs() {
        return Lists.<List<IOutput>>newArrayList(new ArrayList<>(getDisplayOutputs()));
    }

    default List<IOutput> cast(List<? extends IOutput> list) {
        return list.stream().map(o -> (IOutput)o).collect(Collectors.toList());
    }

}
