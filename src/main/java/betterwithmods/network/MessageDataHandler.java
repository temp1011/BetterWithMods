package betterwithmods.network;

import com.google.common.primitives.Primitives;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by michaelepps on 4/3/18.
 */
public class MessageDataHandler<DataType> {

    public static List<MessageDataHandler> handlers = new ArrayList<>();

    static {
        MessageDataHandler.addHandler(byte.class, ByteBuf::readByte, (buf, data) -> buf.writeByte(data));
        MessageDataHandler.addHandler(short.class, ByteBuf::readShort, (buf, data) -> buf.writeShort(data));
        MessageDataHandler.addHandler(int.class, ByteBuf::readInt, ByteBuf::writeInt);
        MessageDataHandler.addHandler(long.class, ByteBuf::readLong, ByteBuf::writeLong);
        MessageDataHandler.addHandler(double.class, ByteBuf::readDouble, ByteBuf::writeDouble);
        MessageDataHandler.addHandler(float.class, ByteBuf::readFloat, ByteBuf::writeFloat);
        MessageDataHandler.addHandler(boolean.class, ByteBuf::readBoolean, ByteBuf::writeBoolean);
        MessageDataHandler.addHandler(char.class, ByteBuf::readChar, (buf, data) -> buf.writeChar(data));

        MessageDataHandler.addHandler(String.class, ByteBufUtils::readUTF8String, ByteBufUtils::writeUTF8String);
        MessageDataHandler.addHandler(NBTTagCompound.class, ByteBufUtils::readTag, ByteBufUtils::writeTag);
        MessageDataHandler.addHandler(ItemStack.class, ByteBufUtils::readItemStack, ByteBufUtils::writeItemStack);
        MessageDataHandler.addHandler(BlockPos.class, buf -> BlockPos.fromLong(buf.readLong()), (buf, data) -> buf.writeLong((data.toLong())));
    }

    private Function<ByteBuf, DataType> reader;
    private BiConsumer<ByteBuf, DataType> writer;
    private Class typeClass;

    private MessageDataHandler(Class typeClass, Function<ByteBuf, DataType> reader, BiConsumer<ByteBuf, DataType> writer) {
        this.reader = reader;
        this.writer = writer;
        this.typeClass = typeClass;
    }

    private static <DataType> void addHandler(Class typeClass, Function<ByteBuf, DataType> reader, BiConsumer<ByteBuf, DataType> writer) {
        handlers.add(new MessageDataHandler(typeClass, reader, writer));
    }

    public static <DataType> MessageDataHandler<DataType> getHandlerType(DataType type) {
        return handlers.stream().filter(handler -> handler.typeMatches(type.getClass())).findFirst().orElse(null);
    }

    public DataType read(ByteBuf buf) {
        return reader.apply(buf);
    }

    public void write(ByteBuf buf, DataType data) {
        writer.accept(buf, data);
    }

    private boolean typeMatches(Class clazz) {
        if (Primitives.isWrapperType(clazz)) {
            clazz = Primitives.unwrap(clazz);
        }

        return clazz.equals(typeClass) || clazz.isAssignableFrom(typeClass);
    }
}