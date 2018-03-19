package betterwithmods.api.tile;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHeatRecipe {
    int getHeat();

    default boolean canCraft(IHeated tile, World world, BlockPos pos) {
        return ignore() || getHeat() == tile.getHeat(world, pos);
    }

    boolean ignore();
}
