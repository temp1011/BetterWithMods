package betterwithmods.module.gameplay;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWRegistry;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.module.Feature;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreIngredient;

/**
 * Created by primetoxinz on 5/10/17.
 */
public class HarderSteelRecipe extends Feature {

    @Override
    public void setupConfig() {
        super.setupConfig();
    }

    @Override
    public String getFeatureDescription() {
        return "Whether Steel requires End Slag, a material only available after the End.";
    }

    @Override
    public void init(FMLInitializationEvent event) {
        BWRegistry.CAULDRON.addStokedRecipe(Ingredient.fromStacks(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.ENDER_SLAG)), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.BRIMSTONE), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SOUL_FLUX)));
        BWRegistry.CAULDRON.addStokedRecipe(Lists.newArrayList(new OreIngredient("blockSoulUrn"), new OreIngredient("ingotIron"), new OreIngredient("dustCarbon"), Ingredient.fromStacks(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SOUL_FLUX))), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.INGOT_STEEL), new ItemStack(BWMBlocks.URN, 1, 0)));
        KilnRecipes.addKilnRecipe(Blocks.END_STONE, 0, new ItemStack(BWMBlocks.AESTHETIC, 1, 7), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.ENDER_SLAG));
    }

    @Override
    public void disabledInit(FMLInitializationEvent event) {
        BWRegistry.CAULDRON.addStokedRecipe(Ingredient.fromStacks(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.ENDER_SLAG)), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.BRIMSTONE)));
        BWRegistry.CAULDRON.addStokedRecipe(Lists.newArrayList(new OreIngredient("blockSoulUrn"), new OreIngredient("ingotIron"), new OreIngredient("dustCarbon")), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.INGOT_STEEL), new ItemStack(BWMBlocks.URN, 1, 0)));
        KilnRecipes.addKilnRecipe(Blocks.END_STONE, 0, new ItemStack(BWMBlocks.AESTHETIC, 1, 7), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.BRIMSTONE));

    }
}
