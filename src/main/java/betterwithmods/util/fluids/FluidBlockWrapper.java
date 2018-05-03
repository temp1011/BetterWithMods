package betterwithmods.util.fluids;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class FluidBlockWrapper implements IFluidHandler {
    protected final IFluidBlock fluidBlock;
    protected final World world;
    protected final BlockPos blockPos;

    public FluidBlockWrapper(IFluidBlock fluidBlock, World world, BlockPos blockPos) {
        this.fluidBlock = fluidBlock;
        this.world = world;
        this.blockPos = blockPos;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        float percentFilled = fluidBlock.getFilledPercentage(world, blockPos);
        if (percentFilled < 0) {
            percentFilled *= -1;
        }
        int amountFilled = Math.round(Fluid.BUCKET_VOLUME * percentFilled);
        FluidStack fluid = amountFilled > 0 ? new FluidStack(fluidBlock.getFluid(), amountFilled) : null;
        return new FluidTankProperties[]{new FluidTankProperties(fluid, Fluid.BUCKET_VOLUME, false, true)};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        // NOTE: "Filling" means placement in this context!
        if (resource == null) {
            return 0;
        }
        return fluidBlock.place(world, blockPos, resource, doFill);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || !fluidBlock.canDrain(world, blockPos)) {
            return null;
        }

        FluidStack simulatedDrain = fluidBlock.drain(world, blockPos, false);
        if (resource.containsFluid(simulatedDrain)) {
            if (doDrain) {
                return fluidBlock.drain(world, blockPos, true);
            } else {
                return simulatedDrain;
            }
        }

        return null;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (maxDrain <= 0 || !fluidBlock.canDrain(world, blockPos)) {
            return null;
        }

        FluidStack simulatedDrain = new FluidStack(fluidBlock.getFluid(), Fluid.BUCKET_VOLUME);
        if (simulatedDrain.amount <= maxDrain) {
            if (doDrain) {
                return fluidBlock.drain(world, blockPos, true);
            } else {
                return simulatedDrain;
            }
        }

        return null;
    }
}