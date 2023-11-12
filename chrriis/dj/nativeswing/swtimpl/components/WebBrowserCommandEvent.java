/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;

public class WebBrowserCommandEvent
extends WebBrowserEvent {
    private String command;
    private Object[] parameters;

    public WebBrowserCommandEvent(JWebBrowser jWebBrowser, String string, Object[] objectArray) {
        super(jWebBrowser);
        this.command = string;
        this.parameters = objectArray;
    }

    public String getCommand() {
        return this.command;
    }

    public Object[] getParameters() {
        return this.parameters;
    }
}

