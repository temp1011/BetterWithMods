package betterwithmods.module.hardcore.world.strata;

import betterwithmods.common.BWOreDictionary;
import betterwithmods.module.Feature;
import betterwithmods.util.item.ToolsManager;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class HCStrata extends Feature {



    public static float[] STRATA_SPEEDS;

    public HCStrata() {
        enabledByDefault = false;
    }

    @Override
    public void setupConfig() {
        STRATA_SPEEDS = new float[]{(float) loadPropDouble("Light Strata", "Speed for Light Strata", 0.75),
                (float) loadPropDouble("Medium Strata", "Speed for Medium Strata", 0.23f),
                (float) loadPropDouble("Dark Strata", "Speed for Dark Strata", 0.10f)
        };
    }


    @Override
    public String getFeatureDescription() {
        return "Divides the underground into three strata. Each strata requires the next tool tier to properly mine";
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        for (BWOreDictionary.Ore ore : BWOreDictionary.oreNames) {
            for (ItemStack stack : ore.getOres()) {
                if (stack.getItem() instanceof ItemBlock) {
                    addBlock(((ItemBlock) stack.getItem()).getBlock());
                }
            }
        }
        addBlock(Blocks.STONE);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        if(Loader.isModLoaded("ctm")) {
            try {
                Class type = Class.forName("team.chisel.ctm.api.texture.ITextureType");
                Method method = Class.forName("team.chisel.ctm.client.texture.type.TextureTypeRegistry").getDeclaredMethod("register",String.class, type);
                try {
                    method.invoke(null, "bwm_strata", new TextureTypeStrata());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }



        }
    }


    public static Set<IBlockState> STATES = Sets.newHashSet();

    public static void addBlock(Block block) {
        STATES.addAll(block.getBlockState().getValidStates());
    }

    public static void addState(IBlockState state) {
        STATES.add(state);
    }

    public static boolean shouldStratify(World world, BlockPos pos) {
        return shouldStratify(world, world.getBlockState(pos));
    }

    public static boolean shouldStratify(World world, IBlockState state) {
        return world.provider.getDimensionType() == DimensionType.OVERWORLD && STATES.contains(state);
    }

    public static int getStratification(int y, int topY) {
        if (y >= (topY - 10))
            return 0;
        if (y >= (topY - 30))
            return 1;
        return 2;
    }

    @SubscribeEvent
    public void onBreak(BlockEvent.HarvestDropsEvent event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        if (shouldStratify(world, event.getState()) && event.getHarvester() != null) {
            Item.ToolMaterial material = ToolsManager.getToolMaterial(event.getHarvester().getHeldItemMainhand());
            int strata = getStratification(pos.getY(), world.getSeaLevel());
            if (material != null) {

                int level = Math.max(1, material.getHarvestLevel()) - 1;
                if (level < (strata)) {
                    event.getDrops().clear();
                }
            }
        }
    }

    @SubscribeEvent
    public void onHarvest(PlayerEvent.BreakSpeed event) {
        World world = event.getEntityPlayer().getEntityWorld();
        BlockPos pos = event.getPos();
        if (shouldStratify(world, pos)) {

            float scale = ToolsManager.getSpeed(event.getEntityPlayer().getHeldItemMainhand(), event.getState());
            int strata = getStratification(pos.getY(), world.getSeaLevel());

            Item.ToolMaterial material = ToolsManager.getToolMaterial(event.getEntityPlayer().getHeldItemMainhand());

            if (material != null) {
                int level = Math.max(1, material.getHarvestLevel()) - 1;
                if (level < (strata)) {
                    scale /= 6;
                }
            }
            event.setNewSpeed(scale * STRATA_SPEEDS[strata]);
        }

    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

}
