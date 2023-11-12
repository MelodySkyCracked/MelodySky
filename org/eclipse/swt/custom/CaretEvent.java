/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.StyledTextEvent;
import org.eclipse.swt.events.TypedEvent;

public class CaretEvent
extends TypedEvent {
    public int caretOffset;
    static final long serialVersionUID = 3257846571587545489L;

    CaretEvent(StyledTextEvent styledTextEvent) {
        super(styledTextEvent);
        this.caretOffset = styledTextEvent.end;
    }
}

