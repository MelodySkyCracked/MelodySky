/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing;

import java.util.HashMap;
import java.util.Map;

public class NSOption {
    private Object key;

    public static Map createOptionMap(NSOption ... nSOptionArray) {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        if (nSOptionArray == null) {
            return hashMap;
        }
        for (NSOption nSOption : nSOptionArray) {
            hashMap.put(nSOption.getOptionKey(), nSOption.getOptionValue());
        }
        return hashMap;
    }

    public NSOption(Object object) {
        this.key = object == null ? this.getClass().getName() : object;
    }

    public Object getOptionKey() {
        return this.key;
    }

    public Object getOptionValue() {
        return this;
    }

    public String toString() {
        Object object = this.getOptionKey();
        String string = object == this ? object.getClass().getName() : object.toString();
        Object object2 = this.getOptionValue();
        if (object2 == this) {
            if (this.getClass() == NSOption.class) {
                return string;
            }
            return string + "=" + object2.getClass().getName();
        }
        return string + "=" + object2;
    }
}

