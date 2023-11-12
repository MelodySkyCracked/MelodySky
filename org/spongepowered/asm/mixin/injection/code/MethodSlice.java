/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 */
package org.spongepowered.asm.mixin.injection.code;

import com.google.common.base.Strings;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.util.ASMHelper;

public class MethodSlice {
    private final InjectionInfo info;
    private final String id;
    private final InjectionPoint from;
    private final InjectionPoint to;
    private final String name;

    private MethodSlice(InjectionInfo injectionInfo, String string, InjectionPoint injectionPoint, InjectionPoint injectionPoint2) {
        if (injectionPoint == null && injectionPoint2 == null) {
            throw new InvalidSliceException(injectionInfo, String.format("%s is redundant. No 'from' or 'to' value specified", this));
        }
        this.info = injectionInfo;
        this.id = Strings.nullToEmpty((String)string);
        this.from = injectionPoint;
        this.to = injectionPoint2;
        this.name = MethodSlice.getSliceName(string);
    }

    public String getId() {
        return this.id;
    }

    public ReadOnlyInsnList getSlice(MethodNode methodNode) {
        int n;
        int n2 = methodNode.instructions.size() - 1;
        int n3 = this.find(methodNode, this.from, 0, this.name + "(from)");
        if (n3 > (n = this.find(methodNode, this.to, n2, this.name + "(to)"))) {
            throw new InvalidSliceException(this.info, String.format("%s is negative size. Range(%d -> %d)", this.describe(), n3, n));
        }
        if (n3 < 0 || n < 0 || n3 > n2 || n > n2) {
            throw new InjectionError("Unexpected critical error in " + this + ": out of bounds start=" + n3 + " end=" + n + " lim=" + n2);
        }
        if (n3 == 0 && n == n2) {
            return new ReadOnlyInsnList(methodNode.instructions);
        }
        return new InsnListSlice(methodNode.instructions, n3, n);
    }

    private int find(MethodNode methodNode, InjectionPoint injectionPoint, int n, String string) {
        if (injectionPoint == null) {
            return n;
        }
        LinkedList linkedList = new LinkedList();
        ReadOnlyInsnList readOnlyInsnList = new ReadOnlyInsnList(methodNode.instructions);
        boolean bl = injectionPoint.find(methodNode.desc, readOnlyInsnList, linkedList);
        InjectionPoint.Selector selector = injectionPoint.getSelector();
        if (linkedList.size() != 1 && selector == InjectionPoint.Selector.ONE) {
            throw new InvalidSliceException(this.info, String.format("%s requires 1 result but found %d", this.describe(string), linkedList.size()));
        }
        if (!bl) {
            return n;
        }
        return methodNode.instructions.indexOf(selector == InjectionPoint.Selector.FIRST ? (AbstractInsnNode)linkedList.getFirst() : (AbstractInsnNode)linkedList.getLast());
    }

    public String toString() {
        return this.describe();
    }

    private String describe() {
        return this.describe(this.name);
    }

    private String describe(String string) {
        return MethodSlice.describeSlice(string, this.info);
    }

    private static String describeSlice(String string, InjectionInfo injectionInfo) {
        String string2 = ASMHelper.getSimpleName(injectionInfo.getAnnotation());
        MethodNode methodNode = injectionInfo.getMethod();
        return String.format("%s->%s(%s)::%s%s", injectionInfo.getContext(), string2, string, methodNode.name, methodNode.desc);
    }

    private static String getSliceName(String string) {
        return String.format("@Slice[%s]", Strings.nullToEmpty((String)string));
    }

    public static MethodSlice parse(InjectionInfo injectionInfo, Slice slice) {
        String string = slice.id();
        At at = slice.from();
        At at2 = slice.to();
        InjectionPoint injectionPoint = at != null ? InjectionPoint.parse((SpecialMethodInfo)injectionInfo, at) : null;
        InjectionPoint injectionPoint2 = at2 != null ? InjectionPoint.parse((SpecialMethodInfo)injectionInfo, at2) : null;
        return new MethodSlice(injectionInfo, string, injectionPoint, injectionPoint2);
    }

    public static MethodSlice parse(InjectionInfo injectionInfo, AnnotationNode annotationNode) {
        String string = (String)ASMHelper.getAnnotationValue(annotationNode, "id");
        AnnotationNode annotationNode2 = (AnnotationNode)ASMHelper.getAnnotationValue(annotationNode, "from");
        AnnotationNode annotationNode3 = (AnnotationNode)ASMHelper.getAnnotationValue(annotationNode, "to");
        InjectionPoint injectionPoint = annotationNode2 != null ? InjectionPoint.parse((SpecialMethodInfo)injectionInfo, annotationNode2) : null;
        InjectionPoint injectionPoint2 = annotationNode3 != null ? InjectionPoint.parse((SpecialMethodInfo)injectionInfo, annotationNode3) : null;
        return new MethodSlice(injectionInfo, string, injectionPoint, injectionPoint2);
    }

    static final class InsnListSlice
    extends ReadOnlyInsnList {
        private final int start;
        private final int end;

        protected InsnListSlice(InsnList insnList, int n, int n2) {
            super(insnList);
            this.start = n;
            this.end = n2;
        }

        @Override
        public ListIterator iterator() {
            return this.iterator(0);
        }

        @Override
        public ListIterator iterator(int n) {
            return new SliceIterator(super.iterator(this.start + n), this.start, this.end, this.start + n);
        }

        @Override
        public AbstractInsnNode[] toArray() {
            AbstractInsnNode[] abstractInsnNodeArray = super.toArray();
            AbstractInsnNode[] abstractInsnNodeArray2 = new AbstractInsnNode[this.size()];
            System.arraycopy(abstractInsnNodeArray, this.start, abstractInsnNodeArray2, 0, abstractInsnNodeArray2.length);
            return abstractInsnNodeArray2;
        }

        @Override
        public int size() {
            return this.end - this.start + 1;
        }

        @Override
        public AbstractInsnNode getFirst() {
            return super.get(this.start);
        }

        @Override
        public AbstractInsnNode getLast() {
            return super.get(this.end);
        }

        @Override
        public AbstractInsnNode get(int n) {
            return super.get(this.start + n);
        }

        @Override
        public boolean contains(AbstractInsnNode abstractInsnNode) {
            for (AbstractInsnNode abstractInsnNode2 : this.toArray()) {
                if (abstractInsnNode2 != abstractInsnNode) continue;
                return true;
            }
            return false;
        }

        @Override
        public int indexOf(AbstractInsnNode abstractInsnNode) {
            int n = super.indexOf(abstractInsnNode);
            return n >= this.start && n <= this.end ? n - this.start : -1;
        }

        public int realIndexOf(AbstractInsnNode abstractInsnNode) {
            return super.indexOf(abstractInsnNode);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        static class SliceIterator
        implements ListIterator {
            private final ListIterator iter;
            private int start;
            private int end;
            private int index;

            public SliceIterator(ListIterator listIterator, int n, int n2, int n3) {
                this.iter = listIterator;
                this.start = n;
                this.end = n2;
                this.index = n3;
            }

            @Override
            public boolean hasNext() {
                return this.index <= this.end && this.iter.hasNext();
            }

            @Override
            public AbstractInsnNode next() {
                if (this.index > this.end) {
                    throw new NoSuchElementException();
                }
                ++this.index;
                return (AbstractInsnNode)this.iter.next();
            }

            @Override
            public boolean hasPrevious() {
                return this.index > this.start;
            }

            public AbstractInsnNode previous() {
                if (this.index <= this.start) {
                    throw new NoSuchElementException();
                }
                --this.index;
                return (AbstractInsnNode)this.iter.previous();
            }

            @Override
            public int nextIndex() {
                return this.index - this.start;
            }

            @Override
            public int previousIndex() {
                return this.index - this.start - 1;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Cannot remove insn from slice");
            }

            public void set(AbstractInsnNode abstractInsnNode) {
                throw new UnsupportedOperationException("Cannot set insn using slice");
            }

            public void add(AbstractInsnNode abstractInsnNode) {
                throw new UnsupportedOperationException("Cannot add insn using slice");
            }

            public void add(Object object) {
                this.add((AbstractInsnNode)object);
            }

            public void set(Object object) {
                this.set((AbstractInsnNode)object);
            }

            public Object previous() {
                return this.previous();
            }

            @Override
            public Object next() {
                return this.next();
            }
        }
    }
}

