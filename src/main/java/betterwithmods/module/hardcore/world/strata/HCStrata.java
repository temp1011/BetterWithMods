package betterwithmods.module.hardcore.world.strata;

import betterwithmods.module.Feature;
import betterwithmods.util.item.ToolsManager;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HCStrata extends Feature {


    public static float[] STRATA_SPEEDS;
    @Override
    public void setupConfig() {
        STRATA_SPEEDS = new float[]{(float) loadPropDouble("Light Strata","Speed for Light Strata", 1),
                (float) loadPropDouble("Medium Strata","Speed for Medium Strata", 0.5f),
                (float) loadPropDouble("Dark Strata","Speed for Dark Strata", 0.05f)
        };
    }

    @Override
    public String getFeatureDescription() {
        return "Changes how hard stone and ores are based on how deep down they are.";
    }


    public static boolean shouldStratify(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getMaterial() == Material.ROCK;
    }

    public static int getStratification(int y, int topY) {
        if(y >= (topY-10))
            return 0;
        if(y >= (topY-30))
            return 1;
        return 2;
    }

    @SubscribeEvent
    public void getHarvest(PlayerEvent.BreakSpeed event) {

        World world = event.getEntityPlayer().getEntityWorld();
        BlockPos pos = event.getPos();
        if(shouldStratify(world,pos)) {
            float scale = ToolsManager.getSpeed(event.getEntityPlayer().getHeldItemMainhand(), event.getState());

            int strata = getStratification(pos.getY(),Math.min(world.getTopSolidOrLiquidBlock(pos).getY(), 70));
            event.setNewSpeed(scale * STRATA_SPEEDS[strata] * 0.5f);
            System.out.println(strata);
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
