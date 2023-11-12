/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.HTMLEditorEvent;
import chrriis.dj.nativeswing.swtimpl.components.JHTMLEditor;

public class HTMLEditorSaveEvent
extends HTMLEditorEvent {
    private String text;

    public HTMLEditorSaveEvent(JHTMLEditor jHTMLEditor, String string) {
        super(jHTMLEditor);
        this.text = string;
    }

    public String getText() {
        return this.text;
    }
}

