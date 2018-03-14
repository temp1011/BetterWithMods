package betterwithmods.api.tile;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHeated {
    int getHeat();
    World getWorld();
    BlockPos getPos();
}
