package betterwithmods.common.registry.bulk.recipes;

import betterwithmods.api.tile.IHeated;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.List;

public class CookingPotRecipe extends BulkRecipe {

    private int heat;

    public CookingPotRecipe(@Nonnull List<Ingredient> inputs, @Nonnull List<ItemStack> outputs, int heat) {
        super(inputs, outputs);
        this.heat = heat;
    }

    public int getHeat() {
        return heat;
    }

    public boolean canCraft(IHeated tile) {
        return tile.getHeat() == getHeat();
    }

    @Override
    public CookingPotRecipe setPriority(int priority) {
        return (CookingPotRecipe) super.setPriority(priority);
    }
}
