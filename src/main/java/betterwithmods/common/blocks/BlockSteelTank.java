package betterwithmods.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
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
        return state.getBlock() instanceof BlockSteelTank;
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


    public enum Connection implements IStringSerializable {
        ALL(EnumFacing.HORIZONTALS),
        EAST_WEST_SOUTH(EnumFacing.EAST,EnumFacing.WEST, EnumFacing.SOUTH),
        EAST_WEST_NORTH(EnumFacing.EAST,EnumFacing.WEST, EnumFacing.NORTH),
        NORTH_SOUTH_EAST(EnumFacing.NORTH,EnumFacing.SOUTH, EnumFacing.EAST),
        NORTH_SOUTH_WEST(EnumFacing.NORTH,EnumFacing.SOUTH, EnumFacing.WEST),
        NORTH_EAST(EnumFacing.NORTH, EnumFacing.EAST),
        NORTH_WEST(EnumFacing.NORTH, EnumFacing.WEST),
        SOUTH_EAST(EnumFacing.SOUTH, EnumFacing.EAST),
        SOUTH_WEST(EnumFacing.SOUTH, EnumFacing.WEST),

        NONE();

        public static final Connection[] VALUES = values();

        private EnumFacing[] faces;

        Connection(EnumFacing... faces) {
            this.faces = faces;
        }

        public boolean connected(IBlockAccess world, BlockPos pos) {
            if(faces == null)
                return true;
            return Arrays.stream(faces).allMatch( f -> canConnectTo(world,pos,f));
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
