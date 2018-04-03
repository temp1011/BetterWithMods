package betterwithmods.common.registry.block.managers;

import betterwithmods.common.BWMRecipes;
import betterwithmods.common.blocks.mechanical.tile.TileEntityTurntable;
import betterwithmods.common.registry.block.recipe.BlockIngredient;
import betterwithmods.common.registry.block.recipe.TurntableRecipe;
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

public class TurntableManagerBlock extends CraftingManagerBlock<TurntableRecipe> {

    public TurntableRecipe addDefaultRecipe(ItemStack input, ItemStack productState) {
        return addDefaultRecipe(new BlockIngredient(input), BWMRecipes.getStateFromStack(productState), Lists.newArrayList());
    }

    public TurntableRecipe addDefaultRecipe(ItemStack input, ItemStack productState, List<ItemStack> outputs) {
        return addDefaultRecipe(new BlockIngredient(input), BWMRecipes.getStateFromStack(productState), outputs);
    }

    public TurntableRecipe addDefaultRecipe(ItemStack input, IBlockState productState, List<ItemStack> outputs) {
        return addDefaultRecipe(new BlockIngredient(input), productState, outputs);
    }

    public TurntableRecipe addDefaultRecipe(BlockIngredient input, IBlockState productState, List<ItemStack> outputs) {
        return addRecipe(input, productState, outputs, 8);
    }

    public TurntableRecipe addRecipe(BlockIngredient input, IBlockState productState, List<ItemStack> outputs, int rotations) {
        return addRecipe(new TurntableRecipe(input, productState, outputs, rotations));
    }

    @Override
    public TurntableRecipe addRecipe(TurntableRecipe recipe) {
        return super.addRecipe(recipe);
    }

    public TileEntityTurntable findTurntable(World world, BlockPos craftingPos) {
        for(int i = 1; i < 2;i++) {
            TileEntity tile = world.getTileEntity(craftingPos.down(i));
            if(tile instanceof TileEntityTurntable) {
                return (TileEntityTurntable) tile;
            }
        }
        return null;
    }

    @Override
    public boolean craftRecipe(World world, BlockPos pos, Random rand, IBlockState state) {
        TileEntityTurntable turntable = findTurntable(world,pos);
        TurntableRecipe recipe = findRecipe(world, pos, state).orElse(null);
        if (recipe != null && turntable.getPotteryRotation() == recipe.getRotations()) {
            InvUtils.ejectStackWithOffset(world, pos, craftItem(world, pos, state));
            state.getBlock().onBlockHarvested(world, pos, state, FakePlayerHandler.getPlayer());
            world.setBlockState(pos, recipe.getProductState(), world.isRemote ? 11 : 3);
            return true;
        }
        return false;
    }
}
