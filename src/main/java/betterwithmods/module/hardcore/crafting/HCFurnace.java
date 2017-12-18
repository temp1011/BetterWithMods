package betterwithmods.module.hardcore.crafting;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.blocks.BlockFurnace;
import betterwithmods.module.Feature;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.HashMap;

public class HCFurnace extends Feature {
    public HCFurnace() {
        enabledByDefault = false;
    }

    public static HashMap<Ingredient, Integer> FURNACE_TIMINGS = Maps.newHashMap();

    public static final Block FURNACE = new BlockFurnace(true).setRegistryName("minecraft:furnace");
    public static final Block LIT_FURNACE = new BlockFurnace(true).setRegistryName("minecraft:lit_furnace");


    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BWMBlocks.registerBlock(FURNACE);
        BWMBlocks.registerBlock(LIT_FURNACE);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        FURNACE_TIMINGS = loadItemStackIntMap("Furnace Timing Recipes", "example recipes  minecraft:iron_ore=1000  or ore:oreIron=1000", new String[]{
                "ore:oreIron=400",
                "ore:oreGold=400",
                "ore:stone=800"
        });
    }
}

