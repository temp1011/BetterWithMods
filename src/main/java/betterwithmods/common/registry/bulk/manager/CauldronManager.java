package betterwithmods.common.registry.bulk.manager;

import betterwithmods.common.BWMItems;
import betterwithmods.common.registry.bulk.recipes.CauldronFoodRecipe;
import betterwithmods.common.registry.bulk.recipes.CauldronRecipe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class CauldronManager extends CraftingManagerBulk<CauldronRecipe> {
    private static final CauldronManager instance = new CauldronManager();

    public static CauldronManager getInstance() {
        return instance;
    }

    public CauldronRecipe addRecipe(ItemStack output, Object[] inputs) {
        return addRecipe(output, ItemStack.EMPTY, inputs);
    }

    public CauldronRecipe addRecipe(ItemStack output, ItemStack secondary, Object[] inputs) {
    	if(output.getItem() instanceof ItemFood && !output.isItemEqual(new ItemStack(BWMItems.KIBBLE)))
		    return addRecipe(new CauldronFoodRecipe(output, secondary, inputs));
        return addRecipe(new CauldronRecipe(output, secondary, inputs));
    }

    @Override
    public CauldronRecipe addRecipe(CauldronRecipe recipe) {
        return super.addRecipe(recipe);
    }
}
