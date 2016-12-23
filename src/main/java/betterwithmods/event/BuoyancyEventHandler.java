package betterwithmods.event;

import betterwithmods.config.BWConfig;
import betterwithmods.entity.item.EntityItemBuoy;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Koward
 */
public class BuoyancyEventHandler {
    /**
     * Substitute the original {@link EntityItem} by our new {@link EntityItemBuoy}.
     */
    @SubscribeEvent
    public void replaceServerEntityItem(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        if (world.isRemote) return;
        if (BWConfig.hardcoreBuoy && event.getEntity().getClass() == EntityItem.class) {
            EntityItem entityItem = (EntityItem) event.getEntity();
            if (entityItem.getEntityItem().stackSize > 0) {
                event.setResult(Result.DENY);
                event.setCanceled(true);
                EntityItemBuoy newEntity = new EntityItemBuoy(entityItem);
                entityItem.setDead();
                entityItem.setInfinitePickupDelay();
                world.spawnEntity(newEntity);
            }
        }
        /* FIXME
        else if (event.getEntity().getClass() == EntityFallingBlock.class) {
            EntityFallingBlock entityBlock = (EntityFallingBlock) event.getEntity();
            event.setResult(Result.DENY);
            event.setCanceled(true);
            EntityFallingBlockCustom newEntity = new EntityFallingBlockCustom(entityBlock);
            entityBlock.setDead();
            world.spawnEntity(newEntity);
        }
        */
    }
}
