package betterwithmods.items;

import betterwithmods.BWMItems;
import betterwithmods.api.IMultiLocations;
import betterwithmods.client.BWCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMaterial extends Item implements IMultiLocations {
    private static final String[] locationNames = {"gear", "nethercoal", "hemp", "hemp_fibers", "hemp_cloth", "dung", "tanned_leather", "scoured_leather", "leather_strap", "leather_belt", "wood_blade",
            "windmill_blade", "glue", "tallow", "ingot_steel", "ground_netherrack", "hellfire_dust", "concentrated_hellfire", "coal_dust", "filament", "polished_lapis",
            "potash", "sawdust", "soul_dust", "screw", "brimstone", "niter", "element", "fuse", "blasting_oil", "nugget_iron", "nugget_steel", "leather_cut",
            "tanned_leather_cut", "scoured_leather_cut", "redstone_latch", "nether_sludge", "flour", "haft", "charcoal_dust", "sharpening_stone", "knife_blade", "soul_flux", "ender_slag", "ender_ocular",
            "padding", "armor_plate", "broadhead", "cocoa_powder", "diamond_ingot", "chain_mail"};

    public static ItemStack getMaterial(EnumMaterial material) {
        return getMaterial(material, 1);
    }

    public ItemMaterial() {
        super();
        this.setCreativeTab(BWCreativeTabs.BWTAB);
        this.setHasSubtypes(true);
    }

    public static ItemStack getMaterial(EnumMaterial material, int count) {
        return new ItemStack(BWMItems.MATERIAL, count, material.getMetadata());
    }

    @Override
    public String[] getLocations() {
        return locationNames;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < locationNames.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + locationNames[stack.getItemDamage()];
    }

    public enum EnumMaterial {
        GEAR,
        NETHERCOAL,
        HEMP,
        HEMP_FIBERS,
        HEMP_CLOTH,
        DUNG,
        TANNED_LEATHER,
        SCOURED_LEATHER,
        LEATHER_STRAP,
        LEATHER_BELT,
        WOOD_BLADE,
        WINDMILL_BLADE,
        GLUE,
        TALLOW,
        INGOT_STEEL,
        GROUND_NETHERRACK,
        HELLFIRE_DUST,
        CONCENTRATED_HELLFIRE,
        COAL_DUST,
        FILAMENT,
        POLISHED_LAPIS,
        POTASH,
        SAWDUST,
        SOUL_DUST,
        SCREW,
        BRIMSTONE,
        NITER,
        ELEMENT,
        FUSE,
        BLASTING_OIL,
        NUGGET_IRON,
        NUGGET_STEEL,
        LEATHER_CUT,
        TANNED_LEATHER_CUT,
        SCOURED_LEATHER_CUT,
        REDSTONE_LATCH,
        NETHER_SLUDGE,
        FLOUR,
        HAFT,
        CHARCOAL_DUST,
        SHARPENING_STONE,
        KNIFE_BLADE,
        SOUL_FLUX,
        ENDER_SLAG,
        ENDER_OCULAR,
        PADDING,
        ARMOR_PLATE,
        BROADHEAD,
        COCOA_POWDER,
        DIAMOND_INGOT,
        CHAIN_MAIL;

        int getMetadata() {
            return this.ordinal();
        }
    }
}
