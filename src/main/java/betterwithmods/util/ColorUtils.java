package betterwithmods.util;

import betterwithmods.common.BWOreDictionary;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ColorUtils {
    public static final PropertyEnum<EnumDyeColor> COLOR = BlockColored.COLOR;

    public static final EnumDyeColor[] DYES = EnumDyeColor.values();

    private static HashMap<String, EnumDyeColor> DYE_CACHE = Maps.newHashMap();
    @Nullable
    private static EnumDyeColor getDye(String dyeOredict) {
        if (!DYE_CACHE.containsKey(dyeOredict)) {
            for (EnumDyeColor dye : DYES) {
                String oredict = String.format("dye%s", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, dye.getUnlocalizedName()));
                if (oredict.matches(dyeOredict)) {
                    DYE_CACHE.put(dyeOredict, dye);
                    break;
                }
            }
        }
        return DYE_CACHE.get(dyeOredict);
    }


    public static float[] average(float[]... arrays) {
        int divisor = arrays.length;
        float[] output = new float[arrays[0].length];
        for (int i = 0; i < divisor; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                output[j] += arrays[i][j];
            }
        }
        for (int i = 0; i < output.length; i++) {
            output[i] = output[i] / divisor;
        }
        return output;
    }

    @Nullable
    public static EnumDyeColor getColor(ItemStack stack) {
        if (stack != ItemStack.EMPTY && BWOreDictionary.hasPrefix(stack, "dye")) {
            for (String ore : BWOreDictionary.getOres(stack)) {
                EnumDyeColor dye = getDye(ore);
                if (dye != null)
                    return dye;
            }
        }
        return null;
    }


    public static float[] getColorFromBlock(World world, BlockPos pos, BlockPos beacon) {
        if (world.isAirBlock(pos))
            return new float[]{1, 1, 1};
        IBlockState state = world.getBlockState(pos);
        float[] color = state.getBlock().getBeaconColorMultiplier(state, world, pos, beacon);
        if (state.getBlock() == Blocks.STAINED_GLASS) {
            color = state.getValue(BlockStainedGlass.COLOR).getColorComponentValues();
        } else if (state.getBlock() == Blocks.STAINED_GLASS_PANE) {
            color = state.getValue(BlockStainedGlassPane.COLOR).getColorComponentValues();
        }
        return color != null ? color : new float[]{1, 1, 1};
    }
}
