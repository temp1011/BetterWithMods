package betterwithmods.common.registry.hopper.filters;

import betterwithmods.BWMod;
import betterwithmods.api.tile.IHopperFilter;
import betterwithmods.client.model.filters.ModelTransparent;
import betterwithmods.client.model.filters.ModelWithResource;
import betterwithmods.client.model.render.RenderUtils;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class HopperFilter implements IHopperFilter {

    public static IHopperFilter NONE = new HopperFilter(new ResourceLocation(BWMod.MODID,"none"), Ingredient.EMPTY, Lists.newArrayList());

    private ModelWithResource modelOverride;

    public ResourceLocation name;
    public Ingredient filter;
    public List<Ingredient> filtered;


    public HopperFilter(ResourceLocation name, Ingredient filter, List<Ingredient> filtered) {
        this.name = name;
        this.filter = filter;
        this.filtered = filtered;
    }

    public ResourceLocation getName() {
        return name;
    }

    public Ingredient getFilter() {
        return filter;
    }

    public List<Ingredient> getFiltered() {
        return filtered;
    }

    public boolean allow(ItemStack stack) {
        return filtered.isEmpty() || filtered.stream().anyMatch(i -> i.apply(stack));
    }

    @Override
    public ModelWithResource getModelOverride(ItemStack filter) {
        if(modelOverride == null)
            return new ModelTransparent(RenderUtils.getResourceLocation(filter));
        return modelOverride;
    }

    @Override
    public void setModelOverride(ModelWithResource model) {
        this.modelOverride = model;
    }

}
