package betterwithmods.common.registry.block.recipe;

import betterwithmods.api.recipe.input.IRecipeInputs;
import betterwithmods.api.recipe.input.impl.BlockInputs;
import betterwithmods.api.recipe.matching.BlockMatchInfo;
import betterwithmods.api.recipe.output.IRecipeOutputs;
import betterwithmods.api.recipe.output.impl.ListOutputs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 03/19/2018
 */
public abstract class BlockRecipe {
    private final IRecipeInputs<Boolean, BlockMatchInfo> recipeInputs;
    private final IRecipeOutputs recipeOutput;

    public BlockRecipe(BlockIngredient input, List<ItemStack> outputs) {
        this(new BlockInputs(input), new ListOutputs(outputs));
    }

    public BlockRecipe(IRecipeInputs<Boolean, BlockMatchInfo> recipeInputs, IRecipeOutputs recipeOutput) {
        this.recipeInputs = recipeInputs;
        this.recipeOutput = recipeOutput;
    }

    public abstract boolean craftRecipe(World world, BlockPos pos, Random rand, IBlockState state);

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
        return (BlockIngredient) recipeInputs.getInput();
    }

    public IRecipeInputs<Boolean, BlockMatchInfo> getRecipeInputs() {
        return recipeInputs;
    }

    public IRecipeOutputs getRecipeOutput() {
        return recipeOutput;
    }

    public NonNullList<ItemStack> getOutputs() {
        return recipeOutput.getOutputs();
    }

    @Override
    public String toString() {
        return String.format("%s-> %s", getInput(), getOutputs());
    }

    public boolean isInvalid() {
        return recipeInputs.isInvalid() || recipeOutput.isInvalid();
    }

    public boolean matches(World world, BlockPos pos, IBlockState state) {
        return recipeInputs.matches(new BlockMatchInfo(world, pos, state));
    }

    public boolean isHidden() {
        return false;
    }


}