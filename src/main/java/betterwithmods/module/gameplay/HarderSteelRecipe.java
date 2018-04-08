package betterwithmods.module.gameplay;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWRegistry;
import betterwithmods.common.blocks.BlockAesthetic;
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
        BWRegistry.CRUCIBLE.addStokedRecipe(Ingredient.fromStacks(ItemMaterial.getStack(ItemMaterial.EnumMaterial.ENDER_SLAG)), Lists.newArrayList(ItemMaterial.getStack(ItemMaterial.EnumMaterial.BRIMSTONE), ItemMaterial.getStack(ItemMaterial.EnumMaterial.SOUL_FLUX)));
        BWRegistry.CRUCIBLE.addStokedRecipe(Lists.newArrayList(new OreIngredient("blockSoulUrn"), new OreIngredient("ingotIron"), new OreIngredient("dustCarbon"), Ingredient.fromStacks(ItemMaterial.getStack(ItemMaterial.EnumMaterial.SOUL_FLUX))), Lists.newArrayList(ItemMaterial.getStack(ItemMaterial.EnumMaterial.STEEL_INGOT), new ItemStack(BWMBlocks.URN, 1, 0)));
        BWRegistry.KILN.addStokedRecipe(new ItemStack(Blocks.END_STONE), Lists.newArrayList(BlockAesthetic.getStack(BlockAesthetic.EnumType.WHITECOBBLE), ItemMaterial.getStack(ItemMaterial.EnumMaterial.ENDER_SLAG)));
    }

    @Override
    public void disabledInit(FMLInitializationEvent event) {
        BWRegistry.CRUCIBLE.addStokedRecipe(Ingredient.fromStacks(ItemMaterial.getStack(ItemMaterial.EnumMaterial.ENDER_SLAG)), Lists.newArrayList(ItemMaterial.getStack(ItemMaterial.EnumMaterial.BRIMSTONE)));
        BWRegistry.CRUCIBLE.addStokedRecipe(Lists.newArrayList(new OreIngredient("blockSoulUrn"), new OreIngredient("ingotIron"), new OreIngredient("dustCarbon")), Lists.newArrayList(ItemMaterial.getStack(ItemMaterial.EnumMaterial.STEEL_INGOT), new ItemStack(BWMBlocks.URN, 1, 0)));
        BWRegistry.KILN.addStokedRecipe(new ItemStack(Blocks.END_STONE), Lists.newArrayList(BlockAesthetic.getStack(BlockAesthetic.EnumType.WHITECOBBLE), ItemMaterial.getStack(ItemMaterial.EnumMaterial.BRIMSTONE)));
    }
}
