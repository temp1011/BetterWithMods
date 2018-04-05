package betterwithmods.module.compat.jei.category;

import betterwithmods.BWMod;
import betterwithmods.common.blocks.mechanical.BlockMechMachines;
import betterwithmods.module.compat.jei.wrapper.HopperRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITooltipCallback;
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

        guiItemStacks.addTooltipCallback(new ITooltipCallback<ItemStack>() {
            @Override
            public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
                if(slotIndex == 2 && !tooltip.isEmpty())
                    tooltip.add(1, TextFormatting.LIGHT_PURPLE+""+TextFormatting.BOLD+Translator.translateToLocal("inv.hopper.place"));
            }
        });

        guiItemStacks.init(0, true, x, y); //inputs item
        guiItemStacks.init(1, true, x - 27, y + 27); //filter
        guiItemStacks.init(2, true, x, y + 45); //urn

        guiItemStacks.init(3, false, x + 28, y + 27); //inventory result
        guiItemStacks.init(4, false, x + 28, y); //main output
        guiItemStacks.init(5, false, x, y + 27); //hopper

        guiItemStacks.set(ingredients);
        guiItemStacks.set(5, BlockMechMachines.getStack(BlockMechMachines.EnumType.HOPPER));
    }
}

