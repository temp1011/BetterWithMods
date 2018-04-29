package betterwithmods.testing.base.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import javax.annotation.Nullable;

public class FakeWorld extends World {

    private IBlockState state;

    public FakeWorld() {
        super(new FakeSaveHandler(), new FakeWorldInfo(), new FakeWorldProvider(), new Profiler(), false);
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        return null;
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState state) {
        this.setState(state);
        return true;
    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
        return getState();
    }

    @Override
    protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
        return false;
    }

    @Nullable
    @Override
    public Entity getEntityByID(int id) {
        return null;
    }

    public IBlockState getState() {
        if (state == null)
            return Blocks.AIR.getDefaultState();
        return state;
    }

    public void setState(IBlockState state) {
        this.state = state;
    }

    @Override
    public boolean setBlockToAir(BlockPos pos) {
        setState(Blocks.AIR.getDefaultState());
        return true;
    }

    @Override
    public boolean spawnEntity(Entity entityIn) {
        return true;
    }
}
