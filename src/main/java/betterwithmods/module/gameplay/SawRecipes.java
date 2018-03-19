package betterwithmods.module.gameplay;

import betterwithmods.api.util.IWood;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWOreDictionary;
import betterwithmods.common.BWRegistry;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.common.registry.blockmeta.recipe.BlockIngredient;
import betterwithmods.common.registry.blockmeta.recipe.SawRecipe;
import betterwithmods.module.Feature;
import betterwithmods.module.ModuleLoader;
import betterwithmods.module.hardcore.crafting.HCLumber;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.util.Random;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class SawRecipes extends Feature {
    public SawRecipes() {
        canDisable = false;
    }

    @Override
    public void init(FMLInitializationEvent event) {
        BWRegistry.WOOD_SAW.addRecipe(new BlockIngredient("cornerWood"), Lists.newArrayList(
                ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GEAR, 2)));
        //TODO only works for one state.
        for (BlockPlanks.EnumType type : BlockPlanks.EnumType.values()) {
            BWRegistry.WOOD_SAW.addRecipe(new ItemStack(Blocks.PLANKS, 1, type.getMetadata()), new ItemStack(BWMBlocks.WOOD_SIDING, 2, type.getMetadata()));
            BWRegistry.WOOD_SAW.addRecipe(new ItemStack(BWMBlocks.WOOD_MOULDING, 1, type.getMetadata()), new ItemStack(BWMBlocks.WOOD_CORNER, 2, type.getMetadata()));
            BWRegistry.WOOD_SAW.addRecipe(new ItemStack(BWMBlocks.WOOD_SIDING, 1, type.getMetadata()), new ItemStack(BWMBlocks.WOOD_MOULDING, 2, type.getMetadata()));
            BWRegistry.WOOD_SAW.addRecipe(new ItemStack(Blocks.WOODEN_SLAB, 1, type.getMetadata()), new ItemStack(BWMBlocks.WOOD_MOULDING, 2, type.getMetadata()));
        }
        BWRegistry.WOOD_SAW.addSelfdropRecipe(new ItemStack(Blocks.PUMPKIN));
        BWRegistry.WOOD_SAW.addSelfdropRecipe(new ItemStack(Blocks.VINE));
        BWRegistry.WOOD_SAW.addSelfdropRecipe(new ItemStack(Blocks.YELLOW_FLOWER));
        BWRegistry.WOOD_SAW.addSelfdropRecipe(new ItemStack(Blocks.BROWN_MUSHROOM));
        BWRegistry.WOOD_SAW.addSelfdropRecipe(new ItemStack(Blocks.RED_MUSHROOM));
        BWRegistry.WOOD_SAW.addSelfdropRecipe(new ItemStack(BWMBlocks.ROPE));
        for (int i = 0; i < 9; i++)
            BWRegistry.WOOD_SAW.addSelfdropRecipe(new ItemStack(Blocks.RED_FLOWER, 1, i));
        BWRegistry.WOOD_SAW.addRecipe(new SawRecipe(new BlockIngredient(new ItemStack(Blocks.MELON_BLOCK)), NonNullList.create()) {
            @Override
            public NonNullList<ItemStack> getOutputs() {
                Random random = new Random();
                return InvUtils.asNonnullList(new ItemStack(Items.MELON, 3 + random.nextInt(5)));
            }
        });
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        int count = ModuleLoader.isFeatureEnabled(HCLumber.class) ? 4 : 6;
        for (IWood wood : BWOreDictionary.woods) {
            BWRegistry.WOOD_SAW.addRecipe(wood.getLog(1), Lists.newArrayList(wood.getPlank(count), wood.getBark(1), wood.getSawdust(2)));
        }
    }


}
