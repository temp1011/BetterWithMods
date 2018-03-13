package betterwithmods.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;

public class StackIngredient extends Ingredient {

    public static StackIngredient fromIngredient(int count, Ingredient ingredient) {
        return new StackIngredient(count,ingredient);
    }

    public static StackIngredient fromStacks(int count, ItemStack... stacks) {
        return new StackIngredient(count,stacks);
    }

    private final int count;

    private StackIngredient(int count, Ingredient ingredient) {
        this(count, ingredient.getMatchingStacks());
    }

    private StackIngredient(int count, ItemStack... stacks) {
        super(stacks);
        this.count = count;
    }

    @Override
    public boolean apply(@Nullable ItemStack stack) {
        return super.apply(stack) && (stack != null && stack.getCount() >= count);
    }

    public int getCount() {
        return count;
    }
}
