package betterwithmods.module.compat.jei.category;

import betterwithmods.BWMod;
import betterwithmods.common.blocks.mechanical.BlockMechMachines;
import betterwithmods.module.compat.jei.wrapper.TurntableRecipeWrapper;
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
 * Purpose:
 *
 * @author primetoxinz
 * @version 11/11/16
 */
public class TurntableRecipeCategory implements IRecipeCategory<TurntableRecipeWrapper> {
    public static final int width = 76;
    public static final int height = 50;
    public static final String UID = "bwm.turntable";
    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;

    int secondaryOutputSlot = 2;

    public TurntableRecipeCategory(IGuiHelper helper) {
        ResourceLocation location = new ResourceLocation(BWMod.MODID, "textures/gui/jei/turntable.png");
        background = helper.createDrawable(location, 0, 0, width, height);
        localizedName = Translator.translateToLocal("inv.turntable.name");
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
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull TurntableRecipeWrapper wrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();
        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
        int x = 5, y = 9;
        guiItemStacks.init(0, true, x, y);
        guiItemStacks.init(1, false, x + 27, y);
        for (int i = 0; i < 2; i++)
            guiItemStacks.init(secondaryOutputSlot+i, false, x + 27 + i*18, y + 20);
        guiItemStacks.init(4, false, x, y + 18);
        guiItemStacks.set(0, inputs.get(0));
        guiItemStacks.set(1, outputs.get(0));
        int index = 0;
        for (List<ItemStack> outputStacks : InvUtils.splitIntoBoxes(ingredients.getOutputs(ItemStack.class).get(1),2))
            guiItemStacks.set(secondaryOutputSlot + index++, outputStacks);
        guiItemStacks.set(4, BlockMechMachines.getStack(BlockMechMachines.EnumType.TURNTABLE));

    }
}

