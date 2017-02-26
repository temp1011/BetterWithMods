package betterwithmods.integration.jei.category;

import betterwithmods.BWMod;
import betterwithmods.integration.jei.wrapper.other.SteelShapedRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.List;

public class SteelCraftingCategory extends BlankRecipeCategory<IRecipeWrapper> {

    public static final String UID = "bwm.steel_anvil";
    public static final String TITLE = "inv.steel_anvil.name";

    public static final int WIDTH = 162;
    public static final int HEIGHT = 199;

    private final IDrawable background;
    //private final String localizedName;
    private final ICraftingGridHelper craftingGridHelper;
    private final int craftInputSlot1;
    private final int craftOutputSlot;

    public SteelCraftingCategory(IGuiHelper guiHelper)
    {
        ResourceLocation location = new ResourceLocation(BWMod.MODID, "textures/gui/jei/steel_anvil.png");
        background = guiHelper.createDrawable(location, 0, 0, WIDTH, HEIGHT);
        craftInputSlot1 = 1;
        craftOutputSlot = 0;
        craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format(TITLE);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

        stacks.init(0, false, 112, 27);

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                int index = craftInputSlot1 + x + (y * 4);
                stacks.init(index, true, x * 18, y * 18);
            }
        }

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class);

        if (recipeWrapper instanceof SteelShapedRecipeWrapper) {
            SteelShapedRecipeWrapper wrapper = (SteelShapedRecipeWrapper) recipeWrapper;
            setInputStacks(stacks, inputs, wrapper.getWidth(), wrapper.getHeight());
            setOutput(stacks, outputs);
        } else {
            setInputStacks(stacks, inputs);
            setOutput(stacks, outputs);
        }
    }

    //Copied from CraftingGridHelper
    private void setInputStacks(IGuiItemStackGroup guiItemStacks, List<List<ItemStack>> input) {
        int width, height;
        if (input.size() > 9) {
            width = height = 4;
        } else if (input.size() > 4) {
            width = height = 3;
        } else if (input.size() > 1) {
            width = height = 2;
        } else {
            width = height = 1;
        }

        setInputStacks(guiItemStacks, input, width, height);
    }

    //Copied from CraftingGridHelper
    private void setInputStacks(IGuiItemStackGroup guiItemStacks, List<List<ItemStack>> input, int width, int height) {
        for (int i = 0; i < input.size(); i++) {
            List<ItemStack> recipeItem = input.get(i);
            int index = getCraftingIndex(i, width, height);

            setInput(guiItemStacks, index, recipeItem);
        }
    }

    private int getCraftingIndex(int i, int width, int height) {
        int x = i % width;
        int y = i / width;
        //4 is max width of grid
        return x + (y * 4);
    }

    //Copied from CraftingGridHelper
    private void setInput(IGuiItemStackGroup guiItemStacks, int inputIndex, Collection<ItemStack> input) {
        guiItemStacks.set(craftInputSlot1 + inputIndex, input);
    }

    //Copied from CraftingGridHelper
    private void setOutput(IGuiItemStackGroup guiItemStacks, List<ItemStack> output) {
        guiItemStacks.set(craftOutputSlot, output);
    }
}
