package betterwithmods.common.blocks;

import betterwithmods.client.BWCreativeTabs;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Set;

public class BlockCobble extends Block {
    public static final Set<Block> BLOCKS = Sets.newHashSet();
    private final BlockCobble.EnumType type;

    public BlockCobble(BlockCobble.EnumType type) {
        super(Material.ROCK);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setCreativeTab(BWCreativeTabs.BWTAB);
        this.setRegistryName("cobblestone_" + type.getName());
        this.type = type;
    }

    public static void init() {
        for (BlockCobble.EnumType type : EnumType.VALUES)
            BLOCKS.add(new BlockCobble(type));
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return type.getColor();
    }

    public enum EnumType implements IStringSerializable {
        GRANITE("granite", MapColor.DIRT),
        DIORITE("diorite", MapColor.QUARTZ),
        ANDESITE("andesite", MapColor.STONE);

        private static final EnumType[] VALUES = values();
        private String name;
        private MapColor color;

        EnumType(String name, MapColor color) {
            this.name = name;
            this.color = color;
        }

        @Override
        public String getName() {
            return name;
        }

        public MapColor getColor() {
            return color;
        }
    }
}
