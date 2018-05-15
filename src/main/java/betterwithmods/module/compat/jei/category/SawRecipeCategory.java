package betterwithmods.module.compat.jei.category;

import betterwithmods.BWMod;
import betterwithmods.api.recipe.IOutput;
import betterwithmods.module.compat.jei.wrapper.BlockRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Created by primetoxinz on 9/5/16.
 */
public class SawRecipeCategory implements IRecipeCategory<BlockRecipeWrapper> {
    public static final int width = 117;
    public static final int height = 36;
    public static final String UID = "bwm.saw";

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;

    public SawRecipeCategory(IGuiHelper helper) {
        ResourceLocation location = new ResourceLocation(BWMod.MODID, "textures/gui/jei/saw.png");
        background = helper.createDrawable(location, 0, 0, width, height);
        localizedName = Translator.translateToLocal("inv.saw.name");
    }

    @Nonnull
    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getModName() {
        return BWMod.NAME;
    }


    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull BlockRecipeWrapper wrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();
        IGuiIngredientGroup<IOutput> guiOutputs = layout.getIngredientsGroup(IOutput.class);
        guiItemStacks.init(0, true, 8, 9);
        guiOutputs.init(1, false, 58, 10);
        guiOutputs.init(2, false, 74, 10);
        guiOutputs.init(3, false, 90, 10);
        guiItemStacks.set(ingredients);
        guiOutputs.set(ingredients);
    }
}
