package betterwithmods.common.registry.bulk.recipes;

import betterwithmods.api.recipe.input.IRecipeInputs;
import betterwithmods.api.recipe.input.impl.BulkListInputs;
import betterwithmods.api.recipe.matching.BulkMatchInfo;
import betterwithmods.api.recipe.output.IRecipeOutputs;
import betterwithmods.api.recipe.output.impl.ListOutputs;
import betterwithmods.util.InvUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class BulkRecipe implements Comparable<BulkRecipe> {

    protected IRecipeOutputs recipeOutput;
    protected IRecipeInputs<Integer, BulkMatchInfo> recipeInputs;
    protected int priority;

    public BulkRecipe(IRecipeInputs<Integer, BulkMatchInfo> inputs, IRecipeOutputs outputs, int priority) {
        this.recipeInputs = inputs;
        this.recipeOutput = outputs;
        this.priority = priority;
    }

    public BulkRecipe(IRecipeInputs<Integer, BulkMatchInfo> inputs, IRecipeOutputs outputs) {
        this(inputs, outputs, 0);
    }

    public BulkRecipe(@Nonnull List<Ingredient> inputs, @Nonnull List<ItemStack> outputs) {
        this(inputs, outputs, 0);
    }


    public BulkRecipe(List<Ingredient> inputs, @Nonnull List<ItemStack> outputs, int priority) {
        this(new BulkListInputs(inputs), new ListOutputs(outputs), priority);
    }

    public NonNullList<ItemStack> onCraft(World world, TileEntity tile, ItemStackHandler inv) {
        NonNullList<ItemStack> items = NonNullList.create();
        if (consumeIngredients(inv, items)) {
            items.addAll(getOutputs());
            return BulkCraftEvent.fireOnCraft(tile, world, inv, this, items);
        }
        return NonNullList.create();
    }

    public IRecipeOutputs getRecipeOutput() {
        return recipeOutput;
    }

    public List<ItemStack> getOutputs() {
        return recipeOutput.getOutputs();
    }

    public List<Ingredient> getInputs() {
        return recipeInputs.getInputs();
    }


    //TODO abstract?
    protected boolean consumeIngredients(ItemStackHandler inventory, NonNullList<ItemStack> containItems) {
        HashSet<Ingredient> toConsume = new HashSet<>(recipeInputs.getInputs());
        for (Ingredient ingredient : toConsume) {
            if (!InvUtils.consumeItemsInInventory(inventory, ingredient, false, containItems))
                return false;
        }
        return true;
    }

    public boolean isInvalid() {
        return recipeInputs.isInvalid() || recipeOutput.isInvalid();
    }

    @Override
    public String toString() {
        return String.format("%s: %s -> %s", getClass().getSimpleName(), this.recipeInputs, this.recipeOutput);
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
        return recipeInputs.matches(new BulkMatchInfo(inventory));
    }

    public boolean isHidden() {
        return false;
    }
}

