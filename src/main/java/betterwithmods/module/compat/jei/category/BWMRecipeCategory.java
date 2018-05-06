package betterwithmods.module.compat.jei.category;

import betterwithmods.BWMod;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

public abstract class BWMRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {
    @Nonnull
    private final IDrawable background;

    @Nonnull
    private final String localizedName, uid;


    public BWMRecipeCategory(@Nonnull IDrawable background, @Nonnull String uid, @Nonnull String unlocalizedName) {
        this.background = background;
        this.localizedName = I18n.format(unlocalizedName);
        this.uid = uid;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    @Nonnull
    public String getUid() {
        return this.uid;
    }

    @Override
    public String getModName() {
        return BWMod.NAME;
    }

}
