package betterwithmods.network;

import betterwithmods.module.hardcore.needs.hunger.HCHunger;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

/**
 * Created by primetoxinz on 6/24/17.
 */
public class MessageFat extends NetworkMessage {
    public String uuid;

    public MessageFat() {
        this("");
    }

    public MessageFat(UUID uuid) {
        this(uuid.toString());
    }

    public MessageFat(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        HCHunger.ClientSide.doFat(uuid);
        return super.handleMessage(context);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.uuid = readData(buf, String.class);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeData(buf, uuid);
    }
}
