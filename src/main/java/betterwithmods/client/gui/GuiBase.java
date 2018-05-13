package betterwithmods.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiBase extends GuiContainer {
    private ResourceLocation background;

    public GuiBase(Container inventorySlotsIn, ResourceLocation background) {
        super(inventorySlotsIn);
        this.background = background;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(background);

        int centerX = (this.width - this.xSize) / 2;
        int centerY = (this.height - this.ySize) / 2;
        drawTexturedModalRect(centerX, centerY, 0, 0, this.xSize, this.ySize);
        drawExtras(partialTicks,mouseX,mouseY,centerX,centerY);
    }

    protected void drawExtras(float partialTicks, int mouseX, int mouseY, int centerX, int centerY) {}
}
