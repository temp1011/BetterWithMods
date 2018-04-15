package betterwithmods.common.registry.block.recipe;

import betterwithmods.api.tile.IHeatRecipe;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class KilnRecipe extends BlockRecipe implements IHeatRecipe {
    private int heat;
    private boolean ignoreHeat;
    private int cookTime;

    public KilnRecipe(BlockIngredient input, List<ItemStack> outputs, int heat, int cookTime) {
        super(input, outputs);
        this.heat = heat;
        this.cookTime = cookTime;
    }

    @Override
    public int getHeat() {
        return heat;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    @Override
    public boolean ignore() {
        return ignoreHeat;
    }

    public KilnRecipe setIgnoreHeat(boolean ignoreHeat) {
        this.ignoreHeat = ignoreHeat;
        return this;
    }
}
