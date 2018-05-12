package betterwithmods.common.penalties.attribute;

import net.minecraft.util.ResourceLocation;

public class Attribute<V> implements IAttribute<V> {
    private ResourceLocation registryName;
    private V value;

    public Attribute(ResourceLocation registryName, V value) {
        this.registryName = registryName;
        this.value = value;
    }

    @Override
    public V getDefaultValue() {
        return value;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }
}
