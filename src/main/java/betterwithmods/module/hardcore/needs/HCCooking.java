package betterwithmods.module.hardcore.needs;

import betterwithmods.common.BWMItems;
import betterwithmods.common.BWMRecipes;
import betterwithmods.common.blocks.BlockRawPastry;
import betterwithmods.module.Feature;
import betterwithmods.module.gameplay.CauldronRecipes;
import betterwithmods.module.gameplay.KilnRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class HCCooking extends Feature{

	@Override
	public String getFeatureDescription() {
		return "Changes the recipes for baked goods to require the Kiln and changes soups to require the Cauldron.";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		BWMRecipes.removeRecipe(new ItemStack(Items.MUSHROOM_STEW));
		BWMRecipes.removeRecipe(new ItemStack(Items.CAKE));
		BWMRecipes.removeRecipe(new ItemStack(Items.COOKIE));
		BWMRecipes.removeRecipe(new ItemStack(Items.PUMPKIN_PIE));
		BWMRecipes.removeRecipe(new ItemStack(Items.RABBIT_STEW));
		BWMRecipes.removeRecipe(new ItemStack(Items.BEETROOT_SOUP));
		BWMRecipes.removeRecipe(new ItemStack(Items.BREAD));
	}

	@Override
	public void init(FMLInitializationEvent event) {
		GameRegistry.addSmelting(BlockRawPastry.getStack(BlockRawPastry.EnumType.COOKIE), new ItemStack(Items.COOKIE, 8), 0.1F);
		GameRegistry.addSmelting(BlockRawPastry.getStack(BlockRawPastry.EnumType.PUMPKIN), new ItemStack(Items.PUMPKIN_PIE, 1), 0.1F);
		GameRegistry.addSmelting(BlockRawPastry.getStack(BlockRawPastry.EnumType.APPLE), new ItemStack(BWMItems.APPLE_PIE, 1), 0.1F);

		KilnRecipes.addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.COOKIE), new ItemStack(Items.COOKIE, 8));
		KilnRecipes.addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.PUMPKIN), new ItemStack(Items.PUMPKIN_PIE, 1));
		KilnRecipes.addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.APPLE), new ItemStack(BWMItems.APPLE_PIE, 1));

		CauldronRecipes.addCauldronRecipe(new ItemStack(Items.MUSHROOM_STEW), new ItemStack(Items.BUCKET), new Object[]{new ItemStack(Blocks.BROWN_MUSHROOM, 3), new ItemStack(Items.MILK_BUCKET), new ItemStack(Items.BOWL)});
		CauldronRecipes.addCauldronRecipe(new ItemStack(Items.BEETROOT_SOUP), new Object[]{new ItemStack(Items.BEETROOT, 6), new ItemStack(Items.BOWL)});
		CauldronRecipes.addCauldronRecipe(new ItemStack(Items.RABBIT_STEW, 5), new Object[]{Items.COOKED_RABBIT, Items.CARROT, Items.BAKED_POTATO, new ItemStack(Items.BOWL, 5), new ItemStack(Blocks.RED_MUSHROOM, 3), "foodFlour"});
	}

	@Override
	public void disabledInit(FMLInitializationEvent event) {
		GameRegistry.addSmelting(BlockRawPastry.getStack(BlockRawPastry.EnumType.COOKIE), new ItemStack(Items.COOKIE, 16), 0.1F);
		GameRegistry.addSmelting(BlockRawPastry.getStack(BlockRawPastry.EnumType.PUMPKIN), new ItemStack(Items.PUMPKIN_PIE, 2), 0.1F);
		KilnRecipes.addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.COOKIE), new ItemStack(Items.COOKIE, 16));
		KilnRecipes.addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.PUMPKIN), new ItemStack(Items.PUMPKIN_PIE, 2));
	}



}
