package betterwithmods.module.gameplay.breeding_harness;

import betterwithmods.BWMod;
import betterwithmods.common.BWMItems;
import betterwithmods.common.items.ItemBreedingHarness;
import betterwithmods.module.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class BreedingHarness extends Feature {

    public BreedingHarness() {
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BWMItems.registerItem(BWMItems.BREEDING_HARNESS);
    }

    private static final ResourceLocation CAPABILITY = new ResourceLocation(BWMod.MODID, "harness");

    @SubscribeEvent
    public void onAttach(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityCow) {
            event.addCapability(CAPABILITY, new CapabilityHarness());
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {

        Entity entity = event.getTarget();
        if (entity.hasCapability(CapabilityHarness.HARNESS_CAPABILITY, null)) {
            CapabilityHarness cap = entity.getCapability(CapabilityHarness.HARNESS_CAPABILITY, null);
            if (cap != null) {
                ItemStack hand = event.getItemStack();
                ItemStack harness = cap.getHarness();

                if (harness.isEmpty()) {
                    if (hand.getItem() instanceof ItemBreedingHarness) {
                        cap.insertHarness(hand);
                        hand.shrink(1);
                        event.getWorld().playSound(null, entity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 0.5f, 1.3f);
                        event.getWorld().playSound(null, entity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundCategory.NEUTRAL, 0.5f, 1.3f);
                        event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
                    }
                } else if (event.getEntityPlayer().isSneaking() && hand.isEmpty()) {
                    ItemStack extract = cap.extractHarness();
                    ItemHandlerHelper.giveItemToPlayer(event.getEntityPlayer(), extract);
                    event.getWorld().playSound(null, entity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.NEUTRAL, 1, 1);
                    event.getWorld().playSound(null, entity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundCategory.NEUTRAL, 1, 1f);
                    event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
                }
            }
        }

    }


    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        if (entity.hasCapability(CapabilityHarness.HARNESS_CAPABILITY, null)) {
            CapabilityHarness cap = entity.getCapability(CapabilityHarness.HARNESS_CAPABILITY, null);
            if(cap != null && cap.getHarness().getItem() instanceof ItemBreedingHarness) {
//                PlayerHelper.changeSpeed(entity, "harness_modifer", 0, );
            } else {
                entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
            }
        }
    }


    @Override
    public boolean hasSubscriptions() {
        return true;
    }
}
