package betterwithmods.module.gameplay;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWMItems;
import betterwithmods.common.blocks.BlockAesthetic;
import betterwithmods.common.blocks.BlockRawPastry;
import betterwithmods.common.registry.KilnStructureManager;
import betterwithmods.common.registry.blockmeta.managers.KilnManager;
import betterwithmods.module.Feature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class KilnRecipes extends Feature {
    public KilnRecipes() {
        canDisable = false;
    }

    public static void addKilnRecipe(ItemStack inputBlock, ItemStack... output) {
        KilnManager.INSTANCE.addRecipe(inputBlock, output);
    }

    public static void addKilnRecipe(Block inputBlock, ItemStack... output) {
        KilnManager.INSTANCE.addRecipe(inputBlock, 0, output);
    }

    public static void addKilnRecipe(Block inputBlock, int inputMeta, ItemStack... output) {
        KilnManager.INSTANCE.addRecipe(inputBlock, inputMeta, output);
    }

    @Override
    public void init(FMLInitializationEvent event) {
//        addKilnRecipe(BWMBlocks.UNFIRED_POTTERY, 1, BlockPlanter.getStack(BlockPlanter.EnumType.EMPTY));
//        addKilnRecipe(BWMBlocks.UNFIRED_POTTERY, 0, new ItemStack(BWMBlocks.CRUCIBLE));
//        addKilnRecipe(BWMBlocks.UNFIRED_POTTERY, 2, new ItemStack(BWMBlocks.URN));
//        addKilnRecipe(BWMBlocks.UNFIRED_POTTERY, 3, BlockVase.getStack(EnumDyeColor.WHITE));
        addKilnRecipe(Blocks.CLAY, 0, new ItemStack(Blocks.HARDENED_CLAY));
        addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.CAKE), new ItemStack(Items.CAKE));
        addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.BREAD), new ItemStack(Items.BREAD));
        addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.APPLE), new ItemStack(BWMItems.APPLE_PIE));
        addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.PUMPKIN), new ItemStack(Items.PUMPKIN_PIE));
        addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.COOKIE), new ItemStack(Items.COOKIE, 16));
        addKilnRecipe(BWMBlocks.NETHER_CLAY, BlockAesthetic.getStack(BlockAesthetic.EnumType.NETHERCLAY));

        GameRegistry.addSmelting(BlockRawPastry.getStack(BlockRawPastry.EnumType.COOKIE), new ItemStack(Items.COOKIE, 16), 0.1F);
        GameRegistry.addSmelting(BlockRawPastry.getStack(BlockRawPastry.EnumType.PUMPKIN), new ItemStack(Items.PUMPKIN_PIE, 2), 0.1F);
        GameRegistry.addSmelting(BlockRawPastry.getStack(BlockRawPastry.EnumType.APPLE), new ItemStack(BWMItems.APPLE_PIE, 1), 0.1F);
        GameRegistry.addSmelting(BlockRawPastry.getStack(BlockRawPastry.EnumType.BREAD), new ItemStack(Items.BREAD, 1), 0.1F);
        //TODO make these recipes only require normal fire.
        KilnRecipes.addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.COOKIE), new ItemStack(Items.COOKIE, 16));
        KilnRecipes.addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.PUMPKIN), new ItemStack(Items.PUMPKIN_PIE, 2));
        KilnRecipes.addKilnRecipe(BlockRawPastry.getStack(BlockRawPastry.EnumType.APPLE), new ItemStack(BWMItems.APPLE_PIE, 1));
    }

    @SubscribeEvent
    public void formKiln(BlockEvent.NeighborNotifyEvent event) {
        KilnStructureManager.createKiln(event.getWorld(), event.getPos());
    }

    @SubscribeEvent
    public void onKilnPlace(BlockEvent.PlaceEvent event) {
        if (event.getPlacedBlock().getBlock() != Blocks.AIR)
            KilnStructureManager.createKiln(event.getWorld(), event.getPos());
    }


    @Override
    public boolean hasSubscriptions() {
        return true;
    }
}

