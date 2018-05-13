package betterwithmods.client.gui;

import betterwithmods.BWMod;
import betterwithmods.client.container.other.ContainerPulley;
import betterwithmods.common.blocks.mechanical.tile.TileEntityPulley;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPulley extends GuiProgress {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BWMod.MODID, "textures/gui/pulley.png");

    private final TileEntityPulley tile;

    public GuiPulley(EntityPlayer player, TileEntityPulley tile) {
        super(new ContainerPulley(player, tile), TEXTURE, tile);
        this.ySize = 193;
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = I18n.format(tile.getName());
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
    }

    @Override
    public int getX() {
        return 81;
    }

    @Override
    public int getY() {
        return 30;
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
}
