package betterwithmods.network;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHarnessSync extends NetworkMessage {
    public ItemStack harness;

    public MessageHarnessSync() {
    }

    public MessageHarnessSync(ItemStack harness) {
        this.harness = harness;
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        return super.handleMessage(context);
    }
}
