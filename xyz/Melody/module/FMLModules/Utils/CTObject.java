/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.FMLModules.Utils;

public class CTObject {
    private String target;
    private String message;
    private boolean strip;

    public CTObject(String string, String string2, boolean bl) {
        this.target = string;
        this.message = string2;
        this.strip = bl;
    }

    public String getTarget() {
        return this.target;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isStrip() {
        return this.strip;
    }

    public void setMessage(String string) {
        this.message = string;
    }

    public void setTarget(String string) {
        this.target = string;
    }

    public void setStrip(boolean bl) {
        this.strip = bl;
    }

    public boolean equals(Object object) {
        if (object instanceof CTObject) {
            CTObject cTObject = (CTObject)object;
            return this.target.equals(cTObject.getTarget()) && this.message.equals(cTObject.getMessage()) && this.strip == cTObject.isStrip();
        }
        return super.equals(object);
    }
}

