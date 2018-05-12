package betterwithmods.util;


import org.apache.commons.lang3.tuple.Pair;

public class ReflectionLib {


    public static final String[] ITEMTOOL_EFFECTIVE_BLOCKS = new String[]{"effectiveBlocks", "field_150914_c"};

    public static final String[] DEFAULT_RESOURCE_PACKS = new String[]{"field_110449_ao", "defaultResourcePacks"};

    public static final String[] IMC_MESSAGE_VALUE = new String[]{"value"};

    public static final String[] MAP_GEN_VILLAGE_SIZE = new String[]{"size", "field_75054_f"};

    public static final String[] ENTITY_VILLAGER_ISWILLINGTOMATE = new String[]{"isWillingToMate", "field_175565_bs"};

    public static final String[] INVENTORY_CRAFTING_EVENTHANDLER = new String[]{"eventHandler", "field_70465_c"};
    public static final String[] CONTAINER_WORKBENCH_PLAYER = new String[]{"player", "field_192390_i"};
    public static final String[] CONTAINER_PLAYER_PLAYER = new String[]{"player", "field_82862_h"};

    public static final String[] ENTITY_PIG_TEMPTATIONITEM = new String[]{"TEMPTATION_ITEMS", "field_184764_bw"};

    public static final String[] TOOLMATERIAL_MAXUSES = new String[]{"field_78002_g", "maxUses"};
    public static final String[] TOOLMATERIAL_EFFICIENCY = new String[]{"field_78010_h", "efficiency"};
    public static final String[] TOOLMATERIAL_ENCHANTABILITIY = new String[]{"field_78008_j", "enchantability"};

    public static final String[] POTIONHELPER_TYPE_CONVERSIONS = new String[]{"field_185213_a", "POTION_TYPE_CONVERSIONS"};
    public static final String[] POTIONHELPER_ITEM_CONVERSIONS = new String[]{"field_185214_b", "POTION_ITEM_CONVERSIONS"};

    public static final String[] ENCHANTMENT_APPLICIBLE_EQUIPMENT_TYPES= new String[]{"field_185263_a", "applicableEquipmentTypes"};

    public static final String CTM_TTR = "team.chisel.ctm.client.texture.type.TextureTypeRegistry";
    public static final String CTM_ITT = "team.chisel.ctm.api.texture.ITextureType";
    public static final String CTM_TTS = "betterwithmods.module.hardcore.world.strata.TextureTypeStrata";
    public static final Pair<String, String> CTM_REGISTER = Pair.of("register", "register");


    public static final Pair<String, String> SILK_TOUCH_DROP = Pair.of("getSilkTouchDrop", "func_180643_i");
}
