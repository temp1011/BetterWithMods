package betterwithmods.api.recipe;

import betterwithmods.util.InvUtils;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ListOutput implements IRecipeOutput {
    protected List<ItemStack> outputs;

    public ListOutput(List<ItemStack> outputs) {
        this.outputs = outputs;
    }

    @Override
    public List<ItemStack> getOutputs() {
        return this.outputs;
    }

    @Override
    public boolean matches(List<ItemStack> outputs) {
        return InvUtils.matchesExact(this.outputs, outputs);
    }

    @Override
    public boolean matchesFuzzy(List<ItemStack> outputs) {
        return InvUtils.matches(this.outputs, outputs);
    }
}
