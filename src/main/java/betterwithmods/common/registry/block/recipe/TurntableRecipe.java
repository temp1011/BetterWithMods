package betterwithmods.common.registry.block.recipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Optional;

/**
 * Created by primetoxinz on 4/20/17.
 */
public class TurntableRecipe extends BlockRecipe {
    private int rotations;

    private IBlockState productState;
    private ItemStack representative;

    public TurntableRecipe(BlockIngredient input, List<ItemStack> outputs, IBlockState productState, int rotations) {
        this(input, productState, outputs, rotations);
    }

    public TurntableRecipe(BlockIngredient input, IBlockState productState, List<ItemStack> outputs, int rotations) {
        this(input, productState, new ItemStack(productState.getBlock(),1,productState.getBlock().getMetaFromState(productState)), outputs, rotations);
    }

    public TurntableRecipe(BlockIngredient input, IBlockState productState, ItemStack representative, List<ItemStack> outputs, int rotations) {
        super(input, outputs);
        this.rotations = rotations;
        this.productState = productState;
        this.representative = representative;
    }

    public int getRotations() {
        return rotations;
    }

    public ItemStack getRepresentative() {
        return representative;
    }

    public IBlockState getProductState() {
        return Optional.ofNullable(productState).orElse(Blocks.AIR.getDefaultState());
    }
}
