/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JHTMLEditor;
import java.util.EventObject;

public class HTMLEditorEvent
extends EventObject {
    private JHTMLEditor htmlEditor;

    public HTMLEditorEvent(JHTMLEditor jHTMLEditor) {
        super(jHTMLEditor);
        this.htmlEditor = jHTMLEditor;
    }

    public JHTMLEditor getHTMLEditor() {
        return this.htmlEditor;
    }
}

