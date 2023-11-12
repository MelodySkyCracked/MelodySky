/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.swtimpl.Message;

public abstract class CommandMessage
extends Message {
    private Object[] args;
    private static final Object[] EMPTY_ARGS = new Object[0];

    void setArgs(Object ... objectArray) {
        if (objectArray.length == 0) {
            objectArray = null;
        }
        this.args = objectArray;
    }

    public void asyncExec(boolean bl, Object ... objectArray) {
        this.setArgs(objectArray);
        this.asyncSend(bl);
    }

    public Object syncExec(boolean bl, Object ... objectArray) {
        this.setArgs(objectArray);
        return this.syncSend(bl);
    }

    protected Object runCommand() throws Exception {
        return this.run(this.args == null ? EMPTY_ARGS : this.args);
    }

    public abstract Object run(Object[] var1) throws Exception;

    @Override
    public String toString() {
        String string = super.toString();
        if (this.args == null || this.args.length == 0) {
            return string + "()";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string).append('(');
        for (int i = 0; i < this.args.length; ++i) {
            Object object = this.args[i];
            if (i > 0) {
                stringBuilder.append(", ");
            }
            if (object != null && object.getClass().isArray()) {
                stringBuilder.append(Utils.arrayDeepToString(object));
                continue;
            }
            stringBuilder.append(object);
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

