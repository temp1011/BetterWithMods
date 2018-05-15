package betterwithmods.api.recipe.impl;

import betterwithmods.api.recipe.IOutput;
import betterwithmods.api.recipe.IRecipeOutputs;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.stream.Collectors;

public class RandomCountOutputs implements IRecipeOutputs {

    private List<RandomOutput> outputs;

    public RandomCountOutputs(RandomOutput... outputs) {
        this(Lists.newArrayList(outputs));
    }

    public RandomCountOutputs(List<RandomOutput> outputs) {
        this.outputs = outputs;
    }

    @Override
    public List<IOutput> getDisplayOutputs() {
        return cast(outputs);
    }

    @Override
    public NonNullList<ItemStack> getOutputs() {
        return findResult();
    }

    @Override
    public boolean matches(List<ItemStack> outputs) {
        return false;
    }

    @Override
    public boolean matchesFuzzy(List<ItemStack> outputs) {
        return false;
    }

    @Override
    public boolean isInvalid() {
        return outputs.isEmpty();
    }

    private NonNullList<ItemStack> findResult() {
        return InvUtils.asNonnullList(outputs.stream().map(RandomOutput::getRandomStack).collect(Collectors.toList()));
    }

}
