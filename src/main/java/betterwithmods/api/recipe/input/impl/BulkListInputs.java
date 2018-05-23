package betterwithmods.api.recipe.input.impl;

import betterwithmods.api.recipe.matching.BulkMatchInfo;
import betterwithmods.util.InvUtils;
import net.minecraft.item.crafting.Ingredient;

import java.util.Collection;
import java.util.List;

public class BulkListInputs extends ListInputs<Integer, BulkMatchInfo> {

    public BulkListInputs(List<IngredientInput> inputs) {
        super(inputs);
    }

    public BulkListInputs(Collection<Ingredient> inputs) {
        super(inputs);
    }

    @Override
    public Integer matches(BulkMatchInfo info) {
        int index = Integer.MAX_VALUE;
        for (Ingredient ingredient : getInputs()) {
            if ((index = (Math.min(index, InvUtils.getFirstOccupiedStackOfItem(info.getInventory(), ingredient)))) == -1)
                return -1;
        }
        return index;
    }


}
