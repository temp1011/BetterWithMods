package betterwithmods.module.gameplay;

import betterwithmods.common.BWMItems;
import betterwithmods.common.BWRegistry;
import betterwithmods.common.items.ItemBark;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.module.Feature;
import betterwithmods.util.StackIngredient;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.oredict.OreIngredient;

import java.util.Map;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class CauldronRecipes extends Feature {
    public CauldronRecipes() {
        canDisable = false;
    }

    @Override
    public void init(FMLInitializationEvent event) {
        //TODO

        BWRegistry.CAULDRON.addUnstokedRecipe(Lists.newArrayList(new OreIngredient("dustPotash"), StackIngredient.fromOre(4, "dustHellfire")),
                Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.NETHER_SLUDGE, 8)));
        BWRegistry.CAULDRON.addUnstokedRecipe(Lists.newArrayList(new OreIngredient("dustHellfire"), new OreIngredient("dustCarbon")), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.NETHERCOAL, 4)));

        BWRegistry.CAULDRON.addUnstokedRecipe(Lists.newArrayList(new OreIngredient("foodFlour"), Ingredient.fromItem(Items.SUGAR)), Lists.newArrayList(new ItemStack(BWMItems.DONUT, 4)));
        BWRegistry.CAULDRON.addUnstokedRecipe(Lists.newArrayList(new OreIngredient("dustHellfire"), Ingredient.fromStacks(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TALLOW))), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.BLASTING_OIL, 2)));

        BWRegistry.CAULDRON.addUnstokedRecipe(Lists.newArrayList(StackIngredient.fromOre(8, "dustHellfire")), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.CONCENTRATED_HELLFIRE)));
        BWRegistry.CAULDRON.addUnstokedRecipe(new OreIngredient("blockCactus"), new ItemStack(Items.DYE, 1, 2));
        BWRegistry.CAULDRON.addUnstokedRecipe(Lists.newArrayList(new OreIngredient("string"),new OreIngredient("dustGlowstone"), new OreIngredient("dustRedstone")), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.FILAMENT)));
        BWRegistry.CAULDRON.addUnstokedRecipe(Lists.newArrayList(new OreIngredient("fiberHemp"),new OreIngredient("dustGlowstone"), new OreIngredient("dustRedstone")), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.FILAMENT)));

        BWRegistry.CAULDRON.addUnstokedRecipe(Lists.newArrayList(new OreIngredient("string"),new OreIngredient("dustBlaze"), new OreIngredient("dustRedstone")), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.FILAMENT)));
        BWRegistry.CAULDRON.addUnstokedRecipe(Lists.newArrayList(new OreIngredient("fiberHemp"),new OreIngredient("dustBlaze"), new OreIngredient("dustRedstone")), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.FILAMENT)));

        BWRegistry.CAULDRON.addUnstokedRecipe(Lists.newArrayList(Ingredient.fromStacks(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER)), StackIngredient.fromOre(8,"barkBlood")), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TANNED_LEATHER)));



        for (BlockPlanks.EnumType type : BlockPlanks.EnumType.values()) {
            int meta = type.getMetadata();

            String bark = ItemBark.barkOres[meta];
            BWRegistry.CAULDRON.addUnstokedRecipe( Lists.newArrayList(Ingredient.fromStacks(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER)), StackIngredient.fromOre(ItemBark.getTanningStackSize(meta),bark)), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TANNED_LEATHER)));

            BWRegistry.CAULDRON.addUnstokedRecipe( Lists.newArrayList(
                    StackIngredient.fromStacks(2,ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER)),
                    StackIngredient.fromOre(ItemBark.getTanningStackSize(meta),bark)
            ), Lists.newArrayList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TANNED_LEATHER_CUT,2)));
        }

//        addCauldronRecipe(, new Object[] {});
//        addCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TANNED_LEATHER_CUT, 2), new Object[] {ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER_CUT, 2), new OreStack("barkBlood", 8)});
//        addCauldronRecipe(new ItemStack(Items.GUNPOWDER, 2, 0), new Object[]{"dustSulfur", "dustSaltpeter", "dustCarbon"});
//        addCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.FUSE), new Object[]{Items.GUNPOWDER, "string"});
//        addCauldronRecipe(new ItemStack(BWMBlocks.AESTHETIC, 4, BlockAesthetic.EnumType.CHOPBLOCK.getMeta()), new Object[]{new ItemStack(BWMBlocks.AESTHETIC, 4, BlockAesthetic.EnumType.CHOPBLOCKBLOOD.getMeta()), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SOAP)});
//        addCauldronRecipe(new ItemStack(Blocks.PISTON, 4), new Object[]{new ItemStack(Blocks.STICKY_PISTON, 4), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SOAP)});
//

        BWRegistry.CAULDRON.addStokedRecipe(new ItemStack(Items.LEATHER), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE));
//        addStokedCauldronRecipe(, new Object[]{});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE), new Object[]{ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TANNED_LEATHER)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE), new Object[]{ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE), new Object[]{ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.LEATHER_STRAP, 8)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE), new Object[]{ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.LEATHER_CUT, 2)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE), new Object[]{ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TANNED_LEATHER_CUT, 2)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE), new Object[]{ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER_CUT, 2)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE, 2), new Object[]{new ItemStack(Items.LEATHER_HELMET, 1, OreDictionary.WILDCARD_VALUE)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE, 2), new Object[]{new ItemStack(Items.LEATHER_BOOTS, 1, OreDictionary.WILDCARD_VALUE)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE, 3), new Object[]{new ItemStack(Items.LEATHER_LEGGINGS, 1, OreDictionary.WILDCARD_VALUE)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE, 4), new Object[]{new ItemStack(Items.LEATHER_CHESTPLATE, 1, OreDictionary.WILDCARD_VALUE)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE, 2), new Object[]{new ItemStack(BWMItems.LEATHER_TANNED_HELMET, 1, OreDictionary.WILDCARD_VALUE)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE, 2), new Object[]{new ItemStack(BWMItems.LEATHER_TANNED_BOOTS, 1, OreDictionary.WILDCARD_VALUE)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE, 3), new Object[]{new ItemStack(BWMItems.LEATHER_TANNED_PANTS, 1, OreDictionary.WILDCARD_VALUE)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE, 4), new Object[]{new ItemStack(BWMItems.LEATHER_TANNED_CHEST, 1, OreDictionary.WILDCARD_VALUE)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.GLUE, 1), new Object[]{new ItemStack(Items.BOOK, 2, OreDictionary.WILDCARD_VALUE)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TALLOW), new Object[]{new ItemStack(Items.COOKED_PORKCHOP)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TALLOW), new Object[]{new ItemStack(Items.PORKCHOP)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TALLOW), new Object[]{new ItemStack(Items.COOKED_BEEF, 4)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TALLOW), new Object[]{new ItemStack(Items.BEEF, 4)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TALLOW), new Object[]{new ItemStack(Items.MUTTON, 6)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TALLOW), new Object[]{new ItemStack(Items.COOKED_MUTTON, 6)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TALLOW), new Object[]{new ItemStack(Items.ROTTEN_FLESH, 10)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SOAP), new Object[]{ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TALLOW), ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.POTASH)});
//
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.POTASH), new Object[]{"logWood"});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.POTASH), new Object[]{new OreStack("plankWood", 6)});
//        addStokedCauldronRecipe(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.POTASH), new Object[]{new OreStack("dustWood", 16)});
//        addCauldronRecipe(new ItemStack(BWMItems.CHICKEN_SOUP, 3), new Object[]{new ItemStack(Items.COOKED_CHICKEN), new ItemStack(Items.CARROT), new ItemStack(Items.BAKED_POTATO), new ItemStack(Items.BOWL, 3)});
//        addCauldronRecipe(new ItemStack(BWMItems.CHOCOLATE, 2), new ItemStack(Items.BUCKET), new Object[]{"foodCocoapowder", new ItemStack(Items.SUGAR), new ItemStack(Items.MILK_BUCKET)});
//        addCauldronRecipe(new ItemStack(BWMItems.CHOWDER, 2), new ItemStack(Items.BUCKET), new Object[]{new ItemStack(Items.COOKED_FISH), new ItemStack(Items.MILK_BUCKET), new ItemStack(Items.BOWL, 2)});
//        addCauldronRecipe(new ItemStack(BWMItems.CHOWDER, 2), new ItemStack(Items.BUCKET), new Object[]{new ItemStack(Items.COOKED_FISH, 1, ItemFishFood.FishType.SALMON.getMetadata()), new ItemStack(Items.MILK_BUCKET), new ItemStack(Items.BOWL, 2)});
//        addCauldronRecipe(new ItemStack(BWMItems.HEARTY_STEW, 5), new Object[]{"listAllmeatcooked", Items.CARROT, Items.BAKED_POTATO, new ItemStack(Items.BOWL, 5), new ItemStack(Blocks.BROWN_MUSHROOM, 3), "foodFlour"});
//
//        CauldronRecipes.addCauldronRecipe(new ItemStack(Items.MUSHROOM_STEW), new ItemStack(Items.BUCKET), new Object[]{new ItemStack(Blocks.BROWN_MUSHROOM, 3), new ItemStack(Items.MILK_BUCKET), new ItemStack(Items.BOWL)});
//        CauldronRecipes.addCauldronRecipe(new ItemStack(Items.BEETROOT_SOUP), new Object[]{new ItemStack(Items.BEETROOT, 6), new ItemStack(Items.BOWL)});
//        CauldronRecipes.addCauldronRecipe(new ItemStack(Items.RABBIT_STEW, 5), new Object[]{Items.COOKED_RABBIT, Items.CARROT, Items.BAKED_POTATO, new ItemStack(Items.BOWL, 5), new ItemStack(Blocks.RED_MUSHROOM, 3), "foodFlour"});
//
//
//        addCauldronRecipe(new ItemStack(BWMItems.KIBBLE, 2), new ItemStack[]{new ItemStack(Items.DYE, 4, EnumDyeColor.WHITE.getDyeDamage()), new ItemStack(Items.ROTTEN_FLESH, 4), new ItemStack(Items.SUGAR)});
//        addCauldronRecipe(new ItemStack(BWMBlocks.BLOOD_SAPLING), new ItemStack[] {new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Blocks.SAPLING, 1, 1), new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(Blocks.SAPLING, 1, 3), new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(Blocks.SAPLING, 1, 5),
//        new ItemStack(Items.NETHER_WART), BlockUrn.getStack(BlockUrn.EnumType.FULL, 8)});
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        //Add all food recipes
        Map<ItemStack, ItemStack> furnace = FurnaceRecipes.instance().getSmeltingList();
        for (ItemStack input : furnace.keySet()) {
            if (input != null) {
                if (input.getItem() instanceof ItemFood && input.getItem() != Items.BREAD) {
                    ItemStack output = FurnaceRecipes.instance().getSmeltingResult(input);
                    if (!output.isEmpty()) {
//                        CauldronRecipes.addCauldronRecipe(new CauldronFoodRecipe(output.copy(), new Object[]{input.copy()}));
                    }
                }
            }
        }
    }

}
