package betterwithmods.module.hardcore.crafting;

import betterwithmods.api.tile.IHeated;
import betterwithmods.common.registry.bulk.recipes.BulkCraftEvent;
import betterwithmods.common.registry.bulk.recipes.CookingPotRecipe;
import betterwithmods.module.Feature;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExplosiveRecipes extends Feature {

    @Override
    public String getFeatureDescription() {
        return "Some recipes can't get too hot or they might explode.";
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @SubscribeEvent
    public void onBulkCraft(BulkCraftEvent event) {

        if (event.getTile() instanceof IHeated && event.getRecipe() instanceof CookingPotRecipe) {
            CookingPotRecipe recipe = (CookingPotRecipe) event.getRecipe();
            if (((IHeated) event.getTile()).getHeat(event.getWorld(), event.getTile().getPos()) > recipe.getHeat()) {
                BlockPos pos = event.getTile().getPos();
                event.getWorld().createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1, true);
            }

        }
    }

}
