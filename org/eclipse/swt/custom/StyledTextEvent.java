/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Event;

class StyledTextEvent
extends Event {
    int[] ranges;
    StyleRange[] styles;
    int alignment;
    int indent;
    int verticalIndent;
    int wrapIndent;
    boolean justify;
    Bullet bullet;
    int bulletIndex;
    int[] tabStops;
    Color lineBackground;
    int replaceCharCount;
    int newCharCount;
    int replaceLineCount;
    int newLineCount;
    int x;
    int y;
    int ascent;
    int descent;
    StyleRange style;

    StyledTextEvent(StyledTextContent styledTextContent) {
        this.data = styledTextContent;
    }
}

