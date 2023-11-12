/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.common.Utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FlashPluginOptions {
    private Map keyToValueVariableMap;
    private Map keyToValueParameterMap;

    public FlashPluginOptions() {
        this.setParameters(null);
        this.setVariables(null);
    }

    public Map getVariables() {
        return this.keyToValueVariableMap;
    }

    public void setVariables(Map map) {
        this.keyToValueVariableMap = map == null ? Collections.synchronizedMap(new HashMap()) : Collections.synchronizedMap(new HashMap(map));
    }

    public Map getParameters() {
        return this.keyToValueParameterMap;
    }

    public void setParameters(Map map) {
        this.keyToValueParameterMap = map == null ? Collections.synchronizedMap(new HashMap()) : Collections.synchronizedMap(new HashMap(map));
    }

    Map getHTMLParameters() {
        HashMap<String, String> hashMap = new HashMap<String, String>(this.getParameters());
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : this.getVariables().entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append('&');
            }
            stringBuilder.append(Utils.encodeURL((String)entry.getKey())).append('=').append(Utils.encodeURL((String)entry.getValue()));
        }
        if (stringBuilder.length() > 0) {
            hashMap.put("flashvars", stringBuilder.toString());
        }
        hashMap.put("allowScriptAccess", "always");
        hashMap.put("swliveconnect", "true");
        return hashMap;
    }
}

