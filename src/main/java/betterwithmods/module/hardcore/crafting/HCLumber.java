package betterwithmods.module.hardcore.crafting;

import betterwithmods.api.util.IWood;
import betterwithmods.common.BWOreDictionary;
import betterwithmods.common.registry.BrokenToolRegistry;
import betterwithmods.common.registry.ChoppingRecipe;
import betterwithmods.module.Feature;
import betterwithmods.util.player.PlayerHelper;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

/**
 * Created by primetoxinz on 4/20/17.
 */
public class HCLumber extends Feature {
    public static int plankAmount, barkAmount, sawDustAmount;

    public static int axePlankAmount, axeBarkAmount, axeSawDustAmount;

    public static boolean hasAxe(EntityPlayer harvester, BlockPos pos, IBlockState state) {
        if(harvester == null)
            return false;
        return PlayerHelper.isCurrentToolEffectiveOnBlock(harvester, pos, state);
    }

    public static int getAxeLevel(ItemStack stack,EntityPlayer player,IBlockState state) { //Yeah uhhh future proofing in case we wanna blacklist or whitelist something, run inline if you hate this i guess
        return stack.getItem().getHarvestLevel(stack,"axe",player,state);
    }

    @Override
    public void setupConfig() {
        plankAmount = loadPropInt("Plank Amount", "Amount of Planks dropped when Punching Wood", 2);
        barkAmount = loadPropInt("Bark Amount", "Amount of Bark dropped when Punching Wood", 1);
        sawDustAmount = loadPropInt("Sawdust Amount", "Amount of Sawdust dropped when Punching Wood", 2);

        axePlankAmount = loadPropInt("Axe Plank Amount", "Amount of Planks dropped when crafted with an axe", 3);
        axeBarkAmount = loadPropInt("Axe Bark Amount", "Amount of Bark dropped when crafted with an axe", 1);
        axeSawDustAmount = loadPropInt("Axe Sawdust Amount", "Amount of Sawdust dropped when crafted with an axe", 2);
    }

    @Override
    public String getFeatureDescription() {
        return "Makes Punching Wood return a single plank and secondary drops instead of a log, to get a log an axe must be used.";
    }

    @Override
    public void init(FMLInitializationEvent event) {
        BrokenToolRegistry.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (!Loader.isModLoaded("primal")) {
            for (IRecipe recipe : BWOreDictionary.logRecipes) {
                ItemStack plank = recipe.getRecipeOutput();
                BWOreDictionary.woods.stream().filter(w -> w.getPlank(axePlankAmount).isItemEqual(plank) && hasLog(recipe, w.getLog(1))).forEach(wood -> {
                    addHardcoreRecipe(new ChoppingRecipe(wood, axePlankAmount).setRegistryName(Objects.requireNonNull(recipe.getRegistryName())));
                });
            }
        }
    }

    @Override
    public void disabledPostInit(FMLPostInitializationEvent event) {
        if (!Loader.isModLoaded("primal")) {
            for (IRecipe recipe : BWOreDictionary.logRecipes) {
                ItemStack plank = recipe.getRecipeOutput();
                BWOreDictionary.woods.stream().filter(w -> w.getPlank(4).isItemEqual(plank) && hasLog(recipe, w.getLog(1))).forEach(wood -> {
                    addHardcoreRecipe(new ChoppingRecipe(wood, 4).setRegistryName(Objects.requireNonNull(recipe.getRegistryName())));
                });
            }
        }
    }

    private boolean hasLog(IRecipe recipe, ItemStack log) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getMatchingStacks().length > 0) {
                for (ItemStack stack : ingredient.getMatchingStacks()) {
                    if (stack.isItemEqual(log))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public void disabledInit(FMLInitializationEvent event) {
    }

    @Override
    public boolean requiresMinecraftRestartToEnable() {
        return true;
    }

    @SubscribeEvent
    public void harvestLog(BlockEvent.HarvestDropsEvent event) {
        if (!event.getWorld().isRemote) {
            IWood wood = BWOreDictionary.getWoodFromState(event.getState());
            if (wood != null) {
                if (event.isSilkTouching() || hasAxe(event.getHarvester(), event.getPos(), event.getState()) || Loader.isModLoaded("primal"))
                    return;
                event.getDrops().clear();
                event.getDrops().addAll(Lists.newArrayList(wood.getPlank(plankAmount), wood.getSawdust(sawDustAmount), wood.getBark(barkAmount)));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void harvestGarbage(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if(event.isCanceled() || player == null || player.isCreative())
            return;
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        ItemStack stack = player.getHeldItemMainhand();
        String tooltype = state.getBlock().getHarvestTool(state);
        if(tooltype != null && state.getBlockHardness(world,pos) <= 0 && stack.getItem().getHarvestLevel(stack,tooltype,player,state) < Item.ToolMaterial.DIAMOND.getHarvestLevel())
            stack.damageItem(1,player); //Make 0 hardness blocks damage tools that are not diamond level
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }
}
