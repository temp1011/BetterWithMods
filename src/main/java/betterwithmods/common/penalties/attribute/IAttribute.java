package betterwithmods.common.penalties.attribute;

import net.minecraft.util.ResourceLocation;

public interface IAttribute<V> {
    V getDefaultValue();
    ResourceLocation getRegistryName();
}
