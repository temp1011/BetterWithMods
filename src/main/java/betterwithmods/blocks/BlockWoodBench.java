package betterwithmods.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockWoodBench extends BlockFurniture
{
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 6);

    public BlockWoodBench()
    {
        super(Material.WOOD);
        this.setUnlocalizedName("bwm:woodBench");
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if(state.getBlock() instanceof BlockWoodBench) {
            state = state.getBlock().getActualState(state, source, pos);
            if (state.getValue(SUPPORTED))
               return BENCH_AABB;
        }
        return HALF_BLOCK_AABB;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BENCH_AABB);
        if(state.getBlock() instanceof BlockWoodBench) {
            state = state.getBlock().getActualState(state, world, pos);
            if (!state.getValue(SUPPORTED))
                addCollisionBoxToList(pos, entityBox, collidingBoxes, BENCH_STAND_AABB);
        }
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