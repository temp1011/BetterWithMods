package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.common.BWMRecipes;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockCorner;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMini;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMoulding;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockSiding;
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
    private String type;

    protected MiniTypeIngredient(String type, Ingredient base) {
        super();
        this.type = type.toLowerCase();
        this.base = base;
    }

    public static String getType(ItemStack stack) {
        if (stack.getItem() instanceof ItemMini) {
            BlockMini mini = (BlockMini) ((ItemMini) stack.getItem()).getBlock();
            if (mini instanceof BlockSiding) {
                return "siding";
            } else if (mini instanceof BlockMoulding) {
                return "moulding";
            } else if (mini instanceof BlockCorner) {
                return "corner";
            }
        }
        return "";
    }

    @Override
    public boolean apply(@Nullable ItemStack stack) {
        IBlockState state = ItemMini.getState(stack);
        if (state != null && type.equals(getType(stack))) {
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
