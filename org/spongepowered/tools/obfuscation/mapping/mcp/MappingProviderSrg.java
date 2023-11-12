/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  com.google.common.io.Files
 *  com.google.common.io.LineProcessor
 */
package org.spongepowered.tools.obfuscation.mapping.mcp;

import com.google.common.collect.BiMap;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.mcp.MappingFieldSrg;
import org.spongepowered.tools.obfuscation.mapping.common.MappingProvider;
import org.spongepowered.tools.obfuscation.mapping.mcp.I;

public class MappingProviderSrg
extends MappingProvider {
    public MappingProviderSrg(Messager messager, Filer filer) {
        super(messager, filer);
    }

    @Override
    public void read(File file) throws IOException {
        BiMap biMap = this.packageMap;
        BiMap biMap2 = this.classMap;
        BiMap biMap3 = this.fieldMap;
        BiMap biMap4 = this.methodMap;
        Files.readLines((File)file, (Charset)Charset.defaultCharset(), (LineProcessor)new I(this, biMap, biMap2, biMap3, biMap4, file));
    }

    @Override
    public MappingField getFieldMapping(MappingField mappingField) {
        if (mappingField.getDesc() != null) {
            mappingField = new MappingFieldSrg(mappingField);
        }
        return (MappingField)this.fieldMap.get((Object)mappingField);
    }
}

