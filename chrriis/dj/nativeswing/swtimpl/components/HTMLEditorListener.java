/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.HTMLEditorDirtyStateEvent;
import chrriis.dj.nativeswing.swtimpl.components.HTMLEditorSaveEvent;
import java.util.EventListener;

public interface HTMLEditorListener
extends EventListener {
    public void saveHTML(HTMLEditorSaveEvent var1);

    public void notifyDirtyStateChanged(HTMLEditorDirtyStateEvent var1);
}

