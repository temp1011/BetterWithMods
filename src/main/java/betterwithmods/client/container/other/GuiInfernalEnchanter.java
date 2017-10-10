package betterwithmods.client.container.other;

import betterwithmods.BWMod;
import betterwithmods.common.blocks.tile.TileEntityInfernalEnchanter;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by tyler on 9/11/16.
 */
public class GuiInfernalEnchanter extends GuiContainer {
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
        if(fontGalactic == null)
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

        int y;
        for (int i = 0; i < container.enchantLevels.length; i++) {
            this.mc.renderEngine.bindTexture(new ResourceLocation(BWMod.MODID, "textures/gui/infernal_enchanter.png"));
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int val = container.enchantLevels[i];

            if (val != 0) {
                int color = 0x407F10;
                String s = String.valueOf(val);
                if (player.capabilities.isCreativeMode || player.experienceLevel >= val) {
                    y = yPos + 17 + (19 * i);
                    drawTexturedModalRect(xPos + 60, y, 0, 211, 108, 19);
                    color = 0x80FF20;
                    String s1 = EnchantmentNameParts.getInstance().generateNewRandomName(this.fontRenderer, 86 - this.fontRenderer.getStringWidth(s));
                    fontGalactic.drawSplitString(s1, xPos + 62, yPos + 19 + 19 * i, 108, (6839882 & 16711422) >> 1);
                }
                fontRenderer.drawStringWithShadow(s, xPos + xSize - 10 - this.fontRenderer.getStringWidth(s), yPos + 8 + 19 * (i + 1), color);
            }
        }
//        for (int i = 0; i < container.enchantLevels.length; i++) {
//            int val = container.enchantLevels[i];
//            if (val != 0) {
//                int color = 0x407F10;
//                if (mc.player.capabilities.isCreativeMode || mc.player.experienceLevel >= val) {
//                    int y = yPos + 17 + (19 * i);
//                    drawTexturedModalRect(xPos + 60, y, 0, 211, 108, 19);
//                    color = 0x80FF20;
//                }
//                String s = String.valueOf(val);
//                this.fontRenderer.drawStringWithShadow(s, xPos + xSize - 10 - this.fontRenderer.getStringWidth(s), yPos + 8 + 19 * (i + 1), color);
//                FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;
//                String s1 = EnchantmentNameParts.getInstance().generateNewRandomName(this.fontRenderer, 86 - this.fontRenderer.getStringWidth(s));
//                fontrenderer.drawSplitString(s1, xPos + 62, yPos + 19 + 19 * i, 108, (6839882 & 16711422) >> 1);
//            }
//        }
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format(tile.getName());
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 0x404040);
        s = "I";
        this.fontRenderer.drawString(s, 50 - this.fontRenderer.getStringWidth(s) / 2, 22, 0x404040);
        s = "II";
        this.fontRenderer.drawString(s, 50 - this.fontRenderer.getStringWidth(s) / 2, 41, 0x404040);
        s = "III";
        this.fontRenderer.drawString(s, 50 - this.fontRenderer.getStringWidth(s) / 2, 60, 0x404040);
        s = "IV";
        this.fontRenderer.drawString(s, 50 - this.fontRenderer.getStringWidth(s) / 2, 79, 0x404040);
        s = "V";
        this.fontRenderer.drawString(s, 50 - this.fontRenderer.getStringWidth(s) / 2, 98, 0x404040);


    }
}

