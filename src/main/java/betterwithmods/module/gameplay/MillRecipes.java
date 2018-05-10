package betterwithmods.module.gameplay;

import betterwithmods.BWMod;
import betterwithmods.api.recipe.RandomOutput;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWMRecipes;
import betterwithmods.common.BWRegistry;
import betterwithmods.common.blocks.BlockRawPastry;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.common.registry.bulk.recipes.MillRecipe;
import betterwithmods.module.Feature;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreIngredient;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class MillRecipes extends Feature {
    private boolean grindingOnly;


    public MillRecipes() {
        canDisable = false;
    }

    private static ItemStack getDye(EnumDyeColor color, int count) {
        return new ItemStack(Items.DYE, count, color.getDyeDamage());
    }

    @Override
    public void setupConfig() {
        grindingOnly = loadPropBool("Grinding Only", "Remove normal recipes for certain grindable items", true);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        if (grindingOnly) {
            BWMRecipes.removeRecipe(new ItemStack(Items.SUGAR));
            BWMRecipes.removeRecipe(new ItemStack(Items.BLAZE_POWDER));
            BWMRecipes.removeRecipe(getDye(EnumDyeColor.RED, 1));
            BWMRecipes.removeRecipe(getDye(EnumDyeColor.YELLOW, 1));
            BWMRecipes.removeRecipe(getDye(EnumDyeColor.LIGHT_BLUE, 1));
            BWMRecipes.removeRecipe(getDye(EnumDyeColor.MAGENTA, 1));
            BWMRecipes.removeRecipe(getDye(EnumDyeColor.SILVER, 1));
            BWMRecipes.removeRecipe(getDye(EnumDyeColor.ORANGE, 1));
            BWMRecipes.removeRecipe(getDye(EnumDyeColor.PINK, 1));
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        BWRegistry.MILLSTONE.addMillRecipe(new OreIngredient("netherrack"), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GROUND_NETHERRACK)), SoundEvents.ENTITY_GHAST_SCREAM);
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.BLAZE_ROD), Lists.newArrayList(new ItemStack(Items.BLAZE_POWDER, 3)), SoundEvents.ENTITY_BLAZE_DEATH);

        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(BWMBlocks.WOLF), Lists.newArrayList(new ItemStack(Items.STRING, 10), getDye(EnumDyeColor.RED, 3)), SoundEvents.ENTITY_WOLF_WHINE);
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.REEDS), Lists.newArrayList(new ItemStack(Items.SUGAR, 2)));

        BWRegistry.MILLSTONE.addMillRecipe(new OreIngredient("cropHemp"), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.HEMP_FIBERS, 3));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.COAL, 1), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.COAL_DUST));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.COAL, 1, 1), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.CHARCOAL_DUST));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.BONE), getDye(EnumDyeColor.WHITE, 6));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.SKULL, 1, 0), getDye(EnumDyeColor.WHITE, 10));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.SKULL, 1, 1), getDye(EnumDyeColor.WHITE, 10));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.BONE_BLOCK), getDye(EnumDyeColor.WHITE, 9));

        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.BEETROOT), getDye(EnumDyeColor.RED, 2));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.LEATHER), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.RABBIT_HIDE), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER_CUT));
        BWRegistry.MILLSTONE.addMillRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.LEATHER_CUT), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER_CUT));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 0), getDye(EnumDyeColor.RED, 4));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 4), getDye(EnumDyeColor.RED, 4));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.YELLOW_FLOWER, 1, 0), getDye(EnumDyeColor.YELLOW, 4));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 1), getDye(EnumDyeColor.LIGHT_BLUE, 4));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 2), getDye(EnumDyeColor.MAGENTA, 4));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 3), getDye(EnumDyeColor.SILVER, 4));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 6), getDye(EnumDyeColor.SILVER, 4));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 7), getDye(EnumDyeColor.PINK, 6));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 8), getDye(EnumDyeColor.SILVER, 4));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), getDye(EnumDyeColor.YELLOW, 6));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), getDye(EnumDyeColor.MAGENTA, 6));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), getDye(EnumDyeColor.RED, 6));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), getDye(EnumDyeColor.PINK, 6));
        BWRegistry.MILLSTONE.addMillRecipe(new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.COCOA_POWDER));
        BWRegistry.MILLSTONE.addMillRecipe(new OreIngredient("cropWheat"), BlockRawPastry.getStack(BlockRawPastry.EnumType.BREAD));
        BWRegistry.MILLSTONE.addMillRecipe(new OreIngredient("cropBarley"), BlockRawPastry.getStack(BlockRawPastry.EnumType.BREAD));
        BWRegistry.MILLSTONE.addMillRecipe(new OreIngredient("cropOats"), BlockRawPastry.getStack(BlockRawPastry.EnumType.BREAD));
        BWRegistry.MILLSTONE.addMillRecipe(new OreIngredient("cropRye"), BlockRawPastry.getStack(BlockRawPastry.EnumType.BREAD));
        BWRegistry.MILLSTONE.addMillRecipe(new OreIngredient("cropRice"), BlockRawPastry.getStack(BlockRawPastry.EnumType.BREAD));


        if(BWMod.isDev()) {

            RandomOutput.WeightedItemStack coal = new RandomOutput.WeightedItemStack(new ItemStack(Items.COAL), 0.75);
            RandomOutput.WeightedItemStack cobble = new RandomOutput.WeightedItemStack(new ItemStack(Blocks.COBBLESTONE), 0.25);

            BWRegistry.MILLSTONE.addRecipe(
                    new MillRecipe(
                            Lists.newArrayList(new OreIngredient("oreCoal")),
                            new RandomOutput(Lists.newArrayList(coal, cobble)), 0, SoundEvents.ENTITY_PLAYER_BURP, 5));


        }
    }
}


