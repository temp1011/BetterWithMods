package betterwithmods.api.recipe.impl;

import betterwithmods.api.recipe.IOutput;
import betterwithmods.util.InvUtils;
import net.minecraft.item.ItemStack;

public class StackOutput implements IOutput {
    protected ItemStack output;

    public StackOutput(ItemStack stack) {
        this.output = stack;
        ALL_OUTPUTS.add(this);
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public String getTooltip() {
        return "";
    }

    @Override
    public boolean equals(IOutput output) {
        return InvUtils.matches(output.getOutput(),this.getOutput());
    }

    @Override
    public IOutput copy() {
        return new StackOutput(output);
    }
}
