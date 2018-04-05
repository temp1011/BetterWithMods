package betterwithmods.network;

import betterwithmods.client.gui.GuiHunger;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by primetoxinz on 6/24/17.
 */
public class MessageGuiShake extends NetworkMessage {
    @Override
    public IMessage handleMessage(MessageContext context) {
        GuiHunger.INSTANCE.shake();
        return super.handleMessage(context);
    }

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}
}
