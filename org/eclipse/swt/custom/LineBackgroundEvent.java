/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.StyledTextEvent;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.graphics.Color;

public class LineBackgroundEvent
extends TypedEvent {
    public int lineOffset;
    public String lineText;
    public Color lineBackground;
    static final long serialVersionUID = 3978711687853324342L;

    public LineBackgroundEvent(StyledTextEvent styledTextEvent) {
        super(styledTextEvent);
        this.lineOffset = styledTextEvent.detail;
        this.lineText = styledTextEvent.text;
        this.lineBackground = styledTextEvent.lineBackground;
    }
}

