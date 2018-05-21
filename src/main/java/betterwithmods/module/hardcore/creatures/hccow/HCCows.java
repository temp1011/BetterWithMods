package betterwithmods.module.hardcore.creatures.hccow;

import betterwithmods.client.model.render.RenderUtils;
import betterwithmods.common.damagesource.BWDamageSource;
import betterwithmods.module.Feature;
import net.minecraft.client.model.ModelCow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HCCows extends Feature {
    public static final BWDamageSource kick = new BWDamageSource.MultiDamageSource("kick");
    public static final DataParameter<Boolean> SCARED = EntityDataManager.createKey(EntityCow.class, DataSerializers.BOOLEAN);

    public static boolean retaliation;

    public static AxisAlignedBB getKickBox(EntityLivingBase entity) {
        Vec3d look = entity.getLook(1).rotateYaw(180);
        return new AxisAlignedBB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5).offset(look.x, look.y, look.z).grow(0.25);
    }

    @Override
    public void setupConfig() {
        retaliation = loadPropBool("Make cows retaliate", "Cows will now retaliate when attacked or scared", true);
    }

    @Override
    public String getFeatureDescription() {
        return "Cows will mess you up man.";
    }

    @SubscribeEvent
    public void addEntityAI(EntityJoinWorldEvent event) {
        if (retaliation) {
            Entity entity = event.getEntity();
            if (entity instanceof EntityCow) {
                ((EntityCow) entity).targetTasks.addTask(1, new AIKick((EntityLivingBase) event.getEntity(), 1d));
                entity.getDataManager().register(SCARED, false);
            }
        }
    }

    @Override
    public boolean hasSubscriptions() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void render(RenderLivingEvent.Pre<EntityCow> event) {

        EntityLivingBase entity = event.getEntity();
        if (entity instanceof EntityCow) {
            RenderUtils.renderDebugBoundingBox(event.getX(), event.getY(), event.getZ(), getKickBox(entity));

            ModelCow cow = (ModelCow) event.getRenderer().getMainModel();
            if (entity.getDataManager().get(SCARED)) {
                System.out.println(cow.leg2.rotateAngleX);
                cow.leg2.rotateAngleX = -1;
            }
        }
    }
}
