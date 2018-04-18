package betterwithmods.network;

import betterwithmods.BWMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Random;

public class MessageCustomBlockDust extends NetworkMessage {
    public static final Random rand = new Random();

    private World world;
    private BlockPos pos = BlockPos.ORIGIN;
    private float posX, posY, posZ;
    private int numberOfParticles;
    private float particleSpeed;

    public MessageCustomBlockDust() { }

    public MessageCustomBlockDust(World world, BlockPos pos, double posX, double posY, double posZ, int numberOfParticles, float particleSpeed) {
        this.world = world;
        this.pos = pos;
        this.posX = (float) posX;
        this.posY = (float) posY;
        this.posZ = (float) posZ;
        this.numberOfParticles = numberOfParticles;
        this.particleSpeed = particleSpeed;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = readData(buf, BlockPos.class);
        numberOfParticles = readData(buf, int.class);
        posX = readData(buf, float.class);
        posY = readData(buf, float.class);
        posZ = readData(buf, float.class);
        particleSpeed = readData(buf, float.class);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        writeData(buf, pos);
        writeData(buf, numberOfParticles);
        writeData(buf, posX);
        writeData(buf, posY);
        writeData(buf, posZ);
        writeData(buf, particleSpeed);
    }

    @Override
    public IMessage onMessage(IMessage message, MessageContext ctx) {
        this.world = Minecraft.getMinecraft().world;
        if (world != null) {
            BWMod.proxy.spawnBlockDustClient(world, pos, rand, posX, posY, posZ, numberOfParticles, particleSpeed, EnumFacing.UP);
        }
        return null;
    }
}