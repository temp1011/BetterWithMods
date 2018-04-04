package betterwithmods.client.container.bulk;

import betterwithmods.BWMod;
import betterwithmods.common.blocks.mechanical.tile.TileEntityCookingPot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiCookingPot extends GuiContainer {
    private static final int fireHeight = 12;
    private static final int stokedHeight = 28;
    private final TileEntityCookingPot tile;
    private final ContainerCookingPot container;

    public GuiCookingPot(EntityPlayer player, TileEntityCookingPot tile) {
        super(new ContainerCookingPot(player, tile));
        this.container = (ContainerCookingPot) this.inventorySlots;
        this.ySize = 193;
        this.tile = tile;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = I18n.format(tile.getName());
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f,
                                                   int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(new ResourceLocation(BWMod.MODID, "textures/gui/cooking_pot.png"));
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);

        if (container.getProgress() > 0) {
            int scaledIconHeight = (int) (14*(container.getProgress()/100d));
            int iconHeight = container.getHeat() > 1 ? stokedHeight : fireHeight;
            drawTexturedModalRect(xPos + 81, yPos + 19 + 12 - scaledIconHeight, 176, iconHeight - scaledIconHeight, 14, scaledIconHeight + 2);
        }
    }
}
