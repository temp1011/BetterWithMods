package betterwithmods.event;

import betterwithmods.world.BWMapGenScatteredFeature;
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
}
