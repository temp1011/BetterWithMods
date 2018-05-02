package betterwithmods.common.registry.bulk.manager;

import betterwithmods.common.BWRegistry;
import betterwithmods.common.BWSounds;
import betterwithmods.common.blocks.mechanical.tile.TileEntityMill;
import betterwithmods.common.registry.bulk.recipes.MillRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
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

    @Override
    public boolean canCraft(TileEntity tile, ItemStackHandler inv) {
        if (tile instanceof TileEntityMill) {
            TileEntityMill mill = (TileEntityMill) tile;
            MillRecipe recipe = findRecipe(recipes, tile, inv).orElse(null);
            if (recipe != null) {
                return mill.grindCounter >= recipe.getTicks();
            }

        }
        return false;
    }

    @Override
    public boolean craftRecipe(World world, TileEntity tile, ItemStackHandler inv) {
        if (tile instanceof TileEntityMill) {
            TileEntityMill mill = (TileEntityMill) tile;
            MillRecipe recipe = findRecipe(recipes, tile, inv).orElse(null);

            if (mill.getBlockWorld().rand.nextInt(20) == 0)
                mill.getBlockWorld().playSound(null, mill.getBlockPos(), BWSounds.STONEGRIND, SoundCategory.BLOCKS, 0.5F + mill.getBlockWorld().rand.nextFloat() * 0.1F, 0.5F + mill.getBlockWorld().rand.nextFloat() * 0.1F);

            if (recipe != null) {
                //Play sounds
                if (mill.getBlockWorld().rand.nextInt(25) < 2)
                    mill.getBlockWorld().playSound(null, mill.getBlockPos(), recipe.getSound(), SoundCategory.BLOCKS, 1F, mill.getBlockWorld().rand.nextFloat() * 0.4F + 0.8F);

                if (canCraft(tile, inv)) {
                    mill.ejectRecipe(BWRegistry.MILLSTONE.craftItem(world, tile, inv));

                    mill.grindCounter = 0;
                    mill.progress = 0;

                    return true;
                } else {
                    mill.grindCounter+=mill.getIncrement();
                    mill.progress = ((double) mill.grindCounter) / ((double) recipe.getTicks());
                }
            } else {
                mill.grindCounter = 0;
                mill.progress = 0;
            }
        }

        return false;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> craftItem(World world, TileEntity tile, ItemStackHandler inv) {
        return super.craftItem(world, tile, inv);
    }
}
