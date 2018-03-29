package betterwithmods.module.gameplay.miniblocks.orientations;

import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.model.TRSRTransformation;

public enum SidingOrientation implements BaseOrientation {
    UP("up", EnumFacing.UP, new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D)),
    DOWN("down", EnumFacing.DOWN, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D)),
    NORTH("north", EnumFacing.NORTH, new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D)),
    SOUTH("south", EnumFacing.SOUTH, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D)),
    WEST("west", EnumFacing.WEST, new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)),
    EAST("east", EnumFacing.EAST, new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D));

    public static final SidingOrientation[] VALUES = values();

    private String name;
    private EnumFacing facing;
    private AxisAlignedBB bounds;

    SidingOrientation(String name, EnumFacing facing, AxisAlignedBB bounds) {
        this.name = name;
        this.facing = facing;
        this.bounds = bounds;
    }

    @Override
    public String getName() {
        return name;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    @Override
    public AxisAlignedBB getBounds() {
        return bounds;
    }

    @Override
    public TRSRTransformation toTransformation() {
        switch (this) {
            case DOWN:
                return new TRSRTransformation(ModelRotation.X0_Y0.getMatrix());
            case NORTH:
                return new TRSRTransformation(ModelRotation.X90_Y0.getMatrix());
            case SOUTH:
                return new TRSRTransformation(ModelRotation.X270_Y0.getMatrix());
            case WEST:
                return new TRSRTransformation(ModelRotation.X270_Y90.getMatrix());
            case EAST:
                return new TRSRTransformation(ModelRotation.X270_Y270.getMatrix());
            case UP:
            default:
                return new TRSRTransformation(ModelRotation.X180_Y0.getMatrix());
        }
    }
}

