package betterwithmods.manual.client.manual.segment;

import betterwithmods.manual.api.manual.ImageRenderer;
import betterwithmods.module.compat.jei.JEI;
import net.minecraft.item.crafting.Ingredient;

public class JEIRenderSegment extends RenderSegment implements IJEISegment {


    private final String recipeOutput;

    public JEIRenderSegment(Segment parent, String title, String recipeOutput, ImageRenderer imageRenderer) {
        super(parent, title, imageRenderer);
        this.recipeOutput = recipeOutput;
    }

    @Override
    public boolean onMouseClick(final int mouseX, final int mouseY) {
        if (JEI.JEI_RUNTIME != null && recipeOutput != null) {
            Ingredient ingredient = getIngredient(recipeOutput);
            JEI.showRecipe(ingredient);
            return true;
        }
        return false;
    }


}
