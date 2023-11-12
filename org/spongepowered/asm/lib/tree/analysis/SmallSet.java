/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree.analysis;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

class SmallSet
extends AbstractSet
implements Iterator {
    Object e1;
    Object e2;

    static final Set emptySet() {
        return new SmallSet(null, null);
    }

    SmallSet(Object object, Object object2) {
        this.e1 = object;
        this.e2 = object2;
    }

    public Iterator iterator() {
        return new SmallSet(this.e1, this.e2);
    }

    public int size() {
        return this.e1 == null ? 0 : (this.e2 == null ? 1 : 2);
    }

    public boolean hasNext() {
        return this.e1 != null;
    }

    public Object next() {
        if (this.e1 == null) {
            throw new NoSuchElementException();
        }
        Object object = this.e1;
        this.e1 = this.e2;
        this.e2 = null;
        return object;
    }

    public void remove() {
    }

    Set union(SmallSet smallSet) {
        if (smallSet.e1 == this.e1 && smallSet.e2 == this.e2 || smallSet.e1 == this.e2 && smallSet.e2 == this.e1) {
            return this;
        }
        if (smallSet.e1 == null) {
            return this;
        }
        if (this.e1 == null) {
            return smallSet;
        }
        if (smallSet.e2 == null) {
            if (this.e2 == null) {
                return new SmallSet(this.e1, smallSet.e1);
            }
            if (smallSet.e1 == this.e1 || smallSet.e1 == this.e2) {
                return this;
            }
        }
        if (this.e2 == null && (this.e1 == smallSet.e1 || this.e1 == smallSet.e2)) {
            return smallSet;
        }
        HashSet<Object> hashSet = new HashSet<Object>(4);
        hashSet.add(this.e1);
        if (this.e2 != null) {
            hashSet.add(this.e2);
        }
        hashSet.add(smallSet.e1);
        if (smallSet.e2 != null) {
            hashSet.add(smallSet.e2);
        }
        return hashSet;
    }
}

