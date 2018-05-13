package betterwithmods.client.gui;

import betterwithmods.BWMod;
import betterwithmods.client.container.other.ContainerInfernalEnchanter;
import betterwithmods.common.blocks.tile.TileEntityInfernalEnchanter;
import com.google.common.collect.Maps;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by primetoxinz on 9/11/16.
 */
public class GuiInfernalEnchanter extends GuiContainer {
    private static HashMap<Integer, String> numerals = Maps.newHashMap();

    static {
        numerals.put(22, "I");
        numerals.put(41, "II");
        numerals.put(60, "III");
        numerals.put(79, "IV");
        numerals.put(98, "V");
    }

    private FontRenderer fontGalactic;
    private TileEntityInfernalEnchanter tile;
    private ContainerInfernalEnchanter container;
    private EntityPlayer player;

    public GuiInfernalEnchanter(EntityPlayer player, TileEntityInfernalEnchanter tile) {
        super(new ContainerInfernalEnchanter(player, tile));
        this.container = (ContainerInfernalEnchanter) inventorySlots;
        this.player = player;
        this.tile = tile;
        ySize = 211;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (fontGalactic == null)
            fontGalactic = this.mc.standardGalacticFontRenderer;
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation(BWMod.MODID, "textures/gui/infernal_enchanter.png"));

        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);
        if (!inventorySlots.getSlot(0).getHasStack())
            drawTexturedModalRect(xPos + 17, yPos + 37, 176, 0, 16, 16);
        if (!inventorySlots.getSlot(1).getHasStack())
            drawTexturedModalRect(xPos + 17, yPos + 75, 192, 0, 16, 16);
        EnchantmentNameParts.getInstance().reseedRandomGenerator((long) this.container.xpSeed);

        int x, y;
        for (int levelIndex = 0; levelIndex < container.enchantLevels.length; levelIndex++) {
            this.mc.renderEngine.bindTexture(new ResourceLocation(BWMod.MODID, "textures/gui/infernal_enchanter.png"));
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            int level = container.enchantLevels[levelIndex];
            if (level > 0) {
                String levelString = String.valueOf(level);
                if (container.hasLevels(player, levelIndex) && container.hasBooks(levelIndex)) {
                    y = yPos + 17 + (19 * levelIndex);
                    x = xPos + 60;
                    if (mouseX >= x && mouseX <= x + 108 && mouseY >= y && mouseY <= y + 19) {
                        drawTexturedModalRect(x, y, 108, 211, 108, 19);
                    } else {
                        drawTexturedModalRect(x, y, 0, 211, 108, 19);
                    }
                }
                String galacticText = EnchantmentNameParts.getInstance().generateNewRandomName(this.fontRenderer, 86 - this.fontRenderer.getStringWidth(levelString));
                fontGalactic.drawSplitString(galacticText, xPos + 62, yPos + 19 + 19 * levelIndex, 108, (6839882 & 16711422) >> 1);
                fontRenderer.drawStringWithShadow(levelString, xPos + xSize - 10 - this.fontRenderer.getStringWidth(levelString), yPos + 8 + 19 * (levelIndex + 1), 0x80FF20);
            }
        }

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format(tile.getName());
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 0x404040);
        for (Map.Entry<Integer, String> e : numerals.entrySet()) {
            this.fontRenderer.drawString(e.getValue(), 50 - this.fontRenderer.getStringWidth(e.getValue()) / 2, e.getKey(), 0x404040);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;

        int x, y;
        for (int levelIndex = 0; levelIndex < container.enchantLevels.length; levelIndex++) {
            if (container.enchantLevels[levelIndex] != -1 && container.hasLevels(player, levelIndex) && container.hasBooks(levelIndex)) {
                y = yPos + 17 + (19 * levelIndex);
                x = xPos + 60;
                if (mouseX >= x && mouseX <= x + 108 && mouseY >= y && mouseY <= y + 19) {
                    if (container.enchantItem(player, levelIndex)) {
                        this.mc.playerController.sendEnchantPacket(this.container.windowId, levelIndex);
                    }
                }
            }
        }
    }
}

