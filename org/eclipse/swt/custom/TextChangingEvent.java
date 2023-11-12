/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.custom.StyledTextEvent;
import org.eclipse.swt.events.TypedEvent;

public class TextChangingEvent
extends TypedEvent {
    public int start;
    public String newText;
    public int replaceCharCount;
    public int newCharCount;
    public int replaceLineCount;
    public int newLineCount;
    static final long serialVersionUID = 3257290210114352439L;

    public TextChangingEvent(StyledTextContent styledTextContent) {
        super(styledTextContent);
    }

    TextChangingEvent(StyledTextContent styledTextContent, StyledTextEvent styledTextEvent) {
        super(styledTextContent);
        this.start = styledTextEvent.start;
        this.replaceCharCount = styledTextEvent.replaceCharCount;
        this.newCharCount = styledTextEvent.newCharCount;
        this.replaceLineCount = styledTextEvent.replaceLineCount;
        this.newLineCount = styledTextEvent.newLineCount;
        this.newText = styledTextEvent.text;
    }
}

