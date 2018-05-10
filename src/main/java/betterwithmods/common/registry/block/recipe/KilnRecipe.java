package betterwithmods.common.registry.block.recipe;

import betterwithmods.api.tile.IHeatRecipe;
import betterwithmods.common.registry.KilnStructureManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class KilnRecipe extends BlockRecipe implements IHeatRecipe {
    private int heat;
    private boolean ignoreHeat;

    public KilnRecipe(BlockIngredient input, List<ItemStack> outputs, int heat) {
        super(input, outputs);
        this.heat = heat;
    }

    @Override
    public int getHeat() {
        return heat;
    }

    @Override
    public boolean ignore() {
        return ignoreHeat;
    }

    public KilnRecipe setIgnoreHeat(boolean ignoreHeat) {
        this.ignoreHeat = ignoreHeat;
        return this;
    }

    @Override
    public boolean matches(World world, BlockPos pos, IBlockState state) {
        return super.matches(world, pos, state) && (ignore() || KilnStructureManager.getKiln().getHeat(world, pos.down()) == getHeat());
    }
}
