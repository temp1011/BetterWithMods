package betterwithmods.client.gui.bulk;

import betterwithmods.BWMod;
import betterwithmods.client.container.bulk.ContainerFilteredHopper;
import betterwithmods.client.gui.GuiProgress;
import betterwithmods.common.blocks.mechanical.tile.TileEntityFilteredHopper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFilteredHopper extends GuiProgress {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BWMod.MODID, "textures/gui/hopper.png");
    private final TileEntityFilteredHopper tile;

    public GuiFilteredHopper(EntityPlayer player, TileEntityFilteredHopper tile) {
        super(new ContainerFilteredHopper(player, tile), TEXTURE, tile);
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
    public int getX() {
        return 80;
    }

    @Override
    public int getY() {
        return 18;
    }

    @Override
    public int getTextureX() {
        return 176;
    }

    @Override
    public int getTextureY() {
        return 14;
    }

    @Override
    public int getHeight() {
        return 14;
    }

    @Override
    public int getWidth() {
        return 14;
    }

    @Override
    protected int toPixels() {
        return (int) (getHeight() * getPercentage());
    }
}
