/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.mixin.injection.modify.InvalidImplicitDiscriminatorException;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.util.ASMHelper;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class LocalVariableDiscriminator {
    private final boolean argsOnly;
    private final int ordinal;
    private final int index;
    private final Set names;

    public LocalVariableDiscriminator(boolean bl, int n, int n2, Set set) {
        this.argsOnly = bl;
        this.ordinal = n;
        this.index = n2;
        this.names = Collections.unmodifiableSet(set);
    }

    public boolean isArgsOnly() {
        return this.argsOnly;
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    public int getIndex() {
        return this.index;
    }

    public Set getNames() {
        return this.names;
    }

    public boolean hasNames() {
        return !this.names.isEmpty();
    }

    public int findLocal(Type type, boolean bl, Target target, AbstractInsnNode abstractInsnNode) {
        try {
            return this.findLocal(new Context(type, bl, target, abstractInsnNode));
        }
        catch (InvalidImplicitDiscriminatorException invalidImplicitDiscriminatorException) {
            return -1;
        }
    }

    public int findLocal(Context context) {
        LocalVariableDiscriminator localVariableDiscriminator = this;
        if (context < 0) {
            return this.findImplicitLocal(context);
        }
        return this.findExplicitLocal(context);
    }

    private int findImplicitLocal(Context context) {
        int n = 0;
        int n2 = 0;
        for (int i = context.baseArgIndex; i < context.locals.length; ++i) {
            Context.Local local = context.locals[i];
            if (local == null || !local.type.equals(context.returnType)) continue;
            ++n2;
            n = i;
        }
        if (n2 == 1) {
            return n;
        }
        throw new InvalidImplicitDiscriminatorException("Found " + n2 + " candidate variables but exactly 1 is required.");
    }

    private int findExplicitLocal(Context context) {
        for (int i = context.baseArgIndex; i < context.locals.length; ++i) {
            Context.Local local = context.locals[i];
            if (local == null || !local.type.equals(context.returnType) || !(this.ordinal > -1 ? this.ordinal == local.ord : (this.index >= context.baseArgIndex ? this.index == i : this.names.contains(local.name)))) continue;
            return i;
        }
        return -1;
    }

    public static LocalVariableDiscriminator parse(AnnotationNode annotationNode) {
        boolean bl = (Boolean)ASMHelper.getAnnotationValue(annotationNode, "argsOnly", Boolean.FALSE);
        int n = (Integer)ASMHelper.getAnnotationValue(annotationNode, "ordinal", -1);
        int n2 = (Integer)ASMHelper.getAnnotationValue(annotationNode, "index", -1);
        HashSet hashSet = new HashSet();
        List list = (List)ASMHelper.getAnnotationValue(annotationNode, "name", (Object)null);
        if (list != null) {
            hashSet.addAll(list);
        }
        return new LocalVariableDiscriminator(bl, n, n2, hashSet);
    }

    public static class Context
    implements PrettyPrinter.IPrettyPrintable {
        final Target target;
        final Type returnType;
        final AbstractInsnNode node;
        final int baseArgIndex;
        final Local[] locals;
        private final boolean isStatic;

        public Context(Type type, boolean bl, Target target, AbstractInsnNode abstractInsnNode) {
            this.isStatic = ASMHelper.methodIsStatic(target.method);
            this.returnType = type;
            this.target = target;
            this.node = abstractInsnNode;
            this.baseArgIndex = this.isStatic ? 0 : 1;
            this.locals = this.initLocals(target, bl, abstractInsnNode);
            this.initOrdinals();
        }

        private Local[] initLocals(Target target, boolean bl, AbstractInsnNode abstractInsnNode) {
            Object[] objectArray;
            if (!bl && (objectArray = Locals.getLocalsAt(target.classNode, target.method, abstractInsnNode)) != null) {
                Local[] localArray = new Local[objectArray.length];
                for (int i = 0; i < objectArray.length; ++i) {
                    if (objectArray[i] == null) continue;
                    localArray[i] = new Local(this, ((LocalVariableNode)objectArray[i]).name, Type.getType(((LocalVariableNode)objectArray[i]).desc));
                }
                return localArray;
            }
            objectArray = new Local[this.baseArgIndex + target.arguments.length];
            if (!this.isStatic) {
                objectArray[0] = new Local(this, "this", Type.getType(target.classNode.name));
            }
            for (int i = this.baseArgIndex; i < objectArray.length; ++i) {
                Type type = target.arguments[i - this.baseArgIndex];
                objectArray[i] = new Local(this, "arg" + i, type);
            }
            return objectArray;
        }

        private void initOrdinals() {
            HashMap<Type, Integer> hashMap = new HashMap<Type, Integer>();
            for (int i = 0; i < this.locals.length; ++i) {
                Integer n = 0;
                if (this.locals[i] == null) continue;
                n = (Integer)hashMap.get(this.locals[i].type);
                n = n == null ? 0 : n + 1;
                hashMap.put(this.locals[i].type, n);
                this.locals[i].ord = n;
            }
        }

        @Override
        public void print(PrettyPrinter prettyPrinter) {
            prettyPrinter.add("%5s  %7s  %30s  %-50s  %s", "INDEX", "ORDINAL", "TYPE", "NAME", "CANDIDATE");
            for (int i = this.baseArgIndex; i < this.locals.length; ++i) {
                Object object;
                Local local = this.locals[i];
                if (local != null) {
                    object = local.type;
                    String string = local.name;
                    int n = local.ord;
                    String string2 = this.returnType.equals(object) ? "YES" : "-";
                    prettyPrinter.add("[%3d]    [%3d]  %30s  %-50s  %s", i, n, SignaturePrinter.getTypeName((Type)object, false), string, string2);
                    continue;
                }
                if (i <= 0) continue;
                object = this.locals[i - 1];
                boolean bl = object != null && ((Local)object).type != null && ((Local)object).type.getSize() > 1;
                prettyPrinter.add("[%3d]           %30s", i, bl ? "<top>" : "-");
            }
        }

        public class Local {
            int ord;
            String name;
            Type type;
            final Context this$0;

            public Local(Context context, String string, Type type) {
                this.this$0 = context;
                this.ord = 0;
                this.name = string;
                this.type = type;
            }

            public String toString() {
                return String.format("Local[ordinal=%d, name=%s, type=%s]", this.ord, this.name, this.type);
            }
        }
    }
}

