package betterwithmods.common.registry.block.recipe;

import betterwithmods.api.recipe.IRecipeOutput;
import betterwithmods.api.recipe.ListOutput;
import betterwithmods.util.InvUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 03/19/2018
 */
public class BlockRecipe {
    private final BlockIngredient input;
    private final IRecipeOutput recipeOutput;

    public BlockRecipe(BlockIngredient input, List<ItemStack> outputs) {
        this(input, new ListOutput(outputs));
    }

    public BlockRecipe(BlockIngredient input, IRecipeOutput recipeOutput) {
        this.input = input;
        this.recipeOutput = recipeOutput;
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

    public IRecipeOutput getRecipeOutput() {
        return recipeOutput;
    }

    public NonNullList<ItemStack> getOutputs() {
        return recipeOutput.getOutputs();
    }

    @Override
    public String toString() {
        return String.format("%s-> %s", input, getOutputs());
    }

    public boolean isInvalid() {
        return (input.isSimple() && InvUtils.isIngredientValid(input) || recipeOutput.isInvalid());
    }

    public boolean matches(World world, BlockPos pos, IBlockState state) {
        return getInput().apply(world, pos, state);
    }
}