package betterwithmods.common.blocks.mechanical.tile;

import net.minecraft.util.EnumFacing;

public class TileSpringActionBellows extends TileBellows {
    @Override
    public void onChange() {
        int power = calculateInput();
        if (power != this.power)
            this.power = power;
        if (this.power > 0) {
            if (tick >= 37) {
                getBlock().setActive(world, pos, !getBlock().isActive(world.getBlockState(pos)));
                tick = 0;
            }
            tick++;
        }
    }

    @Override
    public int getMaximumInput(EnumFacing facing) {
        return 3;
    }
}
