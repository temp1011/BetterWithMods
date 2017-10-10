package betterwithmods.module.compat;

import betterwithmods.module.CompatFeature;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ThermalExpansion extends CompatFeature {
    public ThermalExpansion() {
        super("thermalexpansion");
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        removeSmelterRecipe(new ItemStack(Items.BUCKET), ItemStack.EMPTY);
        addSmelterRecipe(6000, new ItemStack(Items.BUCKET), ItemStack.EMPTY, new ItemStack(Items.IRON_NUGGET, 7));
    }

    public void addSmelterRecipe(int energy, ItemStack primary, ItemStack secondary, ItemStack output) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("energy", energy);
        tag.setTag("primaryInput", primary.serializeNBT());
        tag.setTag("secondaryInput", secondary.serializeNBT());
        tag.setTag("primaryOutput", output.serializeNBT());
        FMLInterModComms.sendMessage(modid, ADD_SMELTER_RECIPE, tag);
    }

    public void removeSmelterRecipe(ItemStack primary, ItemStack secondary) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("primaryInput", primary.serializeNBT());
        tag.setTag("secondaryInput", secondary.serializeNBT());

        FMLInterModComms.sendMessage(modid, REMOVE_SMELTER_RECIPE, tag);
    }

    public static final String ADD_SMELTER_RECIPE = "addsmelterrecipe";
    public static final String REMOVE_SMELTER_RECIPE = "removesmelterrecipe";
}
