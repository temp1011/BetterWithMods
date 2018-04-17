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

import com.sun.istack.internal.NotNull;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class NetworkMessage<REQ extends NetworkMessage> implements IMessage, IMessageHandler<REQ, IMessage> {

    private static final Supplier CANNOT_READ_PACKET_EXCEPTION = () -> new RuntimeException("Cannot read packet data");

    protected static <DataType> DataType readData(ByteBuf buf, Class type) {
        return (DataType) MessageDataHandler.getHandler(type).orElseThrow(CANNOT_READ_PACKET_EXCEPTION).read(buf);
    }

    protected static <DataType> void writeData(@NotNull ByteBuf buf, DataType data) {
        MessageDataHandler.getHandler(data.getClass()).orElseThrow(CANNOT_READ_PACKET_EXCEPTION).write(buf, data);
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