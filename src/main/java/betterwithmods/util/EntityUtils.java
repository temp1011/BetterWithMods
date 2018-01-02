package betterwithmods.util;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityUtils {

    public static void removeAI(EntityLiving entity, Class<? extends EntityAIBase> clazz) {
        entity.tasks.taskEntries.removeIf(entityAITaskEntry -> clazz.isAssignableFrom(entityAITaskEntry.action.getClass()));
    }

}
