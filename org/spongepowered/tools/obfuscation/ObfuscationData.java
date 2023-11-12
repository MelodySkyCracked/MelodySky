/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.tools.obfuscation.ObfuscationType;

public class ObfuscationData
implements Iterable {
    private final Map data = new HashMap();
    private final Object defaultValue;

    public ObfuscationData() {
        this(null);
    }

    public ObfuscationData(Object object) {
        this.defaultValue = object;
    }

    public void add(ObfuscationType obfuscationType, Object object) {
        this.data.put(obfuscationType, object);
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public Object get(ObfuscationType obfuscationType) {
        Object v = this.data.get(obfuscationType);
        return v != null ? v : this.defaultValue;
    }

    public Iterator iterator() {
        return this.data.keySet().iterator();
    }

    public String toString() {
        return String.format("ObfuscationData[%sDEFAULT=%s]", this.listValues(), this.defaultValue);
    }

    private String listValues() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ObfuscationType obfuscationType : this.data.keySet()) {
            stringBuilder.append(obfuscationType.getKey()).append('=').append(this.data.get(obfuscationType)).append(',');
        }
        return stringBuilder.toString();
    }
}

