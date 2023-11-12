/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.common.Utils;
import java.util.ArrayList;
import java.util.Map;

public class WebBrowserNavigationParameters {
    private String[] headers;
    private String postData;

    public void setHeaders(Map map) {
        if (map == null || map.isEmpty()) {
            this.headers = null;
            return;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string : map.keySet()) {
            if (string == null || string.length() <= 0) continue;
            arrayList.add(string + ": " + (String)map.get(string));
        }
        this.headers = arrayList.toArray(new String[0]);
    }

    public String[] getHeaders() {
        return this.headers;
    }

    public void setPostData(String string) {
        this.postData = string;
    }

    public void setPostData(Map map) {
        if (map == null || map.isEmpty()) {
            this.postData = null;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append('&');
            }
            if (string != null && string.length() > 0) {
                stringBuilder.append(Utils.encodeURL(string));
                stringBuilder.append('=');
            }
            stringBuilder.append(Utils.encodeURL((String)map.get(string)));
        }
        this.postData = stringBuilder.toString();
    }

    public String getPostData() {
        return this.postData;
    }
}

