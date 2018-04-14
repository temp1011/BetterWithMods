package betterwithmods.module.industry;

import betterwithmods.module.Module;
import betterwithmods.module.ModuleLoader;
import betterwithmods.module.industry.pollution.Pollution;

public class Industry extends Module {
    public Industry(ModuleLoader loader) {
        super(loader);
    }

    @Override
    public void addFeatures() {
        registerFeature(new Pollution());
        registerFeature(new WeatherControl());
    }

    @Override
    public String getModuleDescription() {
        return "The consequences of heavy industry.";
    }

}
