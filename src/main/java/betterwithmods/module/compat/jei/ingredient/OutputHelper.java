package betterwithmods.module.compat.jei.ingredient;

import betterwithmods.api.recipe.IOutput;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.color.ColorGetter;
import mezz.jei.startup.StackHelper;
import mezz.jei.util.ErrorUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class OutputHelper implements IIngredientHelper<IOutput> {

    private final StackHelper stackHelper;

    public OutputHelper(StackHelper stackHelper) {
        this.stackHelper = stackHelper;
    }

    @Override
    public List<IOutput> expandSubtypes(List<IOutput> contained) {
        return contained;
    }

    @Nullable
    @Override
    public IOutput getMatch(Iterable<IOutput> ingredients, IOutput ingredientToMatch) {
        for (IOutput r : ingredients) {
            if (r.equals(ingredientToMatch)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public String getDisplayName(IOutput ingredient) {
        return ErrorUtil.checkNotNull(ingredient.getOutput().getDisplayName(), "itemStack.getDisplayName()");
    }

    @Override
    public String getUniqueId(IOutput ingredient) {
        ErrorUtil.checkNotEmpty(ingredient.getOutput());
        return stackHelper.getUniqueIdentifierForStack(ingredient.getOutput());
    }

    @Override
    public String getWildcardId(IOutput ingredient) {
        ErrorUtil.checkNotEmpty(ingredient.getOutput());
        return stackHelper.getUniqueIdentifierForStack(ingredient.getOutput(), StackHelper.UidMode.WILDCARD);
    }

    @Override
    public String getModId(IOutput ingredient) {
        ErrorUtil.checkNotEmpty(ingredient.getOutput());

        Item item = ingredient.getOutput().getItem();
        ResourceLocation itemName = item.getRegistryName();
        if (itemName == null) {
            String stackInfo = getErrorInfo(ingredient);
            throw new IllegalStateException("item.getRegistryName() returned null for: " + stackInfo);
        }

        return itemName.getResourceDomain();
    }

    @Override
    public Iterable<Color> getColors(IOutput ingredient) {
        return ColorGetter.getColors(ingredient.getOutput(), 2);
    }

    @Override
    public String getResourceId(IOutput ingredient) {
        ErrorUtil.checkNotEmpty(ingredient.getOutput());

        Item item = ingredient.getOutput().getItem();
        ResourceLocation itemName = item.getRegistryName();
        if (itemName == null) {
            String stackInfo = getErrorInfo(ingredient);
            throw new IllegalStateException("item.getRegistryName() returned null for: " + stackInfo);
        }

        return itemName.getResourcePath();

    }

    @Override
    public IOutput copyIngredient(IOutput ingredient) {
        return ingredient.copy();
    }

    @Override
    public String getErrorInfo(IOutput ingredient) {
        return ErrorUtil.getItemStackInfo(ingredient.getOutput());
    }
}
