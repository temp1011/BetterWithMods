package betterwithmods.module.hardcore.world.villagers;

import betterwithmods.BWMod;
import betterwithmods.module.Feature;
import betterwithmods.util.VillagerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by primetoxinz on 6/11/17.
 */
public class HCVillagers extends Feature {


    private static final ResourceLocation LEVELING = new ResourceLocation(BWMod.MODID, "villager_leveling");

    private static boolean clearTrades = true;
    private static boolean leveling = true;
    private static boolean disableZombieCuring = true;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(VillagerLevel.class, new VillagerLevel.Storage(), VillagerLevel::new);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        if (clearTrades) {
            VillagerUtils.clearTrades();
        }

        VillagerUtils.addTrades("minecraft:priest", "cleric", 1, VillagerTrades.CLERIC_1);
    }

    @SubscribeEvent
    public void onAttachCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityVillager)
            event.addCapability(LEVELING, new VillagerLevel());
    }

    @Override
    public String getFeatureDescription() {
        return "Changes how Villagers work";
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }


    @SubscribeEvent
    public void onTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityVillager) {
            EntityVillager villager = (EntityVillager) entity;
            if (villager.timeUntilReset <= 0)
                villager.timeUntilReset = Integer.MAX_VALUE;
            if (villager.needsInitilization)
                villager.needsInitilization = false;
        }
    }


    @SubscribeEvent
    public void onCureZombie(PlayerInteractEvent.EntityInteractSpecific event) {
        if (!disableZombieCuring)
            return;
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityZombieVillager) {
            event.setCanceled(true);
        }
    }
}
