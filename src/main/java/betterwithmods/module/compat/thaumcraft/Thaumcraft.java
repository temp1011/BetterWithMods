package betterwithmods.module.compat.thaumcraft;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWMItems;
import betterwithmods.common.BWMRecipes;
import betterwithmods.common.blocks.BlockPlanter;
import betterwithmods.common.blocks.BlockUnfiredPottery;
import betterwithmods.common.items.ItemMaterial;
import betterwithmods.module.CompatFeature;
import betterwithmods.module.gameplay.miniblocks.ItemMini;
import betterwithmods.module.gameplay.miniblocks.MiniBlocks;
import betterwithmods.module.gameplay.miniblocks.MiniType;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMini;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class Thaumcraft extends CompatFeature {
    public Thaumcraft() {
        super("thaumcraft");
    }

    private static AspectList getMini(ItemStack stack) {
        IBlockState state = ItemMini.getState(stack);
        AspectList list = new AspectList(BWMRecipes.getStackFromState(state));
        for (Aspect aspect : list.getAspects()) {
            int count = list.getAmount(aspect);
            list.remove(aspect, count / 2);
        }
        return list.copy();
    }


    @Override
    public void postInit(FMLPostInitializationEvent event) {
        registerAspectOverrides();
        registerAspects();
    }

    public void registerAspectOverrides() {

        //Fix the Axe recipe changing the aspects to have metal
        //TODO remove this if fixed by Azanor
        ThaumcraftApi.registerComplexObjectTag("plankWood", new AspectList().add(Aspect.PLANT, 1));
        //This happens due to shears too
        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.LEATHER_CUT), new AspectList().add(Aspect.BEAST, 2).add(Aspect.PROTECT, 2));
    }

    public void registerAspects() {

        //Hemp
        ThaumcraftApi.registerObjectTag("cropHemp", new AspectList().add(Aspect.PLANT, 6).add(Aspect.CRAFT, 6));
        ThaumcraftApi.registerObjectTag(new ItemStack(BWMBlocks.HEMP), new AspectList().add(Aspect.PLANT, 5).add(Aspect.LIFE, 1));

        ThaumcraftApi.registerObjectTag("fiberHemp", new AspectList().add(Aspect.PLANT, 2).add(Aspect.CRAFT, 2));
        ThaumcraftApi.registerObjectTag("fabricHemp", new AspectList().add(Aspect.PLANT, 18).add(Aspect.CRAFT, 18));

        //Misc blocks
        ThaumcraftApi.registerObjectTag(new ItemStack(BWMBlocks.FERTILE_FARMLAND), new AspectList(new ItemStack(Blocks.FARMLAND)).merge(new AspectList(new ItemStack(Items.DYE, EnumDyeColor.WHITE.getDyeDamage()))));

        //Leather
        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER), new AspectList().add(Aspect.BEAST, 5).add(Aspect.PROTECT, 5));
        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.SCOURED_LEATHER_CUT), new AspectList().add(Aspect.BEAST, 2).add(Aspect.PROTECT, 2));
        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TANNED_LEATHER), new AspectList().add(Aspect.BEAST, 4).add(Aspect.PROTECT, 6));
        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.TANNED_LEATHER_CUT), new AspectList().add(Aspect.BEAST, 2).add(Aspect.PROTECT, 3));
        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.LEATHER_STRAP), new AspectList().add(Aspect.BEAST, 1).add(Aspect.CRAFT, 1));
        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.LEATHER_BELT), new AspectList().add(Aspect.BEAST, 4).add(Aspect.CRAFT, 4));

        //Tanned leather armor
        ThaumcraftApi.registerObjectTag(new ItemStack(BWMItems.LEATHER_TANNED_HELMET), new AspectList(new ItemStack(Items.LEATHER_HELMET)).add(Aspect.PROTECT, 4));
        ThaumcraftApi.registerObjectTag(new ItemStack(BWMItems.LEATHER_TANNED_CHEST), new AspectList(new ItemStack(Items.LEATHER_CHESTPLATE)).add(Aspect.PROTECT, 4));
        ThaumcraftApi.registerObjectTag(new ItemStack(BWMItems.LEATHER_TANNED_PANTS), new AspectList(new ItemStack(Items.LEATHER_LEGGINGS)).add(Aspect.PROTECT, 4));
        ThaumcraftApi.registerObjectTag(new ItemStack(BWMItems.LEATHER_TANNED_BOOTS), new AspectList(new ItemStack(Items.LEATHER_BOOTS)).add(Aspect.PROTECT, 4));

        //Unfired pottery
        ThaumcraftApi.registerObjectTag(BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.CRUCIBLE), new AspectList().add(Aspect.WATER, 15).add(Aspect.EARTH, 15));
        ThaumcraftApi.registerObjectTag(BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.PLANTER), new AspectList().add(Aspect.WATER, 15).add(Aspect.EARTH, 15));
        ThaumcraftApi.registerObjectTag(BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.VASE), new AspectList().add(Aspect.WATER, 10).add(Aspect.EARTH, 10));
        ThaumcraftApi.registerObjectTag(BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.URN), new AspectList().add(Aspect.WATER, 5).add(Aspect.EARTH, 5));
        ThaumcraftApi.registerObjectTag(BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.BRICK), new AspectList().add(Aspect.WATER, 5).add(Aspect.EARTH, 5));

        ThaumcraftApi.registerObjectTag("blockVase", new AspectList(BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.VASE)).add(Aspect.FIRE, 1));
        ThaumcraftApi.registerObjectTag("blockUrn", new AspectList(BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.URN)).add(Aspect.FIRE, 1));
        ThaumcraftApi.registerObjectTag("blockSoulUrn", new AspectList(BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.URN)).add(Aspect.FIRE, 1).add(Aspect.SOUL, 16));

        //Planters
        ThaumcraftApi.registerObjectTag(BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY), new AspectList(BlockUnfiredPottery.getStack(BlockUnfiredPottery.EnumType.PLANTER)).add(Aspect.FIRE, 1));
        ThaumcraftApi.registerObjectTag(BlockPlanter.getStack(BlockPlanter.EnumType.FARMLAND), new AspectList(BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY)).add(new AspectList(new ItemStack(Blocks.FARMLAND))));
        ThaumcraftApi.registerObjectTag(BlockPlanter.getStack(BlockPlanter.EnumType.FERTILE), new AspectList(BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY)).add(new AspectList(new ItemStack(BWMBlocks.FERTILE_FARMLAND))));
        ThaumcraftApi.registerObjectTag(BlockPlanter.getStack(BlockPlanter.EnumType.GRASS), new AspectList(BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY)).add(new AspectList(new ItemStack(Blocks.GRASS))));
        ThaumcraftApi.registerObjectTag(BlockPlanter.getStack(BlockPlanter.EnumType.DIRT), new AspectList(BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY)).add(new AspectList(new ItemStack(Blocks.DIRT))));
        ThaumcraftApi.registerObjectTag(BlockPlanter.getStack(BlockPlanter.EnumType.SOULSAND), new AspectList(BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY)).add(new AspectList(new ItemStack(Blocks.SOUL_SAND))));
        ThaumcraftApi.registerObjectTag(BlockPlanter.getStack(BlockPlanter.EnumType.SAND), new AspectList(BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY)).add(new AspectList(new ItemStack(Blocks.SAND))));
        ThaumcraftApi.registerObjectTag(BlockPlanter.getStack(BlockPlanter.EnumType.REDSAND), new AspectList(BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY)).add(new AspectList(new ItemStack(Blocks.SAND, 1, BlockSand.EnumType.RED_SAND.getMetadata()))));
        ThaumcraftApi.registerObjectTag(BlockPlanter.getStack(BlockPlanter.EnumType.GRAVEL), new AspectList(BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY)).add(new AspectList(new ItemStack(Blocks.GRAVEL))));
        ThaumcraftApi.registerObjectTag(BlockPlanter.getStack(BlockPlanter.EnumType.WATER), new AspectList(BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY)).add(Aspect.WATER, 20));

        //Redstone components
        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.REDSTONE_LATCH), new AspectList().add(Aspect.METAL, 3).add(Aspect.DESIRE,3).add(Aspect.ENERGY,10));
        ThaumcraftApi.registerObjectTag(new ItemStack(BWMBlocks.WOODEN_GEARBOX), new AspectList(new ItemStack(BWMBlocks.WOODEN_GEARBOX)).add(new AspectList(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.REDSTONE_LATCH))));

        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.CHARCOAL_DUST), new AspectList().add(Aspect.ENERGY, 10).add(Aspect.FIRE,10));
        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.COAL_DUST), new AspectList().add(Aspect.ENERGY, 10).add(Aspect.FIRE,10));

        ThaumcraftApi.registerObjectTag(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.INGOT_STEEL), new AspectList().add(Aspect.METAL, 15).add(Aspect.ENERGY, 10).add(Aspect.FIRE,10).add(Aspect.SOUL, 16));

        for (MiniType type : MiniBlocks.MINI_MATERIAL_BLOCKS.keySet()) {
            for (BlockMini mini : MiniBlocks.MINI_MATERIAL_BLOCKS.get(type).values()) {
                ItemMini item = (ItemMini) Item.getItemFromBlock(mini);
                if (item.getCreativeTab() != null) {
                    NonNullList<ItemStack> subItems = NonNullList.create();
                    item.getSubItems(item.getCreativeTab(), subItems);
                    for (ItemStack stack : subItems) {
                        ThaumcraftApi.registerComplexObjectTag(stack, getMini(stack));
                    }
                }
            }
        }
    }
}
