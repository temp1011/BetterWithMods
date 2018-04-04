package betterwithmods.common.registry.bulk.recipes;

import betterwithmods.util.InvUtils;
import betterwithmods.util.StackIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import java.util.Comparator;
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
        NonNullList<ItemStack> items = NonNullList.create();
        if (consumeIngredients(inv, items)) {
            items.addAll(getOutputs());
            return BulkCraftEvent.fireOnCraft(tile, world, inv, this, items);
        }
        return NonNullList.create();
    }

    private static NonNullList<ItemStack> defaultRecipeGetRemainingItems(ItemStackHandler inv) {
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getSlots(); i++) {
            ret.set(i, ForgeHooks.getContainerItem(inv.getStackInSlot(i)));
        }
        return ret;
    }

    public List<ItemStack> getOutputs() {
        return outputs.stream().map(ItemStack::copy).collect(Collectors.toList());
    }

    public List<Ingredient> getInputs() {
        return inputs;
    }

    private boolean consumeIngredients(ItemStackHandler inventory, NonNullList<ItemStack> containItems) {
        containItems.addAll(defaultRecipeGetRemainingItems(inventory));
        for (Ingredient ingredient : inputs) {
            int count = ingredient instanceof StackIngredient ? ((StackIngredient) ingredient).getCount() : 1;
            if (!InvUtils.consumeItemsInInventory(inventory, ingredient, count, false))
                return false;
        }
        return true;
    }

    public boolean isInvalid() {
        return (getInputs().isEmpty() || getInputs().stream().anyMatch(i -> ArrayUtils.isEmpty(i.getMatchingStacks())) || getOutputs().isEmpty());
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

    public BulkRecipe setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public int compareTo(@Nonnull BulkRecipe bulkRecipe) {
        return Comparator.comparingInt(BulkRecipe::getPriority).reversed().compare(this, bulkRecipe);
    }

    public int matches(ItemStackHandler inventory) {
        int index = -1;
        for (Ingredient ingredient : inputs) {
            if ((index = InvUtils.getFirstOccupiedStackOfItem(inventory, ingredient)) == -1)
                return -1;
        }
        return index;
    }

    public boolean isHidden() {
        return false;
    }
}

