package betterwithmods.common.registry;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class TurntableRotationManager {
    public static final HashMap<Predicate<Block>, IRotation> PREDICATE_ROTATIONS = Maps.newHashMap();
    public static final HashMap<Block, IRotation> BLOCK_ROTATIONS = Maps.newHashMap();

    private static IRotation NO_ROTATION = (world, pos) -> false, BASE_ROTATION = (world, pos) -> true;

    public interface IRotation {
        boolean isValid(World world, BlockPos pos);

        default boolean rotate(World world, BlockPos pos, Rotation rotation) {
            IBlockState state = world.getBlockState(pos);
            return world.setBlockState(pos, state.withRotation(rotation));
        }

        default boolean canTransmitVertically(World world, BlockPos pos) {
            Block block = world.getBlockState(pos).getBlock();
            if (block == Blocks.GLASS || block == Blocks.STAINED_GLASS)
                return true;
            return world.isBlockNormalCube(pos, false);
        }

        default boolean canTransmitHorizontally(World world, BlockPos pos) {
            return true;
        }
    }

    public static void addRotationBlacklist(Predicate<Block> predicate) {
        addRotationHandler(predicate, NO_ROTATION);
    }

    public static void addRotationHandler(Predicate<Block> predicate, IRotation rotation) {
        PREDICATE_ROTATIONS.put(predicate, rotation);
    }

    public static void addRotationHandler(Block block, IRotation rotation) {
        BLOCK_ROTATIONS.put(block, rotation);
    }

    public static IRotation rotate(World world, BlockPos pos, Rotation rotation) {
        Block block = world.getBlockState(pos).getBlock();

        IRotation handler = BLOCK_ROTATIONS.getOrDefault(block, BASE_ROTATION);
        if(handler == BASE_ROTATION) {
            for (Map.Entry<Predicate<Block>, IRotation> entry : PREDICATE_ROTATIONS.entrySet()) {
                if (entry.getKey().test(block)) {
                    handler = entry.getValue();
                    break;
                }
            }
        }
        if (handler.isValid(world, pos)) {
            if (handler.rotate(world, pos, rotation)) {
                world.scheduleBlockUpdate(pos, block, block.tickRate(world), 1);
                world.notifyNeighborsOfStateChange(pos, block, true);
            }
            return handler;
        }
        return null;
    }

}
