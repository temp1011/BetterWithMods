package betterwithmods.common.items;

import betterwithmods.client.BWCreativeTabs;
import betterwithmods.common.BWMItems;
import com.google.common.collect.Lists;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import java.util.List;

public class ItemBark extends Item {

    public static List<String> barks = Lists.newArrayList("oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "bloody");

    public ItemBark() {
        super();
        this.setCreativeTab(BWCreativeTabs.BWTAB);
        this.setHasSubtypes(true);
    }

    public static ItemStack getStack(String wood, int amount) {
        ItemStack stack = new ItemStack(BWMItems.BARK, amount);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", wood);
        stack.setTagCompound(tag);
        return stack;
    }


    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return 25;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (String bark : barks) {
                items.add(getStack(bark, 1));
            }
        }
    }


    @Override
    public String getUnlocalizedName(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null) {
            String type = tag.getString("type");
            return super.getUnlocalizedName() + "." + type;
        }
        return "Unknown Bark";
    }
}
