package betterwithmods.module.compat.jei;

import betterwithmods.client.container.anvil.ContainerSteelAnvil;
import betterwithmods.client.container.anvil.GuiSteelAnvil;
import betterwithmods.client.container.bulk.GuiCauldron;
import betterwithmods.client.container.bulk.GuiCrucible;
import betterwithmods.client.container.bulk.GuiMill;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWMItems;
import betterwithmods.common.BWRegistry;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.common.registry.HopperInteractions;
import betterwithmods.common.registry.anvil.AnvilCraftingManager;
import betterwithmods.common.registry.anvil.ShapedAnvilRecipe;
import betterwithmods.common.registry.anvil.ShapelessAnvilRecipe;
import betterwithmods.common.registry.block.recipe.KilnRecipe;
import betterwithmods.common.registry.block.recipe.SawRecipe;
import betterwithmods.common.registry.block.recipe.TurntableRecipe;
import betterwithmods.common.registry.bulk.recipes.CookingPotRecipe;
import betterwithmods.common.registry.bulk.recipes.MillRecipe;
import betterwithmods.common.registry.crafting.ToolBaseRecipe;
import betterwithmods.common.registry.crafting.ToolDamageRecipe;
import betterwithmods.common.registry.heat.BWMHeatRegistry;
import betterwithmods.module.compat.jei.category.*;
import betterwithmods.module.compat.jei.wrapper.*;
import com.google.common.collect.Lists;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IVanillaRecipeFactory;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mezz.jei.gui.Focus;
import mezz.jei.plugins.vanilla.crafting.ShapelessRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@mezz.jei.api.JEIPlugin
public class JEI implements IModPlugin {
    public static IJeiHelpers HELPER;

    public static IJeiRuntime JEI_RUNTIME;

    public static void showRecipe(Ingredient ingredient) {
        ItemStack stack = Lists.newArrayList(ingredient.getMatchingStacks()).stream().findFirst().orElse(ItemStack.EMPTY);
        if (stack.isEmpty())
            return;
        IFocus<?> focus = new Focus<Object>(IFocus.Mode.OUTPUT, stack);
        JEI.JEI_RUNTIME.getRecipesGui().show(focus);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        if (JEI_RUNTIME == null) {
            JEI_RUNTIME = jeiRuntime;
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg) {
        final IJeiHelpers helpers = reg.getJeiHelpers();
        final IGuiHelper guiHelper = helpers.getGuiHelper();
        reg.addRecipeCategories(
                new CookingPotRecipeCategory(guiHelper, CookingPotRecipeCategory.CRUCIBLE_UNSTOKED_UID),
                new CookingPotRecipeCategory(guiHelper, CookingPotRecipeCategory.CRUCIBLE_STOKED_UID),
                new CookingPotRecipeCategory(guiHelper, CookingPotRecipeCategory.CAULDRON_UNSTOKED_UID),
                new CookingPotRecipeCategory(guiHelper, CookingPotRecipeCategory.CAULDRON_STOKED_UID),
                new MillRecipeCategory(guiHelper),
                new SawRecipeCategory(guiHelper),
                new SteelSawRecipeCategory(guiHelper),
                new KilnRecipeCategory(guiHelper),
                new TurntableRecipeCategory(guiHelper),
                new HopperRecipeCategory(guiHelper),
                new SteelAnvilRecipeCategory(guiHelper)
        );
    }

    @Override
    public void register(@Nonnull IModRegistry reg) {
        HELPER = reg.getJeiHelpers();
        reg.handleRecipes(CookingPotRecipe.class, recipe -> new BulkRecipeWrapper<>(HELPER, recipe), CookingPotRecipeCategory.CAULDRON_UNSTOKED_UID);
        reg.handleRecipes(CookingPotRecipe.class, recipe -> new BulkRecipeWrapper<>(HELPER, recipe), CookingPotRecipeCategory.CAULDRON_STOKED_UID);

        reg.handleRecipes(CookingPotRecipe.class, recipe -> new BulkRecipeWrapper<>(HELPER, recipe), CookingPotRecipeCategory.CRUCIBLE_UNSTOKED_UID);
        reg.handleRecipes(CookingPotRecipe.class, recipe -> new BulkRecipeWrapper<>(HELPER, recipe), CookingPotRecipeCategory.CRUCIBLE_STOKED_UID);

        reg.handleRecipes(MillRecipe.class, r -> new BulkRecipeWrapper<>(HELPER, r), MillRecipeCategory.UID);
        reg.handleRecipes(KilnRecipe.class, r -> new BlockRecipeWrapper(HELPER, r), KilnRecipeCategory.UID);
        reg.handleRecipes(SawRecipe.class, r -> new BlockRecipeWrapper(HELPER, r), SawRecipeCategory.UID);
        reg.handleRecipes(SawRecipe.class, r -> new BlockRecipeWrapper(HELPER, r), SteelSawRecipeCategory.UID);
        reg.handleRecipes(TurntableRecipe.class, recipe -> new TurntableRecipeWrapper(HELPER, recipe), TurntableRecipeCategory.UID);
        reg.handleRecipes(HopperInteractions.HopperRecipe.class, recipe -> recipe instanceof HopperInteractions.SoulUrnRecipe ? new HopperRecipeWrapper.SoulUrn((HopperInteractions.SoulUrnRecipe) recipe) : new HopperRecipeWrapper(recipe), HopperRecipeCategory.UID);
        reg.handleRecipes(ShapedAnvilRecipe.class, recipe -> new ShapedAnvilRecipeWrapper(HELPER, recipe), SteelCraftingCategory.UID);
        reg.handleRecipes(ShapelessAnvilRecipe.class, recipe -> new ShapelessRecipeWrapper<>(HELPER, recipe), SteelCraftingCategory.UID);
        reg.handleRecipes(ShapelessOreRecipe.class, recipe -> new ShapelessRecipeWrapper<>(HELPER, recipe), SteelCraftingCategory.UID);
        reg.handleRecipes(ShapelessRecipes.class, recipe -> new ShapelessRecipeWrapper<>(HELPER, recipe), SteelCraftingCategory.UID);
        reg.handleRecipes(ToolBaseRecipe.class, recipe -> new ShapelessRecipeWrapper<>(HELPER, recipe), SteelCraftingCategory.UID);
        reg.handleRecipes(ToolBaseRecipe.class, recipe -> new ShapelessRecipeWrapper<>(HELPER, recipe), VanillaRecipeCategoryUid.CRAFTING);
        reg.handleRecipes(ToolDamageRecipe.class, recipe -> new ShapelessRecipeWrapper<>(HELPER, recipe), SteelCraftingCategory.UID);
        reg.handleRecipes(ToolDamageRecipe.class, recipe -> new ShapelessRecipeWrapper<>(HELPER, recipe), "minecraft.crafting");

        reg.addRecipes(BWRegistry.CAULDRON.getRecipes().stream().filter(r -> r.getHeat() == BWMHeatRegistry.UNSTOKED_HEAT).collect(Collectors.toList()), CookingPotRecipeCategory.CAULDRON_UNSTOKED_UID);
        reg.addRecipes(BWRegistry.CAULDRON.getRecipes().stream().filter(r -> r.getHeat() == BWMHeatRegistry.STOKED_HEAT).collect(Collectors.toList()), CookingPotRecipeCategory.CAULDRON_STOKED_UID);

        reg.addRecipes(BWRegistry.CRUCIBLE.getRecipes().stream().filter(r -> r.getHeat() == BWMHeatRegistry.UNSTOKED_HEAT).collect(Collectors.toList()), CookingPotRecipeCategory.CRUCIBLE_UNSTOKED_UID);
        reg.addRecipes(BWRegistry.CRUCIBLE.getRecipes().stream().filter(r -> r.getHeat() == BWMHeatRegistry.STOKED_HEAT).collect(Collectors.toList()), CookingPotRecipeCategory.CRUCIBLE_STOKED_UID);

        reg.addRecipes(BWRegistry.MILLSTONE.getRecipes(), MillRecipeCategory.UID);
        reg.addRecipes(BWRegistry.WOOD_SAW.getRecipes(), SawRecipeCategory.UID);
//        reg.addRecipes(Sets.union(Sets.newHashSet(SawManager.WOOD_SAW.getRecipes()), Sets.newHashSet(SawManager.STEEL_SAW.getRecipes())), SteelSawRecipeCategory.UID);
        reg.addRecipes(BWRegistry.KILN.getRecipes(), KilnRecipeCategory.UID);
        reg.addRecipes(BWRegistry.TURNTABLE.getRecipes(), TurntableRecipeCategory.UID);
        reg.addRecipes(HopperInteractions.RECIPES, HopperRecipeCategory.UID);
        reg.addRecipes(AnvilCraftingManager.ANVIL_CRAFTING, SteelCraftingCategory.UID);

        reg.addRecipeCatalyst(new ItemStack(BWMBlocks.MILLSTONE), MillRecipeCategory.UID);
        reg.addRecipeCatalyst(new ItemStack(BWMBlocks.FILTERED_HOPPER), HopperRecipeCategory.UID);
        reg.addRecipeCatalyst(new ItemStack(BWMBlocks.TURNTABLE), TurntableRecipeCategory.UID);
        reg.addRecipeCatalyst(new ItemStack(BWMBlocks.CAULDRON),CookingPotRecipeCategory.CAULDRON_UNSTOKED_UID, CookingPotRecipeCategory.CAULDRON_STOKED_UID);
        reg.addRecipeCatalyst(new ItemStack(BWMBlocks.CRUCIBLE),CookingPotRecipeCategory.CRUCIBLE_UNSTOKED_UID, CookingPotRecipeCategory.CRUCIBLE_STOKED_UID);
        reg.addRecipeCatalyst(new ItemStack(BWMBlocks.SAW), SawRecipeCategory.UID);
        reg.addRecipeCatalyst(new ItemStack(BWMBlocks.STEEL_SAW), SteelSawRecipeCategory.UID);
        reg.addRecipeCatalyst(new ItemStack(Blocks.BRICK_BLOCK), KilnRecipeCategory.UID);
        reg.addRecipeCatalyst(new ItemStack(BWMBlocks.STEEL_ANVIL), SteelCraftingCategory.UID);

        reg.addRecipeClickArea(GuiCauldron.class, 81, 19, 14, 14, CookingPotRecipeCategory.CAULDRON_UNSTOKED_UID, CookingPotRecipeCategory.CAULDRON_STOKED_UID);
        reg.addRecipeClickArea(GuiCrucible.class, 81, 19, 14, 14, CookingPotRecipeCategory.CRUCIBLE_UNSTOKED_UID, CookingPotRecipeCategory.CRUCIBLE_STOKED_UID);
        reg.addRecipeClickArea(GuiMill.class, 81, 19, 14, 14, MillRecipeCategory.UID);
        reg.addRecipeClickArea(GuiSteelAnvil.class, 88, 41, 28, 23, SteelCraftingCategory.UID);

        registerAnvil(reg);

        IRecipeTransferRegistry recipeTransferRegistry = reg.getRecipeTransferRegistry();
        recipeTransferRegistry.addRecipeTransferHandler(ContainerSteelAnvil.class, SteelCraftingCategory.UID, 1, 16, 17, 36);

    }

    private void registerAnvil(IModRegistry reg) {
        List<ItemStack> tools = Lists.newArrayList(new ItemStack(BWMItems.STEEL_AXE), new ItemStack(BWMItems.STEEL_BATTLEAXE), new ItemStack(BWMItems.STEEL_BOOTS), new ItemStack(BWMItems.STEEL_CHEST), new ItemStack(BWMItems.STEEL_HELMET), new ItemStack(BWMItems.STEEL_HOE), new ItemStack(BWMItems.STEEL_MATTOCK),
                new ItemStack(BWMItems.STEEL_PANTS), new ItemStack(BWMItems.STEEL_PICKAXE), new ItemStack(BWMItems.STEEL_SHOVEL), new ItemStack(BWMItems.STEEL_SWORD));
        IVanillaRecipeFactory v = reg.getJeiHelpers().getVanillaRecipeFactory();
        for (ItemStack stack : tools) {
            ItemStack dam1 = stack.copy();
            dam1.setItemDamage(dam1.getMaxDamage());
            ItemStack dam2 = stack.copy();
            dam2.setItemDamage(dam2.getMaxDamage() * 3 / 4);
            ItemStack dam3 = stack.copy();
            dam3.setItemDamage(dam3.getMaxDamage() * 2 / 4);

            v.createAnvilRecipe(dam1, Collections.singletonList(ItemMaterial.getStack(ItemMaterial.EnumMaterial.STEEL_INGOT)), Collections.singletonList(dam2));
            v.createAnvilRecipe(dam2, Collections.singletonList(ItemMaterial.getStack(ItemMaterial.EnumMaterial.STEEL_INGOT)), Collections.singletonList(dam3));
        }
    }

}
