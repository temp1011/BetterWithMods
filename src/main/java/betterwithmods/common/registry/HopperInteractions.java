package betterwithmods.common.registry;

import betterwithmods.common.blocks.BlockUrn;
import betterwithmods.common.blocks.mechanical.tile.TileEntityFilteredHopper;
import betterwithmods.common.blocks.tile.SimpleStackHandler;
import betterwithmods.util.InvUtils;
import com.google.common.collect.Lists;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author primetoxinz
 * @version 11/13/16
 */
public class HopperInteractions {
    public static final ArrayList<HopperRecipe> RECIPES = new ArrayList<>();

    public static void addHopperRecipe(HopperRecipe recipe) {
        RECIPES.add(recipe);
    }

    public static boolean attemptToCraft(String filterName, World world, BlockPos pos, EntityItem input) {
        for (HopperRecipe recipe : RECIPES) {
            if (recipe.isRecipe(filterName, input)) {
                if (recipe.canCraft(world, pos)) {
                    recipe.craft(input, world, pos);
                    return true;
                }
            }
        }
        return false;
    }

    public static class SoulUrnRecipe extends HopperRecipe {
        private boolean hasUrn = true;

        public SoulUrnRecipe(Ingredient input, ItemStack output, ItemStack... secondaryOutput) {
            super("betterwithmods:soul_sand", input, output, secondaryOutput);
        }

        public SoulUrnRecipe(Ingredient input, List<ItemStack> output, List<ItemStack> secondaryOutput) {
            super("betterwithmods:soul_sand", input, output, secondaryOutput);
        }

        @Override
        public void onCraft(World world, BlockPos pos, EntityItem item) {
            ((TileEntityFilteredHopper) world.getTileEntity(pos)).increaseSoulCount(1);
            if (!world.isRemote) {
                world.playSound(null, pos, SoundEvents.ENTITY_GHAST_AMBIENT, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.45F);
            }
            super.onCraft(world, pos, item);
        }

        @Override
        public List<ItemStack> getContainers() {
            return Lists.newArrayList(BlockUrn.getStack(BlockUrn.EnumType.EMPTY,1));
        }

        private int getCraftsPerUrn() { //Futureproofing if soul count per craft is ever >1
            return hasUrn ? 8 : 1;
        }

        @Override
        public List<ItemStack> getInputs() {
            return super.getInputs().stream().map(stack -> {
                ItemStack newStack = stack.copy();
                newStack.setCount(getCraftsPerUrn());
                return newStack;
            }).collect(Collectors.toList());
        }

        @Override
        public List<ItemStack> getOutputs() {
            return super.getOutputs().stream().map(stack -> {
                ItemStack newStack = stack.copy();
                newStack.setCount(getCraftsPerUrn());
                return newStack;
            }).collect(Collectors.toList());
        }

        @Override
        public List<ItemStack> getSecondaryOutputs() {
            return super.getSecondaryOutputs().stream().map(stack -> {
                ItemStack newStack = stack.copy();
                newStack.setCount(getCraftsPerUrn());
                return newStack;
            }).collect(Collectors.toList());
        }

        public SoulUrnRecipe withoutUrn() {
            SoulUrnRecipe recipe = new SoulUrnRecipe(input,outputs,secondaryOutputs);
            recipe.hasUrn = false;
            return recipe;
        }

        public boolean hasUrn() {
            return hasUrn;
        }
    }

    public static abstract class HopperRecipe {
        protected final String filterName;
        protected final Ingredient input;
        protected List<ItemStack> outputs = new ArrayList<>(); //This goes in
        protected List<ItemStack> secondaryOutputs = new ArrayList<>(); //This stays on top

        public HopperRecipe(String filterName, Ingredient input, ItemStack output, ItemStack... secondaryOutput) {
            this(filterName,input,Lists.newArrayList(output),Lists.newArrayList(secondaryOutput));
        }

        public HopperRecipe(String filterName, Ingredient input, List<ItemStack> output, List<ItemStack> secondaryOutput) {
            this.filterName = filterName;
            this.input = input;
            this.outputs = output;
            this.secondaryOutputs = secondaryOutput;
        }

        public boolean isRecipe(String filterName, EntityItem entity) {
            if (filterName.equals(this.filterName)) {
                if (entity != null) {
                    ItemStack stack = entity.getItem();
                    return input.apply(stack);
                }
                return false;
            }
            return false;
        }

        public void craft(EntityItem inputStack, World world, BlockPos pos) {
            TileEntityFilteredHopper hopper = (TileEntityFilteredHopper) world.getTileEntity(pos);
            SimpleStackHandler inventory = hopper.inventory;
            for (ItemStack output : outputs) {
                ItemStack remainder = InvUtils.insert(inventory, output, false);
                if (!remainder.isEmpty())
                    InvUtils.ejectStackWithOffset(world, inputStack.getPosition(), remainder);
            }
            InvUtils.ejectStackWithOffset(world, inputStack.getPosition(), secondaryOutputs);
            onCraft(world, pos, inputStack);
        }

        public void onCraft(World world, BlockPos pos, EntityItem item) {
            item.getItem().shrink(1);
            if (item.getItem().getCount() <= 0)
                item.setDead();
        }

        public String getFilterType() {
            return filterName;
        }

        public List<ItemStack> getContainers() { return Lists.newArrayList(); } //For showing that it needs urns

        public List<ItemStack> getInputs() {
            return Lists.newArrayList(input.getMatchingStacks());
        }

        public List<ItemStack> getOutputs() {
            return outputs;
        }

        public List<ItemStack> getSecondaryOutputs() {
            return secondaryOutputs;
        }

        public boolean canCraft(World world, BlockPos pos) {
            TileEntityFilteredHopper tile = (TileEntityFilteredHopper) world.getTileEntity(pos);
            if (tile != null) {
                ItemStackHandler inventory = tile.inventory;
                return !outputs.stream().anyMatch(stack -> !InvUtils.insert(inventory, stack, true).isEmpty());
            }
            return true;
        }
    }
}
