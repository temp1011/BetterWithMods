package betterwithmods.module.hardcore.world.strata;

import betterwithmods.client.model.render.RenderUtils;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BakedQuadRetextured;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import team.chisel.ctm.api.texture.ITextureContext;
import team.chisel.ctm.api.util.TextureInfo;
import team.chisel.ctm.client.texture.ctx.TextureContextPosition;
import team.chisel.ctm.client.texture.render.AbstractTexture;

import javax.annotation.Nullable;
import java.util.List;

public class TextureStrata extends AbstractTexture<TextureTypeStrata> {
    public TextureStrata(TextureTypeStrata type, TextureInfo info) {
        super(type, info);
    }

    @Override
    public List<BakedQuad> transformQuad(BakedQuad quad, @Nullable ITextureContext context, int quadGoal) {
        if (context instanceof TextureContextPosition) {
            TextureContextPosition c = (TextureContextPosition) context;
            BlockPos pos = c.getPosition();
            int strata = HCStrata.getStratification(pos.getY(), 50);
//            if(y >= (topY-10))
//                return 0;
//            if(y >= (topY-30))
//                return 1;
//            return 2;
            ResourceLocation[] textures = new ResourceLocation[]{new ResourceLocation("minecraft:blocks/cobblestone"), new ResourceLocation("minecraft:blocks/gray_concrete"), new ResourceLocation("minecraft:blocks/obsidian")};
            return Lists.newArrayList(new BakedQuadRetextured(quad, RenderUtils.textureGetter.apply(textures[strata])));
        }
        return Lists.newArrayList(quad);
    }
}
