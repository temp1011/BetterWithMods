package betterwithmods.common.registry.block.managers;

import betterwithmods.common.registry.block.recipe.BlockRecipe;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 11/11/16
 */
public abstract class CraftingManagerBlock<T extends BlockRecipe> {

    protected final ArrayList<T> recipes = Lists.newArrayList();

    public T addRecipe(T recipe) {
        if (!recipe.isInvalid())
            recipes.add(recipe);
        return recipe;
    }

    protected List<T> findRecipe(List<ItemStack> outputs) {
        return recipes.stream().filter(r -> InvUtils.matches(r.getOutputs(),outputs)).collect(Collectors.toList());
    }

    protected List<T> findRecipeExact(List<ItemStack> outputs) {
        return recipes.stream().filter(r -> InvUtils.matchesExact(r.getOutputs(),outputs)).collect(Collectors.toList());
    }

    public List<T> findRecipes(World world, BlockPos pos, IBlockState state) {
        return recipes.stream().filter(r -> r.getInput().apply(world, pos, state)).collect(Collectors.toList());
    }

    public Optional<T> findRecipe(World world, BlockPos pos, IBlockState state) {
        return findRecipes(world, pos, state).stream().findFirst();
    }

    @Nonnull
    public NonNullList<ItemStack> craftItem(World world, BlockPos pos, IBlockState state) {
        return findRecipe(world, pos, state).map(r -> r.onCraft(world, pos)).orElse(NonNullList.create());
    }

    public boolean canCraft(World world, BlockPos pos, IBlockState state) {
        return findRecipe(world, pos, state).isPresent();
    }

    public boolean remove(T t) {
        return t != null && recipes.remove(t);
    }


    public boolean remove(List<ItemStack> outputs) {
        return recipes.removeAll(findRecipe(outputs));
    }

    public boolean removeExact(List<ItemStack> outputs) {
        return recipes.removeAll(findRecipeExact(outputs));
    }

    public List<T> getRecipes() {
        return recipes;
    }

    public abstract boolean craftRecipe(World world, BlockPos pos, Random rand, IBlockState state);
}
