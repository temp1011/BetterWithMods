package betterwithmods.module.gameplay;

import betterwithmods.BWMod;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWOreDictionary;
import betterwithmods.common.BWRegistry;
import betterwithmods.common.blocks.mechanical.tile.TileEntityFilteredHopper;
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
        )));

        BWRegistry.HOPPER_FILTERS.addFilter(new HopperFilter(BWMod.MODID + ":iron_bar", Ingredient.fromStacks(new ItemStack(Blocks.IRON_BARS)), Lists.newArrayList(
                new IngredientSpecial(stack -> stack.getMaxStackSize() > 1)
        )));

        BWRegistry.HOPPER_FILTERS.addFilter(new HopperFilter(BWMod.MODID + ":slats", new OreIngredient("slats"), Lists.newArrayList(
                //TODO flat items
                new IngredientSpecial(stack -> true)
        )));


        HopperInteractions.addHopperRecipe(new HopperInteractions.SoulUrnRecipe(new OreIngredient("dustNetherrack"), ItemStack.EMPTY, ItemMaterial.getStack(ItemMaterial.EnumMaterial.HELLFIRE_DUST)));
        HopperInteractions.addHopperRecipe(new HopperInteractions.SoulUrnRecipe(new OreIngredient("dustSoul"), ItemStack.EMPTY, ItemMaterial.getStack(ItemMaterial.EnumMaterial.SAWDUST)));
        if (brimstoneFiltering)
            HopperInteractions.addHopperRecipe(new HopperInteractions.SoulUrnRecipe(new OreIngredient("dustGlowstone"), ItemStack.EMPTY, ItemMaterial.getStack(ItemMaterial.EnumMaterial.BRIMSTONE)));
        HopperInteractions.addHopperRecipe(new HopperInteractions.HopperRecipe(BWMod.MODID + ":wicker", Ingredient.fromStacks(new ItemStack(Blocks.GRAVEL)), Lists.newArrayList(new ItemStack(Blocks.SAND), new ItemStack(Blocks.SAND, 1, 1)), Lists.newArrayList(new ItemStack(Items.FLINT))) {
            @Override
            public void craft(EntityItem inputStack, World world, BlockPos pos) {
                TileEntityFilteredHopper hopper = (TileEntityFilteredHopper) world.getTileEntity(pos);
                if (hopper != null) {
                    SimpleStackHandler inventory = hopper.inventory;
                    ItemStack sand = secondaryOutputs.get(world.rand.nextInt(secondaryOutputs.size())).copy();
                    ItemStack remainder = InvUtils.insert(inventory, sand, false);
                    if (!remainder.isEmpty())
                        InvUtils.ejectStackWithOffset(world, inputStack.getPosition(), remainder);
                    InvUtils.ejectStackWithOffset(world, inputStack.getPosition(), secondaryOutputs);
                    onCraft(world, pos, inputStack);
                }
            }
        });
        HopperInteractions.addHopperRecipe(new HopperInteractions.HopperRecipe(BWMod.MODID + ":soul_sand", new OreIngredient("sand"), ItemStack.EMPTY, new ItemStack(Blocks.SOUL_SAND)) {
            @Override
            public boolean canCraft(World world, BlockPos pos) {
                TileEntityFilteredHopper hopper = (TileEntityFilteredHopper) world.getTileEntity(pos);
                assert hopper != null;
                return super.canCraft(world, pos) && hopper.soulsRetained > 0;
            }

            @Override
            public void onCraft(World world, BlockPos pos, EntityItem item) {
                TileEntityFilteredHopper hopper = (TileEntityFilteredHopper) world.getTileEntity(pos);
                if (hopper != null)
                    hopper.decreaseSoulCount(1);
                super.onCraft(world, pos, item);
            }
        });
    }
}


