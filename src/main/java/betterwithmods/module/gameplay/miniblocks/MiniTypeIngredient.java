package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.common.BWMRecipes;
import com.google.gson.JsonObject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MiniTypeIngredient extends Ingredient {
    public Ingredient base;
    private MiniType type;

    protected MiniTypeIngredient(String type, Ingredient base) {
        super();
        this.type = MiniType.fromName(type.toLowerCase());
        this.base = base;
    }

    @Override
    public boolean apply(@Nullable ItemStack stack) {
        IBlockState state = ItemMini.getState(stack);
        if (state != null && MiniType.matches(type, stack)) {
            ItemStack baseStack = BWMRecipes.getStackFromState(state);
            return base.apply(baseStack);
        }
        return false;
    }

    @SuppressWarnings("unused")
    public static class Factory implements IIngredientFactory {
        @Nonnull
        @Override
        public Ingredient parse(JsonContext context, JsonObject json) {
            String type = JsonUtils.getString(json, "minitype");
            Ingredient i = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "baseIngredient"), context);
            return new MiniTypeIngredient(type, i);
        }
    }
}
