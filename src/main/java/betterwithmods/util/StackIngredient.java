package betterwithmods.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

import javax.annotation.Nullable;
import java.util.Arrays;

public class StackIngredient extends Ingredient {


    public static StackIngredient fromIngredient(int count, Ingredient ingredient) {
        return new StackIngredient(count, ingredient);
    }

    public static StackIngredient fromStacks(int count, ItemStack... stacks) {
        return new StackIngredient(count, stacks);
    }

    public static StackIngredient fromOre(int count, String ore) {
        return fromIngredient(count, new OreIngredient(ore));
    }

    private final int count;

    protected StackIngredient(int count, Ingredient ingredient) {
        this(count, ingredient.getMatchingStacks());
    }

    protected StackIngredient(int count, ItemStack... stacks) {
        super(stacks);
        this.count = count;
    }

    @Override
    public boolean apply(@Nullable ItemStack stack) {
        return super.apply(stack) && (stack != null && stack.getCount() >= count);
    }

    @Override
    public ItemStack[] getMatchingStacks() {
        return Arrays.stream(super.getMatchingStacks()).map(stack -> {
            ItemStack copy = stack.copy();
            copy.setCount(count);
            return copy;
        }).toArray(ItemStack[]::new);
    }

    public int getCount() {
        return count;
    }
}
