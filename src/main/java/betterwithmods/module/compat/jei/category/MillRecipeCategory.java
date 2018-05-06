package betterwithmods.module.compat.jei.category;

import betterwithmods.BWMod;
import betterwithmods.module.compat.jei.wrapper.BulkRecipeWrapper;
import betterwithmods.util.InvUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class MillRecipeCategory extends BWMRecipeCategory<BulkRecipeWrapper> {
    public static final String UID = "bwm.mill";
    private static final int width = 149;
    private static final int height = 32;
    private static final int inputSlots = 0;
    private static final int outputSlot = 3;
    private static final ResourceLocation guiTexture = new ResourceLocation(BWMod.MODID, "textures/gui/jei/mill.png");

    @Nonnull
    private final IDrawableAnimated gear;

    public MillRecipeCategory(IGuiHelper helper) {
        super(helper.createDrawable(guiTexture, 0, 0, width, height), UID,"inv.mill.name");
        IDrawableStatic flameDrawable = helper.createDrawable(guiTexture, 150, 0, 14, 14);
        this.gear = helper.createAnimatedDrawable(flameDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        gear.draw(minecraft, 68, 8);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull BulkRecipeWrapper wrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup stacks = layout.getItemStacks();

        List<List<ItemStack>> input = ingredients.getInputs(ItemStack.class);
        for (int i = 0; i < 3; i++) {
            stacks.init(inputSlots + i, true, 7 + i * 18, 7);
            stacks.init(outputSlot + i, false, 89 + i * 18, 7);
            if (input.size() > i && input.get(i) != null) {
                stacks.set(inputSlots + i, input.get(i));
            }
        }
        int index = 0;
        for (List<ItemStack> outputStacks : InvUtils.splitIntoBoxes(wrapper.getRecipe().getOutputs(),3))
            stacks.set(outputSlot + index++, outputStacks);
    }
}
