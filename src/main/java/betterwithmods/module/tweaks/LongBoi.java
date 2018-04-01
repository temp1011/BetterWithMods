package betterwithmods.module.tweaks;

import betterwithmods.client.render.RenderWolf;
import betterwithmods.module.Feature;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class LongBoi extends Feature {
    @Override
    public String getFeatureDescription() {
        return "Long Bois!";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityWolf.class, RenderWolf::new);
    }
}
