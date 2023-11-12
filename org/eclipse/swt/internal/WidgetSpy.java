/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.swt.widgets.Widget;

public class WidgetSpy {
    public static boolean isEnabled;
    private static final WidgetSpy instance;
    private WidgetTracker widgetTracker;

    private WidgetSpy() {
    }

    public static WidgetSpy getInstance() {
        return instance;
    }

    public void setWidgetTracker(WidgetTracker widgetTracker) {
        isEnabled = widgetTracker != null;
        this.widgetTracker = widgetTracker;
    }

    public void widgetCreated(Widget widget) {
        if (this.widgetTracker != null) {
            this.widgetTracker.widgetCreated(widget);
        }
    }

    public void widgetDisposed(Widget widget) {
        if (this.widgetTracker != null) {
            this.widgetTracker.widgetDisposed(widget);
        }
    }

    static {
        instance = new WidgetSpy();
    }

    public static class NonDisposedWidgetTracker
    implements WidgetTracker {
        private final Map nonDisposedWidgets = new LinkedHashMap();
        private final Set trackedTypes = new HashSet();

        @Override
        public void widgetCreated(Widget widget) {
            boolean bl = this.isTracked(widget);
            if (bl) {
                Error error = new Error("Created widget of type: " + widget.getClass().getSimpleName());
                this.nonDisposedWidgets.put(widget, error);
            }
        }

        @Override
        public void widgetDisposed(Widget widget) {
            boolean bl = this.isTracked(widget);
            if (bl) {
                this.nonDisposedWidgets.remove(widget);
            }
        }

        public Map getNonDisposedWidgets() {
            return Collections.unmodifiableMap(this.nonDisposedWidgets);
        }

        public void startTracking() {
            this.clearNonDisposedWidgets();
            WidgetSpy.getInstance().setWidgetTracker(this);
        }

        private void clearNonDisposedWidgets() {
            this.nonDisposedWidgets.clear();
        }

        public void stopTracking() {
            WidgetSpy.getInstance().setWidgetTracker(null);
        }

        public void setTrackingEnabled(boolean bl) {
            if (bl) {
                this.startTracking();
            } else {
                this.stopTracking();
            }
        }

        public void setTrackedTypes(List list) {
            this.trackedTypes.clear();
            this.trackedTypes.addAll(list);
        }

        private boolean isTracked(Widget widget) {
            boolean bl = this.trackedTypes.isEmpty();
            if (bl) {
                return true;
            }
            if (widget != null) {
                Class<?> clazz = widget.getClass();
                if (this.trackedTypes.contains(clazz)) {
                    return true;
                }
                for (Class clazz2 : this.trackedTypes) {
                    if (!clazz2.isAssignableFrom(clazz)) continue;
                    return true;
                }
            }
            return false;
        }
    }

    public static interface WidgetTracker {
        default public void widgetCreated(Widget widget) {
        }

        default public void widgetDisposed(Widget widget) {
        }
    }
}

