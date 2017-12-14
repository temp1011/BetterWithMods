package betterwithmods.module.hardcore.creatures;

import betterwithmods.common.damagesource.BWDamageSource;
import betterwithmods.common.items.tools.ItemSoulforgeArmor;
import betterwithmods.module.Feature;
import betterwithmods.network.NetworkHandler;
import betterwithmods.util.player.PlayerHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HCSquid extends Feature {


    @Override
    public String getFeatureDescription() {
        return "Fear the squid...";
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void addEntityAI(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntitySquid) {
            EntitySquid squid = (EntitySquid) event.getEntity();
//            squid.tasks.taskEntries.clear();
//            squid.targetTasks.taskEntries.clear();
        }
    }

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntitySquid) {
            EntitySquid squid = (EntitySquid) event.getEntityLiving();
            if (squid.isRiding() && squid.getRidingEntity() instanceof EntityLivingBase) {
                EntityLivingBase mount = (EntityLivingBase) squid.getRidingEntity();
                //Attack if not wearing a SFS helmet
                if (!PlayerHelper.hasPart(mount, EntityEquipmentSlot.HEAD, ItemSoulforgeArmor.class)) {
                    if (mount.world.rand.nextInt(5) == 0)
                        mount.world.playSound(null, mount.getPosition(), SoundEvents.ENTITY_SQUID_HURT, SoundCategory.HOSTILE, 0.5f, 1);
                    mount.attackEntityFrom(BWDamageSource.squid, 2);
                }

//                float f1 = MathHelper.sqrt(squid.motionX * squid.motionX + squid.motionZ * squid.motionZ);
//                squid.squidPitch += (-((float) MathHelper.atan2((double) f1, squid.motionY)) * (180F / (float) Math.PI) - squid.squidPitch) * 0.5F;
//
            }
        }
    }

    @SubscribeEvent
    public void onEntityCollide(PlayerInteractEvent.EntityInteractSpecific event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!player.isBeingRidden()) {
            Entity entity = event.getTarget();
            if (entity instanceof EntitySquid) {
                if (entity.startRiding(player, true)) {
                    if (player instanceof EntityPlayerMP) {
                        NetworkHandler.sendPacket(player, new SPacketSetPassengers(player));
                    }
                }
            }
        }
    }


}
