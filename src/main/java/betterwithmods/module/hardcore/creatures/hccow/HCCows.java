package betterwithmods.module.hardcore.creatures.hccow;

import betterwithmods.client.model.render.RenderUtils;
import betterwithmods.common.damagesource.BWDamageSource;
import betterwithmods.module.Feature;
import net.minecraft.client.model.ModelCow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HCCows extends Feature {
    public static final BWDamageSource kick = new BWDamageSource.MultiDamageSource("kick");



    @Override
    public String getFeatureDescription() {
        return "Cows will mess you up man.";
    }

    @SubscribeEvent
    public void addEntityAI(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityCow) {
            ((EntityCow) event.getEntity()).targetTasks.addTask(1, new AIKick((EntityLivingBase) event.getEntity(),1d));
        }
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void render(RenderLivingEvent.Post<EntityCow> event){

        EntityLivingBase entity = event.getEntity();
        if(entity instanceof EntityCow) {
            Vec3d look = entity.getLook(1).rotateYaw(180);

            AxisAlignedBB box = new AxisAlignedBB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5).offset(look.x, look.y, look.z).grow(0.25);

            RenderUtils.renderDebugBoundingBox(event.getX(), event.getY(), event.getZ(), box);

            ModelCow cow = (ModelCow) event.getRenderer().getMainModel();
            if(entity.getRevengeTarget() != null) {
                cow.leg1.rotateAngleY += 1;
                cow.leg2.rotateAngleY += 1;
                cow.leg3.rotateAngleY += 1;
                cow.leg4.rotateAngleY += 1;
            }
        }
    }
}
