/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests.xml;

import java.util.ArrayList;
import org.newdawn.slick.tests.xml.Item;

public class Inventory {
    private ArrayList items = new ArrayList();

    private void add(Item item) {
        this.items.add(item);
    }

    public void dump(String string) {
        System.out.println(string + "Inventory");
        for (int i = 0; i < this.items.size(); ++i) {
            ((Item)this.items.get(i)).dump(string + "\t");
        }
    }
}

