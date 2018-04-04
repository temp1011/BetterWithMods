package betterwithmods.common.registry.block.recipe;

import betterwithmods.util.InvUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 03/19/2018
 */
public class BlockRecipe {
    private final BlockIngredient input;
    private final NonNullList<ItemStack> outputs;

    public BlockRecipe(BlockIngredient input, List<ItemStack> outputs) {
        this.input = input;
        this.outputs = outputs == null ? NonNullList.create() : InvUtils.asNonnullList(outputs.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList()));
    }

    public NonNullList<ItemStack> onCraft(World world, BlockPos pos) {
        NonNullList<ItemStack> items = NonNullList.create();
        if (consumeIngredients(world, pos)) {
            items.addAll(getOutputs());
        }
        return items;
    }

    public boolean consumeIngredients(World world, BlockPos pos) {
        return world.setBlockToAir(pos);
    }

    public BlockIngredient getInput() {
        return input;
    }

    public NonNullList<ItemStack> getOutputs() {
        return outputs;
    }

    @Override
    public String toString() {
        return String.format("%s-> %s", input, getOutputs());
    }

    public boolean isInvalid() {
        return (input.isSimple() && ArrayUtils.isEmpty(input.getMatchingStacks())) || (outputs == null || outputs.isEmpty());
    }
}