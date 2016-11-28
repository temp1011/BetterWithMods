package betterwithmods.event;

import betterwithmods.world.BWMapGenScatteredFeature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by blueyu2 on 11/27/16.
 */
public class BWMWorldGenEvent {

    @SubscribeEvent
    public void overrideScatteredFeature(InitMapGenEvent event){
        if(event.getType().equals(InitMapGenEvent.EventType.SCATTERED_FEATURE))
            event.setNewGen(new BWMapGenScatteredFeature());
    }

    public static boolean isInRadius(World world, BlockPos pos) {
        BlockPos center = world.getSpawnPoint();
        return Math.sqrt(Math.pow(pos.getX() - center.getX(), 2) + Math.pow(pos.getZ() - center.getZ(), 2)) < RespawnEventHandler.HARDCORE_SPAWN_RADIUS;
    }
}
