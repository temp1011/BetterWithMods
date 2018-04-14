package betterwithmods.module.compat.multipart;

import mcmultipart.api.multipart.IMultipartTile;
import net.minecraft.tileentity.TileEntity;

public class MultipartTileProxy implements IMultipartTile {
    private TileEntity tile;

    public MultipartTileProxy(TileEntity tile) {
        this.tile = tile;
    }

    @Override
    public TileEntity getTileEntity() {
        return tile;
    }
}
