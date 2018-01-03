package betterwithmods.module.gameplay.breeding_harness;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityHarness extends ItemStackHandler implements ICapabilityProvider {

    @CapabilityInject(CapabilityHarness.class)
    public static Capability<CapabilityHarness> HARNESS_CAPABILITY = null;

    public CapabilityHarness() {
        super(1);
    }

    @Nonnull
    public ItemStack getHarness() {
        return getStackInSlot(0);
    }

    public void insertHarness(ItemStack harness) {
        this.insertItem(0,harness,false);
    }

    public ItemStack extractHarness() {
        return extractItem(0,1,false);
    }



    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == HARNESS_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return HARNESS_CAPABILITY.cast(this);
    }

}
