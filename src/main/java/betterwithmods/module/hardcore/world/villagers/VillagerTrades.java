package betterwithmods.module.hardcore.world.villagers;

import betterwithmods.common.items.ItemMaterial;
import com.google.common.collect.Lists;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;

public class VillagerTrades {


    public static List<EntityVillager.ITradeList> CLERIC_1 = Lists.newArrayList(
            new EntityVillager.ListItemForEmeralds(ItemMaterial.getMaterial(ItemMaterial.EnumMaterial.HEMP), new EntityVillager.PriceInfo(-18, -22)),
            new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.RED_MUSHROOM), new EntityVillager.PriceInfo(-14,-16)),
            new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.CACTUS), new EntityVillager.PriceInfo(-35,-62)),
            new EntityVillager.ListItemForEmeralds(new ItemStack(Items.FLINT_AND_STEEL), new EntityVillager.PriceInfo(1,1)),
            new EntityVillager.ListItemForEmeralds(new ItemStack(Items.PAINTING), new EntityVillager.PriceInfo(2,3))
    );

//    public static List<EntityVillager.ITradeList> CLERIC_2 = Lists.newArrayList(
//            new EntityVillager.ListEnchantedItemForEmeralds()
//    );


}
