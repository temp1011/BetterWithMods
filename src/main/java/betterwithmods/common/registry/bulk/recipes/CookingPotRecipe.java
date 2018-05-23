package betterwithmods.common.registry.bulk.recipes;

import betterwithmods.api.recipe.input.IRecipeInputs;
import betterwithmods.api.recipe.matching.BulkMatchInfo;
import betterwithmods.api.recipe.output.IRecipeOutputs;
import betterwithmods.api.tile.IHeatRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.List;

public class CookingPotRecipe extends BulkRecipe implements IHeatRecipe{

    private int heat;
    private boolean ignoreHeat;

    public CookingPotRecipe(@Nonnull List<Ingredient> inputs, @Nonnull List<ItemStack> outputs, int heat) {
        super(inputs, outputs);
        this.heat = heat;
    }

    public CookingPotRecipe(IRecipeInputs<Integer, BulkMatchInfo> inputs, IRecipeOutputs outputs, int heat) {
        super(inputs, outputs, 0);
        this.heat = heat;
    }

    @Override
    public int getHeat() {
        return heat;
    }

    @Override
    public boolean ignore() {
        return ignoreHeat;
    }

    public CookingPotRecipe setIgnoreHeat(boolean ignoreHeat) {
        this.ignoreHeat = ignoreHeat;
        return this;
    }

    @Override
    public CookingPotRecipe setPriority(int priority) {
        return (CookingPotRecipe) super.setPriority(priority);
    }
}
