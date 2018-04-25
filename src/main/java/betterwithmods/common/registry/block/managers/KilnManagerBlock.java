package betterwithmods.common.registry.block.managers;

import betterwithmods.common.blocks.tile.TileKiln;
import betterwithmods.common.registry.KilnStructureManager;
import betterwithmods.common.registry.block.recipe.BlockIngredient;
import betterwithmods.common.registry.block.recipe.KilnRecipe;
import betterwithmods.common.registry.heat.BWMHeatRegistry;
import betterwithmods.event.FakePlayerHandler;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class KilnManagerBlock extends CraftingManagerBlock<KilnRecipe> {

    public KilnRecipe addStokedRecipe(ItemStack input, ItemStack outputs) {
        return addRecipe(input, outputs, BWMHeatRegistry.STOKED_HEAT);
    }

    public KilnRecipe addStokedRecipe(ItemStack input, List<ItemStack> outputs) {
        return addRecipe(input, outputs, BWMHeatRegistry.STOKED_HEAT);
    }


    public KilnRecipe addUnstokedRecipe(ItemStack input, ItemStack outputs) {
        return addRecipe(input, outputs, BWMHeatRegistry.UNSTOKED_HEAT);
    }

    public KilnRecipe addRecipe(ItemStack input, ItemStack outputs, int heat) {
        return addRecipe(input, Lists.newArrayList(outputs), heat);
    }

    public KilnRecipe addRecipe(ItemStack input, List<ItemStack> outputs, int heat) {
        return addRecipe(new KilnRecipe(new BlockIngredient(input), outputs, heat, 500 * heat));
    }

    public KilnRecipe addRecipe(ItemStack input, List<ItemStack> outputs, int heat, int cookTime) {
        return addRecipe(new KilnRecipe(new BlockIngredient(input), outputs, heat, cookTime));
    }

    @Override
    public KilnRecipe addRecipe(KilnRecipe recipe) {
        return super.addRecipe(recipe);
    }

    public TileKiln findKiln(World world, BlockPos craftingPos) {
        TileEntity tile = world.getTileEntity(craftingPos.down());
        if (tile instanceof TileKiln) {
            return (TileKiln) tile;
        }
        return null;
    }

    @Override
    public boolean canCraft(World world, BlockPos pos, IBlockState state) {
        KilnRecipe recipe = findRecipe(world, pos, state).orElse(null);
        return recipe != null && (recipe.ignore() || KilnStructureManager.getKiln().getHeat(world, pos.down()) == recipe.getHeat());
    }

    public boolean craftRecipe(World world, BlockPos pos, Random random, IBlockState state) {
        TileKiln turntable = findKiln(world, pos);
        KilnRecipe recipe = findRecipe(world, pos, state).orElse(null);
        if (recipe != null && turntable.getCookTicks() >= recipe.getCookTime()) {
            InvUtils.ejectStackWithOffset(world, pos, craftItem(world, pos, state));
            state.getBlock().onBlockHarvested(world, pos, state, FakePlayerHandler.getPlayer());
            world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
            return true;
        }
        return false;
    }

    public List<KilnRecipe> getRecipesForHeat(int heat) {
        return getRecipes().stream().filter( r -> r.getHeat() == heat).collect(Collectors.toList());
    }
}
