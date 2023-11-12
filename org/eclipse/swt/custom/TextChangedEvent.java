/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.events.TypedEvent;

public class TextChangedEvent
extends TypedEvent {
    static final long serialVersionUID = 3258696524257835065L;

    public TextChangedEvent(StyledTextContent styledTextContent) {
        super(styledTextContent);
    }
}

