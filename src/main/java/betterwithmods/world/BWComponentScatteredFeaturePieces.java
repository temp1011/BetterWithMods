package betterwithmods.world;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.util.Random;

/**
 * Created by blueyu2 on 11/27/16.
 */
public class BWComponentScatteredFeaturePieces extends ComponentScatteredFeaturePieces {

    public static class DesertPyramid extends ComponentScatteredFeaturePieces.DesertPyramid
    {

        DesertPyramid(Random p_i2062_1_, int p_i2062_2_, int p_i2062_3_)
        {
            super(p_i2062_1_, p_i2062_2_, p_i2062_3_);
        }

        @Override
        public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
            boolean result = super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn);
            //TODO only when past spawn radius
            this.setBlockState(worldIn, Blocks.ENCHANTING_TABLE.getDefaultState(), 10, 1, 10, structureBoundingBoxIn);

            //Replace clay with obsidian
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 10, 0, 7, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 10, 0, 8, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 9, 0, 9, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 11, 0, 9, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 8, 0, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 12, 0, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 7, 0, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 13, 0, 10, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 9, 0, 11, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 11, 0, 11, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 10, 0, 12, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 10, 0, 13, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 10, 0, 10, structureBoundingBoxIn);
            for (int j2 = 0; j2 <= this.scatteredFeatureSizeX - 1; j2 += this.scatteredFeatureSizeX - 1)
            {
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), j2, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), j2, 3, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), j2, 4, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), j2, 4, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), j2, 5, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), j2, 6, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), j2, 6, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), j2, 7, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), j2, 7, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), j2, 7, 3, structureBoundingBoxIn);
            }
            for (int k2 = 2; k2 <= this.scatteredFeatureSizeX - 3; k2 += this.scatteredFeatureSizeX - 3 - 2)
            {
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), k2, 2, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), k2, 3, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), k2 - 1, 4, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), k2 + 1, 4, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), k2, 5, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), k2 - 1, 6, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), k2 + 1, 6, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), k2 - 1, 7, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), k2, 7, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), k2 + 1, 7, 0, structureBoundingBoxIn);
            }
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 9, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, Blocks.OBSIDIAN.getDefaultState(), 11, 5, 0, structureBoundingBoxIn);

            return result;
        }
    }
}
