package betterwithmods.module.gameplay.miniblocks;

import betterwithmods.client.model.render.RenderUtils;
import betterwithmods.module.gameplay.miniblocks.orientations.BaseOrientation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MiniCacheInfo implements IRenderComparable<MiniCacheInfo> {
    public final TextureAtlasSprite texture;
    public final BaseOrientation orientation;

    final transient ItemStack block;

    public MiniCacheInfo(TextureAtlasSprite texture, BaseOrientation orientation, ItemStack block) {
        this.texture = texture;
        this.orientation = orientation;
        this.block = block;
    }

    public static MiniCacheInfo from(TileMini mini) {
        return new MiniCacheInfo(RenderUtils.getSprite(mini.texture), mini.orientation, mini.texture);
    }

    public static MiniCacheInfo from(ItemStack mini) {
        NBTTagCompound tag = mini.getTagCompound();
        if (tag != null && tag.hasKey("texture")) {
            ItemStack texture = new ItemStack(tag.getCompoundTag("texture"));
            return new MiniCacheInfo(RenderUtils.getSprite(texture), BaseOrientation.DEFAULT, texture);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiniCacheInfo cacheInfo = (MiniCacheInfo) o;
        if (!texture.equals(cacheInfo.texture)) return false;
        return orientation == cacheInfo.orientation;
    }

    @Override
    public boolean renderEquals(MiniCacheInfo other) {
        return equals(other);
    }

    @Override
    public int renderHashCode() {
        return 31 * texture.hashCode() + orientation.hashCode();
    }

    public TextureAtlasSprite getTexture() {
        if(texture == null)
            return RenderUtils.textureGetter.apply(TextureMap.LOCATION_MISSING_TEXTURE);
        return texture;
    }

    public BaseOrientation getOrientation() {
        if(orientation == null)
            return BaseOrientation.DEFAULT;
        return orientation;
    }
}
