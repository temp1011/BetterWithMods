package betterwithmods.module.hardcore.creatures;

import betterwithmods.bwl.core.event.EntitySetEquipmentEvent;
import betterwithmods.module.CompatFeature;
import com.google.common.collect.Maps;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class HCMobEquipment extends CompatFeature {
    public static final EntityEquipmentSlot[] ARMOR_SLOTS = new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
    public static final ItemStack[] IRON_ARMOR = new ItemStack[]{new ItemStack(Items.IRON_HELMET), new ItemStack(Items.IRON_CHESTPLATE), new ItemStack(Items.IRON_LEGGINGS), new ItemStack(Items.IRON_BOOTS)};
    public static final ItemStack[] GOLD_ARMOR = new ItemStack[]{new ItemStack(Items.GOLDEN_HELMET), new ItemStack(Items.GOLDEN_CHESTPLATE), new ItemStack(Items.GOLDEN_LEGGINGS), new ItemStack(Items.GOLDEN_BOOTS)};


    private static Map<ResourceLocation, Equipment> entityMap = Maps.newHashMap();

    public HCMobEquipment() {
        super("betterwithlib");
        enabledByDefault = false;
    }

    private static void pigman(EntityLivingBase entity ) {
        if (entity.getRNG().nextFloat() < 0.05F) {
                entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
        }
        armor(entity, GOLD_ARMOR);
    }


    private static void skeleton(EntityLivingBase entity) {
        armor(entity, IRON_ARMOR);
        entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    private static void zombie(EntityLivingBase entity ) {
        armor(entity, IRON_ARMOR);
        if (entity.getRNG().nextFloat() < 0.05F) {
            int i = entity.getRNG().nextInt(3);
            if (i == 0) {
                entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            } else {
                entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
            }
        }
    }

    private static void armor(EntityLivingBase entity, ItemStack[] armor) {
        if (entity.getRNG().nextFloat() < 0.018) {
            boolean flag = true;
            for (int s = 0; s < ARMOR_SLOTS.length; s++) {
                EntityEquipmentSlot slot = ARMOR_SLOTS[s];
                ItemStack current = entity.getItemStackFromSlot(slot);
                if (current.isEmpty()) {
                    if (!flag && entity.getRNG().nextFloat() < 0.1) {
                        continue;
                    }
                    flag = false;
                    entity.setItemStackToSlot(slot, armor[s]);
                }
            }
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        entityMap.put(new ResourceLocation("minecraft:zombie"), HCMobEquipment::zombie);
        entityMap.put(new ResourceLocation("minecraft:husk"), HCMobEquipment::zombie);
        entityMap.put(new ResourceLocation("minecraft:skeleton"), HCMobEquipment::skeleton);
        entityMap.put(new ResourceLocation("minecraft:zombie_pigman"), HCMobEquipment::pigman);
    }

    @SubscribeEvent
    public void onSetEquipment(EntitySetEquipmentEvent event) {
        EntityLivingBase entity = (EntityLivingBase) event.getEntity();
        ResourceLocation key = EntityList.getKey(entity);

        Equipment equipment = entityMap.get(key);
        if (equipment != null) {
            event.setCanceled(true);
            equipment.equip(entity);
        }
    }

    @Override
    public String getFeatureDescription() {
        return "Change the equipment that mobs spawn with";
    }


    @FunctionalInterface
    private interface Equipment {
        void equip(EntityLivingBase entity);
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }
}
