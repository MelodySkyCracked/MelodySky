/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing;

import chrriis.dj.nativeswing.NSOption;

public abstract class NSComponentOptions {
    static final String DEACTIVATE_NATIVE_INTEGRATION_OPTION_KEY = "Deactivate Native Integration";
    private static final NSOption DEACTIVATE_NATIVE_INTEGRATION_OPTION = new NSOption("Deactivate Native Integration");
    static final String DESTROY_ON_FINALIZATION_OPTION_KEY = "Destroy On Finalization";
    private static final NSOption DESTROY_ON_FINALIZATION_OPTION = new NSOption("Destroy On Finalization");
    static final String PROXY_COMPONENT_HIERARCHY_OPTION_KEY = "Proxy Component Hierarchy";
    private static final NSOption PROXY_COMPONENT_HIERARCHY_OPTION = new NSOption("Proxy Component Hierarchy");
    static final String CONSTRAIN_VISIBILITY_OPTION_KEY = "Constrain Visibility";
    private static final NSOption CONSTRAIN_VISIBILITY_OPTION = new NSOption("Constrain Visibility");

    public static NSOption deactivateNativeIntegration() {
        return DEACTIVATE_NATIVE_INTEGRATION_OPTION;
    }

    public static NSOption destroyOnFinalization() {
        return DESTROY_ON_FINALIZATION_OPTION;
    }

    public static NSOption proxyComponentHierarchy() {
        return PROXY_COMPONENT_HIERARCHY_OPTION;
    }

    public static NSOption constrainVisibility() {
        return CONSTRAIN_VISIBILITY_OPTION;
    }
}

