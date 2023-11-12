/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.EventObject;
import org.eclipse.swt.graphics.TextStyle;

public class AccessibleTextAttributeEvent
extends EventObject {
    public int offset;
    public int start;
    public int end;
    public TextStyle textStyle;
    public String[] attributes;
    public String result;
    static final long serialVersionUID = 7131825608864332802L;

    public AccessibleTextAttributeEvent(Object object) {
        super(object);
    }

    @Override
    public String toString() {
        return "AccessibleAttributeEvent { offset=" + this.offset + " start=" + this.start + " end=" + this.end + " textStyle=" + this.textStyle + " attributes=" + this.toAttributeString(this.attributes) + " result=" + this.result;
    }

    String toAttributeString(String[] stringArray) {
        if (stringArray == null || stringArray.length == 0) {
            return "" + stringArray;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringArray.length; ++i) {
            stringBuilder.append(stringArray[i]);
            stringBuilder.append(i % 2 == 0 ? ":" : ";");
        }
        return stringBuilder.toString();
    }
}

