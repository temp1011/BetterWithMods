package betterwithmods.client.container.bulk;

import betterwithmods.blocks.tile.TileEntityCauldron;
import net.minecraft.entity.player.EntityPlayer;

public class GuiCauldron extends GuiCookingPot {
    public GuiCauldron(EntityPlayer player, TileEntityCauldron cauldron) {
        super(player, cauldron);
    }
}
