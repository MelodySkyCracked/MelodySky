/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.newdawn.slick.util.pathfinding.AStarHeuristic;
import org.newdawn.slick.util.pathfinding.I;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.newdawn.slick.util.pathfinding.heuristics.ClosestHeuristic;

public class AStarPathFinder
implements PathFinder,
PathFindingContext {
    private ArrayList closed = new ArrayList();
    private PriorityList open = new PriorityList(this, null);
    private TileBasedMap map;
    private int maxSearchDistance;
    private Node[][] nodes;
    private boolean allowDiagMovement;
    private AStarHeuristic heuristic;
    private Node current;
    private Mover mover;
    private int sourceX;
    private int sourceY;
    private int distance;

    public AStarPathFinder(TileBasedMap tileBasedMap, int n, boolean bl) {
        this(tileBasedMap, n, bl, new ClosestHeuristic());
    }

    public AStarPathFinder(TileBasedMap tileBasedMap, int n, boolean bl, AStarHeuristic aStarHeuristic) {
        this.heuristic = aStarHeuristic;
        this.map = tileBasedMap;
        this.maxSearchDistance = n;
        this.allowDiagMovement = bl;
        this.nodes = new Node[tileBasedMap.getWidthInTiles()][tileBasedMap.getHeightInTiles()];
        for (int i = 0; i < tileBasedMap.getWidthInTiles(); ++i) {
            for (int j = 0; j < tileBasedMap.getHeightInTiles(); ++j) {
                this.nodes[i][j] = new Node(this, i, j);
            }
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public Path findPath(Mover var1, int var2, int var3, int var4, int var5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl275 : IINC - null : trying to set 10 previously set to 5
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

    public int getCurrentX() {
        if (this.current == null) {
            return -1;
        }
        return Node.access$400(this.current);
    }

    public int getCurrentY() {
        if (this.current == null) {
            return -1;
        }
        return Node.access$500(this.current);
    }

    protected Node getFirstInOpen() {
        return (Node)this.open.first();
    }

    protected void addToOpen(Node node) {
        node.setOpen(true);
        this.open.add(node);
    }

    protected boolean inOpenList(Node node) {
        return node.isOpen();
    }

    protected void removeFromOpen(Node node) {
        node.setOpen(false);
        this.open.remove(node);
    }

    protected void addToClosed(Node node) {
        node.setClosed(true);
        this.closed.add(node);
    }

    protected boolean inClosedList(Node node) {
        return node.isClosed();
    }

    protected void removeFromClosed(Node node) {
        node.setClosed(false);
        this.closed.remove(node);
    }

    public float getMovementCost(Mover mover, int n, int n2, int n3, int n4) {
        this.mover = mover;
        this.sourceX = n;
        this.sourceY = n2;
        return this.map.getCost(this, n3, n4);
    }

    public float getHeuristicCost(Mover mover, int n, int n2, int n3, int n4) {
        return this.heuristic.getCost(this.map, mover, n, n2, n3, n4);
    }

    @Override
    public Mover getMover() {
        return this.mover;
    }

    @Override
    public int getSearchDistance() {
        return this.distance;
    }

    @Override
    public int getSourceX() {
        return this.sourceX;
    }

    @Override
    public int getSourceY() {
        return this.sourceY;
    }

    private class Node
    implements Comparable {
        private int x;
        private int y;
        private float cost;
        private Node parent;
        private float heuristic;
        private int depth;
        private boolean open;
        private boolean closed;
        final AStarPathFinder this$0;

        public Node(AStarPathFinder aStarPathFinder, int n, int n2) {
            this.this$0 = aStarPathFinder;
            this.x = n;
            this.y = n2;
        }

        public int setParent(Node node) {
            this.depth = node.depth + 1;
            this.parent = node;
            return this.depth;
        }

        public int compareTo(Object object) {
            Node node = (Node)object;
            float f = this.heuristic + this.cost;
            float f2 = node.heuristic + node.cost;
            if (f < f2) {
                return -1;
            }
            if (f > f2) {
                return 1;
            }
            return 0;
        }

        public void setOpen(boolean bl) {
            this.open = bl;
        }

        public boolean isOpen() {
            return this.open;
        }

        public void setClosed(boolean bl) {
            this.closed = bl;
        }

        public boolean isClosed() {
            return this.closed;
        }

        public void reset() {
            this.closed = false;
            this.open = false;
            this.cost = 0.0f;
            this.depth = 0;
        }

        public String toString() {
            return "[Node " + this.x + "," + this.y + "]";
        }

        static float access$102(Node node, float f) {
            node.cost = f;
            return node.cost;
        }

        static int access$202(Node node, int n) {
            node.depth = n;
            return node.depth;
        }

        static Node access$302(Node node, Node node2) {
            node.parent = node2;
            return node.parent;
        }

        static int access$400(Node node) {
            return node.x;
        }

        static int access$500(Node node) {
            return node.y;
        }

        static int access$200(Node node) {
            return node.depth;
        }

        static float access$100(Node node) {
            return node.cost;
        }

        static float access$602(Node node, float f) {
            node.heuristic = f;
            return node.heuristic;
        }

        static Node access$300(Node node) {
            return node.parent;
        }
    }

    private class PriorityList {
        private List list;
        final AStarPathFinder this$0;

        private PriorityList(AStarPathFinder aStarPathFinder) {
            this.this$0 = aStarPathFinder;
            this.list = new LinkedList();
        }

        public Object first() {
            return this.list.get(0);
        }

        public void clear() {
            this.list.clear();
        }

        public void add(Object object) {
            for (int i = 0; i < this.list.size(); ++i) {
                if (((Comparable)this.list.get(i)).compareTo(object) <= 0) continue;
                this.list.add(i, object);
                break;
            }
            if (!this.list.contains(object)) {
                this.list.add(object);
            }
        }

        public void remove(Object object) {
            this.list.remove(object);
        }

        public int size() {
            return this.list.size();
        }

        public boolean contains(Object object) {
            return this.list.contains(object);
        }

        public String toString() {
            String string = "{";
            for (int i = 0; i < this.size(); ++i) {
                string = string + this.list.get(i).toString() + ",";
            }
            string = string + "}";
            return string;
        }

        PriorityList(AStarPathFinder aStarPathFinder, I i) {
            this(aStarPathFinder);
        }
    }
}

