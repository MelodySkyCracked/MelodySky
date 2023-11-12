/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.HTMLEditorEvent;
import chrriis.dj.nativeswing.swtimpl.components.JHTMLEditor;

public class HTMLEditorDirtyStateEvent
extends HTMLEditorEvent {
    private boolean isDirty;

    public HTMLEditorDirtyStateEvent(JHTMLEditor jHTMLEditor, boolean bl) {
        super(jHTMLEditor);
        this.isDirty = bl;
    }

    public boolean isDirty() {
        return this.isDirty;
    }
}

