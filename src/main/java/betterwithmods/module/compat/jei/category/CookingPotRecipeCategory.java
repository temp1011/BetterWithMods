package betterwithmods.module.compat.jei.category;

import betterwithmods.BWMod;
import betterwithmods.common.registry.bulk.recipes.CookingPotRecipe;
import betterwithmods.module.compat.jei.wrapper.BulkRecipeWrapper;
import betterwithmods.util.InvUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class CookingPotRecipeCategory extends BWMRecipeCategory<BulkRecipeWrapper<CookingPotRecipe>> {
    public static final String CAULDRON_UNSTOKED_UID = "bwm.cauldron", CAULDRON_STOKED_UID = "bwm.cauldron.stoked";
    public static final String CRUCIBLE_UNSTOKED_UID = "bwm.crucible", CRUCIBLE_STOKED_UID = "bwm.crucible.stoked";

    private static final int width = 165;
    private static final int height = 57;
    private static final int inputSlots = 9;
    private static final int outputSlot = 0;

    private static final ResourceLocation guiTexture = new ResourceLocation(BWMod.MODID, "textures/gui/jei/cooking.png");
    @Nonnull
    private final ICraftingGridHelper craftingGrid;
    @Nonnull
    private IDrawableAnimated flame;

    private final String uid;

    private IGuiHelper helper;

    public CookingPotRecipeCategory(IGuiHelper helper, String uid) {
        super(helper.createDrawable(guiTexture, 0, 0, width, height), String.format("inv.%s.name", uid.substring(4)));
        this.helper = helper;
        this.uid = uid;
        craftingGrid = helper.createCraftingGridHelper(inputSlots, outputSlot);
        IDrawableStatic flameDrawable = helper.createDrawable(guiTexture, 166, 0, 14, 14);
        this.flame = helper.createAnimatedDrawable(flameDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Nonnull
    @Override
    public String getUid() {
        return this.uid;
    }

    @Override
    public String getModName() {
        return BWMod.NAME;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        flame.draw(minecraft, 77, 22);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull BulkRecipeWrapper<CookingPotRecipe> wrapper, @Nonnull IIngredients ingredients) {

        IDrawableStatic flameDrawable = helper.createDrawable(guiTexture, 166, wrapper.getRecipe().getHeat() > 1 ? 14 : 0, 14, 14);
        this.flame = helper.createAnimatedDrawable(flameDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);

        IGuiItemStackGroup stacks = layout.getItemStacks();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i + (j * 3);
                stacks.init(inputSlots + index, true, 7 + i * 18, 2 + j * 18);
                stacks.init(outputSlot + index, false, 105 + i * 18, 2 + j * 18);
            }
        }
        List<List<ItemStack>> outputs = InvUtils.splitIntoBoxes(wrapper.getRecipe().getOutputs(),9);
        int index = 0;
        for(List<ItemStack> outputStacks : outputs)
            stacks.set(index++, outputStacks);
        List<List<ItemStack>> inputList = ingredients.getInputs(ItemStack.class);
        craftingGrid.setInputs(stacks, inputList);
    }
}
