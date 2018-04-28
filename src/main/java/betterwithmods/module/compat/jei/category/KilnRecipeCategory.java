package betterwithmods.module.compat.jei.category;


import betterwithmods.BWMod;
import betterwithmods.common.registry.heat.BWMHeatRegistry;
import betterwithmods.module.compat.jei.wrapper.KilnRecipeWrapper;
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


public class KilnRecipeCategory extends BWMRecipeCategory<KilnRecipeWrapper> {
    public static final int width = 145;
    public static final int height = 80;
    public static final String UID = "bwm.kiln";
    private static final ResourceLocation guiTexture = new ResourceLocation(BWMod.MODID, "textures/gui/jei/kiln.png");

    @Nonnull
    private IDrawableAnimated flame;
    private IGuiHelper helper;

    public KilnRecipeCategory(IGuiHelper helper, String uid) {
        super(helper.createDrawable(guiTexture, 0, 0, width, height), uid, String.format("inv.%s.name", uid.substring(4)));
        this.helper = helper;
    }

    @Override
    public String getModName() {
        return BWMod.NAME;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        flame.draw(minecraft, 67, 33);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull KilnRecipeWrapper wrapper, @Nonnull IIngredients ingredients) {
        IDrawableStatic flameDrawable = helper.createDrawable(guiTexture, 145, wrapper.getRecipe().getHeat() > 1 ? 14 : 0, 14, 14);
        this.flame = helper.createAnimatedDrawable(flameDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);

        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(0, true, 20, 31);
        int outputSlot = 1;
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                stacks.init(outputSlot + i * 2 + j, false, 94 + j * 18, 22 + i * 18);
        stacks.set(0, ingredients.getInputs(ItemStack.class).get(0));
        int index = 0;
        for (List<ItemStack> outputStacks : InvUtils.splitIntoBoxes(ingredients.getOutputs(ItemStack.class).get(0), 2))
            stacks.set(outputSlot + index++, outputStacks);

        int heat = wrapper.getRecipe().getHeat();
        List<ItemStack> heatSources = BWMHeatRegistry.getStacks(heat);
        if (!heatSources.isEmpty()) {
            stacks.init(5, true, 65, 52);
            stacks.set(5, heatSources);
        }
    }
}
