package betterwithmods.module.hardcore.world;

import betterwithmods.common.BWMBlocks;
import betterwithmods.common.BWMItems;
import betterwithmods.common.BWMRecipes;
import betterwithmods.common.BWOreDictionary;
import betterwithmods.module.Feature;
import betterwithmods.util.InvUtils;
import betterwithmods.util.item.ToolsManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Created by tyler on 4/20/17.
 */
public class HCStumping extends Feature {

    public static Set<Block> STUMP_BLACKLIST = Sets.newHashSet(BWMBlocks.BLOOD_LOG);

    @Override
    public String getFeatureDescription() {
        return "Makes the bottom block of trees into stumps which cannot be removed by hand, making your mark on the world more obvious";
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }

    @Nullable
    public static BlockPlanks.EnumType getWoodType(IBlockState state) {
        if (state.getProperties().containsKey(BlockPlanks.VARIANT)) {
            return state.getValue(BlockPlanks.VARIANT);
        } else if (state.getProperties().containsKey(BlockOldLog.VARIANT)) {
            return state.getValue(BlockOldLog.VARIANT);
        } else if (state.getProperties().containsKey(BlockNewLog.VARIANT)) {
            return state.getValue(BlockNewLog.VARIANT);
        } else return null;
    }

    @Override
    public boolean requiresMinecraftRestartToEnable() {
        return true;
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    public static String[] BLACKLIST_CONFIG;

    public static boolean isStump(IBlockState state) {
        if (!STUMP_BLACKLIST.contains(state.getBlock()) && state.getBlock() instanceof BlockLog) {
            if (state.getPropertyKeys().contains(BlockLog.LOG_AXIS)) {
                return state.getValue(BlockLog.LOG_AXIS).equals(BlockLog.EnumAxis.Y);
            }
            return true;
        }
        return false;
    }

    public static boolean isRoots(IBlockState state) {
        return state.getMaterial() == Material.GROUND || state.getMaterial() == Material.GRASS;
    }

    @Override
    public void setupConfig() {
        BLACKLIST_CONFIG = loadPropStringList("Stump Blacklist", "Logs which do not create stumps", new String[0]);
        for (String block : BLACKLIST_CONFIG) {
            STUMP_BLACKLIST.add(Block.REGISTRY.getObject(new ResourceLocation(block)));
        }
    }

    @SubscribeEvent
    public void getHarvest(PlayerEvent.BreakSpeed event) {
        World world = event.getEntityPlayer().getEntityWorld();
        if (isStump(world.getBlockState(event.getPos())) && isRoots(world.getBlockState(event.getPos().down()))) {
            event.setNewSpeed(0.03f * ToolsManager.getSpeed(event.getEntityPlayer().getHeldItemMainhand()));
        }
        if (isRoots(world.getBlockState(event.getPos())) && isStump(world.getBlockState(event.getPos().up()))) {
            event.setNewSpeed(0.01f * ToolsManager.getSpeed(event.getEntityPlayer().getHeldItemMainhand()));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onHarvest(BlockEvent.HarvestDropsEvent event) {

        if (isStump(event.getState()) && isRoots(event.getWorld().getBlockState(event.getPos().down()))) {
            ItemStack stack = BWMRecipes.getStackFromState(event.getState());
            BWOreDictionary.Wood wood = BWOreDictionary.woods.stream().filter(w -> InvUtils.matches(w.getLog(1), stack)).findFirst().orElse(null);
            if (wood != null) {
                event.getDrops().clear();
                event.getDrops().addAll(Lists.newArrayList(wood.getSawdust(1), wood.getBark(1)));
            }
        }
        if (isRoots(event.getState()) && isStump(event.getWorld().getBlockState(event.getPos().up()))) {
            ItemStack stack = BWMRecipes.getStackFromState(event.getWorld().getBlockState(event.getPos().up()));
            BWOreDictionary.Wood wood = BWOreDictionary.woods.stream().filter(w -> InvUtils.matches(w.getLog(1), stack)).findFirst().orElse(null);
            if (wood != null) {
                event.setResult(Event.Result.DENY);
                event.getDrops().clear();
                event.getDrops().addAll(Lists.newArrayList(new ItemStack(BWMItems.DIRT_PILE, 2), wood.getSawdust(1), wood.getBark(1)));
            }
        }
    }


}
