package betterwithmods.module.hardcore.creatures.hccow;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class AIKick extends EntityAIBase {

    private final EntityLivingBase entity;
    private double radius, ticks;

    public AIKick(EntityLivingBase entity, double radius) {
        this.entity = entity;
        this.radius = radius;
    }

    @Override
    public boolean shouldExecute() {
        return entity != null && entity.getRevengeTarget() != null && entity.getRevengeTarget().isEntityAlive();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return shouldExecute();
    }

    @Override
    public void resetTask() {
        ticks = 0;
    }

    @Override
    public void updateTask() {

        World world = entity.getEntityWorld();
        if(world.isRemote)
            return;
        if(ticks-- < 0) {

            Vec3d look = entity.getLook(1).rotateYaw(180);
            AxisAlignedBB box = new AxisAlignedBB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5).offset(look.x, look.y, look.z).grow(radius).offset(entity.posX, entity.posY, entity.posZ);

            List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, box, entity -> entity != null && !entity.getClass().isAssignableFrom(this.entity.getClass()));
            if(!entities.isEmpty()) {
                entity.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1, 0);
                entities.forEach(entity -> {
                    entity.knockBack(entity, 1, -look.x, -look.z);
                    entity.attackEntityFrom(HCCows.kick, 5);
                });
            }
            ticks=20;
        }

    }
}
