package betterwithmods.module.hardcore.creatures;

import betterwithmods.common.items.ItemArcaneScroll;
import betterwithmods.module.Feature;
import betterwithmods.util.WorldUtils;
import com.google.common.collect.Maps;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

public class ArcaneScrolls extends Feature {
    @Override
    public String getFeatureDescription() {
        return "Adds Arcane Scroll drops to specific mobs, used for enchanting with the Infernal Enchanter";
    }

    @FunctionalInterface
    public interface ScrollDrop {
        ItemStack getScroll(EntityLivingBase entity);

        default double getChance() {
            return 0.1;
        }
    }

    private static final HashMap<Class<? extends EntityLivingBase>, ScrollDrop> SCROLL_DROPS = Maps.newHashMap();

    public static void addScrollDrop(Class<? extends EntityLivingBase> clazz, Enchantment enchantment) {
        SCROLL_DROPS.put(clazz, (entity) -> ItemArcaneScroll.getScrollWithEnchant(enchantment));
    }

    public static void addScrollDrop(Class<? extends EntityLivingBase> clazz, ItemStack scroll) {
        addScrollDrop(clazz, (entity) -> scroll);
    }

    public static void addScrollDrop(Class<? extends EntityLivingBase> clazz, ScrollDrop scroll) {
        SCROLL_DROPS.put(clazz, scroll);
    }


    @Override
    public void init(FMLInitializationEvent event) {
        addScrollDrop(EntitySlime.class, Enchantments.PROTECTION);
        addScrollDrop(EntityPigZombie.class, Enchantments.FIRE_PROTECTION);
        addScrollDrop(EntityBat.class, Enchantments.FEATHER_FALLING);
        addScrollDrop(EntityCreeper.class, Enchantments.BLAST_PROTECTION);
        addScrollDrop(EntitySkeleton.class, (entity) -> {
            if (entity.world.provider.getDimensionType() == DimensionType.NETHER)
                return ItemArcaneScroll.getScrollWithEnchant(Enchantments.INFINITY);
            else
                return ItemArcaneScroll.getScrollWithEnchant(Enchantments.PROJECTILE_PROTECTION);
        });
        addScrollDrop(EntitySquid.class, Enchantments.RESPIRATION);
        addScrollDrop(EntityWitch.class, Enchantments.AQUA_AFFINITY);
        addScrollDrop(EntityZombie.class, Enchantments.SMITE);
        addScrollDrop(EntitySpider.class, Enchantments.BANE_OF_ARTHROPODS);
        addScrollDrop(EntityMagmaCube.class, Enchantments.FIRE_ASPECT);
        addScrollDrop(EntityEnderman.class, Enchantments.SILK_TOUCH);
        addScrollDrop(EntityGhast.class, Enchantments.PUNCH);
        addScrollDrop(EntityBlaze.class, Enchantments.FLAME);
        addScrollDrop(EntityWither.class, new ScrollDrop() {
            @Override
            public ItemStack getScroll(EntityLivingBase entity) {
                return ItemArcaneScroll.getScrollWithEnchant(Enchantments.KNOCKBACK);
            }

            //Always drops
            @Override
            public double getChance() {
                return 1;
            }
        });
        addScrollDrop(EntitySilverfish.class, entity -> {
            if (entity.world.provider.getDimensionType() == DimensionType.THE_END) {
                return ItemArcaneScroll.getScrollWithEnchant(Enchantments.EFFICIENCY);
            }
            return null;
        });

        //TODO
        // SHARPNESS -> Butcher Trade
        // LOOTING -> Farmer Trade
        // UNBREAKING -> Blacksmith Trade
        // FORTUNE ->  Priest Trade
        // POWER   -> Librarian Trade


    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @SubscribeEvent
    public void onDeath(LivingDropsEvent event) {
        for (Class<? extends EntityLivingBase> entity : SCROLL_DROPS.keySet()) {
            if (entity.isAssignableFrom(event.getEntityLiving().getClass())) {
                ScrollDrop drop = SCROLL_DROPS.get(entity);
                if (drop.getScroll(event.getEntityLiving()) != null) {
                    if (event.getEntityLiving().getRNG().nextDouble() <= drop.getChance()) {
                        WorldUtils.addDrop(event, drop.getScroll(event.getEntityLiving()));
                    }
                }
            }
        }
    }


}
