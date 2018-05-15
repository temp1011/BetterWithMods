package betterwithmods.module.compat.jei.category;

import betterwithmods.BWMod;
import betterwithmods.common.blocks.mechanical.BlockMechMachines;
import betterwithmods.module.compat.jei.wrapper.HopperRecipeWrapper;
import betterwithmods.util.InvUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 11/20/16
 */
public class HopperRecipeCategory implements IRecipeCategory<HopperRecipeWrapper> {
    public static final int width = 145;
    public static final int height = 80;
    public static final String UID = "bwm.hopper";
    int outputSlot = 3;
    int secondaryOutputSlot = 5;

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;

    public HopperRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(BWMod.MODID, "textures/gui/jei/hopper.png");
        background = guiHelper.createDrawable(location, 0, 0, width, height);
        localizedName = Translator.translateToLocal("inv.hopper.name");
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

    @SideOnly(Side.CLIENT)
    @Override
    public void drawExtras(Minecraft minecraft) {
        String throwText = Translator.translateToLocal("inv.hopper.throw");
        int l = minecraft.fontRenderer.getStringWidth(throwText);
        int textColor = 0x808080;
        minecraft.fontRenderer.drawString(throwText, width / 2 - l + 5, -11, textColor);
        minecraft.fontRenderer.drawString(Translator.translateToLocal("inv.hopper.filter"), width / 2 - 50, 16, textColor);
        minecraft.fontRenderer.drawString(Translator.translateToLocal("inv.hopper.outputs"), width / 2 + 10, -11, textColor);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull HopperRecipeWrapper wrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();
        int x = width / 2 - 18, y = 0;

        guiItemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if(slotIndex == 2 && !tooltip.isEmpty())
                tooltip.add(1, TextFormatting.LIGHT_PURPLE+""+TextFormatting.BOLD+Translator.translateToLocal("inv.hopper.place"));
        });

        guiItemStacks.init(0, true, x, y); //inputs item
        guiItemStacks.init(1, true, x - 27, y + 27); //filter
        guiItemStacks.init(2, true, x, y + 45); //urn

        for(int i = 0; i < 2; i++) {
            guiItemStacks.init(outputSlot+i, false, x + 28 + i*18, y + 27); //inventory result
            guiItemStacks.init(secondaryOutputSlot+i, false, x + 28 + i*18, y); //main output
        }
        guiItemStacks.init(7, false, x, y + 27); //hopper

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

        guiItemStacks.set(0, inputs.get(0));
        guiItemStacks.set(1, inputs.get(1));
        guiItemStacks.set(2, inputs.get(2));
        int index = 0;
        for (List<ItemStack> outputStacks : InvUtils.splitIntoBoxes(outputs.get(0),2))
            guiItemStacks.set(outputSlot + index++, outputStacks);
        index = 0;
        for (List<ItemStack> outputStacks : InvUtils.splitIntoBoxes(outputs.get(1),2))
            guiItemStacks.set(secondaryOutputSlot + index++, outputStacks);
        guiItemStacks.set(7, BlockMechMachines.getStack(BlockMechMachines.EnumType.HOPPER));
    }
}

