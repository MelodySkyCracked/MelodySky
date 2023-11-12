/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.util.pathfinding.navmesh.Link;
import org.newdawn.slick.util.pathfinding.navmesh.NavPath;

public class Space {
    private float x;
    private float y;
    private float width;
    private float height;
    private HashMap links = new HashMap();
    private ArrayList linksList = new ArrayList();
    private float cost;

    public Space(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    /*
     * Exception decompiling
     */
    public void link(Space var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl99 : ALOAD_0 - null : trying to set 6 previously set to 8
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public Space merge(Space space) {
        float f = Math.min(this.x, space.x);
        float f2 = Math.min(this.y, space.y);
        float f3 = this.width + space.width;
        float f4 = this.height + space.height;
        if (this.x == space.x) {
            f3 = this.width;
        } else {
            f4 = this.height;
        }
        return new Space(f, f2, f3, f4);
    }

    public boolean canMerge(Space space) {
        Space space2 = this;
        if (space == false) {
            return false;
        }
        if (this.x == space.x && this.width == space.width) {
            return true;
        }
        return this.y == space.y && this.height == space.height;
    }

    public int getLinkCount() {
        return this.linksList.size();
    }

    public Link getLink(int n) {
        return (Link)this.linksList.get(n);
    }

    public boolean contains(float f, float f2) {
        return f >= this.x && f < this.x + this.width && f2 >= this.y && f2 < this.y + this.height;
    }

    public void fill(Space space, float f, float f2, float f3) {
        if (f3 >= this.cost) {
            return;
        }
        this.cost = f3;
        if (space == this) {
            return;
        }
        for (int i = 0; i < this.getLinkCount(); ++i) {
            Link link = this.getLink(i);
            float f4 = link.distance2(f, f2);
            float f5 = f3 + f4;
            link.getTarget().fill(space, link.getX(), link.getY(), f5);
        }
    }

    public void clearCost() {
        this.cost = Float.MAX_VALUE;
    }

    public float getCost() {
        return this.cost;
    }

    public boolean pickLowestCost(Space space, NavPath navPath) {
        if (space == this) {
            return true;
        }
        if (this.links.size() == 0) {
            return false;
        }
        Link link = null;
        for (int i = 0; i < this.getLinkCount(); ++i) {
            Link link2 = this.getLink(i);
            if (link != null && !(link2.getTarget().getCost() < link.getTarget().getCost())) continue;
            link = link2;
        }
        navPath.push(link);
        return link.getTarget().pickLowestCost(space, navPath);
    }

    public String toString() {
        return "[Space " + this.x + "," + this.y + " " + this.width + "," + this.height + "]";
    }
}

