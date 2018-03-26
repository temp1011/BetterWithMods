package betterwithmods.common.blocks;

import betterwithmods.module.tweaks.MushroomFarming;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMushroom extends net.minecraft.block.BlockMushroom {
    int maxLightLevel;

    public BlockMushroom(int maxLightLevel) {
        super();
        this.maxLightLevel = maxLightLevel;
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setUnlocalizedName("mushroom");
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if(worldIn.getLight(pos) <= maxLightLevel || MushroomFarming.SPREAD_ON_MYCELLIUM && MushroomFarming.isMushroomSoil(worldIn.getBlockState(pos.down())))
            super.updateTick(worldIn, pos, state, rand);
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (pos.getY() >= 0 && pos.getY() < 256)
        {
            IBlockState soil = worldIn.getBlockState(pos.down());

            if(MushroomFarming.isMushroomSoil(soil))
                return true;
            else
                return worldIn.getLight(pos) <= maxLightLevel && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this);
        }
        else
        {
            return false;
        }
    }
}
