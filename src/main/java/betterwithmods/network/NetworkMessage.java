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
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class NetworkMessage<REQ extends NetworkMessage> implements IMessage, IMessageHandler<REQ, IMessage> {

    private static final HashMap<Class, List<Field>> fieldCache = new HashMap();

    // The thing you override!
    public IMessage handleMessage(MessageContext context) {
        return null;
    }

    @Override
    public final IMessage onMessage(REQ message, MessageContext context) {
        FMLCommonHandler.instance().getWorldThread(context.netHandler).addScheduledTask(() -> message.handleMessage(context));
        return null;
    }

    @Override
    public final void fromBytes(ByteBuf buf) {
        try {
            getAcceptableFields().forEach(field -> setFieldData(field, MessageDataHandler.getHandlerForField(field).read(buf)));
        } catch (Exception e) {
            throw new RuntimeException("Error reading message " + this, e);
        }
    }

    @Override
    public final void toBytes(ByteBuf buf) {
        try {
            getAcceptableFields().forEach(field -> {
                MessageDataHandler.getHandlerForField(field).write(buf, getFieldData(field));
            });
        } catch (Exception e) {
            throw new RuntimeException("Error writing message " + this, e);
        }
    }

    private static List<Field> getClassFields(Class<?> clazz) {

        if (fieldCache.containsValue(clazz)) {
            return fieldCache.get(clazz);
        } else {
            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
            fields.sort(Comparator.comparing(Field::getName));
            fields.forEach(field -> field.setAccessible(true));
            fieldCache.put(clazz, fields);
            return fields;
        }
    }

    public void setFieldData(Field field, Object data) {
        try {
            field.set(this, data);
        } catch (Exception e) {
            throw new RuntimeException("Error setting field  " + field.getName() + " for message "  + this, e);
        }

    }

    public Object getFieldData(Field field) {
        try {
            return field.get(this);
        } catch(Exception e) {
            throw new RuntimeException("Error getting field  " + field.getName() + " for message "  + this, e);
        }
    }

    private static boolean canAcceptField(Field field) throws RuntimeException {

        int mods = field.getModifiers();
        boolean accept = !(Modifier.isFinal(mods) || Modifier.isStatic(mods) || Modifier.isTransient(mods)) && MessageDataHandler.getHandlerForField(field) != null;

        if(!accept) {
            throw new RuntimeException("Cannot process field " + field.getName());
        }

        return accept;
    }

    private List<Field> getAcceptableFields() {
        return getClassFields(getClass()).stream().filter(field -> canAcceptField(field)).collect(Collectors.toList());
    }

}