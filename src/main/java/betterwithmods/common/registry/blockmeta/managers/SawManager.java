package betterwithmods.common.registry.blockmeta.managers;

import betterwithmods.common.registry.blockmeta.recipe.BlockIngredient;
import betterwithmods.common.registry.blockmeta.recipe.SawRecipe;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SawManager extends BlockMetaManager<SawRecipe> {

    public SawRecipe addRecipe(ItemStack input, List<ItemStack> outputs) {
        return addRecipe(new SawRecipe(new BlockIngredient(input), outputs));
    }

    public SawRecipe addRecipe(BlockIngredient input, List<ItemStack> outputs) {
        return addRecipe(new SawRecipe(input, outputs));
    }
}
