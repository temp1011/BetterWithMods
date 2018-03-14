package betterwithmods.module.compat.jei.category;

import betterwithmods.BWMod;
import betterwithmods.common.registry.bulk.recipes.CookingPotRecipe;
import betterwithmods.module.compat.jei.wrapper.BulkRecipeWrapper;
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

    private static final int inputSlots = 1;
    private static final int outputSlot = 0;

    private static final ResourceLocation guiTexture = new ResourceLocation(BWMod.MODID, "textures/gui/jei/cooking.png");
    @Nonnull
    private final ICraftingGridHelper craftingGrid;
    @Nonnull
    private IDrawableAnimated flame;

    private final String uid;

    private IGuiHelper helper;

    public CookingPotRecipeCategory(IGuiHelper helper, String uid) {
        super(helper.createDrawable(guiTexture, 5, 6, 158, 60), String.format("inv.%s.name", uid.substring(4)));
        this.helper = helper;
        this.uid = uid;
        craftingGrid = helper.createCraftingGridHelper(inputSlots, outputSlot);
        IDrawableStatic flameDrawable = helper.createDrawable(guiTexture, 176, 0, 14, 14);
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
        flame.draw(minecraft, 80, 19);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull BulkRecipeWrapper<CookingPotRecipe> wrapper, @Nonnull IIngredients ingredients) {

        IDrawableStatic flameDrawable = helper.createDrawable(guiTexture, 176, wrapper.getRecipe().getHeat() > 1 ? 16 : 0, 14, 14);
        this.flame = helper.createAnimatedDrawable(flameDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);

        IGuiItemStackGroup stacks = layout.getItemStacks();

        stacks.init(outputSlot, false, 118, 18);
        stacks.init(outputSlot + 1, false, 118 + 18, 18);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = inputSlots + i + (j * 3);
                stacks.init(index, true, 2 + i * 18, j * 18);
            }
        }
        stacks.set(outputSlot, wrapper.getRecipe().getOutputs());

        List<List<ItemStack>> inputList = ingredients.getInputs(ItemStack.class);
        craftingGrid.setInputs(stacks, inputList);
    }
}
