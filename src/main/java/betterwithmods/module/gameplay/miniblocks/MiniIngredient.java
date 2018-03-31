package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.common.BWMRecipes;
import betterwithmods.common.BWOreDictionary;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockCorner;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMini;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockMoulding;
import betterwithmods.module.gameplay.miniblocks.blocks.BlockSiding;
import com.google.gson.JsonObject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MiniIngredient extends Ingredient {
    private String type;
    public String baseOredict;

    protected MiniIngredient(String type, String baseOredict) {
        super();
        this.type = type.toLowerCase();
        this.baseOredict = baseOredict;
    }

    @Override
    public boolean apply(@Nullable ItemStack stack) {
        IBlockState state = ItemMini.getState(stack);
        if (state != null && type.equals(getType(stack))) {
            ItemStack baseStack = BWMRecipes.getStackFromState(state);
            boolean ore = BWOreDictionary.isOre(baseStack, baseOredict);
            return ore;
        }
        return false;
    }

    public static String getType(ItemStack stack) {
        if(stack.getItem() instanceof ItemMini) {
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


    @SuppressWarnings("unused")
    public static class Factory implements IIngredientFactory {
        @Nonnull
        @Override
        public Ingredient parse(JsonContext context, JsonObject json) {
            String baseOre = JsonUtils.getString(json, "baseOre");
            String type = JsonUtils.getString(json, "minitype");
            return new MiniIngredient(type,baseOre);
        }
    }
}
