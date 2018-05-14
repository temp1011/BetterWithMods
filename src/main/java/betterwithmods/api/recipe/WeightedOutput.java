package betterwithmods.api.recipe;

import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WeightedOutput implements IRecipeOutput {
    private static final Random RANDOM = new Random();

    protected List<WeightedItemStack> weightedItemStacks;

    public WeightedOutput(WeightedItemStack... weightedItemStacks) {
        this(Lists.newArrayList(weightedItemStacks));
    }

    public WeightedOutput(List<WeightedItemStack> weightedItemStacks) {
        this.weightedItemStacks = weightedItemStacks;
    }

    @Override
    public List<List<ItemStack>> getDisplayOutputs() {
        return weightedItemStacks.stream().map(WeightedItemStack::getStack).map(Lists::newArrayList).collect(Collectors.toList());
    }

    @Override
    public NonNullList<ItemStack> getOutputs() {
        return InvUtils.asNonnullList(findResult());
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
        return weightedItemStacks.isEmpty();
    }

    private ItemStack findResult() {
        ItemStack result = ItemStack.EMPTY;
        double bestValue = Double.MAX_VALUE;
        for (WeightedItemStack element : weightedItemStacks) {
            double value = -Math.log(RANDOM.nextDouble()) / element.getWeight();
            if (value < bestValue) {
                bestValue = value;
                result = element.getStack();
            }
        }
        return result;
    }

    public static class WeightedItemStack {
        private ItemStack stack;
        private double weight;

        public WeightedItemStack(ItemStack stack, double weight) {
            this.stack = stack;
            this.weight = weight;
        }

        public ItemStack getStack() {
            return stack;
        }

        public double getWeight() {
            return weight;
        }
    }

}
