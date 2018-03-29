package betterwithmods.module.gameplay.miniblocks.orientations;

import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.model.TRSRTransformation;

public enum MouldingOrientation implements BaseOrientation {

        /*
    0 west down
    1 north down
    2 east down
    3 south down
    4 west up
    5 north up
    6 east up
    7 south up
    8 north west
    9 north east
    10 south west
    11 south east
     */

    NORTH_UP("north_up", new TRSRTransformation(ModelRotation.X180_Y270.getMatrix()), new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D)),
    SOUTH_UP("south_up", new TRSRTransformation(ModelRotation.X180_Y90.getMatrix()), new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D)),
    WEST_UP("west_up", new TRSRTransformation(ModelRotation.X180_Y180.getMatrix()), new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D)),
    EAST_UP("east_up", new TRSRTransformation(ModelRotation.X180_Y0.getMatrix()), new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D)),
    NORTH_DOWN("north_down", new TRSRTransformation(ModelRotation.X0_Y270.getMatrix()), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D)),
    SOUTH_DOWN("south_down", new TRSRTransformation(ModelRotation.X0_Y0.getMatrix()), new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D)),
    WEST_DOWN("west_down", new TRSRTransformation(ModelRotation.X0_Y180.getMatrix()), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D)),
    EAST_DOWN("east_down", new TRSRTransformation(ModelRotation.X0_Y90.getMatrix()), new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D)),
    SOUTH_WEST("south_west", new TRSRTransformation(ModelRotation.X90_Y90.getMatrix()), new AxisAlignedBB(0.0D, 0.0D, 0.5D, 0.5D, 1.0D, 1.0D)),
    NORTH_WEST("north_west", new TRSRTransformation(ModelRotation.X90_Y180.getMatrix()), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D)),
    NORTH_EAST("north_east", new TRSRTransformation(ModelRotation.X90_Y270.getMatrix()), new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D)),
    SOUTH_EAST("south_east", new TRSRTransformation(ModelRotation.X90_Y0.getMatrix()), new AxisAlignedBB(0.5D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D));

    public static final MouldingOrientation[] VALUES = values();

    private String name;
    private AxisAlignedBB bounds;
    private TRSRTransformation transformation;

    MouldingOrientation(String name, TRSRTransformation transformation, AxisAlignedBB bounds) {
        this.name = name;
        this.transformation = transformation;
        this.bounds = bounds;
    }

    @Override
    public String getName() {
        return name;
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

    private static int getCorner(double hitXFromCenter, double hitZFromCenter) {
        boolean positiveX = hitXFromCenter > 0, positiveZ = hitZFromCenter > 0;
        double x = Math.abs(hitXFromCenter), z = Math.abs(hitZFromCenter);
//        if (x > 0.25 && z > 0.25) {
//            if (positiveX && positiveZ)
//                return 0;
//            if (positiveX && !positiveZ)
//                return 1;
//            if (!positiveX && !positiveZ)
//                return 2;
//            if (!positiveX && positiveZ)
//                return 3;
//        }
        return -1;
    }


    public static MouldingOrientation getFromVec(Vec3d hit, EnumFacing facing) {

//        System.out.println(hit);
//        double hitXFromCenter = hit.x - 0.5F;
//        double hitYFromCenter = hit.y - 0.5F;
//        double hitZFromCenter = hit.z - 0.5F;
//        switch (facing.getAxis()) {
//            case Y:
//                int corner = getCorner(hitXFromCenter, hitZFromCenter);
//                if (corner != -1) {
//////                    int[] corners = new int[]{11, 9, 8, 10};
////                    state = state.withProperty(facingProperty, corners[corner]);
//                } else if (hitYFromCenter > 0) {
//                    if (isMax(hitXFromCenter, hitZFromCenter)) {
//                        return hitXFromCenter > 0 ? SOUTH_DOWN : WEST_DOWN;
//                    } else {
//                        return hitZFromCenter > 0 ? EAST_DOWN : NORTH_DOWN;
//                    }
//                } else {
//                    if (isMax(hitXFromCenter, hitZFromCenter)) {
//                        return hitX
//                        state = state.withProperty(facingProperty, ((hitXFromCenter > 0) ? 7 : 4));
//                    } else {
//                        state = state.withProperty(facingProperty, ((hitZFromCenter > 0) ? 6 : 5));
//                    }
//                }
//                break;
//            case X:
//                corner = getCorner(hitYFromCenter, hitZFromCenter);
//                if (corner != -1) {
//                    int[] corners = new int[]{6, 5, 1, 2};
//                    state = state.withProperty(facingProperty, corners[corner]);
//                } else if (hitXFromCenter > 0) {
//                    if (isMax(hitYFromCenter, hitZFromCenter)) {
//                        state = state.withProperty(facingProperty, ((hitYFromCenter > 0) ? 4 : 0));
//                    } else {
//                        state = state.withProperty(facingProperty, ((hitZFromCenter > 0) ? 10 : 8));
//                    }
//                } else {
//                    if (isMax(hitYFromCenter, hitZFromCenter)) {
//                        state = state.withProperty(facingProperty, ((hitYFromCenter > 0) ? 7 : 3));
//                    } else {
//                        state = state.withProperty(facingProperty, ((hitZFromCenter > 0) ? 11 : 9));
//                    }
//                }
//                break;
//            case Z:
//                corner = getCorner(hitYFromCenter, hitXFromCenter);
//                if (corner != -1) {
//                    int[] corners = new int[]{7, 4, 0, 3};
//                    state = state.withProperty(facingProperty, corners[corner]);
//                } else if (hitZFromCenter > 0) {
//                    if (isMax(hitXFromCenter, hitYFromCenter)) {
//                        state = state.withProperty(facingProperty, ((hitXFromCenter > 0) ? 9 : 8));
//                    } else {
//                        state = state.withProperty(facingProperty, ((hitYFromCenter > 0) ? 5 : 1));
//                    }
//                } else {
//                    if (isMax(hitXFromCenter, hitYFromCenter)) {
//                        state = state.withProperty(facingProperty, ((hitXFromCenter > 0) ? 11 : 10));
//                    } else {
//                        state = state.withProperty(facingProperty, ((hitYFromCenter > 0) ? 6 : 2));
//                    }
//                }
//                break;
//            default:
//                state = state.withProperty(facingProperty, facing.getOpposite().getIndex());
//                break;
//        }

        return MouldingOrientation.NORTH_DOWN;
    }


    public static boolean isMax(double hit1, double hit2) {
        return Math.max(Math.abs(hit1), Math.abs(hit2)) == Math.abs(hit1);
    }

    public static boolean inCenter(float hit1, float hit2, float max) {
        return Math.abs(hit1) <= max && Math.abs(hit2) <= max;
    }
}

