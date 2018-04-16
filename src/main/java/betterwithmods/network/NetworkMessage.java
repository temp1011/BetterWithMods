package betterwithmods.network;

/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 * <p>
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 * <p>
 * File Created @ [11/01/2016, 22:00:30 (GMT)]
 */

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public abstract class NetworkMessage<REQ extends NetworkMessage> implements IMessage, IMessageHandler<REQ, IMessage> {

    private static final HashMap<Class, List<Field>> fieldCache = new HashMap<>();

    protected static <DataType> DataType readData(ByteBuf buf, DataType data) {
        try {
            MessageDataHandler<DataType> handler = MessageDataHandler.getHandlerType(data);
            if (handler != null)
                handler.read(buf);
            return null;
        } catch (NullPointerException e) {
        }
        return null;
    }

    protected static <DataType> void writeData(ByteBuf buf, DataType data) {
        MessageDataHandler<DataType> handler = MessageDataHandler.getHandlerType(data);
        if (handler != null)
            handler.write(buf, data);
    }

    // The thing you override!
    public IMessage handleMessage(MessageContext context) {
        return null;
    }

    @Override
    public final IMessage onMessage(REQ message, MessageContext context) {
        FMLCommonHandler.instance().getWorldThread(context.netHandler).addScheduledTask(() -> message.handleMessage(context));
        return null;
    }
}