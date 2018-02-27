package betterwithmods.common.items.tools;

import betterwithmods.BWMod;
import betterwithmods.client.BWCreativeTabs;
import betterwithmods.common.BWOreDictionary;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class ItemLeatherTannedArmor extends ItemArmor {
    private static final ArmorMaterial LEATHER_TANNED = EnumHelper.addArmorMaterial("leather_tanned", "betterwithmods:leather_tanned", 10, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    public ItemLeatherTannedArmor(EntityEquipmentSlot equipmentSlotIn) {
        super(LEATHER_TANNED, 2, equipmentSlotIn);
        this.setCreativeTab(BWCreativeTabs.BWTAB);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return BWMod.MODID + ":textures/models/armor/leather_tanned_layer_" + (this.armorType.getSlotIndex() == 2 ? "2" : "1") + ".png";
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return BWOreDictionary.listContains(repair, OreDictionary.getOres("hideTanned")) || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return true;
    }
}