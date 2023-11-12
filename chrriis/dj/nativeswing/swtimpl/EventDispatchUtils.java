/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.lIIl;
import javax.swing.SwingUtilities;

public class EventDispatchUtils {
    private EventDispatchUtils() {
    }

    public static void sleepWithEventDispatch(int n) {
        EventDispatchUtils.sleepWithEventDispatch(new lIIl(), n);
    }

    public static void sleepWithEventDispatch(Condition condition, int n) {
        boolean bl = SwingUtilities.isEventDispatchThread();
        long l2 = System.currentTimeMillis();
        while (!condition.getValue() && System.currentTimeMillis() - l2 <= (long)n) {
            if (bl) {
                new Message().syncSend(true);
                if (condition.getValue() || System.currentTimeMillis() - l2 > (long)n) {
                    return;
                }
            }
            try {
                Thread.sleep(50L);
            }
            catch (Exception exception) {
            }
        }
        return;
    }

    public static interface Condition {
        public boolean getValue();
    }
}

