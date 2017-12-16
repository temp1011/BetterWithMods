package betterwithmods.module.hardcore.world.villagers;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class VillagerLevel implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(VillagerLevel.class)
    public static Capability<VillagerLevel> CAPABILITY_LEVEL;

    private int level;


    public VillagerLevel() {
        this(0);
    }

    public VillagerLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CAPABILITY_LEVEL;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(hasCapability(capability,facing))
            return CAPABILITY_LEVEL.cast(this);
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("level", level);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        level = nbt.getInteger("level");
    }

    public static class Storage implements Capability.IStorage<VillagerLevel> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<VillagerLevel> capability, VillagerLevel instance, EnumFacing side) {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<VillagerLevel> capability, VillagerLevel instance, EnumFacing side, NBTBase nbt) {
            instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }
}
