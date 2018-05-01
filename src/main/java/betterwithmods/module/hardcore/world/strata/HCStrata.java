package betterwithmods.module.hardcore.world.strata;

import betterwithmods.common.BWMRecipes;
import betterwithmods.common.BWOreDictionary;
import betterwithmods.common.registry.BrokenToolRegistry;
import betterwithmods.module.ConfigHelper;
import betterwithmods.module.Feature;
import betterwithmods.module.ModuleLoader;
import betterwithmods.util.item.ToolsManager;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
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
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HCStrata extends Feature {
    public static boolean ENABLED;

    public static float[] STRATA_SPEEDS;
    public static HashMap<Integer, Integer> MEDIUM_STRATA_DEPTHS = Maps.newHashMap();
    public static HashMap<Integer, Integer> DARK_STRATA_DEPTHS = Maps.newHashMap();
    public static int MEDIUM_STRATA_DEFAULT_DEPTH;
    public static int DARK_STRATA_DEFAULT_DEPTH;
    public static float INCORRECT_STRATA_SCALE;

    public HCStrata() {
        enabledByDefault = false;
    }

    @Override
    public void setupConfig() {
        STRATA_SPEEDS = new float[]{(float) loadPropDouble("Light Strata", "Speed for Light Strata", 1.0),
                (float) loadPropDouble("Medium Strata", "Speed for Medium Strata", 1.0),
                (float) loadPropDouble("Dark Strata", "Speed for Dark Strata", 1.0)
        };
        INCORRECT_STRATA_SCALE = (float) loadPropDouble("Incorrect Strata", "Speed scale for when the Strata is higher than the tool", 0.35);
        MEDIUM_STRATA_DEFAULT_DEPTH = loadPropInt("Depth Medium Strata Default", "Default Level below sealevel under which medium strata starts (So y < (sealevel - value) is medium strata)", 10);
        DARK_STRATA_DEFAULT_DEPTH = loadPropInt("Depth Dark Strata Default", "Default Level below sealevel under which dark strata starts (So y < (sealevel - value) is dark strata)", 30);
        MEDIUM_STRATA_DEPTHS = ConfigHelper.loadIntIntMap("Depth Medium Strata",configCategory,"Level below sealevel under which medium strata starts on a per dimension basis. Syntax is dim_id=depth.", new String[] {
                "0=10"
        });
        DARK_STRATA_DEPTHS = ConfigHelper.loadIntIntMap("Depth Dark Strata",configCategory,"Level below sealevel under which dark strata starts on a per dimension basis. Syntax is dim_id=depth.", new String[] {
                "0=30"
        });
    }


    @Override
    public String getFeatureDescription() {
        return "Divides the underground into three strata. Each strata requires the next tool tier to properly mine";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

        if(Loader.isModLoaded("ctm")) {
            try {
                Class clazz = Class.forName("team.chisel.ctm.client.texture.type.TextureTypeRegistry");
                Class clazz1 = Class.forName("team.chisel.ctm.api.texture.ITextureType");
                Class clazz2 = Class.forName("betterwithmods.module.hardcore.world.strata.TextureTypeStrata");
                Method register = ReflectionHelper.findMethod(clazz,"register","register",String.class,clazz1);
                register.invoke(null,"bwm_strata", clazz2.newInstance());
            } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        ENABLED = ModuleLoader.isFeatureEnabled(HCStrata.class);
        for (BWOreDictionary.Ore ore : BWOreDictionary.oreNames) {
            for (ItemStack stack : ore.getOres()) {
                if (stack.getItem() instanceof ItemBlock) {
                    addOre(((ItemBlock) stack.getItem()).getBlock());
                }
            }
        }
        List<ItemStack> stones = loadItemStackList("Strata Stones", "Blocks that are considered as stone to HCStrata", new ItemStack[]{new ItemStack(Blocks.STONE, 1, OreDictionary.WILDCARD_VALUE)});
        stones.stream().map(BWMRecipes::getStatesFromStack).flatMap(Set::stream).forEach(HCStrata::addStone);
    }

    private enum BlockType {
        STONE(0),
        ORE(1);
        private int level;

        BlockType(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    public static HashMap<IBlockState, BlockType> STATES = Maps.newHashMap();


    public static void addStone(IBlockState state) {
        STATES.put(state, BlockType.STONE);
    }

    public static void addStone(Block block) {
        for (IBlockState state : block.getBlockState().getValidStates())
            addStone(state);
    }

    public static void addOre(Block block) {
        for (IBlockState state : block.getBlockState().getValidStates())
            STATES.put(state, BlockType.ORE);
    }

    public static boolean shouldStratify(World world, BlockPos pos) {
        return shouldStratify(world, world.getBlockState(pos));
    }

    public static boolean shouldStratify(World world, IBlockState state) {
        return world.provider.getDimensionType() == DimensionType.OVERWORLD && STATES.containsKey(state);
    }

    public static int getMediumStrataDepth(int dimension) {
        return MEDIUM_STRATA_DEPTHS.getOrDefault(dimension, MEDIUM_STRATA_DEFAULT_DEPTH);
    }

    public static int getDarkStrataDepth(int dimension) {
        return DARK_STRATA_DEPTHS.getOrDefault(dimension, DARK_STRATA_DEFAULT_DEPTH);
    }

    public static int getStratification(int y, int topY, int dimension) {
        if (y >= (topY - getMediumStrataDepth(dimension)))
            return 0;
        if (y >= (topY - getDarkStrataDepth(dimension)))
            return 1;
        return 2;
    }

    @SubscribeEvent
    public void onHarvest(BlockEvent.HarvestDropsEvent event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        if (shouldStratify(world, event.getState()) && event.getHarvester() != null) {
            ItemStack stack = BrokenToolRegistry.findItem(event.getHarvester(), event.getState());
            int strata = getStratification(pos.getY(), world.getSeaLevel(), world.provider.getDimension());
            if (STATES.getOrDefault(event.getState(), BlockType.STONE) == BlockType.STONE) {
                int level = Math.max(1, stack.getItem().getHarvestLevel(stack, "pickaxe", event.getHarvester(), event.getState()));
                if (level <= (strata)) {
                    event.getDrops().clear();
                }
            }
        }
    }

    @SubscribeEvent
    public void getBreakSpeed(PlayerEvent.BreakSpeed event) {
        World world = event.getEntityPlayer().getEntityWorld();
        BlockPos pos = event.getPos();
        if (shouldStratify(world, pos)) {
            ItemStack stack = BrokenToolRegistry.findItem(event.getEntityPlayer(), event.getState());
            float scale = ToolsManager.getSpeed(stack, event.getState());
            int strata = getStratification(pos.getY(), world.getSeaLevel(), world.provider.getDimension());
            if (STATES.getOrDefault(event.getState(), BlockType.STONE) == BlockType.STONE) {
                int level = Math.max(1, stack.getItem().getHarvestLevel(stack, "pickaxe", event.getEntityPlayer(), event.getState()));
                if (level <= (strata)) {
                    scale = INCORRECT_STRATA_SCALE;
                }
            }
            event.setNewSpeed(scale * STRATA_SPEEDS[strata] * event.getOriginalSpeed());
        }

    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

}
