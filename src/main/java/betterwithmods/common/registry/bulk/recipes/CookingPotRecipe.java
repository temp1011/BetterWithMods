package betterwithmods.common.registry.bulk.recipes;

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
}
