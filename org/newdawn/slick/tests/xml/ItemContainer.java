/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests.xml;

import java.util.ArrayList;
import org.newdawn.slick.tests.xml.Item;

public class ItemContainer
extends Item {
    private ArrayList items = new ArrayList();

    private void add(Item item) {
        this.items.add(item);
    }

    private void setName(String string) {
        this.name = string;
    }

    private void setCondition(int n) {
        this.condition = n;
    }

    @Override
    public void dump(String string) {
        System.out.println(string + "Item Container " + this.name + "," + this.condition);
        for (int i = 0; i < this.items.size(); ++i) {
            ((Item)this.items.get(i)).dump(string + "\t");
        }
    }
}

