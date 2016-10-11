package com.amhzing.activities.ui.feature;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum AppFeatures implements Feature {

    @EnabledByDefault
    @Label("Facilitated activity")
    ACTIVITY_FACILITATED,

    @Label("Hosted activity")
    ACTIVITY_HOSTED;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}