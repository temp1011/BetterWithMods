package betterwithmods.manual.client.manual.segment;

import betterwithmods.module.ModuleLoader;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 6/19/17.
 */
public class FeatureSegment extends TextSegment {
    public FeatureSegment(@Nullable Segment parent, String feature, String enabled, String disabled) {
        super(parent, ModuleLoader.isFeatureEnabledSimple(feature) ? enabled : disabled);
    }
}
