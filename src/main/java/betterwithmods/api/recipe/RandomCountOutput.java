package betterwithmods.api.recipe;

import betterwithmods.util.InvUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomCountOutput implements IRecipeOutput {
    private static final Random RANDOM = new Random();

    private List<ItemStack> stacks;
    private int lower, upper;

    public RandomCountOutput(List<ItemStack> stacks, int lower, int upper) {
        this.stacks = stacks;
        this.lower = lower;
        this.upper = upper;
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
        return stacks.isEmpty();
    }

    private int rand() {
        return RANDOM.nextInt((upper - lower) + 1) + lower;
    }

    private NonNullList<ItemStack> findResult() {
        return InvUtils.asNonnullList(stacks.stream().map( s -> InvUtils.setCount(s, rand())).collect(Collectors.toList()));
    }

}
