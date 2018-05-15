package betterwithmods.api.recipe;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IOutput{

    List<IOutput> ALL_OUTPUTS = Lists.newArrayList();

    ItemStack getOutput();

    String getTooltip();

    boolean equals(IOutput output);

    IOutput copy();
}
