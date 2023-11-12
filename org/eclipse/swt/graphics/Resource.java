/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.util.function.Consumer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;

public abstract class Resource {
    Device device;
    private static Consumer nonDisposedReporter;
    private ResourceTracker tracker;

    public Resource() {
        this.initNonDisposeTracking();
    }

    Resource(Device device) {
        if (device == null) {
            device = Device.getDevice();
        }
        if (device == null) {
            SWT.error(4);
        }
        this.device = device;
        this.initNonDisposeTracking();
    }

    void destroy() {
    }

    public void dispose() {
        if (this.device == null) {
            return;
        }
        if (this.device.isDisposed()) {
            return;
        }
        this.destroy();
        if (this.device.tracking) {
            this.device.dispose_Object(this);
        }
        this.device = null;
    }

    public Device getDevice() {
        Device device = this.device;
        if (device == null || this.isDisposed()) {
            SWT.error(44);
        }
        return device;
    }

    void ignoreNonDisposed() {
        if (this.tracker != null) {
            this.tracker.ignoreMe = true;
        }
    }

    void init() {
        if (this.device.tracking) {
            this.device.new_Object(this);
        }
    }

    void initNonDisposeTracking() {
        if (this instanceof Color) {
            return;
        }
        if (nonDisposedReporter == null) {
            return;
        }
        Error error = new Error("SWT Resource was not properly disposed");
        this.tracker = new ResourceTracker(this, error);
    }

    public abstract boolean isDisposed();

    public static void setNonDisposeHandler(Consumer consumer) {
        nonDisposedReporter = consumer;
    }

    private static void lambda$static$0(Error error) {
        if (error != null) {
            error.printStackTrace();
        } else {
            System.err.println("SWT Resource was not properly disposed");
        }
    }

    static Consumer access$000() {
        return nonDisposedReporter;
    }

    static {
        boolean bl = Boolean.getBoolean("org.eclipse.swt.graphics.Resource.reportNonDisposed");
        if (bl) {
            Resource.setNonDisposeHandler(Resource::lambda$static$0);
        }
    }

    private static class ResourceTracker {
        private Resource resource;
        private Error allocationStack;
        boolean ignoreMe;

        ResourceTracker(Resource resource, Error error) {
            this.resource = resource;
            this.allocationStack = error;
        }

        protected void finalize() {
            if (this.ignoreMe) {
                return;
            }
            if (Resource.access$000() == null) {
                return;
            }
            if (!this.resource.isDisposed()) {
                Resource.access$000().accept(this.allocationStack);
            }
        }
    }
}

