/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object;

public final class Packer {
    private volatile Object packingItem;

    public Packer() {
    }

    public Packer(Object object) {
        this.packingItem = object;
    }

    public Object getPackingItem() {
        return this.packingItem;
    }

    public void setPackingItem(Object object) {
        this.packingItem = object;
    }
}

