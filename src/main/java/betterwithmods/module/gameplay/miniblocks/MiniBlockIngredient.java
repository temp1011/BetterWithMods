package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.common.BWMRecipes;
import betterwithmods.common.registry.blockmeta.recipe.BlockIngredient;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMini;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MiniBlockIngredient extends BlockIngredient {

    private Ingredient base;
    private ItemStack stack;
    private MiniType type;
    private ItemStack[] cache = null;

    protected MiniBlockIngredient(String type, ItemStack stack) {
        this.type = MiniType.fromName(type.toLowerCase());
        this.base = Ingredient.fromStacks(stack);
        this.stack = stack;
    }

    @Override
    public boolean apply(@Nullable ItemStack stack) {
        IBlockState state = ItemMini.getState(stack);
        if (state != null && MiniType.matches(type, stack)) {
            ItemStack baseStack = BWMRecipes.getStackFromState(state);
            return base.apply(baseStack);
        }
        return false;
    }

    @Override
    public boolean apply(World world, BlockPos pos, @Nullable IBlockState state) {
        return state != null && apply(state.getBlock().getPickBlock(state, null, world, pos, null));
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack[] getMatchingStacks() {
        if (cache == null) {
            if (!stack.isEmpty() && stack.getItem() instanceof ItemBlock) {
                IBlockState state = BWMRecipes.getStateFromStack(stack);
                Material material = state.getMaterial();
                BlockMini blockMini = MiniBlocks.MINI_MATERIL_BLOCKS.get(type).get(material);
                cache = new ItemStack[]{MiniBlocks.fromParent(blockMini, state)};
            } else {
                cache = new ItemStack[0];
            }
        }
        return cache;
    }
}
