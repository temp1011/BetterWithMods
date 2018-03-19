package betterwithmods.common.registry.bulk.recipes;

import betterwithmods.api.tile.IHeated;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.List;

public class CookingPotRecipe extends BulkRecipe {

    private int heat;
    private boolean ignoreHeat;

    public CookingPotRecipe(@Nonnull List<Ingredient> inputs, @Nonnull List<ItemStack> outputs, int heat) {
        super(inputs, outputs);
        this.heat = heat;
    }

    public int getHeat() {
        return heat;
    }

    public boolean canCraft(IHeated tile) {
        return ignoreHeat || (tile.getHeat() == getHeat());
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
