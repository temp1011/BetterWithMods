package betterwithmods.common.registry.heat;

import betterwithmods.api.tile.IHeatSource;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class BWMHeatRegistry {
    private static final List<HeatSource> HEAT_SOURCES = Lists.newArrayList();

    public static void addHeatSource(Predicate<IBlockState> state, int heat) {
        HEAT_SOURCES.add(new BlockStateHeatSource(state, heat));
    }

    public static void addHeatSource(Block block, int heat) {
        HEAT_SOURCES.add(new BlockStateHeatSource(state -> state.getBlock().equals(block), heat));
    }


    public static int getHeat(World world, BlockPos pos) {
        HeatSource source = get(world, pos);
        if (source != null)
            return source.getHeat();
        return 0;
    }

    public static HeatSource get(World world, BlockPos pos) {
        return HEAT_SOURCES.stream().filter(bm -> bm.matches(world, pos)).findFirst().orElse(null);
    }

    public static class HeatSource implements IHeatSource {
        private BiPredicate<World, BlockPos> predicate;
        private int heat;

        public HeatSource(BiPredicate<World, BlockPos> predicate, int heat) {
            this.predicate = predicate;
            this.heat = heat;
        }

        public int getHeat() {
            return heat;
        }

        public boolean matches(World world, BlockPos pos) {
            return predicate.test(world, pos);
        }
    }

    public static class BlockStateHeatSource extends HeatSource {
        public BlockStateHeatSource(Predicate<IBlockState> predicate, int heat) {
            super((world, pos) -> predicate.test(world.getBlockState(pos)), heat);
        }
    }
}