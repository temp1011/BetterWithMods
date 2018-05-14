package betterwithmods.api.recipe;

import betterwithmods.util.InvUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.stream.Collectors;

public class ListOutput implements IRecipeOutput {
    protected NonNullList<ItemStack> outputs;

    public ListOutput(List<ItemStack> outputs) {
        this.outputs = InvUtils.asNonnullList(outputs.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList()));
    }

    @Override
    public NonNullList<ItemStack> getOutputs() {
        return InvUtils.asNonnullList(this.outputs.stream().map(ItemStack::copy).collect(Collectors.toList()));
    }

    @Override
    public boolean matches(List<ItemStack> outputs) {
        return InvUtils.matchesExact(this.outputs, outputs);
    }

    @Override
    public boolean matchesFuzzy(List<ItemStack> outputs) {
        return InvUtils.matches(this.outputs, outputs);
    }

    @Override
    public boolean isInvalid() {
        return outputs.isEmpty();
    }
}
