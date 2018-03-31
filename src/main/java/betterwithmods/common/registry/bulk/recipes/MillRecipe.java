package betterwithmods.common.registry.bulk.recipes;

import betterwithmods.util.WorldUtils;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Calendar;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class MillRecipe extends BulkRecipe {
    private int grindType;
    public MillRecipe(int grindType, @Nonnull ItemStack output, @Nonnull ItemStack secondaryOutput, Object... inputs) {
        super(output, secondaryOutput, inputs);
        if(WorldUtils.isSpecialDay()) grindType = 2;
        this.grindType = grindType;
    }

    public int getGrindType() {
        return grindType;
    }
}
