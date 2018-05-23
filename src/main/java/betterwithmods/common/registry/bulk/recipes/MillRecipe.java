package betterwithmods.common.registry.bulk.recipes;

import betterwithmods.api.recipe.input.IRecipeInputs;
import betterwithmods.api.recipe.matching.BulkMatchInfo;
import betterwithmods.api.recipe.output.IRecipeOutputs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by primetoxinz on 5/16/17.
 */
public class MillRecipe extends BulkRecipe {
    private SoundEvent sound;

    private int ticks;

    public MillRecipe(IRecipeInputs<Integer, BulkMatchInfo> inputs, IRecipeOutputs outputs, SoundEvent sound) {
        super(inputs, outputs, 0);
        this.sound = sound;
    }

    public MillRecipe(@Nonnull List<Ingredient> inputs, @Nonnull List<ItemStack> outputs, int ticks) {
        super(inputs, outputs);
        this.ticks = ticks;
    }

    public MillRecipe(@Nonnull List<Ingredient> inputs, @Nonnull List<ItemStack> outputs) {
        this(inputs, outputs,200);

    }

    public SoundEvent getSound() {
        return sound;
    }

    public MillRecipe setSound(String sound) {
        SoundEvent s = null;
        if(sound != null && !sound.isEmpty()) {
            try {
                s = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(sound));
            } catch(Throwable ignore) {}
        }
        return setSound(s);
    }

    public MillRecipe setSound(SoundEvent sound) {
        this.sound = sound;
        return this;
    }

    @Override
    public MillRecipe setPriority(int priority) {
        return (MillRecipe) super.setPriority(priority);
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

}
