package betterwithmods.manual.client.manual.segment;

import betterwithmods.manual.api.manual.ImageRenderer;
import betterwithmods.module.compat.jei.JEI;
import com.google.common.base.Strings;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.gui.Focus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class JEIRenderSegment extends RenderSegment {


    private final String recipeOutput;

    public JEIRenderSegment(Segment parent, String title, String recipeOutput, ImageRenderer imageRenderer) {
        super(parent, title, imageRenderer);
        this.recipeOutput = recipeOutput;
    }

    @Override
    public boolean onMouseClick(final int mouseX, final int mouseY) {
        if (JEI.JEI_RUNTIME != null && recipeOutput != null) {
            ItemStack stack = getStack(recipeOutput);
            if (!stack.isEmpty()) {
                IFocus<?> focus = new Focus<Object>(IFocus.Mode.OUTPUT, stack);
                JEI.JEI_RUNTIME.getRecipesGui().show(focus);
            }
            return true;
        }
        return false;
    }

    public ItemStack getStack(String data) {
        data = data.substring(data.indexOf(":") + 1);
        final int splitIndex = data.lastIndexOf('@');
        final String name, optMeta;
        if (splitIndex > 0) {
            name = data.substring(0, splitIndex);
            optMeta = data.substring(splitIndex);
        } else {
            name = data;
            optMeta = "";
        }
        final int meta = (Strings.isNullOrEmpty(optMeta)) ? 0 : Integer.parseInt(optMeta.substring(1));
        final Item item = Item.REGISTRY.getObject(new ResourceLocation(name));
        if (item == null)
            return ItemStack.EMPTY;
        return new ItemStack(item, 1, meta);
    }


}
