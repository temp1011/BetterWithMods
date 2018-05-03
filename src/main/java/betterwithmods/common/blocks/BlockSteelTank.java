package betterwithmods.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Arrays;

public class BlockSteelTank extends BWMBlock {

    public static final PropertyEnum<Connection> CONNECTIONS = PropertyEnum.create("connection", Connection.class);

    public BlockSteelTank() {
        super(Material.IRON);
        setSoundType(SoundType.METAL);
        setHardness(100F);
        setResistance(4000F);
    }

    private static boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing dir) {
        IBlockState state = world.getBlockState(pos.offset(dir));
        return state.getBlock() instanceof BlockSteelTank || state.getBlock() instanceof BlockSteelTankValve;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CONNECTIONS);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        for (Connection connection : Connection.VALUES) {
            if (connection.connected(world, pos)) {
                return state.withProperty(CONNECTIONS, connection);
            }
        }
        return state;
    }


    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return getActualState(state,source,pos).getValue(CONNECTIONS).getBounds();
    }

    public enum Connection implements IStringSerializable {
        ALL(Block.FULL_BLOCK_AABB, EnumFacing.HORIZONTALS),
        EAST_WEST_SOUTH(new AxisAlignedBB(0, 0, 2 / 16d, 1, 1, 1), EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH),
        EAST_WEST_NORTH(new AxisAlignedBB(0, 0, 0, 1, 1, 14 / 16d), EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH),
        NORTH_SOUTH_EAST(new AxisAlignedBB(2 / 16d, 0, 0, 1, 1, 1), EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST),
        NORTH_SOUTH_WEST(new AxisAlignedBB(0, 0, 0, 14 / 16d, 1, 1), EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST),
        NORTH_EAST(new AxisAlignedBB(2 / 16d, 0, 0, 1, 1, 14 / 16d), EnumFacing.NORTH, EnumFacing.EAST),
        NORTH_WEST(new AxisAlignedBB(0, 0, 0, 14 / 16d, 1, 14 / 16d), EnumFacing.NORTH, EnumFacing.WEST),
        SOUTH_EAST(new AxisAlignedBB(2 / 16d, 0, 2 / 16d, 1, 1, 1), EnumFacing.SOUTH, EnumFacing.EAST),
        SOUTH_WEST(new AxisAlignedBB(0, 0, 2 / 16d, 14 / 16d, 1, 1), EnumFacing.SOUTH, EnumFacing.WEST),
        NONE(new AxisAlignedBB(2 / 16d, 2 / 16d, 2 / 16d, 14 / 16d, 14 / 16d, 14 / 16d));

        public static final Connection[] VALUES = values();

        private final AxisAlignedBB bounds;
        private final EnumFacing[] faces;

        Connection(AxisAlignedBB bounds, EnumFacing... faces) {
            this.bounds = bounds;
            this.faces = faces;
        }

        public boolean connected(IBlockAccess world, BlockPos pos) {
            if (faces == null)
                return true;
            return Arrays.stream(faces).allMatch(f -> canConnectTo(world, pos, f));
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

        public AxisAlignedBB getBounds() {
            return bounds;
        }
    }
}
