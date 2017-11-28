package betterwithmods.module.hardcore.world.strata;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import team.chisel.ctm.client.texture.ctx.OffsetProviderRegistry;
import team.chisel.ctm.client.texture.ctx.TextureContextPosition;

public class TextureContextWorld extends TextureContextPosition {
    BlockPos top;

    public TextureContextWorld(BlockPos pos, BlockPos top) {
        super(pos);
        this.top = top;
    }

    public BlockPos getTop() {
        return top;
    }

    public TextureContextPosition applyOffset() {
        this.top = top.add(OffsetProviderRegistry.INSTANCE.getOffset(Minecraft.getMinecraft().world, top));
        return super.applyOffset();
    }

    @Override
    public long getCompressedData() {
        return 0L;
    }
}
