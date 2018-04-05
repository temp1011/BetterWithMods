package betterwithmods.network;

import betterwithmods.BWMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.function.Function;

public class MessageHarnessSync extends NetworkMessage {
    public ItemStack harness = ItemStack.EMPTY;
    public int entityID = 0;

    public MessageHarnessSync() {
        this(0,ItemStack.EMPTY);
    }

    public MessageHarnessSync(int entityID, ItemStack harness) {
        this.entityID = entityID;
        this.harness = harness;
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        BWMod.proxy.syncHarness(entityID, harness);
        return super.handleMessage(context);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityID = readData(buf, entityID);
        this.harness = readData(buf, harness);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeData(buf, entityID);
        writeData(buf, harness);
    }
}
