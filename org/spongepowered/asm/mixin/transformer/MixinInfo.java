/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  com.google.common.base.Functions
 *  com.google.common.collect.Lists
 *  net.minecraft.launchwrapper.Launch
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.InnerClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.I;
import org.spongepowered.asm.mixin.transformer.InterfaceInfo;
import org.spongepowered.asm.mixin.transformer.MixinConfig;
import org.spongepowered.asm.mixin.transformer.MixinPreProcessorAccessor;
import org.spongepowered.asm.mixin.transformer.MixinPreProcessorInterface;
import org.spongepowered.asm.mixin.transformer.MixinPreProcessorStandard;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.TargetClassContext;
import org.spongepowered.asm.mixin.transformer.TreeInfo;
import org.spongepowered.asm.mixin.transformer.llI;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTargetAlreadyLoadedException;
import org.spongepowered.asm.util.ASMHelper;
import org.spongepowered.asm.util.launchwrapper.LaunchClassLoaderUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class MixinInfo
extends TreeInfo
implements Comparable,
IMixinInfo {
    private static final LaunchClassLoaderUtil classLoaderUtil = LaunchClassLoaderUtil.forClassLoader(Launch.classLoader);
    static int mixinOrder = 0;
    private final transient Logger logger = LogManager.getLogger((String)"mixin");
    private final transient MixinConfig parent;
    private final String name;
    private final String className;
    private final int priority;
    private final boolean virtual;
    private final List targetClasses;
    private final List targetClassNames;
    private final transient int order = mixinOrder++;
    private final transient IMixinConfigPlugin plugin;
    private final transient MixinEnvironment.Phase phase;
    private final transient ClassInfo info;
    private final transient SubType type;
    private final transient boolean strict;
    private transient State pendingState;
    private transient State state;

    MixinInfo(MixinConfig mixinConfig, String string, boolean bl, IMixinConfigPlugin iMixinConfigPlugin, boolean bl2) {
        this.parent = mixinConfig;
        this.name = string;
        this.className = mixinConfig.getMixinPackage() + string;
        this.plugin = iMixinConfigPlugin;
        this.phase = mixinConfig.getEnvironment().getPhase();
        this.strict = mixinConfig.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_TARGETS);
        try {
            byte[] byArray = this.loadMixinClass(this.className, bl);
            this.pendingState = new State(this, byArray);
            this.info = this.pendingState.getClassInfo();
            this.type = SubType.getTypeFor(this);
        }
        catch (InvalidMixinException invalidMixinException) {
            throw invalidMixinException;
        }
        catch (Exception exception) {
            throw new InvalidMixinException((IMixinInfo)this, (Throwable)exception);
        }
        if (!this.type.isLoadable()) {
            classLoaderUtil.registerInvalidClass(this.className);
        }
        try {
            this.priority = this.readPriority(this.pendingState.getClassNode());
            this.virtual = this.readPseudo(this.pendingState.getClassNode());
            this.targetClasses = this.readTargetClasses(this.pendingState.getClassNode(), bl2);
            this.targetClassNames = Collections.unmodifiableList(Lists.transform((List)this.targetClasses, (Function)Functions.toStringFunction()));
        }
        catch (InvalidMixinException invalidMixinException) {
            throw invalidMixinException;
        }
        catch (Exception exception) {
            throw new InvalidMixinException((IMixinInfo)this, (Throwable)exception);
        }
    }

    void validate() {
        if (this.pendingState == null) {
            throw new IllegalStateException("No pending validation state for " + this);
        }
        this.pendingState.validate(this.type, this.targetClasses);
        this.state = this.pendingState;
        this.pendingState = null;
    }

    protected List readTargetClasses(MixinClassNode mixinClassNode, boolean bl) {
        if (mixinClassNode == null) {
            return Collections.emptyList();
        }
        AnnotationNode annotationNode = ASMHelper.getInvisibleAnnotation(mixinClassNode, Mixin.class);
        if (annotationNode == null) {
            throw new InvalidMixinException((IMixinInfo)this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
        }
        ArrayList arrayList = new ArrayList();
        List list = (List)ASMHelper.getAnnotationValue(annotationNode, "value");
        List list2 = (List)ASMHelper.getAnnotationValue(annotationNode, "targets");
        if (list != null) {
            this.readTargets(arrayList, Lists.transform((List)list, (Function)new llI(this)), bl, false);
        }
        if (list2 != null) {
            this.readTargets(arrayList, Lists.transform((List)list2, (Function)new I(this)), bl, true);
        }
        return arrayList;
    }

    private void readTargets(Collection collection, Collection collection2, boolean bl, boolean bl2) {
        for (String string : collection2) {
            Object object;
            String string2 = string.replace('/', '.');
            if (classLoaderUtil.isClassLoaded(string2) && !this.isReloading()) {
                object = String.format("Critical problem: %s target %s was already transformed.", this, string2);
                if (this.parent.isRequired()) {
                    throw new MixinTargetAlreadyLoadedException((IMixinInfo)this, (String)object, string2);
                }
                this.logger.error((String)object);
            }
            if (this.plugin != null && !bl && !this.plugin.shouldApplyMixin(string2, this.className) || (object = this.getTarget(string2, bl2)) == null || collection.contains(object)) continue;
            collection.add(object);
            ((ClassInfo)object).addMixin(this);
        }
    }

    private ClassInfo getTarget(String string, boolean bl) throws InvalidMixinException {
        ClassInfo classInfo = ClassInfo.forName(string);
        if (classInfo == null) {
            if (this.isVirtual()) {
                this.logger.debug("Skipping virtual target {} for {}", new Object[]{string, this});
            } else {
                this.handleTargetError(String.format("@Mixin target %s was not found %s", string, this));
            }
            return null;
        }
        this.type.validateTarget(string, classInfo);
        if (bl && classInfo.isPublic() && !this.isVirtual()) {
            this.handleTargetError(String.format("@Mixin target %s is public in %s and should be specified in value", string, this));
        }
        return classInfo;
    }

    private void handleTargetError(String string) {
        if (this.strict) {
            this.logger.error(string);
            throw new InvalidMixinException((IMixinInfo)this, string);
        }
        this.logger.warn(string);
    }

    protected int readPriority(ClassNode classNode) {
        if (classNode == null) {
            return this.parent.getDefaultMixinPriority();
        }
        AnnotationNode annotationNode = ASMHelper.getInvisibleAnnotation(classNode, Mixin.class);
        if (annotationNode == null) {
            throw new InvalidMixinException((IMixinInfo)this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
        }
        Integer n = (Integer)ASMHelper.getAnnotationValue(annotationNode, "priority");
        return n == null ? this.parent.getDefaultMixinPriority() : n.intValue();
    }

    protected boolean readPseudo(ClassNode classNode) {
        return ASMHelper.getInvisibleAnnotation(classNode, Pseudo.class) != null;
    }

    private boolean isReloading() {
        return this.pendingState instanceof Reloaded;
    }

    private State getState() {
        return this.state != null ? this.state : this.pendingState;
    }

    ClassInfo getClassInfo() {
        return this.info;
    }

    @Override
    public IMixinConfig getConfig() {
        return this.parent;
    }

    MixinConfig getParent() {
        return this.parent;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getClassName() {
        return this.className;
    }

    @Override
    public String getClassRef() {
        return this.getClassInfo().getName();
    }

    @Override
    public byte[] getClassBytes() {
        return this.getState().getClassBytes();
    }

    @Override
    public boolean isDetachedSuper() {
        return this.getState().isDetachedSuper();
    }

    public boolean isUnique() {
        return this.getState().isUnique();
    }

    public boolean isVirtual() {
        return this.virtual;
    }

    public boolean isLoadable() {
        return this.type.isLoadable();
    }

    public Level getLoggingLevel() {
        return this.parent.getLoggingLevel();
    }

    @Override
    public MixinEnvironment.Phase getPhase() {
        return this.phase;
    }

    @Override
    public MixinClassNode getClassNode(int n) {
        return this.getState().createClassNode(n);
    }

    @Override
    public List getTargetClasses() {
        return this.targetClassNames;
    }

    List getSoftImplements() {
        return Collections.unmodifiableList(this.getState().getSoftImplements());
    }

    Set getSyntheticInnerClasses() {
        return Collections.unmodifiableSet(this.getState().getSyntheticInnerClasses());
    }

    List getTargets() {
        return Collections.unmodifiableList(this.targetClasses);
    }

    Set getInterfaces() {
        return this.getState().getInterfaces();
    }

    MixinTargetContext createContextFor(TargetClassContext targetClassContext) {
        MixinClassNode mixinClassNode = this.getClassNode(8);
        return this.type.createPreProcessor(mixinClassNode).prepare().createContextFor(targetClassContext);
    }

    private byte[] loadMixinClass(String string, boolean bl) throws ClassNotFoundException {
        byte[] byArray = null;
        try {
            byArray = TreeInfo.loadClass(string, bl);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new ClassNotFoundException(String.format("The specified mixin '%s' was not found", string));
        }
        catch (IOException iOException) {
            this.logger.warn("Failed to load mixin %s, the specified mixin will not be applied", new Object[]{string});
            throw new InvalidMixinException(this, "An error was encountered whilst loading the mixin class", (Throwable)iOException);
        }
        return byArray;
    }

    void reloadMixin(byte[] byArray) {
        if (this.pendingState != null) {
            throw new IllegalStateException("Cannot reload mixin while it is initialising");
        }
        this.pendingState = new Reloaded(this, this.state, byArray);
        this.validate();
    }

    public int compareTo(MixinInfo mixinInfo) {
        if (mixinInfo == null) {
            return 0;
        }
        if (mixinInfo.priority == this.priority) {
            return this.order - mixinInfo.order;
        }
        return this.priority - mixinInfo.priority;
    }

    public void preApply(String string, ClassNode classNode) {
        if (this.plugin != null) {
            this.plugin.preApply(string, classNode, this.className, this);
        }
    }

    public void postApply(String string, ClassNode classNode) {
        if (this.plugin != null) {
            this.plugin.postApply(string, classNode, this.className, this);
        }
        this.parent.postApply(string, classNode);
    }

    public String toString() {
        return String.format("%s:%s", this.parent.getName(), this.name);
    }

    public int compareTo(Object object) {
        return this.compareTo((MixinInfo)object);
    }

    @Override
    public ClassNode getClassNode(int n) {
        return this.getClassNode(n);
    }

    static abstract class SubType {
        protected final MixinInfo mixin;
        protected final String annotationType;
        protected final boolean targetMustBeInterface;
        protected boolean detached;

        SubType(MixinInfo mixinInfo, String string, boolean bl) {
            this.mixin = mixinInfo;
            this.annotationType = string;
            this.targetMustBeInterface = bl;
        }

        Collection getInterfaces() {
            return Collections.emptyList();
        }

        boolean isDetachedSuper() {
            return this.detached;
        }

        boolean isLoadable() {
            return false;
        }

        void validateTarget(String string, ClassInfo classInfo) {
            boolean bl = classInfo.isInterface();
            if (bl != this.targetMustBeInterface) {
                String string2 = bl ? "" : "not ";
                throw new InvalidMixinException((IMixinInfo)this.mixin, this.annotationType + " target type mismatch: " + string + " is " + string2 + "an interface in " + this);
            }
        }

        abstract void validate(State var1, List var2);

        abstract MixinPreProcessorStandard createPreProcessor(MixinClassNode var1);

        static SubType getTypeFor(MixinInfo mixinInfo) {
            if (!mixinInfo.getClassInfo().isInterface()) {
                return new Standard(mixinInfo);
            }
            boolean bl = false;
            for (ClassInfo.Method method : mixinInfo.getClassInfo().getMethods()) {
                bl |= !method.isAccessor();
            }
            if (bl) {
                return new Interface(mixinInfo);
            }
            return new Accessor(mixinInfo);
        }

        static class Accessor
        extends SubType {
            private final Collection interfaces = new ArrayList();

            Accessor(MixinInfo mixinInfo) {
                super(mixinInfo, "@Mixin", false);
                this.interfaces.add(mixinInfo.getClassRef());
            }

            @Override
            boolean isLoadable() {
                return true;
            }

            @Override
            Collection getInterfaces() {
                return this.interfaces;
            }

            @Override
            void validateTarget(String string, ClassInfo classInfo) {
                boolean bl = classInfo.isInterface();
                if (bl && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
                    throw new InvalidMixinException((IMixinInfo)this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");
                }
            }

            @Override
            void validate(State state, List list) {
                MixinClassNode mixinClassNode = state.getClassNode();
                if (!"java/lang/Object".equals(mixinClassNode.superName)) {
                    throw new InvalidMixinException((IMixinInfo)this.mixin, "Super class of " + this + " is invalid, found " + mixinClassNode.superName.replace('/', '.'));
                }
            }

            @Override
            MixinPreProcessorStandard createPreProcessor(MixinClassNode mixinClassNode) {
                return new MixinPreProcessorAccessor(this.mixin, mixinClassNode);
            }
        }

        static class Interface
        extends SubType {
            Interface(MixinInfo mixinInfo) {
                super(mixinInfo, "@Mixin", true);
            }

            @Override
            void validate(State state, List list) {
                if (!MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
                    throw new InvalidMixinException((IMixinInfo)this.mixin, "Interface mixin not supported in current enviromnment");
                }
                MixinClassNode mixinClassNode = state.getClassNode();
                if (!"java/lang/Object".equals(mixinClassNode.superName)) {
                    throw new InvalidMixinException((IMixinInfo)this.mixin, "Super class of " + this + " is invalid, found " + mixinClassNode.superName.replace('/', '.'));
                }
            }

            @Override
            MixinPreProcessorStandard createPreProcessor(MixinClassNode mixinClassNode) {
                return new MixinPreProcessorInterface(this.mixin, mixinClassNode);
            }
        }

        static class Standard
        extends SubType {
            Standard(MixinInfo mixinInfo) {
                super(mixinInfo, "@Mixin", false);
            }

            @Override
            void validate(State state, List list) {
                MixinClassNode mixinClassNode = state.getClassNode();
                for (ClassInfo classInfo : list) {
                    if (mixinClassNode.superName.equals(classInfo.getSuperName())) continue;
                    if (!classInfo.hasSuperClass(mixinClassNode.superName, ClassInfo.Traversal.SUPER)) {
                        ClassInfo classInfo2 = ClassInfo.forName(mixinClassNode.superName);
                        if (classInfo2.isMixin()) {
                            for (ClassInfo classInfo3 : classInfo2.getTargets()) {
                                if (!list.contains(classInfo3)) continue;
                                throw new InvalidMixinException((IMixinInfo)this.mixin, "Illegal hierarchy detected. Derived mixin " + this + " targets the same class " + classInfo3.getClassName() + " as its superclass " + classInfo2.getClassName());
                            }
                        }
                        throw new InvalidMixinException((IMixinInfo)this.mixin, "Super class '" + mixinClassNode.superName.replace('/', '.') + "' of " + this.mixin.getName() + " was not found in the hierarchy of target class '" + classInfo + "'");
                    }
                    this.detached = true;
                }
            }

            @Override
            MixinPreProcessorStandard createPreProcessor(MixinClassNode mixinClassNode) {
                return new MixinPreProcessorStandard(this.mixin, mixinClassNode);
            }
        }
    }

    class Reloaded
    extends State {
        private final State previous;
        final MixinInfo this$0;

        Reloaded(MixinInfo mixinInfo, State state, byte[] byArray) {
            this.this$0 = mixinInfo;
            super(mixinInfo, byArray, state.getClassInfo());
            this.previous = state;
        }

        @Override
        protected void validateChanges(SubType subType, List list) {
            if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses)) {
                throw new MixinReloadException(this.this$0, "Cannot change inner classes");
            }
            if (!this.interfaces.equals(this.previous.interfaces)) {
                throw new MixinReloadException(this.this$0, "Cannot change interfaces");
            }
            if (!new HashSet(this.softImplements).equals(new HashSet(this.previous.softImplements))) {
                throw new MixinReloadException(this.this$0, "Cannot change soft interfaces");
            }
            List list2 = this.this$0.readTargetClasses(this.classNode, true);
            if (!new HashSet(list2).equals(new HashSet(list))) {
                throw new MixinReloadException(this.this$0, "Cannot change target classes");
            }
            int n = this.this$0.readPriority(this.classNode);
            if (n != this.this$0.getPriority()) {
                throw new MixinReloadException(this.this$0, "Cannot change mixin priority");
            }
        }
    }

    class State {
        private byte[] mixinBytes;
        private final ClassInfo classInfo;
        private boolean detachedSuper;
        private boolean unique;
        protected final Set interfaces;
        protected final List softImplements;
        protected final Set syntheticInnerClasses;
        protected MixinClassNode classNode;
        final MixinInfo this$0;

        State(MixinInfo mixinInfo, byte[] byArray) {
            this(mixinInfo, byArray, null);
        }

        State(MixinInfo mixinInfo, byte[] byArray, ClassInfo classInfo) {
            this.this$0 = mixinInfo;
            this.interfaces = new HashSet();
            this.softImplements = new ArrayList();
            this.syntheticInnerClasses = new HashSet();
            this.mixinBytes = byArray;
            this.connect();
            this.classInfo = classInfo != null ? classInfo : ClassInfo.fromClassNode(this.getClassNode());
        }

        private void connect() {
            this.classNode = this.createClassNode(0);
        }

        private void complete() {
            this.classNode = null;
        }

        ClassInfo getClassInfo() {
            return this.classInfo;
        }

        byte[] getClassBytes() {
            return this.mixinBytes;
        }

        MixinClassNode getClassNode() {
            return this.classNode;
        }

        boolean isDetachedSuper() {
            return this.detachedSuper;
        }

        boolean isUnique() {
            return this.unique;
        }

        List getSoftImplements() {
            return this.softImplements;
        }

        Set getSyntheticInnerClasses() {
            return this.syntheticInnerClasses;
        }

        Set getInterfaces() {
            return this.interfaces;
        }

        MixinClassNode createClassNode(int n) {
            MixinClassNode mixinClassNode = new MixinClassNode(this.this$0, this.this$0);
            ClassReader classReader = new ClassReader(this.mixinBytes);
            classReader.accept(mixinClassNode, n);
            return mixinClassNode;
        }

        void validate(SubType subType, List list) {
            MixinPreProcessorStandard mixinPreProcessorStandard = subType.createPreProcessor(this.getClassNode()).prepare();
            for (ClassInfo classInfo : list) {
                mixinPreProcessorStandard.conform(classInfo);
            }
            subType.validate(this, list);
            this.detachedSuper = subType.isDetachedSuper();
            this.unique = ASMHelper.getVisibleAnnotation(this.getClassNode(), Unique.class) != null;
            this.validateInner();
            this.validateClassVersion();
            this.validateRemappables(list);
            this.readImplementations(subType);
            this.readInnerClasses();
            this.validateChanges(subType, list);
            this.complete();
        }

        private void validateInner() {
            if (!this.classInfo.isProbablyStatic()) {
                throw new InvalidMixinException((IMixinInfo)this.this$0, "Inner class mixin must be declared static");
            }
        }

        private void validateClassVersion() {
            if (this.classNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
                String string = ".";
                for (MixinEnvironment.CompatibilityLevel compatibilityLevel : MixinEnvironment.CompatibilityLevel.values()) {
                    if (compatibilityLevel.classVersion() < this.classNode.version) continue;
                    string = String.format(". Mixin requires compatibility level %s or above.", compatibilityLevel.name());
                }
                throw new InvalidMixinException((IMixinInfo)this.this$0, "Unsupported mixin class version " + this.classNode.version + string);
            }
        }

        private void validateRemappables(List list) {
            if (list.size() > 1) {
                for (Object object : this.classNode.fields) {
                    this.validateRemappable(Shadow.class, ((FieldNode)object).name, ASMHelper.getVisibleAnnotation((FieldNode)object, Shadow.class));
                }
                for (Object object : this.classNode.methods) {
                    this.validateRemappable(Shadow.class, ((MethodNode)object).name, ASMHelper.getVisibleAnnotation((MethodNode)object, Shadow.class));
                    AnnotationNode annotationNode = ASMHelper.getVisibleAnnotation((MethodNode)object, Overwrite.class);
                    if (annotationNode == null || (((MethodNode)object).access & 8) != 0 && (((MethodNode)object).access & 1) != 0) continue;
                    throw new InvalidMixinException((IMixinInfo)this.this$0, "Found @Overwrite annotation on " + ((MethodNode)object).name + " in " + this);
                }
            }
        }

        private void validateRemappable(Class clazz, String string, AnnotationNode annotationNode) {
            if (annotationNode != null && ((Boolean)ASMHelper.getAnnotationValue(annotationNode, "remap", Boolean.TRUE)).booleanValue()) {
                throw new InvalidMixinException((IMixinInfo)this.this$0, "Found a remappable @" + clazz.getSimpleName() + " annotation on " + string + " in " + this);
            }
        }

        void readImplementations(SubType subType) {
            this.interfaces.addAll(this.classNode.interfaces);
            this.interfaces.addAll(subType.getInterfaces());
            AnnotationNode annotationNode = ASMHelper.getInvisibleAnnotation(this.classNode, Implements.class);
            if (annotationNode == null) {
                return;
            }
            List list = (List)ASMHelper.getAnnotationValue(annotationNode);
            if (list == null) {
                return;
            }
            for (AnnotationNode annotationNode2 : list) {
                InterfaceInfo interfaceInfo = InterfaceInfo.fromAnnotation(this.this$0, annotationNode2);
                this.softImplements.add(interfaceInfo);
                this.interfaces.add(interfaceInfo.getInternalName());
                if (this instanceof Reloaded) continue;
                this.classInfo.addInterface(interfaceInfo.getInternalName());
            }
        }

        void readInnerClasses() {
            for (InnerClassNode innerClassNode : this.classNode.innerClasses) {
                ClassInfo classInfo = ClassInfo.forName(innerClassNode.name);
                if (!classInfo.isSynthetic() || !classInfo.isProbablyStatic()) continue;
                if (innerClassNode.outerName != null && innerClassNode.outerName.equals(this.classInfo.getName()) || innerClassNode.name.startsWith(this.classNode.name + "$")) {
                    this.syntheticInnerClasses.add(innerClassNode.name);
                    continue;
                }
                throw new InvalidMixinException((IMixinInfo)this.this$0, "Unhandled synthetic inner class found: " + innerClassNode.name);
            }
        }

        protected void validateChanges(SubType subType, List list) {
            subType.createPreProcessor(this.classNode).prepare();
        }
    }

    class MixinClassNode
    extends ClassNode {
        public final List mixinMethods;
        final MixinInfo this$0;

        public MixinClassNode(MixinInfo mixinInfo, MixinInfo mixinInfo2) {
            this(mixinInfo, 327680);
        }

        public MixinClassNode(MixinInfo mixinInfo, int n) {
            this.this$0 = mixinInfo;
            super(n);
            this.mixinMethods = this.methods;
        }

        public MixinInfo getMixin() {
            return this.this$0;
        }

        @Override
        public MethodVisitor visitMethod(int n, String string, String string2, String string3, String[] stringArray) {
            MixinMethodNode mixinMethodNode = new MixinMethodNode(this.this$0, n, string, string2, string3, stringArray);
            this.methods.add(mixinMethodNode);
            return mixinMethodNode;
        }
    }

    class MixinMethodNode
    extends MethodNode {
        private final String originalName;
        final MixinInfo this$0;

        public MixinMethodNode(MixinInfo mixinInfo, int n, String string, String string2, String string3, String[] stringArray) {
            this.this$0 = mixinInfo;
            super(327680, n, string, string2, string3, stringArray);
            this.originalName = string;
        }

        public String toString() {
            return String.format("%s%s", this.originalName, this.desc);
        }

        public String getOriginalName() {
            return this.originalName;
        }

        public boolean isInjector() {
            return this.getInjectorAnnotation() != null || this != null;
        }

        public AnnotationNode getVisibleAnnotation(Class clazz) {
            return ASMHelper.getVisibleAnnotation(this, clazz);
        }

        public AnnotationNode getInjectorAnnotation() {
            return InjectionInfo.getInjectorAnnotation(this.this$0, this);
        }

        public IMixinInfo getOwner() {
            return this.this$0;
        }
    }
}

