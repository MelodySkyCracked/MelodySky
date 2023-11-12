/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  org.apache.logging.log4j.LogManager
 */
package org.spongepowered.asm.mixin.injection;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal;
import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;
import org.spongepowered.asm.mixin.injection.points.AfterInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeFinalReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeStringInvoke;
import org.spongepowered.asm.mixin.injection.points.JumpInsnPoint;
import org.spongepowered.asm.mixin.injection.points.MethodHead;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.ASMHelper;

public abstract class InjectionPoint {
    private static Map types = new HashMap();
    private final String slice;
    private final Selector selector;

    protected InjectionPoint() {
        this("", Selector.DEFAULT);
    }

    protected InjectionPoint(InjectionPointData injectionPointData) {
        this(injectionPointData.getSlice(), injectionPointData.getSelector());
    }

    public InjectionPoint(String string, Selector selector) {
        this.slice = string;
        this.selector = selector;
    }

    public String getSlice() {
        return this.slice;
    }

    public Selector getSelector() {
        return this.selector;
    }

    public abstract boolean find(String var1, InsnList var2, Collection var3);

    public String toString() {
        return "InjectionPoint(" + this.getClass().getSimpleName() + ")";
    }

    protected static AbstractInsnNode nextNode(InsnList insnList, AbstractInsnNode abstractInsnNode) {
        int n = insnList.indexOf(abstractInsnNode) + 1;
        if (n > 0 && n < insnList.size()) {
            return insnList.get(n);
        }
        return abstractInsnNode;
    }

    public static InjectionPoint and(InjectionPoint ... injectionPointArray) {
        return new Intersection(injectionPointArray);
    }

    public static InjectionPoint or(InjectionPoint ... injectionPointArray) {
        return new Union(injectionPointArray);
    }

    public static InjectionPoint after(InjectionPoint injectionPoint) {
        return new Shift(injectionPoint, 1);
    }

    public static InjectionPoint before(InjectionPoint injectionPoint) {
        return new Shift(injectionPoint, -1);
    }

    public static InjectionPoint shift(InjectionPoint injectionPoint, int n) {
        return new Shift(injectionPoint, n);
    }

    public static List parse(SpecialMethodInfo specialMethodInfo, List list) {
        return InjectionPoint.parse(specialMethodInfo.getContext(), specialMethodInfo.getMethod(), specialMethodInfo.getAnnotation(), list);
    }

    public static List parse(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode, List list) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (AnnotationNode annotationNode2 : list) {
            InjectionPoint injectionPoint = InjectionPoint.parse(mixinTargetContext, methodNode, annotationNode, annotationNode2);
            if (injectionPoint == null) continue;
            builder.add((Object)injectionPoint);
        }
        return builder.build();
    }

    public static InjectionPoint parse(SpecialMethodInfo specialMethodInfo, At at) {
        return InjectionPoint.parse(specialMethodInfo.getContext(), specialMethodInfo.getMethod(), specialMethodInfo.getAnnotation(), at.value(), at.shift(), at.by(), Arrays.asList(at.args()), at.target(), at.slice(), at.ordinal(), at.opcode());
    }

    public static InjectionPoint parse(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode, At at) {
        return InjectionPoint.parse(mixinTargetContext, methodNode, annotationNode, at.value(), at.shift(), at.by(), Arrays.asList(at.args()), at.target(), at.slice(), at.ordinal(), at.opcode());
    }

    public static InjectionPoint parse(SpecialMethodInfo specialMethodInfo, AnnotationNode annotationNode) {
        return InjectionPoint.parse(specialMethodInfo.getContext(), specialMethodInfo.getMethod(), specialMethodInfo.getAnnotation(), annotationNode);
    }

    public static InjectionPoint parse(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode, AnnotationNode annotationNode2) {
        String string = (String)ASMHelper.getAnnotationValue(annotationNode2, "value");
        List list = (List)ASMHelper.getAnnotationValue(annotationNode2, "args");
        String string2 = (String)ASMHelper.getAnnotationValue(annotationNode2, "target", "");
        String string3 = (String)ASMHelper.getAnnotationValue(annotationNode2, "slice", "");
        At.Shift shift = (At.Shift)ASMHelper.getAnnotationValue(annotationNode2, "shift", At.Shift.class, At.Shift.NONE);
        int n = (Integer)ASMHelper.getAnnotationValue(annotationNode2, "by", 0);
        int n2 = (Integer)ASMHelper.getAnnotationValue(annotationNode2, "ordinal", -1);
        int n3 = (Integer)ASMHelper.getAnnotationValue(annotationNode2, "opcode", 0);
        if (list == null) {
            list = ImmutableList.of();
        }
        return InjectionPoint.parse(mixinTargetContext, methodNode, annotationNode, string, shift, n, list, string2, string3, n2, n3);
    }

    public static InjectionPoint parse(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode, String string, At.Shift shift, int n, List list, String string2, String string3, int n2, int n3) {
        InjectionPointData injectionPointData = new InjectionPointData(mixinTargetContext, methodNode, annotationNode, string, list, string2, string3, n2, n3);
        Class clazz = InjectionPoint.findClass(mixinTargetContext, injectionPointData);
        InjectionPoint injectionPoint = InjectionPoint.create(mixinTargetContext, injectionPointData, clazz);
        return InjectionPoint.shift(injectionPoint, shift, n);
    }

    private static Class findClass(MixinTargetContext mixinTargetContext, InjectionPointData injectionPointData) {
        String string = injectionPointData.getType();
        Class<?> clazz = (Class<?>)types.get(string);
        if (clazz == null) {
            if (string.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
                try {
                    clazz = Class.forName(string);
                    types.put(string, clazz);
                }
                catch (Exception exception) {
                    throw new InvalidInjectionException(mixinTargetContext, injectionPointData + " could not be loaded or is not a valid InjectionPoint", (Throwable)exception);
                }
            } else {
                throw new InvalidInjectionException((IReferenceMapperContext)mixinTargetContext, injectionPointData + " is not a valid injection point specifier");
            }
        }
        return clazz;
    }

    private static InjectionPoint create(MixinTargetContext mixinTargetContext, InjectionPointData injectionPointData, Class clazz) {
        Constructor constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor(InjectionPointData.class);
            constructor.setAccessible(true);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new InvalidInjectionException(mixinTargetContext, clazz.getName() + " must contain a constructor which accepts an InjectionPointData", (Throwable)noSuchMethodException);
        }
        InjectionPoint injectionPoint = null;
        try {
            injectionPoint = (InjectionPoint)constructor.newInstance(injectionPointData);
        }
        catch (Exception exception) {
            throw new InvalidInjectionException(mixinTargetContext, "Error whilst instancing injection point " + clazz.getName() + " for " + injectionPointData.getAt(), (Throwable)exception);
        }
        return injectionPoint;
    }

    private static InjectionPoint shift(InjectionPoint injectionPoint, At.Shift shift, int n) {
        if (injectionPoint != null) {
            if (shift == At.Shift.BEFORE) {
                return InjectionPoint.before(injectionPoint);
            }
            if (shift == At.Shift.AFTER) {
                return InjectionPoint.after(injectionPoint);
            }
            if (shift == At.Shift.BY) {
                return InjectionPoint.shift(injectionPoint, n);
            }
        }
        return injectionPoint;
    }

    public static void register(Class clazz) {
        AtCode atCode = clazz.getAnnotation(AtCode.class);
        if (atCode == null) {
            throw new IllegalArgumentException("Injection point class " + clazz + " is not annotated with @AtCode");
        }
        Class clazz2 = (Class)types.get(atCode.value());
        if (clazz2 != null && !clazz2.equals(clazz)) {
            LogManager.getLogger((String)"mixin").debug("Overriding InjectionPoint {} with {} (previously {})", new Object[]{atCode.value(), clazz.getName(), clazz2.getName()});
        }
        types.put(atCode.value(), clazz);
    }

    static {
        InjectionPoint.register(BeforeFieldAccess.class);
        InjectionPoint.register(BeforeInvoke.class);
        InjectionPoint.register(BeforeNew.class);
        InjectionPoint.register(BeforeReturn.class);
        InjectionPoint.register(BeforeStringInvoke.class);
        InjectionPoint.register(JumpInsnPoint.class);
        InjectionPoint.register(MethodHead.class);
        InjectionPoint.register(AfterInvoke.class);
        InjectionPoint.register(BeforeLoadLocal.class);
        InjectionPoint.register(AfterStoreLocal.class);
        InjectionPoint.register(BeforeFinalReturn.class);
    }

    static final class Shift
    extends InjectionPoint {
        private final InjectionPoint input;
        private final int shift;

        public Shift(InjectionPoint injectionPoint, int n) {
            if (injectionPoint == null) {
                throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
            }
            this.input = injectionPoint;
            this.shift = n;
        }

        @Override
        public String toString() {
            return "InjectionPoint(" + this.getClass().getSimpleName() + ")[" + this.input + "]";
        }

        @Override
        public boolean find(String string, InsnList insnList, Collection collection) {
            List list = collection instanceof List ? (List)collection : new ArrayList(collection);
            this.input.find(string, insnList, collection);
            for (int i = 0; i < list.size(); ++i) {
                list.set(i, insnList.get(insnList.indexOf((AbstractInsnNode)list.get(i)) + this.shift));
            }
            if (collection != list) {
                collection.clear();
                collection.addAll(list);
            }
            return collection.size() > 0;
        }
    }

    static final class Union
    extends CompositeInjectionPoint {
        public Union(InjectionPoint ... injectionPointArray) {
            super(injectionPointArray);
        }

        @Override
        public boolean find(String string, InsnList insnList, Collection collection) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (int i = 0; i < this.components.length; ++i) {
                this.components[i].find(string, insnList, linkedHashSet);
            }
            collection.addAll(linkedHashSet);
            return linkedHashSet.size() > 0;
        }
    }

    static final class Intersection
    extends CompositeInjectionPoint {
        public Intersection(InjectionPoint ... injectionPointArray) {
            super(injectionPointArray);
        }

        @Override
        public boolean find(String string, InsnList insnList, Collection collection) {
            boolean bl = false;
            ArrayList[] arrayListArray = (ArrayList[])Array.newInstance(ArrayList.class, this.components.length);
            for (int i = 0; i < this.components.length; ++i) {
                arrayListArray[i] = new ArrayList();
                this.components[i].find(string, insnList, arrayListArray[i]);
            }
            ArrayList arrayList = arrayListArray[0];
            for (int i = 0; i < arrayList.size(); ++i) {
                AbstractInsnNode abstractInsnNode = (AbstractInsnNode)arrayList.get(i);
                boolean bl2 = true;
                for (int j = 1; j < arrayListArray.length && arrayListArray[j].contains(abstractInsnNode); ++j) {
                }
                if (!bl2) continue;
                collection.add(abstractInsnNode);
                bl = true;
            }
            return bl;
        }
    }

    static abstract class CompositeInjectionPoint
    extends InjectionPoint {
        protected final InjectionPoint[] components;

        protected CompositeInjectionPoint(InjectionPoint ... injectionPointArray) {
            if (injectionPointArray == null || injectionPointArray.length < 2) {
                throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
            }
            this.components = injectionPointArray;
        }

        @Override
        public String toString() {
            return "CompositeInjectionPoint(" + this.getClass().getSimpleName() + ")[" + Joiner.on((char)',').join((Object[])this.components) + "]";
        }
    }

    public static enum Selector {
        FIRST,
        LAST,
        ONE;

        public static final Selector DEFAULT;

        static {
            DEFAULT = FIRST;
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface AtCode {
        public String value();
    }
}

