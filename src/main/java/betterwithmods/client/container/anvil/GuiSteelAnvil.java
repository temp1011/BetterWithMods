package betterwithmods.client.container.anvil;

import betterwithmods.BWMod;
import betterwithmods.common.blocks.tile.TileSteelAnvil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSteelAnvil extends GuiContainer {

    private static final ResourceLocation tex = new ResourceLocation(BWMod.MODID, "textures/gui/steel_anvil.png");
    private TileSteelAnvil tile;

    public GuiSteelAnvil(TileSteelAnvil tileEntity, ContainerSteelAnvil container) {
        super(container);
        this.ySize = 183;
        tile = tileEntity;
    }
