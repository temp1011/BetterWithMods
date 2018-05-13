package betterwithmods.client.container.anvil;

import betterwithmods.BWMod;
import betterwithmods.client.gui.GuiBase;
import betterwithmods.common.blocks.tile.TileEntitySteelAnvil;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiSteelAnvil extends GuiBase {

    private static final ResourceLocation tex = new ResourceLocation(BWMod.MODID, "textures/gui/steel_anvil.png");
    private TileEntitySteelAnvil tile;

    public GuiSteelAnvil(TileEntitySteelAnvil tileEntity, ContainerSteelAnvil container) {
        super(container, tex);
        this.ySize = 183;
        tile = tileEntity;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(I18n.format(tile.getName()), 10, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 3, 4210752);
    }
}