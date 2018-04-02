package betterwithmods.common.registry.blockmeta.managers;

import betterwithmods.common.registry.KilnStructureManager;
import betterwithmods.common.registry.blockmeta.recipe.BlockIngredient;
import betterwithmods.common.registry.blockmeta.recipe.KilnRecipe;
import betterwithmods.common.registry.heat.BWMHeatRegistry;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class KilnManager extends BlockMetaManager<KilnRecipe> {

    public KilnRecipe addStokedRecipe(ItemStack input, ItemStack outputs) {
        return addRecipe(input, outputs, BWMHeatRegistry.STOKED_HEAT);
    }

    public KilnRecipe addUnstokedRecipe(ItemStack input, ItemStack outputs) {
        return addRecipe(input, outputs, BWMHeatRegistry.UNSTOKED_HEAT);
    }

    public KilnRecipe addRecipe(ItemStack input, ItemStack outputs, int heat) {
        return addRecipe(input, Lists.newArrayList(outputs), heat);
    }

    public KilnRecipe addRecipe(ItemStack input, List<ItemStack> outputs, int heat) {
        return addRecipe(new KilnRecipe(new BlockIngredient(input), outputs, heat));
    }

    @Override
    public KilnRecipe addRecipe(KilnRecipe recipe) {
        return super.addRecipe(recipe);
    }

    @Override
    public boolean canCraft(World world, BlockPos pos, IBlockState state) {
        KilnRecipe recipe = findRecipe(world,pos,state).orElse(null);
        return recipe != null && (recipe.ignore() || KilnStructureManager.getKiln().getHeat(world, pos.down()) == recipe.getHeat());
    }

    public void craftRecipe(World world, BlockPos pos, Random random, IBlockState state) {
        if (canCraft(world, pos, state)) {
            InvUtils.ejectStackWithOffset(world, pos, craftItem(world, pos, state));
            world.setBlockToAir(pos);
        }
    }
}
