package betterwithmods.client.gui.bulk;

import betterwithmods.BWMod;
import betterwithmods.client.container.bulk.ContainerMill;
import betterwithmods.client.gui.GuiProgress;
import betterwithmods.common.blocks.mechanical.tile.TileEntityMill;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMill extends GuiProgress {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BWMod.MODID, "textures/gui/mill.png");
    private static final String NAME = "inv.mill.name";
    private final TileEntityMill mill;
    private ContainerMill container;

    public GuiMill(EntityPlayer player, TileEntityMill mill) {
        super(new ContainerMill(player, mill), TEXTURE, mill);
        this.container = (ContainerMill) inventorySlots;
        this.ySize = 158;
        this.mill = mill;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x,y);
        String s = I18n.format(NAME);
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f,
                                                   int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        //TODO
//        if (container.blocked) {
//            String str = "Blocked";
//            int width = fontRenderer.getStringWidth(str) / 2;
//            drawString(fontRenderer, str, xPos + this.xSize / 2 - width, yPos + 32, EnumDyeColor.RED.getColorValue());
//            drawToolTip(x, y, xPos + this.xSize / 2 - width, yPos + 32, 32, 32, "The Millstone has too many solid blocks around it.");
//        }

    }

    private void drawToolTip(int mouseX, int mouseY, int x, int y, int w, int h, String text) {
        if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= (y + h)) {
            drawHoveringText(text, mouseX, mouseY);
        }
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
