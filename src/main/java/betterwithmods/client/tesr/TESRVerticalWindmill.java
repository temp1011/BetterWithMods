package betterwithmods.client.tesr;

import betterwithmods.client.model.ModelVerticalFrame;
import betterwithmods.client.model.ModelVerticalSails;
import betterwithmods.client.model.ModelVerticalShafts;
import betterwithmods.client.model.render.RenderUtils;
import betterwithmods.common.blocks.mechanical.tile.TileWindmillVertical;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class TESRVerticalWindmill extends TileEntitySpecialRenderer<TileWindmillVertical> {
    private final ModelVerticalShafts modelShafts = new ModelVerticalShafts();
    private final ModelVerticalSails modelSails = new ModelVerticalSails();
    private final ModelVerticalFrame modelFrame = new ModelVerticalFrame();

    @Override
    public void render(TileWindmillVertical te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {


        float rotation = (te.getCurrentRotation() + (te.getMechanicalOutput(EnumFacing.UP) == 0 ? 0 : partialTicks * te.getPrevRotation()));
        rotation = -rotation;

        BlockPos pos = te.getBlockPos();
        RenderUtils.renderDebugBoundingBox(x,y,z,te.getRenderBoundingBox().offset(-pos.getX(),-pos.getY(),-pos.getZ()));

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
        modelShafts.setRotateAngle(modelShafts.axle, 0, (float) Math.toRadians(rotation), 0);
        modelSails.setRotateAngleForSails(0, (float) Math.toRadians(rotation), 0);
        modelFrame.setRotateAngle(modelFrame.axle, 0, (float) Math.toRadians(rotation), 0);
        this.bindTexture(new ResourceLocation("minecraft", "textures/blocks/log_oak.png"));
        this.modelShafts.render(0.0625F);
        this.bindTexture(new ResourceLocation("minecraft", "textures/blocks/planks_oak.png"));
        this.modelFrame.render(0.0625F);
        this.bindTexture(new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png"));
        this.modelSails.render(0.0625F, te);
        GlStateManager.popMatrix();



    }

}
