/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI.ClickGui;

import java.util.HashMap;
import xyz.Melody.Event.value.Value;
import xyz.Melody.GUI.ClickGui.Utils.Elements.Element;
import xyz.Melody.module.Module;

public class ValueElement {
    private Module module;
    private HashMap valueMap = new HashMap();

    public ValueElement(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return this.module;
    }

    public Element getValueElement(Value value) {
        return (Element)this.valueMap.get(value);
    }

    public void addValueElement(Value value, Element element) {
        this.valueMap.put(value, element);
    }

    public HashMap getValueMap() {
        return this.valueMap;
    }
}

