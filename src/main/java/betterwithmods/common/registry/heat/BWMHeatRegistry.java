package betterwithmods.common.registry.heat;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import java.util.List;
import java.util.function.Predicate;

public class BWMHeatRegistry {
    private static final List<HeatSource> HEAT_SOURCES = Lists.newArrayList();

    public static void addHeatSource(Predicate<IBlockState> state, int heat) {
        HEAT_SOURCES.add(new HeatSource(state, heat));
    }

    public static void addHeatSource(Block block, int heat) {
        HEAT_SOURCES.add(new HeatSource(state -> state.getBlock().equals(block), heat));
    }

    public static int getHeat(IBlockState state) {
        HeatSource source = get(state);
        if (source != null)
            return source.getHeat();
        return 0;
    }

    public static HeatSource get(IBlockState state) {
        return HEAT_SOURCES.stream().filter(bm -> bm.matches(state)).findFirst().orElse(null);
    }

    public static class HeatSource {
        private Predicate<IBlockState> predicate;
        private int heat;

        public HeatSource(Predicate<IBlockState> predicate, int heat) {
            this.predicate = predicate;
            this.heat = heat;
        }

        public int getHeat() {
            return heat;
        }

        public boolean matches(IBlockState state) {
            return predicate.test(state);
        }
    }
}