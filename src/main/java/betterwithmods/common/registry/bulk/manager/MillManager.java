package betterwithmods.common.registry.bulk.manager;

import betterwithmods.common.registry.bulk.recipes.MillRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

import java.util.List;

public class MillManager extends CraftingManagerBulk<MillRecipe> {
    public MillRecipe addMillRecipe(List<Ingredient> inputs, List<ItemStack> outputs, SoundEvent type) {
        return addRecipe(new MillRecipe(inputs, outputs).setSound(type));
    }

    public MillRecipe addMillRecipe(List<Ingredient> inputs, List<ItemStack> outputs) {
        return addRecipe(new MillRecipe(inputs, outputs));
    }

    public MillRecipe addMillRecipe(Ingredient input, List<ItemStack> outputs, SoundEvent type) {
        return addMillRecipe(Lists.newArrayList(input), outputs, type);
    }

    public MillRecipe addMillRecipe(Ingredient input, ItemStack output, SoundEvent type) {
        return addMillRecipe(Lists.newArrayList(input), Lists.newArrayList(output), type);
    }

    public MillRecipe addMillRecipe(ItemStack input, List<ItemStack> outputs, SoundEvent type) {
        return addMillRecipe(Ingredient.fromStacks(input), outputs, type);
    }

    public MillRecipe addMillRecipe(ItemStack input, ItemStack output, SoundEvent type) {
        return addMillRecipe(Ingredient.fromStacks(input), output, type);
    }

    public MillRecipe addMillRecipe(Ingredient input, List<ItemStack> outputs) {
        return addMillRecipe(Lists.newArrayList(input), outputs);
    }

    public MillRecipe addMillRecipe(Ingredient input, ItemStack output) {
        return addMillRecipe(Lists.newArrayList(input), Lists.newArrayList(output));
    }

    public MillRecipe addMillRecipe(ItemStack input, List<ItemStack> outputs) {
        return addMillRecipe(Ingredient.fromStacks(input), outputs);
    }

    public MillRecipe addMillRecipe(ItemStack input, ItemStack output) {
        return addMillRecipe(Ingredient.fromStacks(input), output);
    }


}
