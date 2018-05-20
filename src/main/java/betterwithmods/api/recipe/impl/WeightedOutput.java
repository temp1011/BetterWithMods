package betterwithmods.api.recipe.impl;

import betterwithmods.api.recipe.IOutput;
import betterwithmods.util.InvUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class WeightedOutput extends StackOutput {
    private double weight;

    public WeightedOutput(ItemStack stack, double weight) {
        super(stack);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String getTooltip() {
        return I18n.format("bwm.weighted_output.tooltip", weight*100);
    }

    @Override
    public boolean equals(IOutput output) {
        if (output instanceof WeightedOutput) {
            WeightedOutput other = (WeightedOutput) output;
            return other.getWeight() == this.getWeight() && InvUtils.matches(other.getOutput(), this.getOutput());
        }
        return false;
    }
}
