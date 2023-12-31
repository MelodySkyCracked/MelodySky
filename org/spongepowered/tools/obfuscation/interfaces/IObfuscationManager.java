/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation.interfaces;

import java.util.List;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

public interface IObfuscationManager {
    public void init();

    public IObfuscationDataProvider getDataProvider();

    public IReferenceManager getReferenceManager();

    public IMappingConsumer createMappingConsumer();

    public List getEnvironments();

    public void writeMappings();

    public void writeReferences();
}

