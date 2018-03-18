package betterwithmods.common.registry.bulk.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class MillRecipe extends BulkRecipe {
    private final int grindType;

    public MillRecipe(@Nonnull List<Ingredient> inputs, @Nonnull List<ItemStack> outputs, int grindType) {
        super(inputs, outputs);
        this.grindType = grindType;
    }

    public int getGrindType() {
        return grindType;
    }

    @Override
    public MillRecipe setPriority(int priority) {
        return (MillRecipe) super.setPriority(priority);
    }
}
