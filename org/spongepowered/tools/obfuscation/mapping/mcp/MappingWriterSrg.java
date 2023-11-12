/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation.mapping.mcp;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.common.MappingWriter;

public class MappingWriterSrg
extends MappingWriter {
    public MappingWriterSrg(Messager messager, Filer filer) {
        super(messager, filer);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void write(String string, ObfuscationType obfuscationType, IMappingConsumer.MappingSet mappingSet, IMappingConsumer.MappingSet mappingSet2) {
        if (string == null) {
            return;
        }
        PrintWriter printWriter = null;
        try {
            printWriter = this.openFileWriter(string, obfuscationType + " output SRGs");
            this.writeFieldMappings(printWriter, mappingSet);
            this.writeMethodMappings(printWriter, mappingSet2);
            if (printWriter == null) return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            if (printWriter == null) return;
            try {
                printWriter.close();
                return;
            }
            catch (Exception exception) {}
            return;
        }
        try {
            printWriter.close();
            return;
        }
        catch (Exception exception) {}
    }

    protected void writeFieldMappings(PrintWriter printWriter, IMappingConsumer.MappingSet mappingSet) {
        for (IMappingConsumer.MappingSet.Pair pair : mappingSet) {
            printWriter.println(this.formatFieldMapping(pair));
        }
    }

    protected void writeMethodMappings(PrintWriter printWriter, IMappingConsumer.MappingSet mappingSet) {
        for (IMappingConsumer.MappingSet.Pair pair : mappingSet) {
            printWriter.println(this.formatMethodMapping(pair));
        }
    }

    protected String formatFieldMapping(IMappingConsumer.MappingSet.Pair pair) {
        return String.format("FD: %s/%s %s/%s", ((MappingField)pair.from).getOwner(), ((MappingField)pair.from).getName(), ((MappingField)pair.to).getOwner(), ((MappingField)pair.to).getName());
    }

    protected String formatMethodMapping(IMappingConsumer.MappingSet.Pair pair) {
        return String.format("MD: %s %s %s %s", ((MappingMethod)pair.from).getName(), ((MappingMethod)pair.from).getDesc(), ((MappingMethod)pair.to).getName(), ((MappingMethod)pair.to).getDesc());
    }
}

