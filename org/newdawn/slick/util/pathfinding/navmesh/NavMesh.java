/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;
import org.newdawn.slick.util.pathfinding.navmesh.Link;
import org.newdawn.slick.util.pathfinding.navmesh.NavPath;
import org.newdawn.slick.util.pathfinding.navmesh.Space;

public class NavMesh {
    private ArrayList spaces = new ArrayList();

    public NavMesh() {
    }

    public NavMesh(ArrayList arrayList) {
        this.spaces.addAll(arrayList);
    }

    public int getSpaceCount() {
        return this.spaces.size();
    }

    public Space getSpace(int n) {
        return (Space)this.spaces.get(n);
    }

    public void addSpace(Space space) {
        this.spaces.add(space);
    }

    public Space findSpace(float f, float f2) {
        for (int i = 0; i < this.spaces.size(); ++i) {
            Space space = this.getSpace(i);
            if (!space.contains(f, f2)) continue;
            return space;
        }
        return null;
    }

    public NavPath findPath(float f, float f2, float f3, float f4, boolean bl) {
        Space space = this.findSpace(f, f2);
        Space space2 = this.findSpace(f3, f4);
        if (space == null || space2 == null) {
            return null;
        }
        for (int i = 0; i < this.spaces.size(); ++i) {
            ((Space)this.spaces.get(i)).clearCost();
        }
        space2.fill(space, f3, f4, 0.0f);
        if (space2.getCost() == Float.MAX_VALUE) {
            return null;
        }
        if (space.getCost() == Float.MAX_VALUE) {
            return null;
        }
        NavPath navPath = new NavPath();
        navPath.push(new Link(f, f2, null));
        if (space.pickLowestCost(space2, navPath)) {
            navPath.push(new Link(f3, f4, null));
            if (bl) {
                this.optimize(navPath);
            }
            return navPath;
        }
        return null;
    }

    /*
     * Exception decompiling
     */
    private void optimize(NavPath var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl3 : ILOAD_2 - null : trying to set 4 previously set to 0
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
}

