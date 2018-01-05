package betterwithmods.common.blocks.mechanical.tile;

import betterwithmods.api.capabilities.CapabilityMechanicalPower;
import betterwithmods.api.tile.IMechanicalPower;
import betterwithmods.api.util.MechanicalUtil;
import betterwithmods.common.blocks.mechanical.BlockGearbox;
import betterwithmods.common.blocks.tile.TileBasic;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.List;

public class TileGearbox extends TileBasic implements IMechanicalPower {
    protected int power;
    protected int maxPower;

    private List<Integer> history = Lists.newArrayList(0, 0, 0, 0, 0);

    public TileGearbox() {
    }

    public TileGearbox(int maxPower) {
        this.maxPower = maxPower;
    }

    public void onChanged() {
        if (this.getBlockWorld().getTotalWorldTime() % 20L != 0L)
            return;

        if (MechanicalUtil.isRedstonePowered(world, pos)) {
            setPower(0);
            markDirty();
            return;
        }
        int power = this.getMechanicalInput(getFacing());

        if (history.size() >= 5)
            history.remove(0);
        history.add(power);
        int average = (int) Math.floor(history.stream().mapToDouble(i -> i).average().orElse(0));
        if (average > getMaximumInput(getFacing())) {
            getBlock().overpower(world, pos);
        }
        if (power != this.power) {
            setPower(power);
        }
        markDirty();
    }

    @Override
    public int getMinimumInput(EnumFacing facing) {
        return 1;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing facing) {
        return capability == CapabilityMechanicalPower.MECHANICAL_POWER
                || super.hasCapability(capability, facing);
    }

    @Nonnull
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing facing) {
        if (capability == CapabilityMechanicalPower.MECHANICAL_POWER)
            return CapabilityMechanicalPower.MECHANICAL_POWER.cast(this);
        return super.getCapability(capability, facing);
    }

    @Override
    public int getMechanicalOutput(EnumFacing facing) {
        if (facing != getFacing() && MechanicalUtil.isAxle(world, pos.offset(facing), facing.getOpposite()))
            return Math.min(power, maxPower);
        return -1;
    }

    @Override
    public int getMechanicalInput(EnumFacing facing) {
        BlockPos pos = getBlockPos().offset(facing);
        if (MechanicalUtil.getMechanicalPower(world, pos, facing.getOpposite()) != null && !(MechanicalUtil.getMechanicalPower(world, pos, facing.getOpposite()) instanceof TileGearbox))
            return MechanicalUtil.getPowerOutput(world, pos, facing.getOpposite());
        return 0;
    }

    @Override
    public int getMaximumInput(EnumFacing facing) {
        return this.maxPower;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        power = tag.getInteger("power");
        maxPower = tag.getInteger("maxPower");
        history.clear();
        for (int i : tag.getIntArray("history"))
            history.add(i);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        NBTTagCompound t = super.writeToNBT(tag);
        tag.setInteger("power", power);
        tag.setInteger("maxPower", maxPower);
        tag.setIntArray("history", history.stream().mapToInt(i -> i).toArray());
        return t;
    }


    public BlockGearbox getBlock() {
        return (BlockGearbox) getBlockType();
    }

    public EnumFacing getFacing() {
        return getBlock().getFacing(world, pos);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        getBlock().setActive(world, pos, power > 0);
    }

    @Override
    public String toString() {
        return String.format("%s", power);
    }

    @Override
    public World getBlockWorld() {
        return super.getWorld();
    }

    @Override
    public BlockPos getBlockPos() {
        return super.getPos();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
