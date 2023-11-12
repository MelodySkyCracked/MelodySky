/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

public final class Handle {
    final int tag;
    final String owner;
    final String name;
    final String desc;

    public Handle(int n, String string, String string2, String string3) {
        this.tag = n;
        this.owner = string;
        this.name = string2;
        this.desc = string3;
    }

    public int getTag() {
        return this.tag;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Handle)) {
            return false;
        }
        Handle handle = (Handle)object;
        return this.tag == handle.tag && this.owner.equals(handle.owner) && this.name.equals(handle.name) && this.desc.equals(handle.desc);
    }

    public int hashCode() {
        return this.tag + this.owner.hashCode() * this.name.hashCode() * this.desc.hashCode();
    }

    public String toString() {
        return this.owner + '.' + this.name + this.desc + " (" + this.tag + ')';
    }
}

