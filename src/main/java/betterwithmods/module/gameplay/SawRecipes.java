package betterwithmods.module.gameplay;

import betterwithmods.common.BWRegistry;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.common.registry.blockmeta.recipe.BlockIngredient;
import betterwithmods.module.Feature;
import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class SawRecipes extends Feature {
    public SawRecipes() {
        canDisable = false;
    }

//
//    public static void addSteelSawRecipe(Block block, int meta, ItemStack output) {
//        SawManager.STEEL_SAW.addRecipe(block, meta, new ItemStack[]{output});
//    }
//
//    public static void addSawRecipe(Block block, int meta, ItemStack output) {
//        SawManager.WOOD_SAW.addRecipe(block, meta, new ItemStack[]{output});
//    }
//
//    public static void addSawRecipe(Block block, int meta, ItemStack... outputs) {
//        SawManager.WOOD_SAW.addRecipe(block, meta, outputs);
//    }
//
//    public static void addSelfSawRecipe(Block block, int meta) {
//        addSawRecipe(new SawSelfDropRecipe(block, meta));
//    }

//    public static void addSawRecipe(SawRecipe recipe) {
//        SawManager.WOOD_SAW.addRecipe(recipe);
//    }

    @Override
    public void init(FMLInitializationEvent event) {


        BWRegistry.WOOD_SAW.addRecipe(new BlockIngredient("cornerWood"), Lists.newArrayList(
                ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GEAR,2)
        ));
        //TODO
//        for (BlockPlanks.EnumType type : BlockPlanks.EnumType.values()) {
//            addSawRecipe(Blocks.PLANKS, type.getMetadata(), new ItemStack(BWMBlocks.WOOD_SIDING, 2, type.getMetadata()));
//            addSawRecipe(BWMBlocks.WOOD_CORNER, type.getMetadata(), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GEAR, 2));
//            addSawRecipe(BWMBlocks.WOOD_MOULDING, type.getMetadata(), new ItemStack(BWMBlocks.WOOD_CORNER, 2, type.getMetadata()));
//            addSawRecipe(BWMBlocks.WOOD_SIDING, type.getMetadata(), new ItemStack(BWMBlocks.WOOD_MOULDING, 2, type.getMetadata()));
//            addSawRecipe(Blocks.WOODEN_SLAB, type.getMetadata(), new ItemStack(BWMBlocks.WOOD_MOULDING, 2, type.getMetadata()));
//        }
//        addSelfSawRecipe(Blocks.PUMPKIN, 0);
//        addSelfSawRecipe(Blocks.VINE, 0);
//        for (int i = 0; i < 9; i++)
//            addSelfSawRecipe(Blocks.RED_FLOWER, i);
//        addSelfSawRecipe(Blocks.YELLOW_FLOWER, 0);
//        addSelfSawRecipe(Blocks.BROWN_MUSHROOM, 0);
//        addSelfSawRecipe(Blocks.RED_MUSHROOM, 0);
//	    addSelfSawRecipe(BWMBlocks.ROPE, 0);
//	    SawManager.WOOD_SAW.addRecipe(new SawRecipe(Blocks.MELON_BLOCK, 0, null) {
//            @Override
//            public NonNullList<ItemStack> getOutputs() {
//                Random random = new Random();
//                return InvUtils.asNonnullList(new ItemStack(Items.MELON, 3 + random.nextInt(5)));
//            }
//        });
//        addSteelSawRecipe(Blocks.STONE, 0, new ItemStack(BWMBlocks.STONE_SIDING, 2, 0));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
//        int count = ModuleLoader.isFeatureEnabled(HCLumber.class) ? 4 : 6;
//        for (IWood wood : BWOreDictionary.woods) {
//            addSawRecipe(new SawRecipe(wood.getLog(1), Lists.newArrayList(wood.getPlank(count), wood.getBark(1), wood.getSawdust(2))));
//        }
    }

//    public static class SawSelfDropRecipe extends SawRecipe {
//        public SawSelfDropRecipe(Block block, int meta) {
//            super(block, meta, Lists.newArrayList(new ItemStack(block, 1, meta)));
//        }
//    }


}
