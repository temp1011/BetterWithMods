package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.common.blocks.camo.BlockCamo;
import betterwithmods.module.gameplay.miniblocks.blocks.*;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public enum MiniType {
    SIDING(BlockSiding.class, "siding"),
    MOULDING(BlockMoulding.class, "moulding"),
    CORNER(BlockCorner.class, "corner"),
    COLUMN(BlockColumn.class, "column"),
    PEDESTAL(BlockPedestals.class, "pedestal"),
    TABLE(BlockTable.class, "table"),
    BENCH(BlockBench.class, "bench"),
    UNKNOWN(null, "");
    public static MiniType[] VALUES = values();

    private Class<? extends BlockCamo> block;
    private String name;

    MiniType(Class<? extends BlockCamo> block, String name) {
        this.block = block;
        this.name = name;
    }

    public static boolean matches(MiniType type, ItemStack stack) {
        return fromStack(stack).equals(type);
    }

    public static MiniType fromName(String name) {
        return Arrays.stream(VALUES).filter(t -> t.isName(name)).findFirst().orElse(null);
    }

    public static MiniType fromBlock(BlockCamo block) {
        return Arrays.stream(VALUES).filter(t -> t.isBlock(block)).findFirst().orElse(null);
    }

    public static MiniType fromStack(ItemStack stack) {
        if (stack.getItem() instanceof ItemCamo) {
            BlockCamo mini = (BlockCamo) ((ItemCamo) stack.getItem()).getBlock();
            return fromBlock(mini);
        }
        return UNKNOWN;
    }

    private boolean isName(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    private boolean isBlock(BlockCamo mini) {
        return this.block.isAssignableFrom(mini.getClass());
    }
}
