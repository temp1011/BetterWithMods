package betterwithmods.module.tweaks;

import betterwithmods.client.render.RenderWolf;
import betterwithmods.module.Feature;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LongBoi extends Feature {
    @Override
    public String getFeatureDescription() {
        return "Long Bois!";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void preInitClient(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityWolf.class, RenderWolf::new);
    }

    @Override
    public String[] getIncompatibleMods() {
        return new String[]{"wolfarmor"};
    }
}
