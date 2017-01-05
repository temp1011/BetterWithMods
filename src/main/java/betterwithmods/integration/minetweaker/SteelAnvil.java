package betterwithmods.integration.minetweaker;

import betterwithmods.craft.steelanvil.CraftingManagerSteelAnvil;
import betterwithmods.craft.steelanvil.ShapedSteelAnvilRecipe;
import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.utils.BaseListAddition;
import com.blamejared.mtlib.utils.BaseListRemoval;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.List;

/**
 * Purpose:
 *
 * @author Tyler Marshall
 * @version 1/5/17
 */
@ZenClass(SteelAnvil.clazz)
public class SteelAnvil {
    public static final String clazz = "mods.betterwithmods.SteelAnvil";

    @ZenMethod
    public static void add(IItemStack result, @NotNull IIngredient[][] recipe) {
        ItemStack output = InputHelper.toStack(result);
        int height = recipe.length;
        int width = 0;
        for (IIngredient[] row : recipe) {
            if (width < row.length)
                width = row.length;
        }
        Object[] input = new Object[width*height];
        int count = 0;
        for (IIngredient[] row : recipe) {
            for (IIngredient ingredient : row) {
                if (ingredient instanceof IItemStack) {
                    input[count++] = InputHelper.toStack((IItemStack) ingredient);
                } else if (ingredient instanceof IOreDictEntry) {
                    input[count++] = ((IOreDictEntry) ingredient).getName();
                }
            }
        }
        MineTweakerAPI.apply(new Add(new ShapedSteelAnvilRecipe(output,input)));
    }

    public static class Add extends BaseListAddition<IRecipe> {

        protected Add(IRecipe recipe) {
            super("steelAnvil", Arrays.asList(recipe));
        }

        @Override
        protected String getRecipeInfo(IRecipe recipe) {
            return recipe.getRecipeOutput().getDisplayName();
        }
    }

    @ZenMethod
    public static void remove(IItemStack output) {
        MineTweakerAPI.apply(new Remove(CraftingManagerSteelAnvil.INSTANCE.removeRecipes(InputHelper.toStack(output))));
    }
    public static class Remove extends BaseListRemoval<IRecipe> {

        protected Remove(List<IRecipe> recipes) {
            super("steelAnvil", CraftingManagerSteelAnvil.INSTANCE.getRecipes(), recipes);
        }

        @Override
        protected String getRecipeInfo(IRecipe recipe) {
            return recipe.getRecipeOutput().getDisplayName();
        }
    }
    //    public ShapedSteelAnvilRecipe(IItemStack result, IIngredient[][] recipe) {
//        output = InputHelper.toStack(result);
//
//        height = recipe.length;
//        for (IIngredient[] row : recipe) {
//            if (width < row.length)
//                width = row.length;
//        }
//
//        input = new Object[width * height];
//
//        int count = 0;
//        for (IIngredient[] row : recipe) {
//            for (IIngredient ingredient : row) {
//                if (ingredient instanceof IItemStack) {
//                    input[count++] = InputHelper.toStack((IItemStack) ingredient);
//                } else if (ingredient instanceof IOreDictEntry) {
//                    input[count++] = ((IOreDictEntry) ingredient).getName();
//                }
//            }
//        }
//    }
}
