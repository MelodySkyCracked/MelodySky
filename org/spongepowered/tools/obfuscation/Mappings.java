/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

class Mappings
implements IMappingConsumer {
    private final Map fieldMappings = new HashMap();
    private final Map methodMappings = new HashMap();
    private UniqueMappings unique;

    public Mappings() {
        this.init();
    }

    private void init() {
        for (ObfuscationType obfuscationType : ObfuscationType.types()) {
            this.fieldMappings.put(obfuscationType, new IMappingConsumer.MappingSet());
            this.methodMappings.put(obfuscationType, new IMappingConsumer.MappingSet());
        }
    }

    public IMappingConsumer asUnique() {
        if (this.unique == null) {
            this.unique = new UniqueMappings(this);
        }
        return this.unique;
    }

    @Override
    public IMappingConsumer.MappingSet getFieldMappings(ObfuscationType obfuscationType) {
        IMappingConsumer.MappingSet mappingSet = (IMappingConsumer.MappingSet)this.fieldMappings.get(obfuscationType);
        return mappingSet != null ? mappingSet : new IMappingConsumer.MappingSet();
    }

    @Override
    public IMappingConsumer.MappingSet getMethodMappings(ObfuscationType obfuscationType) {
        IMappingConsumer.MappingSet mappingSet = (IMappingConsumer.MappingSet)this.methodMappings.get(obfuscationType);
        return mappingSet != null ? mappingSet : new IMappingConsumer.MappingSet();
    }

    @Override
    public void clear() {
        this.fieldMappings.clear();
        this.methodMappings.clear();
        if (this.unique != null) {
            this.unique.clearMaps();
        }
        this.init();
    }

    @Override
    public void addFieldMapping(ObfuscationType obfuscationType, MappingField mappingField, MappingField mappingField2) {
        IMappingConsumer.MappingSet mappingSet = (IMappingConsumer.MappingSet)this.fieldMappings.get(obfuscationType);
        if (mappingSet == null) {
            mappingSet = new IMappingConsumer.MappingSet();
            this.fieldMappings.put(obfuscationType, mappingSet);
        }
        mappingSet.add(new IMappingConsumer.MappingSet.Pair(mappingField, mappingField2));
    }

    @Override
    public void addMethodMapping(ObfuscationType obfuscationType, MappingMethod mappingMethod, MappingMethod mappingMethod2) {
        IMappingConsumer.MappingSet mappingSet = (IMappingConsumer.MappingSet)this.methodMappings.get(obfuscationType);
        if (mappingSet == null) {
            mappingSet = new IMappingConsumer.MappingSet();
            this.methodMappings.put(obfuscationType, mappingSet);
        }
        mappingSet.add(new IMappingConsumer.MappingSet.Pair(mappingMethod, mappingMethod2));
    }

    static class UniqueMappings
    implements IMappingConsumer {
        private final IMappingConsumer mappings;
        private final Map fields = new HashMap();
        private final Map methods = new HashMap();

        public UniqueMappings(IMappingConsumer iMappingConsumer) {
            this.mappings = iMappingConsumer;
        }

        @Override
        public void clear() {
            this.clearMaps();
            this.mappings.clear();
        }

        protected void clearMaps() {
            this.fields.clear();
            this.methods.clear();
        }

        @Override
        public void addFieldMapping(ObfuscationType obfuscationType, MappingField mappingField, MappingField mappingField2) {
            UniqueMappings uniqueMappings = this;
            ObfuscationType obfuscationType2 = obfuscationType;
            MappingField mappingField3 = mappingField;
            MappingField mappingField4 = mappingField2;
            if (this.fields == null) {
                this.mappings.addFieldMapping(obfuscationType, mappingField, mappingField2);
            }
        }

        @Override
        public void addMethodMapping(ObfuscationType obfuscationType, MappingMethod mappingMethod, MappingMethod mappingMethod2) {
            UniqueMappings uniqueMappings = this;
            ObfuscationType obfuscationType2 = obfuscationType;
            MappingMethod mappingMethod3 = mappingMethod;
            MappingMethod mappingMethod4 = mappingMethod2;
            if (this.methods == null) {
                this.mappings.addMethodMapping(obfuscationType, mappingMethod, mappingMethod2);
            }
        }

        @Override
        public IMappingConsumer.MappingSet getFieldMappings(ObfuscationType obfuscationType) {
            return this.mappings.getFieldMappings(obfuscationType);
        }

        @Override
        public IMappingConsumer.MappingSet getMethodMappings(ObfuscationType obfuscationType) {
            return this.mappings.getMethodMappings(obfuscationType);
        }
    }

    public static class MappingConflictException
    extends RuntimeException {
        private static final long serialVersionUID = 1L;
        private final IMapping oldMapping;
        private final IMapping newMapping;

        public MappingConflictException(IMapping iMapping, IMapping iMapping2) {
            this.oldMapping = iMapping;
            this.newMapping = iMapping2;
        }

        public IMapping getOld() {
            return this.oldMapping;
        }

        public IMapping getNew() {
            return this.newMapping;
        }
    }
}

