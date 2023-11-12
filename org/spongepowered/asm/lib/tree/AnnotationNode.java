/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;

public class AnnotationNode
extends AnnotationVisitor {
    public String desc;
    public List values;

    public AnnotationNode(String string) {
        this(327680, string);
        if (this.getClass() != AnnotationNode.class) {
            throw new IllegalStateException();
        }
    }

    public AnnotationNode(int n, String string) {
        super(n);
        this.desc = string;
    }

    AnnotationNode(List list) {
        super(327680);
        this.values = list;
    }

    public void visit(String string, Object object) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(string);
        }
        this.values.add(object);
    }

    public void visitEnum(String string, String string2, String string3) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(string);
        }
        this.values.add(new String[]{string2, string3});
    }

    public AnnotationVisitor visitAnnotation(String string, String string2) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(string);
        }
        AnnotationNode annotationNode = new AnnotationNode(string2);
        this.values.add(annotationNode);
        return annotationNode;
    }

    public AnnotationVisitor visitArray(String string) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(string);
        }
        ArrayList arrayList = new ArrayList();
        this.values.add(arrayList);
        return new AnnotationNode(arrayList);
    }

    public void visitEnd() {
    }

    public void check(int n) {
    }

    public void accept(AnnotationVisitor annotationVisitor) {
        if (annotationVisitor != null) {
            if (this.values != null) {
                for (int i = 0; i < this.values.size(); i += 2) {
                    String string = (String)this.values.get(i);
                    Object e = this.values.get(i + 1);
                    AnnotationNode.accept(annotationVisitor, string, e);
                }
            }
            annotationVisitor.visitEnd();
        }
    }

    static void accept(AnnotationVisitor annotationVisitor, String string, Object object) {
        if (annotationVisitor != null) {
            if (object instanceof String[]) {
                String[] stringArray = (String[])object;
                annotationVisitor.visitEnum(string, stringArray[0], stringArray[1]);
            } else if (object instanceof AnnotationNode) {
                AnnotationNode annotationNode = (AnnotationNode)object;
                annotationNode.accept(annotationVisitor.visitAnnotation(string, annotationNode.desc));
            } else if (object instanceof List) {
                AnnotationVisitor annotationVisitor2 = annotationVisitor.visitArray(string);
                List list = (List)object;
                for (int i = 0; i < list.size(); ++i) {
                    AnnotationNode.accept(annotationVisitor2, null, list.get(i));
                }
                annotationVisitor2.visitEnd();
            } else {
                annotationVisitor.visit(string, object);
            }
        }
    }
}

