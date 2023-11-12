/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledTextEvent;
import org.eclipse.swt.events.TypedEvent;

public class LineStyleEvent
extends TypedEvent {
    public int lineOffset;
    public String lineText;
    public int[] ranges;
    public StyleRange[] styles;
    public int alignment;
    public int indent;
    int verticalIndent;
    public int wrapIndent;
    public boolean justify;
    public Bullet bullet;
    public int bulletIndex;
    public int[] tabStops;
    static final long serialVersionUID = 3906081274027192884L;

    public LineStyleEvent(StyledTextEvent styledTextEvent) {
        super(styledTextEvent);
        this.styles = styledTextEvent.styles;
        this.ranges = styledTextEvent.ranges;
        this.lineOffset = styledTextEvent.detail;
        this.lineText = styledTextEvent.text;
        this.alignment = styledTextEvent.alignment;
        this.justify = styledTextEvent.justify;
        this.indent = styledTextEvent.indent;
        this.verticalIndent = styledTextEvent.verticalIndent;
        this.wrapIndent = styledTextEvent.wrapIndent;
        this.bullet = styledTextEvent.bullet;
        this.bulletIndex = styledTextEvent.bulletIndex;
        this.tabStops = styledTextEvent.tabStops;
    }
}

