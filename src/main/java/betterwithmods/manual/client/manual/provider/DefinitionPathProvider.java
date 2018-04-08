package betterwithmods.manual.client.manual.provider;

import betterwithmods.BWMod;
import betterwithmods.common.BWMBlocks;
import betterwithmods.common.blocks.*;
import betterwithmods.common.blocks.mechanical.BlockWindmill;
import betterwithmods.manual.api.manual.PathProvider;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockFurniture;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMini;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.BiFunction;

import static net.minecraft.block.material.Material.ROCK;
import static net.minecraft.block.material.Material.WOOD;

/**
 * Created by primetoxinz on 6/18/17.
 */
public class DefinitionPathProvider implements PathProvider {
    public static Set<ItemStack> blacklist = Sets.newHashSet(ItemStack.EMPTY);

    BiFunction<World, IBlockState, String> customPath = (world, state) -> {
        Block block = state.getBlock();
        return null;
    };

    public static boolean isBlacklisted(ItemStack stack) {
        return blacklist.stream().anyMatch(s -> stack.isItemEqual(s));
    }

    @Nullable
    @Override
    public String pathFor(ItemStack stack) {
        if (isBlacklisted(stack) || !stack.getItem().getRegistryName().getResourceDomain().equals(BWMod.MODID))
            return null;
        String path = stack.getUnlocalizedName().replace("item.bwm:", "");
        return "%LANGUAGE/items/" + path + ".md";
    }

    @Nullable
    @Override
    public String pathFor(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        int meta = block.damageDropped(state);

        if (block instanceof BlockKiln)
            return "%LANGUAGE%/blocks/kiln.md";

        ItemStack stack = new ItemStack(block, 1, meta);

        if (isBlacklisted(stack) || !stack.getItem().getRegistryName().getResourceDomain().equals(BWMod.MODID))
            return null;
        else if (block instanceof BlockRope)
            return "%LANGUAGE%/items/rope.md";
        else if (block instanceof BlockPlanter)
            return "%LANGUAGE%/blocks/planter.md";
        else if (block == BWMBlocks.BLOOD_SAPLING || block == BWMBlocks.BLOOD_LOG || block == BWMBlocks.BLOOD_LEAVES) {
            return "%LANGUAGE%/blocks/blood_wood.md";
        } else if (block instanceof BlockChime) {
            return "%LANGUAGE%/blocks/wind_chime.md";
        } else if (block instanceof BlockFurniture || block instanceof BlockPane || block instanceof BlockIronWall) {
            return "%LANGUAGE%/blocks/decoration.md";
        } else if (block instanceof BlockMini) {
            Material mat = state.getMaterial();
            if (mat == WOOD) {
                return "%LANGUAGE%/blocks/minimized_wood.md";
            } else if (mat == ROCK) {
                return "%LANGUAGE%/blocks/minimized_stone.md";
            }
        } else if (block instanceof BlockEnderchest || state.equals(BlockAesthetic.getVariant(BlockAesthetic.EnumType.ENDERBLOCK))) {
            return "%LANGUAGE%/hardcore/beacons.md";
        } else if (block instanceof BlockWindmill) {
            return "%LANGUAGE%/blocks/windmill.md";
        }

        String path = stack.getUnlocalizedName().replace("tile.bwm:", "");
        return "%LANGUAGE%/blocks/" + path + ".md";
    }
}
