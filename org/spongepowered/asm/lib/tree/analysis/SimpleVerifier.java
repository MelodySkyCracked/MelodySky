/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.BasicVerifier;
import org.spongepowered.asm.lib.tree.analysis.Value;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SimpleVerifier
extends BasicVerifier {
    private final Type currentClass;
    private final Type currentSuperClass;
    private final List currentClassInterfaces;
    private final boolean isInterface;
    private ClassLoader loader = this.getClass().getClassLoader();

    public SimpleVerifier() {
        this(null, null, false);
    }

    public SimpleVerifier(Type type, Type type2, boolean bl) {
        this(type, type2, null, bl);
    }

    public SimpleVerifier(Type type, Type type2, List list, boolean bl) {
        this(327680, type, type2, list, bl);
    }

    protected SimpleVerifier(int n, Type type, Type type2, List list, boolean bl) {
        super(n);
        this.currentClass = type;
        this.currentSuperClass = type2;
        this.currentClassInterfaces = list;
        this.isInterface = bl;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }

    public BasicValue newValue(Type type) {
        BasicValue basicValue;
        boolean bl;
        if (type == null) {
            return BasicValue.UNINITIALIZED_VALUE;
        }
        boolean bl2 = bl = type.getSort() == 9;
        if (bl) {
            switch (type.getElementType().getSort()) {
                case 1: 
                case 2: 
                case 3: 
                case 4: {
                    return new BasicValue(type);
                }
            }
        }
        if (BasicValue.REFERENCE_VALUE.equals(basicValue = super.newValue(type))) {
            if (bl) {
                basicValue = this.newValue(type.getElementType());
                String string = basicValue.getType().getDescriptor();
                for (int i = 0; i < type.getDimensions(); ++i) {
                    string = '[' + string;
                }
                basicValue = new BasicValue(Type.getType(string));
            } else {
                basicValue = new BasicValue(type);
            }
        }
        return basicValue;
    }

    protected boolean isArrayValue(BasicValue basicValue) {
        Type type = basicValue.getType();
        return type != null && ("Lnull;".equals(type.getDescriptor()) || type.getSort() == 9);
    }

    protected BasicValue getElementValue(BasicValue basicValue) throws AnalyzerException {
        Type type = basicValue.getType();
        if (type != null) {
            if (type.getSort() == 9) {
                return this.newValue(Type.getType(type.getDescriptor().substring(1)));
            }
            if ("Lnull;".equals(type.getDescriptor())) {
                return basicValue;
            }
        }
        throw new Error("Internal error");
    }

    protected boolean isSubTypeOf(BasicValue basicValue, BasicValue basicValue2) {
        Type type = basicValue2.getType();
        Type type2 = basicValue.getType();
        switch (type.getSort()) {
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                return type2.equals(type);
            }
            case 9: 
            case 10: {
                if ("Lnull;".equals(type2.getDescriptor())) {
                    return true;
                }
                if (type2.getSort() == 10 || type2.getSort() == 9) {
                    return this.isAssignableFrom(type, type2);
                }
                return false;
            }
        }
        throw new Error("Internal error");
    }

    /*
     * Exception decompiling
     */
    public BasicValue merge(BasicValue var1, BasicValue var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl57 : ALOAD_3 - null : trying to set 7 previously set to 4
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

    protected Type getSuperClass(Type type) {
        if (this.currentClass != null && type.equals(this.currentClass)) {
            return this.currentSuperClass;
        }
        Class clazz = this.getClass(type).getSuperclass();
        return clazz == null ? null : Type.getType(clazz);
    }

    protected Class getClass(Type type) {
        try {
            if (type.getSort() == 9) {
                return Class.forName(type.getDescriptor().replace('/', '.'), false, this.loader);
            }
            return Class.forName(type.getClassName(), false, this.loader);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException(classNotFoundException.toString());
        }
    }

    public Value merge(Value value, Value value2) {
        return this.merge((BasicValue)value, (BasicValue)value2);
    }

    public Value newValue(Type type) {
        return this.newValue(type);
    }
}

