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

    public TurntableRecipe(BlockIngredient input, List<ItemStack> outputs, IBlockState productState, int rotations) {
        super(input, outputs);
        this.rotations = rotations;
        this.productState = productState;
    }

    public TurntableRecipe(BlockIngredient input, IBlockState productState, List<ItemStack> outputs, int rotations) {
        super(input, outputs);
        this.rotations = rotations;
        this.productState = productState;
    }

    public int getRotations() {
        return rotations;
    }

    public IBlockState getProductState() {
        return Optional.ofNullable(productState).orElse(Blocks.AIR.getDefaultState());
    }
}
