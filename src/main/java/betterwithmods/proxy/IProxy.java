package betterwithmods.proxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.Arrays;

public interface IProxy {
    default void preInit(FMLPreInitializationEvent event) {
    }

    default void init(FMLInitializationEvent event) {
    }

    default void postInit(FMLPostInitializationEvent event) {
    }

    default void addResourceOverride(String space, String dir, String file, String ext) {
    }

    default void addResourceOverride(String space, String domain, String dir, String file, String ext) {
    }

    default void syncHarness(int entityId, ItemStack harness) {
    }

    default NonNullList<ItemStack> getSubItems(Item item) {
        return NonNullList.withSize(1,item.getDefaultInstance());
    }
}
