/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.events;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;

public class SegmentEvent
extends TypedEvent {
    public int lineOffset;
    public String lineText;
    public int[] segments;
    public char[] segmentsChars;
    static final long serialVersionUID = -2414889726745247762L;

    public SegmentEvent(Event event) {
        super(event);
        this.lineText = event.text;
        this.lineOffset = event.detail;
    }
}

