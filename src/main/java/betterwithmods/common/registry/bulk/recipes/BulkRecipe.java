package betterwithmods.common.registry.bulk.recipes;

import betterwithmods.util.InvUtils;
import betterwithmods.util.StackIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class BulkRecipe implements Comparable<BulkRecipe> {

    protected NonNullList<Ingredient> inputs;
    protected List<ItemStack> outputs;
    protected int priority;

    public BulkRecipe(@Nonnull List<Ingredient> inputs, @Nonnull List<ItemStack> outputs) {
        this(inputs, outputs, 0);
    }

    public BulkRecipe(List<Ingredient> inputs, @Nonnull List<ItemStack> outputs, int priority) {
        this.outputs = outputs;
        this.inputs = InvUtils.asNonnullList(inputs);
        this.priority = priority;
    }

    public NonNullList<ItemStack> onCraft(World world, TileEntity tile, ItemStackHandler inv) {
        if (consumeIngredients(inv)) {
            return InvUtils.asNonnullList(getOutputs());
        }
        return NonNullList.create();
    }

    public List<ItemStack> getOutputs() {
        return outputs.stream().map(ItemStack::copy).collect(Collectors.toList());
    }

    public List<Ingredient> getInputs() {
        return inputs;
    }

    private boolean consumeIngredients(ItemStackHandler inventory) {
        for (Ingredient ingredient : inputs) {
            int count = ingredient instanceof StackIngredient ? ((StackIngredient) ingredient).getCount() : 1;
            if (!InvUtils.consumeItemsInInventory(inventory, ingredient, count, false))
                return false;
        }
        return true;
    }

    public boolean isInvalid() {
        return getInputs().isEmpty() || getOutputs().isEmpty();
    }

    @Override
    public String toString() {
        return String.format("%s: %s -> %s", getClass().getSimpleName(), this.inputs, this.outputs);
    }

    /**
     * Recipes with higher priority will be crafted first.
     *
     * @return sorting priority for Comparable
     */
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(@Nonnull BulkRecipe bulkRecipe) {
        return Integer.compare(getPriority(), bulkRecipe.getPriority());
    }


    public boolean matches(ItemStackHandler inventory) {
        for (Ingredient ingredient : inputs) {
            if (!InvUtils.containsIngredient(inventory, ingredient))
                return false;
        }
        return true;
    }
}
