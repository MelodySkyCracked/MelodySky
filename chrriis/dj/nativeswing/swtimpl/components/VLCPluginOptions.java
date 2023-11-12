/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VLCPluginOptions {
    private Map keyToValueParameterMap;

    public VLCPluginOptions() {
        this.setParameters(null);
    }

    public Map getParameters() {
        return this.keyToValueParameterMap;
    }

    public void setParameters(Map map) {
        this.keyToValueParameterMap = map == null ? Collections.synchronizedMap(new HashMap()) : Collections.synchronizedMap(new HashMap(map));
    }
}

