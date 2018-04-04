package betterwithmods.module.stronghold;

import betterwithmods.common.world.BWMapGenVillage;
import betterwithmods.module.Feature;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Strongholds extends Feature {
    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @Override
    public boolean hasTerrainSubscriptions() {
        return true;
    }

    @SubscribeEvent
    public void onGenerate(InitMapGenEvent event) {
        if (event.getType() == InitMapGenEvent.EventType.STRONGHOLD) {
            event.setNewGen(new BWMapGenVillage());
        }
    }
}
