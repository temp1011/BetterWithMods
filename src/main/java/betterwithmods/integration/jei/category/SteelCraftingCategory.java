package betterwithmods.integration.jei.category;

import betterwithmods.BWMod;
import betterwithmods.craft.steelanvil.SteelCraftingManager;
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

import java.util.List;

public class SteelCraftingCategory extends BlankRecipeCategory<IRecipeWrapper> {

    public static final String UID = "bwm.steel_anvil";
    public static final String TITLE = "inv.steel_anvil.name";

    public static final int WIDTH = 162;
    public static final int HEIGHT = 199;

    private final IDrawable background;
    //private final String localizedName;
    private final ICraftingGridHelper craftingGridHelper;

    public SteelCraftingCategory(IGuiHelper guiHelper)
    {
        ResourceLocation location = new ResourceLocation(BWMod.MODID, "textures/gui/jei/steel_anvil.png");
        background = guiHelper.createDrawable(location, 0, 0, WIDTH, HEIGHT);
        craftingGridHelper = guiHelper.createCraftingGridHelper(1, 0);
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
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, false, 71, 176);

        for (int i = 0; i < SteelCraftingManager.WIDTH; i++) {
            for (int j = 0; j < SteelCraftingManager.HEIGHT; j++) {
                int index = 1 + j + (i * SteelCraftingManager.WIDTH);
                guiItemStacks.init(index, true, j * 18, i * 18);
            }
        }

        recipeLayout.setRecipeTransferButton(145, 185);

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class);

        craftingGridHelper.setInputStacks(guiItemStacks, inputs);
        craftingGridHelper.setOutput(guiItemStacks, outputs);
    }
}
