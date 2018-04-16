package betterwithmods.common.blocks.mechanical.tile;

import betterwithmods.api.BWMAPI;
import betterwithmods.api.capabilities.CapabilityMechanicalPower;
import betterwithmods.api.tile.ICrankable;
import betterwithmods.api.tile.IMechanicalPower;
import betterwithmods.common.BWRegistry;
import betterwithmods.common.BWSounds;
import betterwithmods.common.blocks.mechanical.mech_machine.BlockMechMachine;
import betterwithmods.common.blocks.tile.TileBasicInventory;
import betterwithmods.common.registry.bulk.recipes.MillRecipe;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class TileMill extends TileBasicInventory implements ITickable, IMechanicalPower, ICrankable {

    public static final int GRIND_TIME = 200;

    public int power;
    public int grindCounter;
    public boolean blocked;
    private SoundEvent grindType;
    private boolean validateContents;
    private boolean containsIngredientsToGrind;

    public TileMill() {
        this.grindCounter = 0;
        this.validateContents = true;
    }

    public boolean isActive() {
        return power > 0;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    public BlockMechMachine getBlock() {
        return (BlockMechMachine) this.getBlockType();
    }

    private boolean findIfBlocked() {
        int count = 0;
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            if (world.isSideSolid(pos.offset(facing), facing.getOpposite())) {
                count++;
            }
        }
        return count > 1;
    }

    public boolean isBlocked() {
        return blocked;
    }

    @Override
    public void update() {
        if (this.getBlockWorld().isRemote)
            return;

        this.power = calculateInput();
        this.blocked = findIfBlocked();
        getBlock().setActive(world, pos, isActive());

        validateContents();

        if (isBlocked()) {
            return;
        }

        if (isActive()) {
            if (getBlockWorld().rand.nextInt(20) == 0)
                getBlockWorld().playSound(null, pos, BWSounds.STONEGRIND, SoundCategory.BLOCKS, 0.5F + getBlockWorld().rand.nextFloat() * 0.1F, 0.5F + getBlockWorld().rand.nextFloat() * 0.1F);
        }

        if (this.containsIngredientsToGrind && isActive()) {

            if (!this.getBlockWorld().isRemote) {
                if (grindType != null) {
                    if (this.getBlockWorld().rand.nextInt(25) < 2) {
                        getBlockWorld().playSound(null, pos, grindType, SoundCategory.BLOCKS, 1F, getBlockWorld().rand.nextFloat() * 0.4F + 0.8F);
                    }
                }
            }
            this.grindCounter++;
            if (this.grindCounter > GRIND_TIME - 1) {
                grindContents();
                this.grindCounter = 0;

            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("blocked"))
            this.blocked = tag.getBoolean("blocked");
        if (tag.hasKey("GrindCounter"))
            this.grindCounter = tag.getInteger("GrindCounter");
        this.power = tag.getInteger("power");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("GrindCounter", this.grindCounter);
        tag.setInteger("power", power);
        tag.setBoolean("blocked", blocked);
        return tag;
    }

    @Override
    public int getInventorySize() {
        return 3;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        validateContents();
        if (this.getBlockWorld() != null && !this.getBlockWorld().isRemote) {
            this.validateContents = true;
        }
    }

    private boolean canEject(EnumFacing facing) {
        if (world.isAirBlock(pos.offset(facing)))
            return true;
        return !world.isBlockFullCube(pos.offset(facing)) && !world.isSideSolid(pos.offset(facing), facing.getOpposite());
    }

    private void ejectStack(ItemStack stack) {
        List<EnumFacing> validDirections = Lists.newArrayList(EnumFacing.HORIZONTALS).stream().filter(this::canEject).collect(Collectors.toList());
        if (validDirections.isEmpty()) {
            blocked = true;
            return;
        }

        InvUtils.ejectStackWithOffset(getBlockWorld(), pos.offset(validDirections.get(getBlockWorld().rand.nextInt(validDirections.size()))), stack);
    }

    public double getGrindProgress() {
        return this.grindCounter / (double) GRIND_TIME;
    }

    public boolean isGrinding() {
        return this.grindCounter > 0;
    }

    private boolean grindContents() {
        if (BWRegistry.MILLSTONE.canCraft(this, inventory)) {
            NonNullList<ItemStack> output = BWRegistry.MILLSTONE.craftItem(world, this, inventory);
            if (!output.isEmpty()) {
                for (ItemStack anOutput : output) {
                    ItemStack stack = anOutput.copy();
                    if (!stack.isEmpty())
                        ejectStack(stack);
                }
            }
            return true;
        }
        return false;
    }

    private void validateContents() {
        SoundEvent oldGrindType = grindType;
        SoundEvent newGrindType = null;
        MillRecipe recipe = BWRegistry.MILLSTONE.getRecipe(this, inventory);
        if (recipe != null) {
            this.containsIngredientsToGrind = true;
            newGrindType = recipe.getSound();
        } else {
            this.grindCounter = 0;
            this.containsIngredientsToGrind = false;
        }
        this.validateContents = false;
        if (oldGrindType != newGrindType) {
            this.grindType = newGrindType;
        }
    }

    @Override
    public int getMechanicalOutput(EnumFacing facing) {
        return -1;
    }

    @Override
    public int getMechanicalInput(EnumFacing facing) {
        if (facing.getAxis().isVertical())
            return BWMAPI.IMPLEMENTATION.getPowerOutput(world, pos.offset(facing), facing.getOpposite());
        if (world.getTileEntity(pos.offset(facing)) instanceof TileCrank) {
            return BWMAPI.IMPLEMENTATION.getPowerOutput(world, pos.offset(facing), facing.getOpposite());
        }
        return 0;
    }

    @Override
    public int getMaximumInput(EnumFacing facing) {
        return 1;
    }

    @Override
    public int getMinimumInput(EnumFacing facing) {
        return 0;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing facing) {
        if (capability == CapabilityMechanicalPower.MECHANICAL_POWER)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nonnull
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing facing) {
        if (capability == CapabilityMechanicalPower.MECHANICAL_POWER)
            return CapabilityMechanicalPower.MECHANICAL_POWER.cast(this);
        return super.getCapability(capability, facing);
    }

    @Override
    public World getBlockWorld() {
        return super.getWorld();
    }

    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.getBlockWorld().getTileEntity(this.pos) == this && player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
    }
}
