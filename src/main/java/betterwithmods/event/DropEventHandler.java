package betterwithmods.event;

import betterwithmods.BWMItems;
import betterwithmods.util.player.EntityPlayerExt;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Koward
 */
public class DropEventHandler {
    //@SubscribeEvent //TODO Finish event
    public void onHarvest(BlockEvent.HarvestDropsEvent event) {
        Block block = event.getState().getBlock();
        ItemStack stack = event.getHarvester().getHeldItemMainhand();
        if (block == Blocks.GRAVEL) {
            if (!EntityPlayerExt.isCurrentToolEffectiveOnBlock(stack, event.getState())) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(BWMItems.GRAVEL_PILE, 1));
                event.setDropChance(1.0F);
            }
        }
    }
}
