package betterwithmods.common.registry.block.managers;

import betterwithmods.common.registry.block.recipe.BlockIngredient;
import betterwithmods.common.registry.block.recipe.SawRecipe;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class SawManagerBlock extends CraftingManagerBlock<SawRecipe> {

    public SawRecipe addRecipe(ItemStack input, ItemStack outputs) {
        return addRecipe(input, Lists.newArrayList(outputs));
    }

    public SawRecipe addRecipe(ItemStack input, List<ItemStack> outputs) {
        return addRecipe(new SawRecipe(new BlockIngredient(input), outputs));
    }

    public SawRecipe addRecipe(BlockIngredient input, ItemStack outputs) {
        return addRecipe(input, Lists.newArrayList(outputs));
    }

    public SawRecipe addRecipe(BlockIngredient input, List<ItemStack> outputs) {
        return addRecipe(new SawRecipe(input, outputs));
    }

    public SawRecipe addSelfdropRecipe(ItemStack stack) {
        return addRecipe(new BlockIngredient(stack), Lists.newArrayList(stack));
    }

    public boolean craftRecipe(World world, BlockPos pos, Random rand, IBlockState state) {
        if (canCraft(world, pos, state)) {
            List<ItemStack> output = craftItem(world, pos, state);
            world.setBlockToAir(pos);
            InvUtils.ejectStackWithOffset(world, pos, output);
            world.playSound(null, pos, SoundEvents.ENTITY_MINECART_RIDING, SoundCategory.BLOCKS, 1.5F + rand.nextFloat() * 0.1F, 2.0F + rand.nextFloat() * 0.1F);
            return true;
        }
        return false;
    }

}
