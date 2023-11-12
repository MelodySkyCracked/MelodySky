/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing;

import chrriis.dj.nativeswing.I;
import chrriis.dj.nativeswing.ll;
import java.security.AccessController;

public enum NSSystemProperty {
    LOCALHOSTADDRESS("nativeswing.localhostAddress", Type.READ_WRITE),
    LOCALHOSTADDRESS_DEBUG_PRINTDETECTION("nativeswing.localhostAddress.debug.printDetection", Type.READ_WRITE),
    LOCALHOSTADDRESS_DEBUG_PRINT("nativeswing.localhostAddress.debug.print", Type.READ_WRITE),
    WEBSERVER_DEBUG_PRINTPORT("nativeswing.webserver.debug.printPort", Type.READ_WRITE),
    WEBSERVER_DEBUG_PRINTREQUESTS("nativeswing.webserver.debug.printRequests", Type.READ_WRITE),
    WEBSERVER_DEBUG_PRINTDATA("nativeswing.webserver.debug.printData", Type.READ_WRITE),
    WEBSERVER_ACTIVATEOLDRESOURCEMETHOD("nativeswing.webserver.activateOldResourceMethod", Type.READ_WRITE),
    COMPONENTS_DEBUG_PRINTOPTIONS("nativeswing.components.debug.printOptions", Type.READ_WRITE),
    COMPONENTS_DEBUG_PRINTSHAPECOMPUTING("nativeswing.components.debug.printShapeComputing", Type.READ_WRITE),
    COMPONENTS_FORCESINGLERECTANGLESHAPES("nativeswing.components.forceSingleRectangleShapes", Type.READ_WRITE),
    INTEGRATION_ACTIVE("nativeswing.integration.active", Type.READ_WRITE),
    DEPENDENCIES_CHECKVERSIONS("nativeswing.dependencies.checkVersions", Type.READ_WRITE),
    JNA_FORCE_HW_POPUP("jna.force_hw_popups", Type.READ_WRITE),
    DEPLOYMENT_TYPE("nativeswing.deployment.type", Type.READ_WRITE),
    INTEGRATION_USEDEFAULTCLIPPING("nativeswing.integration.useDefaultClipping", Type.READ_WRITE);

    private final String _name;
    private final boolean _readOnly;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private NSSystemProperty() {
        this((String)var1_-1, (int)var2_-1, (String)var3_1, Type.READ_ONLY);
        void var3_1;
        void var2_-1;
        void var1_-1;
    }

    /*
     * WARNING - void declaration
     */
    private NSSystemProperty() {
        void var4_2;
        String string3;
        if (string3 == null) {
            throw new NullPointerException("name");
        }
        if ("".equals(string3 = string3.trim())) {
            throw new IllegalArgumentException();
        }
        this._name = string3;
        this._readOnly = var4_2 == Type.READ_ONLY;
    }

    public String get() {
        return this.get(null);
    }

    public String get(String string) {
        return (String)AccessController.doPrivileged(new ll(this, string));
    }

    public String set(String string) {
        if (this.isReadOnly()) {
            throw new UnsupportedOperationException(this.getName() + " is a read-only property");
        }
        return (String)AccessController.doPrivileged(new I(this, string));
    }

    public String getName() {
        return this._name;
    }

    public boolean isReadOnly() {
        return this._readOnly;
    }

    public String toString() {
        return this.get();
    }

    public String toDebugString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name()).append(": ");
        stringBuilder.append(this.getName()).append("=");
        stringBuilder.append(this.get());
        if (this.isReadOnly()) {
            stringBuilder.append(" (read-only)");
        }
        return stringBuilder.toString();
    }

    private static enum Type {
        READ_WRITE,
        READ_ONLY;

    }
}

