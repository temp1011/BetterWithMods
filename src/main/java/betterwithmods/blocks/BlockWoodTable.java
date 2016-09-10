package betterwithmods.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockWoodTable extends BlockFurniture
{
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 6);

    public BlockWoodTable()
    {
        super(Material.WOOD);
        this.setUnlocalizedName("bwm:woodTable");
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if(state.getBlock() instanceof BlockWoodTable) {
            state = state.getBlock().getActualState(state, source, pos);
            if (state.getValue(SUPPORTED))
                return TABLE_AABB;
        }
        return FULL_BLOCK_AABB;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, TABLE_AABB);
        if(state.getBlock() instanceof BlockWoodTable) {
            state = state.getBlock().getActualState(state, world, pos);
            if (!state.getValue(SUPPORTED))
                addCollisionBoxToList(pos, entityBox, collidingBoxes, TABLE_STAND_AABB);
        }
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(TYPE, meta);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, SUPPORTED, TYPE);
    }
}