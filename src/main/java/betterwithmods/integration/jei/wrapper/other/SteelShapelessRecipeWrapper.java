package betterwithmods.integration.jei.wrapper.other;

import betterwithmods.craft.steelanvil.SteelShapelessRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SteelShapelessRecipeWrapper extends BlankRecipeWrapper implements IRecipeWrapper{

    private IJeiHelpers jeiHelpers;

    private final SteelShapelessRecipe recipe;

    public SteelShapelessRecipeWrapper(IJeiHelpers jeiHelpers, SteelShapelessRecipe recipe)
    {
        this.jeiHelpers = jeiHelpers;
        this.recipe = recipe;
        for(Object input : this.recipe.recipeItems)
        {
            if(input instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) input;
                if(itemStack.stackSize != 1)
                    itemStack.stackSize = 1;
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        IStackHelper stackHelper = jeiHelpers.getStackHelper();
        ItemStack recipeOutput = recipe.getRecipeOutput();

        List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(recipe.recipeItems);
        ingredients.setInputLists(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, recipeOutput);
    }
}
