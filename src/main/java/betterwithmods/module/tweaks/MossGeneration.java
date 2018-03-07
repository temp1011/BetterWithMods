package betterwithmods.module.tweaks;

import betterwithmods.module.Feature;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.RandomUtils;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by primetoxinz on 4/20/17.
 */
public class MossGeneration extends Feature {
    private static final int RADIUS = 5;

    @SubscribeEvent
    public void generateMossNearSpawner(TickEvent.WorldTickEvent event) {
        World world = event.world;
        if (world.isRemote || event.phase != TickEvent.Phase.END || event.side != Side.SERVER)
            return;
        try {
            List<BlockPos> positions = world.loadedTileEntityList.stream().filter(t -> t instanceof TileEntityMobSpawner).map(TileEntity::getPos).collect(Collectors.toList());
            positions.forEach(pos -> {
                BlockPos min = pos.add(-RADIUS, -RADIUS, -RADIUS), max = pos.add(RADIUS, RADIUS, RADIUS);
                BlockPos set = randomPosition(world, min, max);
                if (set != null) {
                    mossify(world, set);
                }
            });
        } catch (ConcurrentModificationException ignored) {
            ignored.printStackTrace();
        }
    }

    public static BlockPos randomPosition(World world, BlockPos start, BlockPos end) {
        if (world.isAreaLoaded(start, end)) {
            return new BlockPos(randomRange(start.getX(), end.getX()), randomRange(start.getY(), end.getY()), randomRange(start.getZ(), end.getZ()));
        }
        return null;
    }

    public static void mossify(World world, BlockPos pos) {
        IBlockState mossy;
        if (world.rand.nextInt(30) == 0 && (mossy = getMossyVariant(world.getBlockState(pos))) != null) {
            world.setBlockState(pos, mossy);
        }
    }

    public static IBlockState getMossyVariant(IBlockState state) {
        if (state.getBlock() == Blocks.COBBLESTONE) {
            return Blocks.MOSSY_COBBLESTONE.getDefaultState();
        } else if (state.getBlock() == Blocks.STONEBRICK) {
            return Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
        }
        return null;
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @Override
    public String getFeatureDescription() {
        return "Cobblestone or Stonebrick within the spawning radius of a Mob Spawner will randomly grow into the Mossy version.";
    }

    public static int randomRange(int start, int end) {
        int d = end - start;
        return start + RandomUtils.nextInt(0,d);
    }
}
