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
    public KilnRecipe(BlockIngredient input, List<ItemStack> outputs, int heat) {
        super(input, outputs);
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

    public KilnRecipe setIgnoreHeat(boolean ignoreHeat) {
        this.ignoreHeat = ignoreHeat;
        return this;
    }
}
