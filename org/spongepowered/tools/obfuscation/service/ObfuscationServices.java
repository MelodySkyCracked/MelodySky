/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.tools.obfuscation.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import javax.tools.Diagnostic;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.service.IObfuscationService;
import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;

public class ObfuscationServices {
    private static ObfuscationServices instance;
    private final ServiceLoader serviceLoader;
    private final Set services = new HashSet();

    private ObfuscationServices() {
        this.serviceLoader = ServiceLoader.load(IObfuscationService.class, this.getClass().getClassLoader());
    }

    public static ObfuscationServices getInstance() {
        if (instance == null) {
            instance = new ObfuscationServices();
        }
        return instance;
    }

    public void initProviders(IMixinAnnotationProcessor iMixinAnnotationProcessor) {
        try {
            for (IObfuscationService iObfuscationService : this.serviceLoader) {
                if (this.services.contains(iObfuscationService)) continue;
                this.services.add(iObfuscationService);
                String string = iObfuscationService.getClass().getSimpleName();
                Collection collection = iObfuscationService.getObfuscationTypes();
                if (collection == null) continue;
                for (ObfuscationTypeDescriptor obfuscationTypeDescriptor : collection) {
                    try {
                        ObfuscationType obfuscationType = ObfuscationType.create(obfuscationTypeDescriptor, iMixinAnnotationProcessor);
                        iMixinAnnotationProcessor.printMessage(Diagnostic.Kind.NOTE, string + " supports type: \"" + obfuscationType + "\"");
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        catch (ServiceConfigurationError serviceConfigurationError) {
            iMixinAnnotationProcessor.printMessage(Diagnostic.Kind.ERROR, serviceConfigurationError.getClass().getSimpleName() + ": " + serviceConfigurationError.getMessage());
            serviceConfigurationError.printStackTrace();
        }
    }

    public Set getSupportedOptions() {
        HashSet hashSet = new HashSet();
        for (IObfuscationService iObfuscationService : this.serviceLoader) {
            Set set = iObfuscationService.getSupportedOptions();
            if (set == null) continue;
            hashSet.addAll(set);
        }
        return hashSet;
    }

    public IObfuscationService getService(Class clazz) {
        for (IObfuscationService iObfuscationService : this.serviceLoader) {
            if (!clazz.getName().equals(iObfuscationService.getClass().getName())) continue;
            return iObfuscationService;
        }
        return null;
    }
}

