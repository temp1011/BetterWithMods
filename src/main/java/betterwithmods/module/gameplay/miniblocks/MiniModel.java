package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.client.model.render.RenderUtils;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class MiniModel extends ModelFactory<MiniCacheInfo> {

    public static MiniModel SIDING;

    public IModel template;

    protected MiniModel(IModel template) {
        super(BlockMini.MINI_INFO, TextureMap.LOCATION_MISSING_TEXTURE);
        this.template = template;
    }

    @Override
    public IBakedModel bake(MiniCacheInfo object, boolean isItem, BlockRenderLayer layer) {
        ImmutableMap.Builder<String, String> textures = new ImmutableMap.Builder<>();
        textures.put("side", object.getTexture().getIconName());
        IModelState state = object.getOrientation().toTransformation();
        IModel retexture = template.retexture(textures.build()).uvlock(true);
        return new WrappedBakedModel(retexture.bake(state, DefaultVertexFormats.BLOCK, RenderUtils.textureGetter)).addDefaultBlockTransforms();
    }


    @Override
    public MiniCacheInfo fromItemStack(ItemStack stack) {
        return MiniCacheInfo.from(stack);
    }
}
