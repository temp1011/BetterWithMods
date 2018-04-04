package betterwithmods.common.registry.bulk.recipes;

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

    public MillRecipe(@Nonnull List<Ingredient> inputs, @Nonnull List<ItemStack> outputs) {
        super(inputs, outputs);
    }

    public SoundEvent getSound() {
        return sound;
    }

    public MillRecipe setSound(String sound) {
        SoundEvent s = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(sound));
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
}
