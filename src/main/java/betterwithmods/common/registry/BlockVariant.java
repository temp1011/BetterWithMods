package betterwithmods.common.registry;

import betterwithmods.api.util.IBlockVariants;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class BlockVariant implements IBlockVariants {
    private HashMap<EnumBlock, ItemStack> variants = Maps.newHashMap();

    public static final BlockVariant builder() {
        return new BlockVariant();
    }

//    public BlockVariant(ItemStack log, ItemStack plank) {
//        this(log, plank, ItemBark.getStack("oak", 1));
//    }
//
//    public BlockVariant(ItemStack log, ItemStack plank, ItemStack bark) {
//        addVariant(EnumBlock.LOG, log);
//        addVariant(EnumBlock.BLOCK, plank);
//        addVariant(EnumBlock.BARK, bark);
//        addVariant(EnumBlock.SAWDUST, ItemMaterial.getStack(ItemMaterial.EnumMaterial.SAWDUST));
//    }

    //TODO souldust

    @Override
    public ItemStack getVariant(EnumBlock type, int count) {
        return variants.getOrDefault(type, ItemStack.EMPTY);
    }

    @Override
    public BlockVariant addVariant(EnumBlock type, ItemStack stack) {
        variants.put(type, stack.copy());
        return this;
    }

}