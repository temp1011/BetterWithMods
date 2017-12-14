/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 * <p>
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 * <p>
 * File Created @ [18/03/2016, 22:16:30 (GMT)]
 */
package betterwithmods.module;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigHelper {

    public static boolean needsRestart;
    public static boolean allNeedRestart = false;


    public static int[] loadPropIntList(String propName, String category, String comment, int[] default_) {
        Property prop = ModuleLoader.config.get(category, propName, default_, comment);
        setNeedsRestart(prop);
        return prop.getIntList();
    }

    public static int loadPropInt(String propName, String category, String desc, String comment, int default_, int min, int max) {
        Property prop = ModuleLoader.config.get(category, propName, default_, comment, min, max);
        prop.setComment(desc);
        setNeedsRestart(prop);

        return prop.getInt(default_);
    }

    public static int loadPropInt(String propName, String category, String desc, int default_) {
        Property prop = ModuleLoader.config.get(category, propName, default_);
        prop.setComment(desc);
        setNeedsRestart(prop);

        return prop.getInt(default_);
    }

    public static double loadPropDouble(String propName, String category, String desc, double default_) {
        Property prop = ModuleLoader.config.get(category, propName, default_);
        prop.setComment(desc);
        setNeedsRestart(prop);

        return prop.getDouble(default_);
    }

    public static double loadPropDouble(String propName, String category, String desc, double default_, double min, double max) {
        Property prop = ModuleLoader.config.get(category, propName, default_, desc, min, max);
        prop.setComment(desc);
        setNeedsRestart(prop);

        return prop.getDouble(default_);
    }

    public static boolean loadPropBool(String propName, String category, String desc, boolean default_) {
        Property prop = ModuleLoader.config.get(category, propName, default_);
        prop.setComment(desc);
        setNeedsRestart(prop);

        return prop.getBoolean(default_);
    }

    public static String loadPropString(String propName, String category, String desc, String default_) {
        Property prop = ModuleLoader.config.get(category, propName, default_);
        prop.setComment(desc);
        setNeedsRestart(prop);

        return prop.getString();
    }

    public static String[] loadPropStringList(String propName, String category, String desc, String[] default_) {
        Property prop = ModuleLoader.config.get(category, propName, default_);
        prop.setComment(desc);
        setNeedsRestart(prop);

        return prop.getStringList();
    }

    private static ItemStack fromString(String name) {
        String[] split = name.split(":");
        if (split.length > 1) {
            int meta = 0;
            if (split.length > 2) {
                meta = Integer.parseInt(split[2]);
            }
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(split[0], split[1]));
            if(item != null) {
                return new ItemStack(item, 1, meta);
            }
        }
        return ItemStack.EMPTY;
    }

    private static String fromStack(ItemStack stack) {
        if (stack.getMetadata() == 0) {
            return stack.getItem().getRegistryName().toString();
        } else {
            return String.format("%s:%s", stack.getItem().getRegistryName(), stack.getMetadata());
        }

    }

    public static List<ItemStack> loadItemStackList(String propName, String category, String desc, ItemStack[] default_) {
        String[] strings_ = new String[default_.length];
        Arrays.stream(default_).map(ConfigHelper::fromStack).collect(Collectors.toList()).toArray(strings_);
        return Arrays.stream(loadPropStringList(propName,category,desc, strings_)).map(ConfigHelper::fromString).collect(Collectors.toList());
    }

    private static void setNeedsRestart(Property prop) {
        if (needsRestart)
            prop.setRequiresMcRestart(needsRestart);
        needsRestart = allNeedRestart;
    }


}
