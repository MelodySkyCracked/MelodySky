/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.util.ArrayList;
import java.util.HashSet;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.GeomUtil;
import org.newdawn.slick.geom.GeomUtilListener;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class GeomUtilTileTest
extends BasicGame
implements GeomUtilListener {
    private Shape source;
    private Shape cut;
    private Shape[] result;
    private GeomUtil util = new GeomUtil();
    private ArrayList original = new ArrayList();
    private ArrayList combined = new ArrayList();
    private ArrayList intersections = new ArrayList();
    private ArrayList used = new ArrayList();
    private ArrayList[][] quadSpace;
    private Shape[][] quadSpaceShapes;

    public GeomUtilTileTest() {
        super("GeomUtilTileTest");
    }

    /*
     * Exception decompiling
     */
    private void generateSpace(ArrayList var1, float var2, float var3, float var4, float var5, int var6) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl114 : ILOAD - null : trying to set 2 previously set to 0
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

    private void removeFromQuadSpace(Shape shape) {
        int n = this.quadSpace.length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                this.quadSpace[i][j].remove(shape);
            }
        }
    }

    /*
     * Exception decompiling
     */
    private void addToQuadSpace(Shape var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl12 : ILOAD - null : trying to set 2 previously set to 0
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

    public void init() {
        int n = 10;
        int[][] nArrayArray = new int[][]{{0, 0, 0, 0, 0, 0, 0, 3, 0, 0}, {0, 1, 1, 1, 0, 0, 1, 1, 1, 0}, {0, 1, 1, 0, 0, 0, 5, 1, 6, 0}, {0, 1, 2, 0, 0, 0, 4, 1, 1, 0}, {0, 1, 1, 0, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 3, 0, 1, 1, 0, 0}, {0, 0, 0, 1, 1, 0, 0, 0, 1, 0}, {0, 0, 0, 1, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        for (int i = 0; i < nArrayArray[0].length; ++i) {
            block9: for (int j = 0; j < nArrayArray.length; ++j) {
                if (nArrayArray[j][i] == 0) continue;
                switch (nArrayArray[j][i]) {
                    case 1: {
                        Polygon polygon = new Polygon();
                        polygon.addPoint(i * 32, j * 32);
                        polygon.addPoint(i * 32 + 32, j * 32);
                        polygon.addPoint(i * 32 + 32, j * 32 + 32);
                        polygon.addPoint(i * 32, j * 32 + 32);
                        this.original.add(polygon);
                        continue block9;
                    }
                    case 2: {
                        Polygon polygon = new Polygon();
                        polygon.addPoint(i * 32, j * 32);
                        polygon.addPoint(i * 32 + 32, j * 32);
                        polygon.addPoint(i * 32, j * 32 + 32);
                        this.original.add(polygon);
                        continue block9;
                    }
                    case 3: {
                        Circle circle = new Circle((float)(i * 32 + 16), (float)(j * 32 + 32), 16.0f, 16);
                        this.original.add(circle);
                        continue block9;
                    }
                    case 4: {
                        Polygon polygon = new Polygon();
                        polygon.addPoint(i * 32 + 32, j * 32);
                        polygon.addPoint(i * 32 + 32, j * 32 + 32);
                        polygon.addPoint(i * 32, j * 32 + 32);
                        this.original.add(polygon);
                        continue block9;
                    }
                    case 5: {
                        Polygon polygon = new Polygon();
                        polygon.addPoint(i * 32, j * 32);
                        polygon.addPoint(i * 32 + 32, j * 32);
                        polygon.addPoint(i * 32 + 32, j * 32 + 32);
                        this.original.add(polygon);
                        continue block9;
                    }
                    case 6: {
                        Polygon polygon = new Polygon();
                        polygon.addPoint(i * 32, j * 32);
                        polygon.addPoint(i * 32 + 32, j * 32);
                        polygon.addPoint(i * 32, j * 32 + 32);
                        this.original.add(polygon);
                    }
                }
            }
        }
        long l2 = System.currentTimeMillis();
        this.generateSpace(this.original, 0.0f, 0.0f, (n + 1) * 32, (n + 1) * 32, 8);
        this.combined = this.combineQuadSpace();
        long l3 = System.currentTimeMillis();
        System.out.println("Combine took: " + (l3 - l2));
        System.out.println("Combine result: " + this.combined.size());
    }

    private ArrayList combineQuadSpace() {
        int n;
        boolean bl = true;
        while (bl) {
            bl = false;
            for (int i = 0; i < this.quadSpace.length; ++i) {
                for (n = 0; n < this.quadSpace.length; ++n) {
                    ArrayList arrayList = this.quadSpace[i][n];
                    int n2 = arrayList.size();
                    this.combine(arrayList);
                    int n3 = arrayList.size();
                    bl |= n2 != n3;
                }
            }
        }
        HashSet hashSet = new HashSet();
        for (n = 0; n < this.quadSpace.length; ++n) {
            for (int i = 0; i < this.quadSpace.length; ++i) {
                hashSet.addAll(this.quadSpace[n][i]);
            }
        }
        return new ArrayList(hashSet);
    }

    private ArrayList combine(ArrayList arrayList) {
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = arrayList;
        boolean bl = true;
        while (arrayList3.size() != arrayList2.size() || bl) {
            bl = false;
            arrayList2 = arrayList3;
            arrayList3 = this.combineImpl(arrayList3);
        }
        ArrayList<Shape> arrayList4 = new ArrayList<Shape>();
        for (int i = 0; i < arrayList3.size(); ++i) {
            arrayList4.add(((Shape)arrayList3.get(i)).prune());
        }
        return arrayList4;
    }

    private ArrayList combineImpl(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList);
        if (this.quadSpace != null) {
            arrayList2 = arrayList;
        }
        for (int i = 0; i < arrayList.size(); ++i) {
            Shape shape = (Shape)arrayList.get(i);
            for (int j = i + 1; j < arrayList.size(); ++j) {
                Shape[] shapeArray;
                Shape shape2 = (Shape)arrayList.get(j);
                if (!shape.intersects(shape2) || (shapeArray = this.util.union(shape, shape2)).length != 1) continue;
                if (this.quadSpace != null) {
                    this.removeFromQuadSpace(shape);
                    this.removeFromQuadSpace(shape2);
                    this.addToQuadSpace(shapeArray[0]);
                } else {
                    arrayList2.remove(shape);
                    arrayList2.remove(shape2);
                    arrayList2.add(shapeArray[0]);
                }
                return arrayList2;
            }
        }
        return arrayList2;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.util.setListener(this);
        this.init();
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        Shape shape;
        int n;
        graphics.setColor(Color.green);
        for (n = 0; n < this.original.size(); ++n) {
            shape = (Shape)this.original.get(n);
            graphics.draw(shape);
        }
        graphics.setColor(Color.white);
        if (this.quadSpaceShapes != null) {
            graphics.draw(this.quadSpaceShapes[0][0]);
        }
        graphics.translate(0.0f, 320.0f);
        for (n = 0; n < this.combined.size(); ++n) {
            graphics.setColor(Color.white);
            shape = (Shape)this.combined.get(n);
            graphics.draw(shape);
            for (int i = 0; i < shape.getPointCount(); ++i) {
                graphics.setColor(Color.yellow);
                float[] fArray = shape.getPoint(i);
                graphics.fillOval(fArray[0] - 1.0f, fArray[1] - 1.0f, 3.0f, 3.0f);
            }
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new GeomUtilTileTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void pointExcluded(float f, float f2) {
    }

    @Override
    public void pointIntersected(float f, float f2) {
        this.intersections.add(new Vector2f(f, f2));
    }

    @Override
    public void pointUsed(float f, float f2) {
        this.used.add(new Vector2f(f, f2));
    }
}

