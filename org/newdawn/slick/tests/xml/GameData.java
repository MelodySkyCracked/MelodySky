/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests.xml;

import java.util.ArrayList;
import org.newdawn.slick.tests.xml.Entity;

public class GameData {
    private ArrayList entities = new ArrayList();

    private void add(Entity entity) {
        this.entities.add(entity);
    }

    public void dump(String string) {
        System.out.println(string + "GameData");
        for (int i = 0; i < this.entities.size(); ++i) {
            ((Entity)this.entities.get(i)).dump(string + "\t");
        }
    }
}

