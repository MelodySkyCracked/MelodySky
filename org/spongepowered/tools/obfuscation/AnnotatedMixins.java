/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package org.spongepowered.tools.obfuscation;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.tools.obfuscation.AnnotatedMixin;
import org.spongepowered.tools.obfuscation.ObfuscationManager;
import org.spongepowered.tools.obfuscation.TargetMap;
import org.spongepowered.tools.obfuscation.interfaces.IJavadocProvider;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandleSimulated;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;
import org.spongepowered.tools.obfuscation.validation.ParentValidator;
import org.spongepowered.tools.obfuscation.validation.TargetValidator;

class AnnotatedMixins
implements IMixinAnnotationProcessor,
ITokenProvider,
ITypeHandleProvider,
IJavadocProvider {
    private static final String MAPID_SYSTEM_PROPERTY = "mixin.target.mapid";
    private static Map instances = new HashMap();
    private final IMixinAnnotationProcessor.CompilerEnvironment env;
    private final ProcessingEnvironment processingEnv;
    private final Map mixins = new HashMap();
    private final List mixinsForPass = new ArrayList();
    private final IObfuscationManager obf;
    private final List validators;
    private final Map tokenCache = new HashMap();
    private final TargetMap targets;
    private Properties properties;

    private AnnotatedMixins(ProcessingEnvironment processingEnvironment) {
        this.env = this.detectEnvironment(processingEnvironment);
        this.processingEnv = processingEnvironment;
        this.printMessage(Diagnostic.Kind.NOTE, "SpongePowered MIXIN Annotation Processor Version=0.6.5");
        this.targets = this.initTargetMap();
        this.obf = new ObfuscationManager(this);
        this.obf.init();
        this.validators = ImmutableList.of((Object)new ParentValidator(this), (Object)new TargetValidator(this));
        this.initTokenCache(this.getOption("tokens"));
    }

    protected TargetMap initTargetMap() {
        TargetMap targetMap = TargetMap.create(System.getProperty(MAPID_SYSTEM_PROPERTY));
        System.setProperty(MAPID_SYSTEM_PROPERTY, targetMap.getSessionId());
        String string = this.getOption("dependencyTargetsFile");
        if (string != null) {
            try {
                targetMap.readImports(new File(string));
            }
            catch (IOException iOException) {
                this.printMessage(Diagnostic.Kind.WARNING, "Could not read from specified imports file: " + string);
            }
        }
        return targetMap;
    }

    private void initTokenCache(String string) {
        if (string != null) {
            String[] stringArray;
            Pattern pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)=([0-9]+)$");
            for (String string2 : stringArray = string.replaceAll("\\s", "").toUpperCase().split("[;,]")) {
                Matcher matcher = pattern.matcher(string2);
                if (!matcher.matches()) continue;
                this.tokenCache.put(matcher.group(1), Integer.parseInt(matcher.group(2)));
            }
        }
    }

    @Override
    public ITypeHandleProvider getTypeProvider() {
        return this;
    }

    @Override
    public ITokenProvider getTokenProvider() {
        return this;
    }

    @Override
    public IObfuscationManager getObfuscationManager() {
        return this.obf;
    }

    @Override
    public IJavadocProvider getJavadocProvider() {
        return this;
    }

    @Override
    public ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }

    @Override
    public IMixinAnnotationProcessor.CompilerEnvironment getCompilerEnvironment() {
        return this.env;
    }

    @Override
    public Integer getToken(String string) {
        if (this.tokenCache.containsKey(string)) {
            return (Integer)this.tokenCache.get(string);
        }
        String string2 = this.getOption(string);
        Integer n = null;
        try {
            n = Integer.parseInt(string2);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.tokenCache.put(string, n);
        return n;
    }

    @Override
    public String getOption(String string) {
        if (string == null) {
            return null;
        }
        String string2 = this.processingEnv.getOptions().get(string);
        if (string2 != null) {
            return string2;
        }
        return this.getProperties().getProperty(string);
    }

    public Properties getProperties() {
        if (this.properties == null) {
            this.properties = new Properties();
            try {
                Filer filer = this.processingEnv.getFiler();
                FileObject fileObject = filer.getResource(StandardLocation.SOURCE_PATH, "", "mixin.properties");
                if (fileObject != null) {
                    InputStream inputStream = fileObject.openInputStream();
                    this.properties.load(inputStream);
                    inputStream.close();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return this.properties;
    }

    private IMixinAnnotationProcessor.CompilerEnvironment detectEnvironment(ProcessingEnvironment processingEnvironment) {
        if (processingEnvironment.getClass().getName().contains("jdt")) {
            return IMixinAnnotationProcessor.CompilerEnvironment.JDT;
        }
        return IMixinAnnotationProcessor.CompilerEnvironment.JAVAC;
    }

    public void writeMappings() {
        this.obf.writeMappings();
    }

    public void writeReferences() {
        this.obf.writeReferences();
    }

    public void clear() {
        this.mixins.clear();
    }

    public void registerMixin(TypeElement typeElement) {
        String string = typeElement.getQualifiedName().toString();
        if (!this.mixins.containsKey(string)) {
            AnnotatedMixin annotatedMixin = new AnnotatedMixin(this, typeElement);
            this.targets.registerTargets(annotatedMixin);
            annotatedMixin.runValidators(IMixinValidator.ValidationPass.EARLY, this.validators);
            this.mixins.put(string, annotatedMixin);
            this.mixinsForPass.add(annotatedMixin);
        }
    }

    public AnnotatedMixin getMixin(TypeElement typeElement) {
        return this.getMixin(typeElement.getQualifiedName().toString());
    }

    public AnnotatedMixin getMixin(String string) {
        return (AnnotatedMixin)this.mixins.get(string);
    }

    public Collection getMixinsTargeting(TypeMirror typeMirror) {
        return this.getMixinsTargeting((TypeElement)((DeclaredType)typeMirror).asElement());
    }

    public Collection getMixinsTargeting(TypeElement typeElement) {
        ArrayList<TypeMirror> arrayList = new ArrayList<TypeMirror>();
        for (TypeReference typeReference : this.targets.getMixinsTargeting(typeElement)) {
            TypeHandle typeHandle = typeReference.getHandle(this.processingEnv);
            if (typeHandle == null) continue;
            arrayList.add(typeHandle.getType());
        }
        return arrayList;
    }

    public void registerAccessor(TypeElement typeElement, ExecutableElement executableElement) {
        AnnotatedMixin annotatedMixin = this.getMixin(typeElement);
        if (annotatedMixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", executableElement);
            return;
        }
        annotatedMixin.registerAccessor(executableElement, AnnotationHandle.of(executableElement, Accessor.class));
    }

    public void registerInvoker(TypeElement typeElement, ExecutableElement executableElement) {
        AnnotatedMixin annotatedMixin = this.getMixin(typeElement);
        if (annotatedMixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", executableElement);
            return;
        }
        annotatedMixin.registerInvoker(executableElement, AnnotationHandle.of(executableElement, Invoker.class));
    }

    public void registerOverwrite(TypeElement typeElement, ExecutableElement executableElement) {
        AnnotatedMixin annotatedMixin = this.getMixin(typeElement);
        if (annotatedMixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Overwrite annotation on a non-mixin method", executableElement);
            return;
        }
        annotatedMixin.registerOverwrite(executableElement, AnnotationHandle.of(executableElement, Overwrite.class));
    }

    public void registerShadow(TypeElement typeElement, VariableElement variableElement, AnnotationHandle annotationHandle) {
        AnnotatedMixin annotatedMixin = this.getMixin(typeElement);
        if (annotatedMixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin field", variableElement);
            return;
        }
        annotatedMixin.registerShadow(variableElement, annotationHandle, this.shouldRemap(annotatedMixin, annotationHandle));
    }

    public void registerShadow(TypeElement typeElement, ExecutableElement executableElement, AnnotationHandle annotationHandle) {
        AnnotatedMixin annotatedMixin = this.getMixin(typeElement);
        if (annotatedMixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin method", executableElement);
            return;
        }
        annotatedMixin.registerShadow(executableElement, annotationHandle, this.shouldRemap(annotatedMixin, annotationHandle));
    }

    public void registerInjector(TypeElement typeElement, ExecutableElement executableElement, AnnotationHandle annotationHandle) {
        AnnotatedMixin annotatedMixin = this.getMixin(typeElement);
        if (annotatedMixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found " + annotationHandle + " annotation on a non-mixin method", executableElement);
            return;
        }
        InjectorRemap injectorRemap = new InjectorRemap(this.shouldRemap(annotatedMixin, annotationHandle));
        annotatedMixin.registerInjector(executableElement, annotationHandle, injectorRemap);
        injectorRemap.dispatchPendingMessages(this);
    }

    public void registerSoftImplements(TypeElement typeElement, AnnotationHandle annotationHandle) {
        AnnotatedMixin annotatedMixin = this.getMixin(typeElement);
        if (annotatedMixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Implements annotation on a non-mixin class");
            return;
        }
        annotatedMixin.registerSoftImplements(annotationHandle);
    }

    public void onPassStarted() {
        this.mixinsForPass.clear();
    }

    public void onPassCompleted() {
        if (!"true".equalsIgnoreCase(this.getOption("disableTargetExport"))) {
            this.targets.write(true);
        }
        for (AnnotatedMixin annotatedMixin : this.mixinsForPass) {
            annotatedMixin.runValidators(IMixinValidator.ValidationPass.LATE, this.validators);
        }
    }

    private boolean shouldRemap(AnnotatedMixin annotatedMixin, AnnotationHandle annotationHandle) {
        return annotatedMixin.remap() && annotationHandle.getBoolean("remap", true);
    }

    @Override
    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence) {
        if (this.env == IMixinAnnotationProcessor.CompilerEnvironment.JAVAC || kind != Diagnostic.Kind.NOTE) {
            this.processingEnv.getMessager().printMessage(kind, charSequence);
        }
    }

    @Override
    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element) {
        this.processingEnv.getMessager().printMessage(kind, charSequence, element);
    }

    @Override
    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror) {
        this.processingEnv.getMessager().printMessage(kind, charSequence, element, annotationMirror);
    }

    @Override
    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        this.processingEnv.getMessager().printMessage(kind, charSequence, element, annotationMirror, annotationValue);
    }

    @Override
    public TypeHandle getTypeHandle(String string) {
        String string2;
        PackageElement packageElement;
        int n;
        string = string.replace('/', '.');
        Elements elements = this.processingEnv.getElementUtils();
        TypeElement typeElement = elements.getTypeElement(string);
        if (typeElement != null) {
            try {
                return new TypeHandle(typeElement);
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
        }
        if ((n = string.lastIndexOf(46)) > -1 && (packageElement = elements.getPackageElement(string2 = string.substring(0, n))) != null) {
            return new TypeHandle(packageElement, string);
        }
        return null;
    }

    @Override
    public TypeHandle getSimulatedHandle(String string, TypeMirror typeMirror) {
        int n = (string = string.replace('/', '.')).lastIndexOf(46);
        if (n > -1) {
            String string2 = string.substring(0, n);
            PackageElement packageElement = this.processingEnv.getElementUtils().getPackageElement(string2);
            if (packageElement != null) {
                return new TypeHandleSimulated(packageElement, string, typeMirror);
            }
        }
        return new TypeHandleSimulated(string, typeMirror);
    }

    @Override
    public String getJavadoc(Element element) {
        Elements elements = this.processingEnv.getElementUtils();
        return elements.getDocComment(element);
    }

    public static AnnotatedMixins getMixinsForEnvironment(ProcessingEnvironment processingEnvironment) {
        AnnotatedMixins annotatedMixins = (AnnotatedMixins)instances.get(processingEnvironment);
        if (annotatedMixins == null) {
            annotatedMixins = new AnnotatedMixins(processingEnvironment);
            instances.put(processingEnvironment, annotatedMixins);
        }
        return annotatedMixins;
    }
}

