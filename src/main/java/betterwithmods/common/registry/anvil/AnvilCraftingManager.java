package betterwithmods.common.registry.anvil;

import com.google.common.collect.Maps;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnvilCraftingManager {

    public static List<IRecipe> ANVIL_CRAFTING = new ArrayList<>();

    public static HashMap<IRecipe, VanillaShapedAnvilRecipe> RECIPE_CACHE = Maps.newHashMap();
    /**
     * Retrieves an ItemStack that has multiple recipes for it.
     */
    public static ItemStack findMatchingResult(InventoryCrafting inventory, World world) {
        for (IRecipe irecipe : ANVIL_CRAFTING) {
            if (irecipe.matches(inventory, world)) {
                return irecipe.getCraftingResult(inventory);
            }
        }

        for (IRecipe irecipe : CraftingManager.REGISTRY) {
            if (!(irecipe instanceof ShapedRecipes || irecipe instanceof ShapedOreRecipe))
                continue;
            VanillaShapedAnvilRecipe recipe = RECIPE_CACHE.get(irecipe);
            if (recipe == null) {
                recipe = new VanillaShapedAnvilRecipe(irecipe);
                RECIPE_CACHE.put(irecipe, recipe);
            }
            if (recipe.matches(inventory, world)) {
                return recipe.getCraftingResult(inventory);
            }
        }

        return ItemStack.EMPTY;
    }



    public static NonNullList<ItemStack> getRemainingItems(InventoryCrafting inventory, World craftMatrix) {

        for (IRecipe irecipe : ANVIL_CRAFTING) {
            if (irecipe.matches(inventory, craftMatrix)) {
                return irecipe.getRemainingItems(inventory);
            }
        }
        for (IRecipe irecipe : CraftingManager.REGISTRY) {
            if (!(irecipe instanceof ShapedRecipes || irecipe instanceof ShapedOreRecipe))
                continue;
            VanillaShapedAnvilRecipe recipe = RECIPE_CACHE.get(irecipe);
            if (recipe == null) {
                recipe = new VanillaShapedAnvilRecipe(irecipe);
                RECIPE_CACHE.put(irecipe, recipe);
            }
            if (recipe.matches(inventory, craftMatrix)) {
                return recipe.getRemainingItems(inventory);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inventory.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            nonnulllist.set(i, inventory.getStackInSlot(i));
        }

        return nonnulllist;
    }


}