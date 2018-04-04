package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.common.BWMRecipes;
import betterwithmods.common.registry.block.recipe.BlockIngredient;
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
import java.util.ArrayList;

public class MiniBlockIngredient extends BlockIngredient {

    private Ingredient base;
    private MiniType type;
    private ItemStack[] cache = null;

    public MiniBlockIngredient(String type, Ingredient base) {
        this.type = MiniType.fromName(type.toLowerCase());
        this.base = base;
    }

    public MiniBlockIngredient(String type, ItemStack stack) {
        this(type,Ingredient.fromStacks(stack));
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
    protected void invalidate() {
        super.invalidate();
        cache = null;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack[] getMatchingStacks() {
        if (cache == null) {
            ArrayList<ItemStack> stacks = new ArrayList<>();
            for (ItemStack stack : base.getMatchingStacks()) {
                if (!stack.isEmpty() && stack.getItem() instanceof ItemBlock) {
                    IBlockState state = BWMRecipes.getStateFromStack(stack);
                    Material material = state.getMaterial();
                    BlockMini blockMini = MiniBlocks.MINI_MATERIL_BLOCKS.get(type).get(material);
                    stacks.add(MiniBlocks.fromParent(blockMini, state));
                }
            }
            cache = stacks.toArray(new ItemStack[stacks.size()]);
        }
        return cache;
    }
}
