/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.transformer.MethodMapper;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.mixin.transformer.TreeInfo;
import org.spongepowered.asm.util.ASMHelper;

public class ClassInfo
extends TreeInfo {
    public static final int INCLUDE_PRIVATE = 2;
    public static final int INCLUDE_STATIC = 8;
    public static final int INCLUDE_ALL = 10;
    private static final Logger logger = LogManager.getLogger((String)"mixin");
    private static final String JAVA_LANG_OBJECT = "java/lang/Object";
    private static final Map cache = new HashMap();
    private static final ClassInfo OBJECT = new ClassInfo();
    private final String name;
    private final String superName;
    private final String outerName;
    private final boolean isProbablyStatic;
    private final Set interfaces;
    private final Set methods;
    private final Set fields;
    private final Set mixins = new HashSet();
    private final Map correspondingTypes = new HashMap();
    private final MixinInfo mixin;
    private final MethodMapper methodMapper;
    private final boolean isMixin;
    private final boolean isInterface;
    private final int access;
    private ClassInfo superClass;
    private ClassInfo outerClass;

    private ClassInfo() {
        this.name = JAVA_LANG_OBJECT;
        this.superName = null;
        this.outerName = null;
        this.isProbablyStatic = true;
        this.methods = ImmutableSet.of((Object)new Method(this, "getClass", "()Ljava/lang/Class;"), (Object)new Method(this, "hashCode", "()I"), (Object)new Method(this, "equals", "(Ljava/lang/Object;)Z"), (Object)new Method(this, "clone", "()Ljava/lang/Object;"), (Object)new Method(this, "toString", "()Ljava/lang/String;"), (Object)new Method(this, "notify", "()V"), (Object[])new Method[]{new Method(this, "notifyAll", "()V"), new Method(this, "wait", "(J)V"), new Method(this, "wait", "(JI)V"), new Method(this, "wait", "()V"), new Method(this, "finalize", "()V")});
        this.fields = Collections.emptySet();
        this.isInterface = false;
        this.interfaces = Collections.emptySet();
        this.access = 1;
        this.isMixin = false;
        this.mixin = null;
        this.methodMapper = null;
    }

    private ClassInfo(ClassNode classNode) {
        Object object2;
        this.name = classNode.name;
        this.superName = classNode.superName != null ? classNode.superName : JAVA_LANG_OBJECT;
        this.methods = new HashSet();
        this.fields = new HashSet();
        this.isInterface = (classNode.access & 0x200) != 0;
        this.interfaces = new HashSet();
        this.access = classNode.access;
        this.isMixin = classNode instanceof MixinInfo.MixinClassNode;
        this.mixin = this.isMixin ? ((MixinInfo.MixinClassNode)classNode).getMixin() : null;
        this.interfaces.addAll(classNode.interfaces);
        for (Object object2 : classNode.methods) {
            this.addMethod((MethodNode)object2, this.isMixin);
        }
        boolean bl = true;
        object2 = classNode.outerClass;
        if (object2 == null) {
            for (FieldNode fieldNode : classNode.fields) {
                if ((fieldNode.access & 0x1000) != 0 && fieldNode.name.startsWith("this$")) {
                    bl = false;
                    object2 = fieldNode.desc;
                    if (((String)object2).startsWith("L")) {
                        object2 = ((String)object2).substring(1, ((String)object2).length() - 1);
                    }
                }
                this.fields.add(new Field(this, fieldNode, this.isMixin));
            }
        }
        this.isProbablyStatic = bl;
        this.outerName = object2;
        this.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), this);
    }

    void addInterface(String string) {
        this.interfaces.add(string);
    }

    void addMethod(MethodNode methodNode) {
        this.addMethod(methodNode, true);
    }

    private void addMethod(MethodNode methodNode, boolean bl) {
        if (!methodNode.name.startsWith("<")) {
            this.methods.add(new Method(this, methodNode, bl));
        }
    }

    void addMixin(MixinInfo mixinInfo) {
        if (this.isMixin) {
            throw new IllegalArgumentException("Cannot add target " + this.name + " for " + mixinInfo.getClassName() + " because the target is a mixin");
        }
        this.mixins.add(mixinInfo);
    }

    public Set getMixins() {
        return Collections.unmodifiableSet(this.mixins);
    }

    public boolean isMixin() {
        return this.isMixin;
    }

    public boolean isPublic() {
        return (this.access & 1) != 0;
    }

    public boolean isAbstract() {
        return (this.access & 0x400) != 0;
    }

    public boolean isSynthetic() {
        return (this.access & 0x1000) != 0;
    }

    public boolean isProbablyStatic() {
        return this.isProbablyStatic;
    }

    public boolean isInner() {
        return this.outerName != null;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public Set getInterfaces() {
        return Collections.unmodifiableSet(this.interfaces);
    }

    public String toString() {
        return this.name;
    }

    public MethodMapper getMethodMapper() {
        return this.methodMapper;
    }

    public int getAccess() {
        return this.access;
    }

    public String getName() {
        return this.name;
    }

    public String getClassName() {
        return this.name.replace('/', '.');
    }

    public String getSuperName() {
        return this.superName;
    }

    public ClassInfo getSuperClass() {
        if (this.superClass == null && this.superName != null) {
            this.superClass = ClassInfo.forName(this.superName);
        }
        return this.superClass;
    }

    public String getOuterName() {
        return this.outerName;
    }

    public ClassInfo getOuterClass() {
        if (this.outerClass == null && this.outerName != null) {
            this.outerClass = ClassInfo.forName(this.outerName);
        }
        return this.outerClass;
    }

    List getTargets() {
        if (this.mixin != null) {
            ArrayList<ClassInfo> arrayList = new ArrayList<ClassInfo>();
            arrayList.add(this);
            arrayList.addAll(this.mixin.getTargets());
            return arrayList;
        }
        return ImmutableList.of((Object)this);
    }

    public Set getMethods() {
        return Collections.unmodifiableSet(this.methods);
    }

    public Set getInterfaceMethods(boolean bl) {
        HashSet hashSet = new HashSet();
        if (!this.isInterface) {
            for (ClassInfo classInfo = this.addMethodsRecursive(hashSet, bl); classInfo != null && classInfo != OBJECT; classInfo = classInfo.addMethodsRecursive(hashSet, bl)) {
            }
        }
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            if (((Method)iterator.next()).isAbstract()) continue;
            iterator.remove();
        }
        return Collections.unmodifiableSet(hashSet);
    }

    private ClassInfo addMethodsRecursive(Set set, boolean bl) {
        if (this.isInterface) {
            for (Object object : this.methods) {
                if (!((Method)object).isAbstract()) {
                    set.remove(object);
                }
                set.add(object);
            }
        } else if (!this.isMixin && bl) {
            for (Object object : this.mixins) {
                ((MixinInfo)object).getClassInfo().addMethodsRecursive(set, bl);
            }
        }
        for (Object object : this.interfaces) {
            ClassInfo.forName((String)object).addMethodsRecursive(set, bl);
        }
        return this.getSuperClass();
    }

    public boolean hasSuperClass(String string) {
        return this.hasSuperClass(string, Traversal.NONE);
    }

    public boolean hasSuperClass(String string, Traversal traversal) {
        if (JAVA_LANG_OBJECT.equals(string)) {
            return true;
        }
        return this.findSuperClass(string, traversal) != null;
    }

    public boolean hasSuperClass(ClassInfo classInfo) {
        return this.hasSuperClass(classInfo, Traversal.NONE);
    }

    public boolean hasSuperClass(ClassInfo classInfo, Traversal traversal) {
        if (OBJECT == classInfo) {
            return true;
        }
        return this.findSuperClass(classInfo.name, traversal) != null;
    }

    public ClassInfo findSuperClass(String string) {
        return this.findSuperClass(string, Traversal.NONE);
    }

    public ClassInfo findSuperClass(String string, Traversal traversal) {
        if (ClassInfo.OBJECT.name == string) {
            return null;
        }
        return this.findSuperClass(string, traversal, new HashSet());
    }

    private ClassInfo findSuperClass(String string, Traversal traversal, Set set) {
        ClassInfo classInfo;
        Object object;
        ClassInfo classInfo2 = this.getSuperClass();
        if (classInfo2 != null) {
            List list = classInfo2.getTargets();
            Object object2 = list.iterator();
            while (object2.hasNext()) {
                object = (ClassInfo)object2.next();
                if (string.equals(((ClassInfo)object).getName())) {
                    return classInfo2;
                }
                classInfo = super.findSuperClass(string, traversal.next(), set);
                if (classInfo == null) continue;
                return classInfo;
            }
        }
        if (traversal.canTraverse()) {
            for (Object object2 : this.mixins) {
                object = ((MixinInfo)object2).getClassName();
                if (set.contains(object)) continue;
                set.add(object);
                classInfo = ((MixinInfo)object2).getClassInfo();
                if (string.equals(classInfo.getName())) {
                    return classInfo;
                }
                ClassInfo classInfo3 = classInfo.findSuperClass(string, Traversal.ALL, set);
                if (classInfo3 == null) continue;
                return classInfo3;
            }
        }
        return null;
    }

    ClassInfo findCorrespondingType(ClassInfo classInfo) {
        if (classInfo == null || !classInfo.isMixin || this.isMixin) {
            return null;
        }
        ClassInfo classInfo2 = (ClassInfo)this.correspondingTypes.get(classInfo);
        if (classInfo2 == null) {
            classInfo2 = this.findSuperTypeForMixin(classInfo);
            this.correspondingTypes.put(classInfo, classInfo2);
        }
        return classInfo2;
    }

    /*
     * Exception decompiling
     */
    private ClassInfo findSuperTypeForMixin(ClassInfo var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl12 : ALOAD_3 - null : trying to set 1 previously set to 0
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

    public boolean hasMixinInHierarchy() {
        if (!this.isMixin) {
            return false;
        }
        for (ClassInfo classInfo = this.getSuperClass(); classInfo != null && classInfo != OBJECT; classInfo = classInfo.getSuperClass()) {
            if (!classInfo.isMixin) continue;
            return true;
        }
        return false;
    }

    public boolean hasMixinTargetInHierarchy() {
        if (this.isMixin) {
            return false;
        }
        for (ClassInfo classInfo = this.getSuperClass(); classInfo != null && classInfo != OBJECT; classInfo = classInfo.getSuperClass()) {
            if (classInfo.mixins.size() <= 0) continue;
            return true;
        }
        return false;
    }

    public Method findMethodInHierarchy(MethodNode methodNode, SearchType searchType) {
        return this.findMethodInHierarchy(methodNode.name, methodNode.desc, searchType, Traversal.NONE);
    }

    public Method findMethodInHierarchy(MethodNode methodNode, SearchType searchType, int n) {
        return this.findMethodInHierarchy(methodNode.name, methodNode.desc, searchType, Traversal.NONE, n);
    }

    public Method findMethodInHierarchy(MethodInsnNode methodInsnNode, SearchType searchType) {
        return this.findMethodInHierarchy(methodInsnNode.name, methodInsnNode.desc, searchType, Traversal.NONE);
    }

    public Method findMethodInHierarchy(MethodInsnNode methodInsnNode, SearchType searchType, int n) {
        return this.findMethodInHierarchy(methodInsnNode.name, methodInsnNode.desc, searchType, Traversal.NONE, n);
    }

    public Method findMethodInHierarchy(String string, String string2, SearchType searchType) {
        return this.findMethodInHierarchy(string, string2, searchType, Traversal.NONE);
    }

    public Method findMethodInHierarchy(String string, String string2, SearchType searchType, Traversal traversal) {
        return this.findMethodInHierarchy(string, string2, searchType, traversal, 0);
    }

    public Method findMethodInHierarchy(String string, String string2, SearchType searchType, Traversal traversal, int n) {
        return (Method)this.findInHierarchy(string, string2, searchType, traversal, n, Member.Type.METHOD);
    }

    public Field findFieldInHierarchy(FieldNode fieldNode, SearchType searchType) {
        return this.findFieldInHierarchy(fieldNode.name, fieldNode.desc, searchType, Traversal.NONE);
    }

    public Field findFieldInHierarchy(FieldNode fieldNode, SearchType searchType, int n) {
        return this.findFieldInHierarchy(fieldNode.name, fieldNode.desc, searchType, Traversal.NONE, n);
    }

    public Field findFieldInHierarchy(FieldInsnNode fieldInsnNode, SearchType searchType) {
        return this.findFieldInHierarchy(fieldInsnNode.name, fieldInsnNode.desc, searchType, Traversal.NONE);
    }

    public Field findFieldInHierarchy(FieldInsnNode fieldInsnNode, SearchType searchType, int n) {
        return this.findFieldInHierarchy(fieldInsnNode.name, fieldInsnNode.desc, searchType, Traversal.NONE, n);
    }

    public Field findFieldInHierarchy(String string, String string2, SearchType searchType) {
        return this.findFieldInHierarchy(string, string2, searchType, Traversal.NONE);
    }

    public Field findFieldInHierarchy(String string, String string2, SearchType searchType, Traversal traversal) {
        return this.findFieldInHierarchy(string, string2, searchType, traversal, 0);
    }

    public Field findFieldInHierarchy(String string, String string2, SearchType searchType, Traversal traversal, int n) {
        return (Field)this.findInHierarchy(string, string2, searchType, traversal, n, Member.Type.FIELD);
    }

    private Member findInHierarchy(String string, String string2, SearchType searchType, Traversal traversal, int n, Member.Type type) {
        Object object;
        Object object2;
        if (searchType == SearchType.ALL_CLASSES) {
            object2 = this.findMember(string, string2, n, type);
            if (object2 != null) {
                return object2;
            }
            if (traversal.canTraverse()) {
                for (Object object3 : this.mixins) {
                    object = ((MixinInfo)object3).getClassInfo().findMember(string, string2, n, type);
                    if (object == null) continue;
                    return this.cloneMember((Member)object);
                }
            }
        }
        if ((object2 = this.getSuperClass()) != null) {
            for (Object object3 : ((ClassInfo)object2).getTargets()) {
                object = ((ClassInfo)object3).findInHierarchy(string, string2, SearchType.ALL_CLASSES, traversal.next(), n & 0xFFFFFFFD, type);
                if (object == null) continue;
                return object;
            }
        }
        if (type == Member.Type.METHOD && (this.isInterface || MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces())) {
            for (Object object3 : this.interfaces) {
                object = ClassInfo.forName((String)object3);
                if (object == null) {
                    logger.debug("Failed to resolve declared interface {} on {}", new Object[]{object3, this.name});
                    continue;
                }
                Member member = super.findInHierarchy(string, string2, SearchType.ALL_CLASSES, traversal.next(), n & 0xFFFFFFFD, type);
                if (member == null) continue;
                return this.isInterface ? member : new InterfaceMethod(this, member);
            }
        }
        return null;
    }

    private Member cloneMember(Member member) {
        if (member instanceof Method) {
            return new Method(this, member);
        }
        return new Field(this, member);
    }

    public Method findMethod(MethodNode methodNode) {
        return this.findMethod(methodNode.name, methodNode.desc, methodNode.access);
    }

    public Method findMethod(MethodNode methodNode, int n) {
        return this.findMethod(methodNode.name, methodNode.desc, n);
    }

    public Method findMethod(MethodInsnNode methodInsnNode) {
        return this.findMethod(methodInsnNode.name, methodInsnNode.desc, 0);
    }

    public Method findMethod(MethodInsnNode methodInsnNode, int n) {
        return this.findMethod(methodInsnNode.name, methodInsnNode.desc, n);
    }

    public Method findMethod(String string, String string2, int n) {
        return (Method)this.findMember(string, string2, n, Member.Type.METHOD);
    }

    public Field findField(FieldNode fieldNode) {
        return this.findField(fieldNode.name, fieldNode.desc, fieldNode.access);
    }

    public Field findField(FieldInsnNode fieldInsnNode, int n) {
        return this.findField(fieldInsnNode.name, fieldInsnNode.desc, n);
    }

    public Field findField(String string, String string2, int n) {
        return (Field)this.findMember(string, string2, n, Member.Type.FIELD);
    }

    private Member findMember(String string, String string2, int n, Member.Type type) {
        Set set = type == Member.Type.METHOD ? this.methods : this.fields;
        for (Member member : set) {
            if (!member.equals(string, string2) || !member.matchesFlags(n)) continue;
            return member;
        }
        return null;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    static ClassInfo fromClassNode(ClassNode classNode) {
        ClassInfo classInfo = (ClassInfo)cache.get(classNode.name);
        if (classInfo == null) {
            classInfo = new ClassInfo(classNode);
            cache.put(classNode.name, classInfo);
        }
        return classInfo;
    }

    public static ClassInfo forName(String string) {
        ClassInfo classInfo = (ClassInfo)cache.get(string = string.replace('.', '/'));
        if (classInfo == null) {
            try {
                ClassNode classNode = TreeInfo.getClassNode(string);
                classInfo = new ClassInfo(classNode);
            }
            catch (Exception exception) {
                logger.warn("Error loading class: {}", new Object[]{string});
            }
            cache.put(string, classInfo);
            logger.trace("Added class metadata for {} to metadata cache", new Object[]{string});
        }
        return classInfo;
    }

    public static ClassInfo forType(Type type) {
        if (type.getSort() == 9) {
            return ClassInfo.forType(type.getElementType());
        }
        if (type.getSort() < 9) {
            return null;
        }
        return ClassInfo.forName(type.getClassName().replace('.', '/'));
    }

    static {
        cache.put(JAVA_LANG_OBJECT, OBJECT);
    }

    class Field
    extends Member {
        final ClassInfo this$0;

        public Field(ClassInfo classInfo, Member member) {
            this.this$0 = classInfo;
            super(member);
        }

        public Field(ClassInfo classInfo, FieldNode fieldNode) {
            this(classInfo, fieldNode, false);
        }

        public Field(ClassInfo classInfo, FieldNode fieldNode, boolean bl) {
            this.this$0 = classInfo;
            super(Member.Type.FIELD, fieldNode.name, fieldNode.desc, fieldNode.access, bl);
            this.setUnique(ASMHelper.getVisibleAnnotation(fieldNode, Unique.class) != null);
            if (ASMHelper.getVisibleAnnotation(fieldNode, Shadow.class) != null) {
                boolean bl2 = ASMHelper.getVisibleAnnotation(fieldNode, Final.class) != null;
                boolean bl3 = ASMHelper.getVisibleAnnotation(fieldNode, Mutable.class) != null;
                this.setDecoratedFinal(bl2, bl3);
            }
        }

        public Field(ClassInfo classInfo, String string, String string2, int n) {
            this.this$0 = classInfo;
            super(Member.Type.FIELD, string, string2, n, false);
        }

        public Field(ClassInfo classInfo, String string, String string2, int n, boolean bl) {
            this.this$0 = classInfo;
            super(Member.Type.FIELD, string, string2, n, bl);
        }

        @Override
        public ClassInfo getOwner() {
            return this.this$0;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Field)) {
                return false;
            }
            return super.equals(object);
        }

        @Override
        protected String getDisplayFormat() {
            return "%s:%s";
        }
    }

    public class InterfaceMethod
    extends Method {
        private final ClassInfo owner;
        final ClassInfo this$0;

        public InterfaceMethod(ClassInfo classInfo, Member member) {
            this.this$0 = classInfo;
            super(classInfo, member);
            this.owner = member.getOwner();
        }

        @Override
        public ClassInfo getOwner() {
            return this.owner;
        }

        @Override
        public ClassInfo getImplementor() {
            return this.this$0;
        }
    }

    public class Method
    extends Member {
        private final List frames;
        private boolean isAccessor;
        final ClassInfo this$0;

        public Method(ClassInfo classInfo, Member member) {
            this.this$0 = classInfo;
            super(member);
            this.frames = member instanceof Method ? ((Method)member).frames : null;
        }

        public Method(ClassInfo classInfo, MethodNode methodNode) {
            this(classInfo, methodNode, false);
            this.setUnique(ASMHelper.getVisibleAnnotation(methodNode, Unique.class) != null);
            this.isAccessor = ASMHelper.getSingleVisibleAnnotation(methodNode, Accessor.class, Invoker.class) != null;
        }

        public Method(ClassInfo classInfo, MethodNode methodNode, boolean bl) {
            this.this$0 = classInfo;
            super(Member.Type.METHOD, methodNode.name, methodNode.desc, methodNode.access, bl);
            this.frames = this.gatherFrames(methodNode);
            this.setUnique(ASMHelper.getVisibleAnnotation(methodNode, Unique.class) != null);
            this.isAccessor = ASMHelper.getSingleVisibleAnnotation(methodNode, Accessor.class, Invoker.class) != null;
        }

        public Method(ClassInfo classInfo, String string, String string2) {
            this.this$0 = classInfo;
            super(Member.Type.METHOD, string, string2, 1, false);
            this.frames = null;
        }

        public Method(ClassInfo classInfo, String string, String string2, int n) {
            this.this$0 = classInfo;
            super(Member.Type.METHOD, string, string2, n, false);
            this.frames = null;
        }

        public Method(ClassInfo classInfo, String string, String string2, int n, boolean bl) {
            this.this$0 = classInfo;
            super(Member.Type.METHOD, string, string2, n, bl);
            this.frames = null;
        }

        private List gatherFrames(MethodNode methodNode) {
            ArrayList<FrameData> arrayList = new ArrayList<FrameData>();
            ListIterator listIterator = methodNode.instructions.iterator();
            while (listIterator.hasNext()) {
                AbstractInsnNode abstractInsnNode = (AbstractInsnNode)listIterator.next();
                if (!(abstractInsnNode instanceof FrameNode)) continue;
                arrayList.add(new FrameData(methodNode.instructions.indexOf(abstractInsnNode), (FrameNode)abstractInsnNode));
            }
            return arrayList;
        }

        public List getFrames() {
            return this.frames;
        }

        @Override
        public ClassInfo getOwner() {
            return this.this$0;
        }

        public boolean isAccessor() {
            return this.isAccessor;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Method)) {
                return false;
            }
            return super.equals(object);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(String string, String string2) {
            return super.equals(string, string2);
        }

        @Override
        public String renameTo(String string) {
            return super.renameTo(string);
        }

        @Override
        public int getAccess() {
            return super.getAccess();
        }

        @Override
        public ClassInfo getImplementor() {
            return super.getImplementor();
        }

        @Override
        public boolean matchesFlags(int n) {
            return super.matchesFlags(n);
        }

        @Override
        public void setDecoratedFinal(boolean bl, boolean bl2) {
            super.setDecoratedFinal(bl, bl2);
        }

        @Override
        public boolean isDecoratedMutable() {
            return super.isDecoratedMutable();
        }

        @Override
        public boolean isDecoratedFinal() {
            return super.isDecoratedFinal();
        }

        @Override
        public void setUnique(boolean bl) {
            super.setUnique(bl);
        }

        @Override
        public boolean isUnique() {
            return super.isUnique();
        }

        @Override
        public boolean isFinal() {
            return super.isFinal();
        }

        @Override
        public boolean isAbstract() {
            return super.isAbstract();
        }

        @Override
        public boolean isStatic() {
            return super.isStatic();
        }

        @Override
        public boolean isPrivate() {
            return super.isPrivate();
        }

        @Override
        public boolean isRenamed() {
            return super.isRenamed();
        }

        @Override
        public boolean isInjected() {
            return super.isInjected();
        }

        @Override
        public String getDesc() {
            return super.getDesc();
        }

        @Override
        public String getName() {
            return super.getName();
        }

        @Override
        public String getOriginalName() {
            return super.getOriginalName();
        }
    }

    static abstract class Member {
        private final Type type;
        private final String memberName;
        private final String memberDesc;
        private final boolean isInjected;
        private final int modifiers;
        private String currentName;
        private boolean decoratedFinal;
        private boolean decoratedMutable;
        private boolean unique;

        protected Member(Member member) {
            this(member.type, member.memberName, member.memberDesc, member.modifiers, member.isInjected);
            this.currentName = member.currentName;
            this.unique = member.unique;
        }

        protected Member(Type type, String string, String string2, int n) {
            this(type, string, string2, n, false);
        }

        protected Member(Type type, String string, String string2, int n, boolean bl) {
            this.type = type;
            this.memberName = string;
            this.memberDesc = string2;
            this.isInjected = bl;
            this.currentName = string;
            this.modifiers = n;
        }

        public String getOriginalName() {
            return this.memberName;
        }

        public String getName() {
            return this.currentName;
        }

        public String getDesc() {
            return this.memberDesc;
        }

        public boolean isInjected() {
            return this.isInjected;
        }

        public boolean isRenamed() {
            return this.currentName != this.memberName;
        }

        public boolean isPrivate() {
            return (this.modifiers & 2) != 0;
        }

        public boolean isStatic() {
            return (this.modifiers & 8) != 0;
        }

        public boolean isAbstract() {
            return (this.modifiers & 0x400) != 0;
        }

        public boolean isFinal() {
            return (this.modifiers & 0x10) != 0;
        }

        public boolean isUnique() {
            return this.unique;
        }

        public void setUnique(boolean bl) {
            this.unique = bl;
        }

        public boolean isDecoratedFinal() {
            return this.decoratedFinal;
        }

        public boolean isDecoratedMutable() {
            return this.decoratedMutable;
        }

        public void setDecoratedFinal(boolean bl, boolean bl2) {
            this.decoratedFinal = bl;
            this.decoratedMutable = bl2;
        }

        public boolean matchesFlags(int n) {
            return ((~this.modifiers | n & 2) & 2) != 0 && ((~this.modifiers | n & 8) & 8) != 0;
        }

        public abstract ClassInfo getOwner();

        public ClassInfo getImplementor() {
            return this.getOwner();
        }

        public int getAccess() {
            return this.modifiers;
        }

        public String renameTo(String string) {
            this.currentName = string;
            return string;
        }

        public boolean equals(String string, String string2) {
            return (this.memberName.equals(string) || this.currentName.equals(string)) && this.memberDesc.equals(string2);
        }

        public boolean equals(Object object) {
            if (!(object instanceof Member)) {
                return false;
            }
            Member member = (Member)object;
            return (member.memberName.equals(this.memberName) || member.currentName.equals(this.currentName)) && member.memberDesc.equals(this.memberDesc);
        }

        public int hashCode() {
            return this.toString().hashCode();
        }

        public String toString() {
            return String.format(this.getDisplayFormat(), this.memberName, this.memberDesc);
        }

        protected String getDisplayFormat() {
            return "%s%s";
        }

        static enum Type {
            METHOD,
            FIELD;

        }
    }

    public static class FrameData {
        private static final String[] FRAMETYPES = new String[]{"NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1"};
        public final int index;
        public final int type;
        public final int locals;

        FrameData(int n, int n2, int n3) {
            this.index = n;
            this.type = n2;
            this.locals = n3;
        }

        FrameData(int n, FrameNode frameNode) {
            this.index = n;
            this.type = frameNode.type;
            this.locals = frameNode.local != null ? frameNode.local.size() : 0;
        }

        public String toString() {
            return String.format("FrameData[index=%d, type=%s, locals=%d]", this.index, FRAMETYPES[this.type + 1], this.locals);
        }
    }

    public static enum Traversal {
        NONE(null, false, SearchType.SUPER_CLASSES_ONLY),
        ALL(null, true, SearchType.ALL_CLASSES),
        IMMEDIATE(NONE, true, SearchType.SUPER_CLASSES_ONLY),
        SUPER(ALL, false, SearchType.SUPER_CLASSES_ONLY);

        private final Traversal next;
        private final boolean traverse;
        private final SearchType searchType;

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private Traversal(SearchType searchType) {
            void var5_3;
            void var4_2;
            this.next = searchType != null ? searchType : this;
            this.traverse = var4_2;
            this.searchType = var5_3;
        }

        public Traversal next() {
            return this.next;
        }

        public boolean canTraverse() {
            return this.traverse;
        }

        public SearchType getSearchType() {
            return this.searchType;
        }
    }

    public static enum SearchType {
        ALL_CLASSES,
        SUPER_CLASSES_ONLY;

    }
}

