package betterwithmods.common.registry.bulk.manager;

import betterwithmods.api.tile.IHeated;
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
    public static final int UNSTOKED_HEAT = 1;
    public static final int STOKED_HEAT = 2;

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
        return addRecipe(new CookingPotRecipe(inputs, outputs, STOKED_HEAT));
    }

    //Unstoked
    public CookingPotRecipe addUnstokedRecipe(List<Ingredient> inputs, ItemStack output) {
        return addUnstokedRecipe(inputs, Lists.newArrayList(output));
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
        return addRecipe(new CookingPotRecipe(inputs, outputs, UNSTOKED_HEAT));
    }

    public CookingPotRecipe addHeatlessRecipe(List<Ingredient> inputs, List<ItemStack> outputs, int heat) {
        return addRecipe(new CookingPotRecipe(inputs, outputs, heat).setIgnoreHeat(true));
    }

    @Override
    protected Optional<CookingPotRecipe> findRecipe(List<CookingPotRecipe> recipes, TileEntity tile, ItemStackHandler inv) {
        if (tile instanceof IHeated) {
            List<CookingPotRecipe> r1 = recipes.stream().filter(r -> r.canCraft(((IHeated) tile))).map(CookingPotRecipe.class::cast).collect(Collectors.toList());
            return super.findRecipe(r1, tile, inv);
        }
        return Optional.empty();
    }

}
