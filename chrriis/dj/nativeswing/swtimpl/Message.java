/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import java.io.Serializable;

public class Message
implements Serializable {
    private static int nextID = 1;
    private int id;
    private boolean isSyncExec;
    private boolean isUI = true;

    void setUI(boolean bl) {
        this.isUI = bl;
    }

    boolean isUI() {
        return this.isUI;
    }

    int getID() {
        return this.id;
    }

    void setSyncExec(boolean bl) {
        this.isSyncExec = bl;
    }

    boolean isSyncExec() {
        return this.isSyncExec;
    }

    public void asyncSend(boolean bl) {
        NativeInterface.asyncSend(bl, this);
    }

    public Object syncSend(boolean bl) {
        return NativeInterface.syncSend(bl, this);
    }

    void computeID(boolean bl) {
        if (this.id != 0) {
            return;
        }
        this.id = bl ? nextID++ : -nextID++;
    }

    protected boolean isValid() {
        return true;
    }

    public String toString() {
        String string = this.getClass().getName();
        if (string.startsWith("chrriis.dj.nativeswing.swtimpl.")) {
            string = string.substring(31);
        }
        return string;
    }
}

