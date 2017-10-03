package betterwithmods.module.hardcore.creatures;

import betterwithmods.common.damagesource.BWDamageSource;
import betterwithmods.common.items.tools.ItemSoulforgeArmor;
import betterwithmods.module.Feature;
import betterwithmods.network.NetworkHandler;
import betterwithmods.util.InvUtils;
import betterwithmods.util.player.PlayerHelper;
import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityMountEvent;
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

	@SubscribeEvent
	public void onEntityTick(LivingEvent.LivingUpdateEvent event) {

		if (event.getEntityLiving() instanceof EntitySquid) {
			EntitySquid squid = (EntitySquid) event.getEntityLiving();
			if (squid.isRiding() && squid.getRidingEntity() instanceof EntityLivingBase) {
				EntityLivingBase mount = (EntityLivingBase) squid.getRidingEntity();

				//Attack if not wearing a SFS helmet
				if(!PlayerHelper.hasPart(mount, EntityEquipmentSlot.HEAD, ItemSoulforgeArmor.class)) {
					if(mount.world.rand.nextInt(5)==0)
						mount.world.playSound(null,mount.getPosition(), SoundEvents.ENTITY_SQUID_HURT, SoundCategory.HOSTILE,0.5f,1);
					mount.attackEntityFrom(BWDamageSource.squid, 1);
				}

				float f1 = MathHelper.sqrt(squid.motionX * squid.motionX + squid.motionZ * squid.motionZ);
				squid.squidPitch += (-((float) MathHelper.atan2((double) f1, squid.motionY)) * (180F / (float) Math.PI) - squid.squidPitch) * 0.5F;
				squid.setAir(300);
			}
		}
	}

	@SubscribeEvent
	public void onEntityCollide(PlayerInteractEvent.EntityInteractSpecific event) {
		if (event.getWorld().isRemote)
			return;
		if (event.getTarget() instanceof EntitySquid) {
			EntityPlayer player = event.getEntityPlayer();
			if (!player.isBeingRidden() && event.getTarget().startRiding(player, true)) {
				((EntitySquid) event.getTarget()).setNoAI(true);
				if (player instanceof EntityPlayerMP) {
					NetworkHandler.sendPacket(player, new SPacketSetPassengers(player));
				}
			}
		}
	}



}
