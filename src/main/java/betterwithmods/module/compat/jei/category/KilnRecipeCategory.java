package betterwithmods.module.compat.jei.category;


import betterwithmods.BWMod;
import betterwithmods.module.compat.jei.wrapper.BlockRecipeWrapper;
import betterwithmods.util.InvUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;


public class KilnRecipeCategory implements IRecipeCategory<BlockRecipeWrapper> {

    public static final int width = 145;
    public static final int height = 80;
    public static final String UID = "bwm.kiln";

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;
    int outputSlot = 1;
    private final String uid;

    public KilnRecipeCategory(IGuiHelper guiHelper, String uid) {
        ResourceLocation location = new ResourceLocation(BWMod.MODID, "textures/gui/jei/kiln.png");
        this.background = guiHelper.createDrawable(location, 0, 0, width, height);
        this.localizedName = Translator.translateToLocalFormatted("inv.%s.name", uid);
        this.uid = uid;
    }

    @Nonnull
    @Override
    public String getUid() {
        return uid;
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
        guiItemStacks.init(0, true, 20, 31);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                guiItemStacks.init(outputSlot + i * 2 + j, false, 94 + j * 18, 22 + i * 18);
        guiItemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));
        int index = 0;
        for (List<ItemStack> outputStacks : InvUtils.splitIntoBoxes(ingredients.getOutputs(ItemStack.class).get(0), 2))
            guiItemStacks.set(outputSlot + index++, outputStacks);
    }
}
