package betterwithmods.module.hardcore.world.strata;

import betterwithmods.common.BWOreDictionary;
import betterwithmods.module.Feature;
import betterwithmods.util.item.ToolsManager;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

public class HCStrata extends Feature {


    public static float[] STRATA_SPEEDS;

    @Override
    public void setupConfig() {
        STRATA_SPEEDS = new float[]{(float) loadPropDouble("Light Strata", "Speed for Light Strata", 1),
                (float) loadPropDouble("Medium Strata", "Speed for Medium Strata", 0.5f),
                (float) loadPropDouble("Dark Strata", "Speed for Dark Strata", 0.25f)
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

    }

    public static Set<IBlockState> STATES = Sets.newHashSet();

    public static void addBlock(Block block) {
        STATES.addAll(block.getBlockState().getValidStates());
    }

    public static void addState(IBlockState state) {
        STATES.add(state);
    }

    public static boolean shouldStratify(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getMaterial() == Material.ROCK;
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
        if (shouldStratify(world, pos)) {
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
                    scale /= 2;
                }
            }
            event.setNewSpeed(scale * STRATA_SPEEDS[strata]);
            System.out.println(event.getNewSpeed());
        }

    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public static void addModels(ModelBakeEvent event) {

    }
}
