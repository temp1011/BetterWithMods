package betterwithmods.common.registry.bulk.manager;

import betterwithmods.common.blocks.mechanical.tile.TileEntityCookingPot;
import betterwithmods.common.registry.bulk.recipes.CookingPotRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CookingPotManager extends CraftingManagerBulk<CookingPotRecipe> {
    private static final CookingPotManager instance = new CookingPotManager();

    public static CookingPotManager getInstance() {
        return instance;
    }

    public CookingPotRecipe addStokedRecipe(ItemStack input, ItemStack... output) {
        return addStokedRecipe(Lists.newArrayList(Ingredient.fromStacks(input.copy())), Lists.newArrayList(output));
    }

    public CookingPotRecipe addStokedRecipe(ItemStack input, List<ItemStack> output) {
        return addStokedRecipe(Lists.newArrayList(Ingredient.fromStacks(input.copy())), output);
    }

    public CookingPotRecipe addStokedRecipe(ItemStack input, ItemStack output) {
        return addStokedRecipe(Lists.newArrayList(Ingredient.fromStacks(input)), Lists.newArrayList(output));
    }

    public CookingPotRecipe addStokedRecipe(Ingredient ingredient, ItemStack output) {
        return addStokedRecipe(Lists.newArrayList(ingredient), Lists.newArrayList(output));
    }

    public CookingPotRecipe addStokedRecipe(Ingredient ingredient, List<ItemStack> outputs) {
        return addStokedRecipe(Lists.newArrayList(ingredient), outputs);
    }

    public CookingPotRecipe addStokedRecipe(List<Ingredient> inputs, List<ItemStack> outputs) {
        return addRecipe(new CookingPotRecipe(inputs, outputs, 2));
    }

    //Unstoked
    public CookingPotRecipe addUnstokedRecipe(List<ItemStack> input, ItemStack output) {
        return addUnstokedRecipe(input.stream().map(Ingredient::fromStacks).collect(Collectors.toList()), Lists.newArrayList(output));
    }

    public CookingPotRecipe addUnstokedRecipe(ItemStack input, ItemStack... output) {
        return addUnstokedRecipe(Lists.newArrayList(Ingredient.fromStacks(input.copy())), Lists.newArrayList(output));
    }

    public CookingPotRecipe addUnstokedRecipe(ItemStack input, List<ItemStack> output) {
        return addUnstokedRecipe(Lists.newArrayList(Ingredient.fromStacks(input)), output);
    }

    public CookingPotRecipe addUnstokedRecipe(ItemStack input, ItemStack output) {
        return addUnstokedRecipe(Lists.newArrayList(Ingredient.fromStacks(input)), Lists.newArrayList(output));
    }

    public CookingPotRecipe addUnstokedRecipe(Ingredient ingredient, ItemStack output) {
        return addUnstokedRecipe(Lists.newArrayList(ingredient), Lists.newArrayList(output));
    }

    public CookingPotRecipe addUnstokedRecipe(Ingredient ingredient, List<ItemStack> outputs) {
        return addUnstokedRecipe(Lists.newArrayList(ingredient), outputs);
    }

    public CookingPotRecipe addUnstokedRecipe(List<Ingredient> inputs, List<ItemStack> outputs) {
        return addRecipe(new CookingPotRecipe(inputs, outputs, 1));
    }

    @Override
    protected Optional<CookingPotRecipe> findRecipe(TileEntity tile, ItemStackHandler inv) {
        int heat = tile instanceof TileEntityCookingPot ? ((TileEntityCookingPot) tile).getHeat() : 0;
        return recipes.stream().filter(r -> r.matches(inv) && r.getHeat() >= heat).sorted().findFirst();
    }
}
