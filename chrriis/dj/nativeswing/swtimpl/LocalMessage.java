/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

import chrriis.dj.nativeswing.swtimpl.CommandMessage;

public abstract class LocalMessage
extends CommandMessage {
    @Override
    protected Object runCommand() {
        try {
            return super.runCommand();
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public abstract Object run(Object[] var1);
}

