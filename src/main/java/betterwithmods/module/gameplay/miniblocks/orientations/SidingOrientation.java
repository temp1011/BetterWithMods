package betterwithmods.module.gameplay.miniblocks.orientations;

import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.model.TRSRTransformation;

public enum SidingOrientation implements BaseOrientation {
    UP("up", EnumFacing.UP, new TRSRTransformation(ModelRotation.X180_Y0.getMatrix()), new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D)),
    DOWN("down", EnumFacing.DOWN, new TRSRTransformation(ModelRotation.X0_Y0.getMatrix()), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D)),
    NORTH("north", EnumFacing.NORTH, new TRSRTransformation(ModelRotation.X90_Y0.getMatrix()), new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D)),
    SOUTH("south", EnumFacing.SOUTH, new TRSRTransformation(ModelRotation.X270_Y0.getMatrix()), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D)),
    WEST("west", EnumFacing.WEST, new TRSRTransformation(ModelRotation.X270_Y90.getMatrix()), new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)),
    EAST("east", EnumFacing.EAST, new TRSRTransformation(ModelRotation.X270_Y270.getMatrix()), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D));

    public static final SidingOrientation[] VALUES = values();

    private String name;
    private EnumFacing facing;
    private AxisAlignedBB bounds;
    private TRSRTransformation transformation;

    SidingOrientation(String name, EnumFacing facing, TRSRTransformation transformation, AxisAlignedBB bounds) {
        this.name = name;
        this.facing = facing;
        this.transformation = transformation;
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
        return transformation;
    }

    @Override
    public BaseOrientation next() {
        return VALUES[(this.ordinal() + 1) % (VALUES.length)];
    }
}

