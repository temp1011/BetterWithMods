package betterwithmods.module.gameplay;

import betterwithmods.BWMod;
import betterwithmods.api.recipe.impl.ChanceOutput;
import betterwithmods.api.recipe.impl.ListOutputs;
import betterwithmods.api.recipe.impl.WeightedOutputs;
import betterwithmods.client.model.filters.ModelGrate;
import betterwithmods.client.model.filters.ModelSlats;
import betterwithmods.client.model.filters.ModelWithResource;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWOreDictionary;
import betterwithmods.common.BWRegistry;
import betterwithmods.common.blocks.mechanical.tile.TileFilteredHopper;
import betterwithmods.common.blocks.mechanical.tile.TileFilteredHopper;
import betterwithmods.common.blocks.tile.SimpleStackHandler;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.common.registry.HopperFilter;
import betterwithmods.common.registry.HopperInteractions;
import betterwithmods.common.registry.SoulsandFilter;
import betterwithmods.common.registry.block.recipe.IngredientSpecial;
import betterwithmods.module.Feature;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreIngredient;

/**
 * Created by primetoxinz on 6/23/17.
 */
public class HopperRecipes extends Feature {
    public static boolean brimstoneFiltering;

    public HopperRecipes() {
        canDisable = false;
    }


    @Override
    public void setupConfig() {
        brimstoneFiltering = loadPropBool("Glowstone Filtering", "Passing glowstone through a soulsand filter makes brimstone.", false);
    }

    @Override
    public void init(FMLInitializationEvent event) {

        BWRegistry.HOPPER_FILTERS.addFilter(new HopperFilter(BWMod.MODID + ":ladder", Ingredient.fromStacks(new ItemStack(Blocks.LADDER)), Lists.newArrayList(
                new IngredientSpecial(stack -> !(stack.getItem() instanceof ItemBlock)),
                new OreIngredient("treeSapling")
        )));

        BWRegistry.HOPPER_FILTERS.addFilter(new SoulsandFilter(Ingredient.fromStacks(new ItemStack(Blocks.SOUL_SAND)), Lists.newArrayList(Ingredient.fromStacks(new ItemStack(Blocks.SOUL_SAND)))));

        BWRegistry.HOPPER_FILTERS.addFilter(new HopperFilter(BWMod.MODID + ":wicker", Ingredient.fromStacks(new ItemStack(BWMBlocks.WICKER)), Lists.newArrayList(
                new OreIngredient("sand"),
                new OreIngredient("listAllseeds"),
                new OreIngredient("foodFlour"),
                new OreIngredient("pile"),
                new IngredientSpecial(stack -> BWOreDictionary.dustNames.stream().anyMatch(ore -> ore.apply(stack)))
        )));

        BWRegistry.HOPPER_FILTERS.addFilter(new HopperFilter(BWMod.MODID + ":trapdoor", Ingredient.fromStacks(new ItemStack(Blocks.TRAPDOOR)), Lists.newArrayList(
                new IngredientSpecial(stack -> stack.getItem() instanceof ItemBlock)
        )));

        BWRegistry.HOPPER_FILTERS.addFilter(new HopperFilter(BWMod.MODID + ":grates", new OreIngredient("grates"), Lists.newArrayList(
                new IngredientSpecial(stack -> stack.getMaxStackSize() == 1)
        )) {
            @Override
            public ModelWithResource getModelOverride(ItemStack filter) {
                return new ModelGrate(filter);
            }
        });

        BWRegistry.HOPPER_FILTERS.addFilter(new HopperFilter(BWMod.MODID + ":iron_bar", Ingredient.fromStacks(new ItemStack(Blocks.IRON_BARS)), Lists.newArrayList(
                new IngredientSpecial(stack -> stack.getMaxStackSize() > 1)
        )));

        BWRegistry.HOPPER_FILTERS.addFilter(new HopperFilter(BWMod.MODID + ":slats", new OreIngredient("slats"), Lists.newArrayList(
                //TODO flat items
                new IngredientSpecial(stack -> true)
        )) {
            @Override
            public ModelWithResource getModelOverride(ItemStack filter) {
                return new ModelSlats(filter);
            }
        });

        HopperInteractions.addHopperRecipe(new HopperInteractions.HopperRecipe(BWMod.MODID + ":wicker", new OreIngredient("dustRedstone"), Lists.newArrayList(new ItemStack(Items.GLOWSTONE_DUST)), Lists.newArrayList(new ItemStack(Items.GUNPOWDER))));

        HopperInteractions.addHopperRecipe(new HopperInteractions.SoulUrnRecipe(new OreIngredient("dustNetherrack"), ItemStack.EMPTY, ItemMaterial.getStack(ItemMaterial.EnumMaterial.HELLFIRE_DUST)));
        HopperInteractions.addHopperRecipe(new HopperInteractions.SoulUrnRecipe(new OreIngredient("dustSoul"), ItemStack.EMPTY, ItemMaterial.getStack(ItemMaterial.EnumMaterial.SAWDUST)));
        if (brimstoneFiltering)
            HopperInteractions.addHopperRecipe(new HopperInteractions.SoulUrnRecipe(new OreIngredient("dustGlowstone"), ItemStack.EMPTY, ItemMaterial.getStack(ItemMaterial.EnumMaterial.BRIMSTONE)));

        HopperInteractions.addHopperRecipe(new HopperInteractions.HopperRecipe(BWMod.MODID + ":wicker",
                Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)),
                new WeightedOutputs(new ChanceOutput(new ItemStack(Blocks.SAND), 0.5), new ChanceOutput(new ItemStack(Blocks.SAND, 1, 1), 0.5)),
                new ListOutputs(new ItemStack(Items.FLINT))
        ));
        
        HopperInteractions.addHopperRecipe(new HopperInteractions.HopperRecipe(BWMod.MODID + ":soul_sand", new OreIngredient("sand"), ItemStack.EMPTY, new ItemStack(Blocks.SOUL_SAND)) {
            @Override
            public boolean canCraft(World world, BlockPos pos) {
                TileFilteredHopper hopper = (TileFilteredHopper) world.getTileEntity(pos);
                assert hopper != null;
                return super.canCraft(world, pos) && hopper.soulsRetained > 0;
            }

            @Override
            public void onCraft(World world, BlockPos pos, EntityItem item, TileFilteredHopper tile) {
                if (tile != null)
                    tile.decreaseSoulCount(1);
                super.onCraft(world, pos, item, tile);
            }
        });
    }
}


