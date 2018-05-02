package betterwithmods.client.render;

import betterwithmods.BWMod;
import betterwithmods.client.model.ModelLongBoi;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class RenderWolf extends RenderLiving<EntityWolf> {
    private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation(BWMod.MODID, "textures/entity/wild_boi.png");
    private static final ResourceLocation TAMED_WOLF_TEXTURES = new ResourceLocation(BWMod.MODID, "textures/entity/long_boi.png");
    private static final ResourceLocation ANRGY_WOLF_TEXTURES = new ResourceLocation(BWMod.MODID, "textures/entity/mad_boi.png");

    public RenderWolf(RenderManager p_i47187_1_) {
        super(p_i47187_1_, new ModelLongBoi(), 0.5F);
        this.addLayer(new LayerCollar(this));
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityWolf livingBase, float partialTicks) {
        return livingBase.getTailRotation();
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityWolf entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity.isWolfWet()) {
            float f = entity.getBrightness() * entity.getShadingWhileWet(partialTicks);
            GlStateManager.color(f, f, f);
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityWolf entity) {
        if (entity.isTamed()) {
            return TAMED_WOLF_TEXTURES;
        } else {
            return entity.isAngry() ? ANRGY_WOLF_TEXTURES : WOLF_TEXTURES;
        }
    }
}
