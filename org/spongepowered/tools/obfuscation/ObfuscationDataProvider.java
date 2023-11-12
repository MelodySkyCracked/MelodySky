/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation;

import java.util.List;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public class ObfuscationDataProvider
implements IObfuscationDataProvider {
    private final IMixinAnnotationProcessor ap;
    private final List environments;

    public ObfuscationDataProvider(IMixinAnnotationProcessor iMixinAnnotationProcessor, List list) {
        this.ap = iMixinAnnotationProcessor;
        this.environments = list;
    }

    @Override
    public ObfuscationData getObfEntryRecursive(MemberInfo memberInfo) {
        MemberInfo memberInfo2 = memberInfo;
        ObfuscationData obfuscationData = this.getObfClass(memberInfo2.owner);
        ObfuscationData obfuscationData2 = this.getObfEntry(memberInfo2);
        try {
            while (obfuscationData2.isEmpty()) {
                TypeHandle typeHandle = this.ap.getTypeProvider().getTypeHandle(memberInfo2.owner);
                if (typeHandle == null) {
                    return obfuscationData2;
                }
                TypeHandle typeHandle2 = typeHandle.getSuperclass();
                if (typeHandle2 == null) {
                    return obfuscationData2;
                }
                obfuscationData2 = this.getObfEntry(memberInfo2 = memberInfo2.move(typeHandle2.getName()));
                if (obfuscationData2.isEmpty()) continue;
                for (ObfuscationType obfuscationType : obfuscationData2) {
                    String string = (String)obfuscationData.get(obfuscationType);
                    Object object = obfuscationData2.get(obfuscationType);
                    obfuscationData2.add(obfuscationType, MemberInfo.fromMapping((IMapping)object).move(string).asMapping());
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return this.getObfEntry(memberInfo);
        }
        return obfuscationData2;
    }

    @Override
    public ObfuscationData getObfEntry(MemberInfo memberInfo) {
        if (memberInfo.isField()) {
            return this.getObfField(memberInfo);
        }
        return this.getObfMethod(memberInfo.asMethodMapping());
    }

    @Override
    public ObfuscationData getObfEntry(IMapping iMapping) {
        if (iMapping != null) {
            if (iMapping.getType() == IMapping.Type.FIELD) {
                return this.getObfField((MappingField)iMapping);
            }
            if (iMapping.getType() == IMapping.Type.METHOD) {
                return this.getObfMethod((MappingMethod)iMapping);
            }
        }
        return new ObfuscationData();
    }

    @Override
    public ObfuscationData getObfMethodRecursive(MemberInfo memberInfo) {
        return this.getObfEntryRecursive(memberInfo);
    }

    @Override
    public ObfuscationData getObfMethod(MemberInfo memberInfo) {
        return this.getRemappedMethod(memberInfo, "<init>".equals(memberInfo.name));
    }

    @Override
    public ObfuscationData getRemappedMethod(MemberInfo memberInfo) {
        return this.getRemappedMethod(memberInfo, true);
    }

    private ObfuscationData getRemappedMethod(MemberInfo memberInfo, boolean bl) {
        ObfuscationData obfuscationData = new ObfuscationData();
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MappingMethod mappingMethod = obfuscationEnvironment.getObfMethod(memberInfo);
            if (mappingMethod == null) continue;
            obfuscationData.add(obfuscationEnvironment.getType(), mappingMethod);
        }
        if (!obfuscationData.isEmpty() || !bl) {
            return obfuscationData;
        }
        return this.remapDescriptor(obfuscationData, memberInfo);
    }

    @Override
    public ObfuscationData getObfMethod(MappingMethod mappingMethod) {
        return this.getRemappedMethod(mappingMethod, "<init>".equals(mappingMethod.getSimpleName()));
    }

    @Override
    public ObfuscationData getRemappedMethod(MappingMethod mappingMethod) {
        return this.getRemappedMethod(mappingMethod, true);
    }

    private ObfuscationData getRemappedMethod(MappingMethod mappingMethod, boolean bl) {
        ObfuscationData obfuscationData = new ObfuscationData();
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MappingMethod mappingMethod2 = obfuscationEnvironment.getObfMethod(mappingMethod);
            if (mappingMethod2 == null) continue;
            obfuscationData.add(obfuscationEnvironment.getType(), mappingMethod2);
        }
        if (!obfuscationData.isEmpty() || !bl) {
            return obfuscationData;
        }
        return this.remapDescriptor(obfuscationData, new MemberInfo(mappingMethod));
    }

    public ObfuscationData remapDescriptor(ObfuscationData obfuscationData, MemberInfo memberInfo) {
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MemberInfo memberInfo2 = obfuscationEnvironment.remapDescriptor(memberInfo);
            if (memberInfo2 == null) continue;
            obfuscationData.add(obfuscationEnvironment.getType(), memberInfo2.asMethodMapping());
        }
        return obfuscationData;
    }

    @Override
    public ObfuscationData getObfFieldRecursive(MemberInfo memberInfo) {
        return this.getObfEntryRecursive(memberInfo);
    }

    @Override
    public ObfuscationData getObfField(MemberInfo memberInfo) {
        return this.getObfField(memberInfo.asFieldMapping());
    }

    @Override
    public ObfuscationData getObfField(MappingField mappingField) {
        ObfuscationData obfuscationData = new ObfuscationData();
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MappingField mappingField2 = obfuscationEnvironment.getObfField(mappingField);
            if (mappingField2 == null) continue;
            if (mappingField2.getDesc() == null && mappingField.getDesc() != null) {
                mappingField2 = mappingField2.transform(obfuscationEnvironment.remapDescriptor(mappingField.getDesc()));
            }
            obfuscationData.add(obfuscationEnvironment.getType(), mappingField2);
        }
        return obfuscationData;
    }

    @Override
    public ObfuscationData getObfClass(TypeHandle typeHandle) {
        return this.getObfClass(typeHandle.getName());
    }

    @Override
    public ObfuscationData getObfClass(String string) {
        ObfuscationData obfuscationData = new ObfuscationData(string);
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            String string2 = obfuscationEnvironment.getObfClass(string);
            if (string2 == null) continue;
            obfuscationData.add(obfuscationEnvironment.getType(), string2);
        }
        return obfuscationData;
    }
}

