package betterwithmods.manual.client.manual.provider;

import betterwithmods.BWMod;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.blocks.*;
import betterwithmods.common.blocks.mechanical.BlockCookingPot;
import betterwithmods.common.blocks.mechanical.BlockMechMachines;
import betterwithmods.common.blocks.mechanical.BlockWindmill;
import betterwithmods.common.blocks.mini.BlockMini;
import betterwithmods.manual.api.manual.PathProvider;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

import static net.minecraft.block.material.Material.ROCK;
import static net.minecraft.block.material.Material.WOOD;

/**
 * Created by primetoxinz on 6/18/17.
 */
public class DefinitionPathProvider implements PathProvider {
    public static Set<ItemStack> blacklist = Sets.newHashSet(ItemStack.EMPTY);

    public static boolean isBlacklisted(ItemStack stack) {
        return blacklist.stream().anyMatch(stack::isItemEqual);
    }

    @Nullable
    @Override
    public String pathFor(ItemStack stack) {
        if (isBlacklisted(stack) || !stack.getItem().getRegistryName().getResourceDomain().equals(BWMod.MODID))
            return null;
        String path = stack.getUnlocalizedName().replace("item.bwm:", "");
        return "%LANGUAGE/items/" + path;
    }

    @Nullable
    @Override
    public String pathFor(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        int meta = block.damageDropped(state);

        if (block instanceof BlockKiln)
            return "%LANGUAGE%/blocks/kiln";

        ItemStack stack = new ItemStack(block, 1, meta);

        if (isBlacklisted(stack) || !stack.getItem().getRegistryName().getResourceDomain().equals(BWMod.MODID))
            return null;
        else if (block instanceof BlockRope)
            return "%LANGUAGE%/items/rope";
        else if (block == BWMBlocks.PLANTER)
            return "%LANGUAGE%/blocks/planter";
        else if (block == BWMBlocks.BLOOD_SAPLING || block == BWMBlocks.BLOOD_LOG || block == BWMBlocks.BLOOD_LEAVES) {
            return "%LANGUAGE%/blocks/blood_wood";
        } else if (block instanceof BlockChime) {
            return "%LANGUAGE%/blocks/wind_chime";
        } else if (block instanceof BlockCookingPot) {
            return "%LANGUAGE%/blocks/" + state.getValue(BlockCookingPot.TYPE).getName();
        } else if (block instanceof BlockMechMachines) {
            return "%LANGUAGE%/blocks/" + state.getValue(BlockMechMachines.TYPE).getName();
        } else if (block instanceof BlockFurniture || block instanceof BlockPane || block instanceof BlockIronWall) {
            return "%LANGUAGE%/blocks/decoration";
        } else if (block instanceof BlockMini) {
            Material mat = state.getMaterial();
            if (mat == WOOD || mat == BlockMini.MINI) {
                return "%LANGUAGE%/blocks/minimized_wood";
            } else if (mat == ROCK) {
                return "%LANGUAGE%/blocks/minimized_stone";
            }
        } else if (block instanceof BlockEnderchest || state.equals(BlockAesthetic.getVariant(BlockAesthetic.EnumType.ENDERBLOCK))) {
            return "%LANGUAGE%/hardcore/beacons";
        } else if (block instanceof BlockWindmill) {
            return "%LANGUAGE%/blocks/windmill";
        }

        String path = stack.getUnlocalizedName().replace("tile.bwm:", "");
        return "%LANGUAGE%/blocks/" + path;
    }
}
