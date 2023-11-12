/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class AnnotationHandle {
    private final AnnotationMirror annotation;

    private AnnotationHandle(AnnotationMirror annotationMirror) {
        this.annotation = annotationMirror;
    }

    public AnnotationMirror asMirror() {
        return this.annotation;
    }

    public boolean exists() {
        return this.annotation != null;
    }

    public String toString() {
        if (this.annotation == null) {
            return "@{UnknownAnnotation}";
        }
        return "@" + this.annotation.getAnnotationType().asElement().getSimpleName();
    }

    public Object getValue(String string, Object object) {
        if (this.annotation == null) {
            return object;
        }
        AnnotationValue annotationValue = this.getAnnotationValue(string);
        if (object instanceof Enum && annotationValue != null) {
            VariableElement variableElement = (VariableElement)annotationValue.getValue();
            if (variableElement == null) {
                return object;
            }
            return Enum.valueOf(object.getClass(), variableElement.getSimpleName().toString());
        }
        return annotationValue != null ? annotationValue.getValue() : object;
    }

    public Object getValue() {
        return this.getValue("value", null);
    }

    public Object getValue(String string) {
        return this.getValue(string, null);
    }

    public boolean getBoolean(String string, boolean bl) {
        return (Boolean)this.getValue(string, bl);
    }

    public AnnotationHandle getAnnotation(String string) {
        Object object;
        Object object2 = this.getValue(string);
        if (object2 instanceof AnnotationMirror) {
            return AnnotationHandle.of((AnnotationMirror)object2);
        }
        if (object2 instanceof AnnotationValue && (object = ((AnnotationValue)object2).getValue()) instanceof AnnotationMirror) {
            return AnnotationHandle.of((AnnotationMirror)object);
        }
        return null;
    }

    public List getList() {
        return this.getList("value");
    }

    public List getList(String string) {
        List list = (List)this.getValue(string, Collections.emptyList());
        return AnnotationHandle.unwrapAnnotationValueList(list);
    }

    public List getAnnotationList(String string) {
        Object object = this.getValue(string, null);
        if (object == null) {
            return Collections.emptyList();
        }
        if (object instanceof AnnotationMirror) {
            return ImmutableList.of((Object)AnnotationHandle.of((AnnotationMirror)object));
        }
        List list = (List)object;
        ArrayList<AnnotationHandle> arrayList = new ArrayList<AnnotationHandle>(list.size());
        for (AnnotationValue annotationValue : list) {
            arrayList.add(new AnnotationHandle((AnnotationMirror)annotationValue.getValue()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    protected AnnotationValue getAnnotationValue(String string) {
        for (ExecutableElement executableElement : this.annotation.getElementValues().keySet()) {
            if (!executableElement.getSimpleName().contentEquals(string)) continue;
            return this.annotation.getElementValues().get(executableElement);
        }
        return null;
    }

    protected static List unwrapAnnotationValueList(List list) {
        if (list == null) {
            return Collections.emptyList();
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(list.size());
        for (AnnotationValue annotationValue : list) {
            arrayList.add(annotationValue.getValue());
        }
        return arrayList;
    }

    protected static AnnotationMirror getAnnotation(Element element, Class clazz) {
        if (element == null) {
            return null;
        }
        List<? extends AnnotationMirror> list = element.getAnnotationMirrors();
        if (list == null) {
            return null;
        }
        for (AnnotationMirror annotationMirror : list) {
            TypeElement typeElement;
            Element element2 = annotationMirror.getAnnotationType().asElement();
            if (!(element2 instanceof TypeElement) || !(typeElement = (TypeElement)element2).getQualifiedName().contentEquals(clazz.getName())) continue;
            return annotationMirror;
        }
        return null;
    }

    public static AnnotationHandle of(AnnotationMirror annotationMirror) {
        return new AnnotationHandle(annotationMirror);
    }

    public static AnnotationHandle of(Element element, Class clazz) {
        return new AnnotationHandle(AnnotationHandle.getAnnotation(element, clazz));
    }
}

