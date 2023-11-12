/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.code.MethodSlice;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.util.ASMHelper;

public class MethodSlices {
    private final InjectionInfo info;
    private final Map slices = new HashMap(4);

    private MethodSlices(InjectionInfo injectionInfo) {
        this.info = injectionInfo;
    }

    private void add(MethodSlice methodSlice) {
        String string = this.info.getSliceId(methodSlice.getId());
        if (this.slices.containsKey(string)) {
            throw new InvalidSliceException(this.info, methodSlice + " has a duplicate id, '" + string + "' was already defined");
        }
        this.slices.put(string, methodSlice);
    }

    public MethodSlice get(String string) {
        return (MethodSlice)this.slices.get(string);
    }

    public String toString() {
        return String.format("MethodSlices%s", this.slices.keySet());
    }

    public static MethodSlices parse(InjectionInfo injectionInfo) {
        MethodSlices methodSlices = new MethodSlices(injectionInfo);
        AnnotationNode annotationNode = injectionInfo.getAnnotation();
        if (annotationNode != null) {
            ArrayList<AnnotationNode> arrayList = new ArrayList<AnnotationNode>();
            Object object = ASMHelper.getAnnotationValue(annotationNode, "slice");
            if (object instanceof List) {
                arrayList.addAll((List)object);
            } else if (object instanceof AnnotationNode) {
                arrayList.add((AnnotationNode)object);
            }
            for (AnnotationNode annotationNode2 : arrayList) {
                MethodSlice methodSlice = MethodSlice.parse(injectionInfo, annotationNode2);
                methodSlices.add(methodSlice);
            }
        }
        return methodSlices;
    }
}

