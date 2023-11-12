/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.swtimpl.components.JFlashPlayer;
import java.util.EventObject;

public class FlashPlayerCommandEvent
extends EventObject {
    private JFlashPlayer flashPlayer;
    private String command;
    private Object[] parameters;

    public FlashPlayerCommandEvent(JFlashPlayer jFlashPlayer, String string, Object[] objectArray) {
        super(jFlashPlayer);
        this.flashPlayer = jFlashPlayer;
        this.command = string;
        this.parameters = objectArray;
    }

    public JFlashPlayer getFlashPlayer() {
        return this.flashPlayer;
    }

    public String getCommand() {
        return this.command;
    }

    public Object[] getParameters() {
        return this.parameters;
    }
}

