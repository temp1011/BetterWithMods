package betterwithmods.world;

import betterwithmods.event.BWMWorldGenEvent;
import net.minecraft.block.BlockLadder;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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

            if(BWMWorldGenEvent.isInRadius(worldIn, new BlockPos(this.getXWithOffset(10, 10), this.getYWithOffset(1), this.getZWithOffset(10, 10)))) {
                //Dig hole
                this.setAir(worldIn, 10, 0, 10, structureBoundingBoxIn);
                this.setAir(worldIn, 11, 0, 10, structureBoundingBoxIn);
                this.setAir(worldIn, 10, 0, 9, structureBoundingBoxIn);
                this.setAir(worldIn, 10, -11, 10, structureBoundingBoxIn);
                for(int x = 0; x < 3; x++) {
                    for(int z = 0; z < 3; z++) {
                        this.setAir(worldIn, 9 + x, -12, 9 + z, structureBoundingBoxIn);
                        this.setAir(worldIn, 9 + x, -13, 9 + z, structureBoundingBoxIn);
                    }
                }

                //Create ladder
                for(int i = 0; i >= -13; i--)
                    this.setBlockState(worldIn, Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.WEST), 11, i, 9, structureBoundingBoxIn);

                //Remove chest loot
                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                {
                    int k1 = enumfacing.getFrontOffsetX() * 2;
                    int l1 = enumfacing.getFrontOffsetZ() * 2;
                    TileEntity tileentity = worldIn.getTileEntity(new BlockPos(this.getXWithOffset(10 + k1, 10 + l1), this.getYWithOffset(-11), this.getZWithOffset(10 + k1, 10 + l1)));
                    if (tileentity instanceof TileEntityChest)
                        //TODO create desert temple loot table that only has rotten flesh and bones?
                        ((TileEntityChest)tileentity).setLootTable(null, randomIn.nextLong());
                }
            }
            else
                this.setBlockState(worldIn, Blocks.ENCHANTING_TABLE.getDefaultState(), 10, 1, 10, structureBoundingBoxIn);

            return result;
        }

        private void setAir(World world, int x, int y, int z, StructureBoundingBox structureBoundingBox) {
            this.setBlockState(world, Blocks.AIR.getDefaultState(), x, y, z, structureBoundingBox);
        }
    }
}
