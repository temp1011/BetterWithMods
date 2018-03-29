package betterwithmods.module.gameplay.miniblocks.orientations;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.model.TRSRTransformation;

public enum MouldingOrientation implements BaseOrientation {
    DOWN("down", EnumFacing.DOWN),
    UP("up", EnumFacing.UP),
    NORTH("north", EnumFacing.NORTH),
    SOUTH("south", EnumFacing.SOUTH),
    WEST("west", EnumFacing.WEST),
    EAST("east", EnumFacing.EAST);

    public static final MouldingOrientation[] VALUES = values();
    private static final AxisAlignedBB[] boxes = new AxisAlignedBB[]{
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
            new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D),
            new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D),
            new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
    };

    private String name;
    private EnumFacing facing;
    private AxisAlignedBB box;

    MouldingOrientation(String name, EnumFacing facing) {
        this.name = name;
        this.facing = facing;
    }

    @Override
    public String getName() {
        return name;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    @Override
    public TRSRTransformation toTransformation() {
        return new TRSRTransformation(getFacing());
    }
}

