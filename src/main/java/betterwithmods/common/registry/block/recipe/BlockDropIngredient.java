package betterwithmods.common.registry.block.recipe;

import betterwithmods.common.BWMRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockDropIngredient extends BlockIngredient {

    public BlockDropIngredient(ItemStack... stacks) {
        super(stacks);
    }

    @Override
    public boolean apply(World world, BlockPos pos, @Nullable IBlockState state) {
        return apply(BWMRecipes.getStackFromState(state));
    }
}
