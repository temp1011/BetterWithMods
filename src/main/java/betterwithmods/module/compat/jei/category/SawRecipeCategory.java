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
        guiItemStacks.init(0, true, 8, 9);
        guiItemStacks.init(1, false, 57, 9);
        guiItemStacks.init(2, false, 75, 9);
        guiItemStacks.init(3, false, 93, 9);
//        guiItemStacks.init(4, false, 32, 27);
        guiItemStacks.set(ingredients);
        int index = 0;
        for (List<ItemStack> outputStacks : InvUtils.splitIntoBoxes(wrapper.recipe.getOutputs(),3))
            guiItemStacks.set(1 + index++, outputStacks);
//        guiItemStacks.set(4, new ItemStack(BWMBlocks.SAW));
    }
}
