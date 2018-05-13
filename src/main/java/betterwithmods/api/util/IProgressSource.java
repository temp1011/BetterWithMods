package betterwithmods.api.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IProgressSource {


    boolean showProgress();

    int getMax();
    int getProgress();


    @SideOnly(Side.CLIENT)
    void setProgress(int progress);
    @SideOnly(Side.CLIENT)
    void setMax(int max);
}
