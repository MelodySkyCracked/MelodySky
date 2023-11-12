/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.IClassTransformer
 *  net.minecraft.launchwrapper.Launch
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.transformer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.util.launchwrapper.LaunchClassLoaderUtil;

abstract class TreeInfo {
    private static final Logger logger = LogManager.getLogger((String)"mixin");
    private static MixinTransformer.ReEntranceState lock;

    TreeInfo() {
    }

    static void setLock(MixinTransformer.ReEntranceState reEntranceState) {
        lock = reEntranceState;
    }

    static ClassNode getClassNode(String string) throws ClassNotFoundException, IOException {
        return TreeInfo.getClassNode(TreeInfo.loadClass(string, true), 0);
    }

    protected static ClassNode getClassNode(byte[] byArray, int n) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(byArray);
        classReader.accept(classNode, n);
        return classNode;
    }

    protected static byte[] loadClass(String string, boolean bl) throws ClassNotFoundException, IOException {
        String string2 = string.replace('/', '.');
        String string3 = MixinEnvironment.getCurrentEnvironment().unmap(string2);
        byte[] byArray = TreeInfo.getClassBytes(string3, string2);
        if (bl) {
            byArray = TreeInfo.applyTransformers(string3, string2, byArray);
        }
        if (byArray == null) {
            throw new ClassNotFoundException(String.format("The specified class '%s' was not found", string2));
        }
        return byArray;
    }

    private static byte[] getClassBytes(String string, String string2) throws IOException {
        byte[] byArray;
        byte[] byArray2 = Launch.classLoader.getClassBytes(string);
        if (byArray2 != null) {
            return byArray2;
        }
        URLClassLoader uRLClassLoader = (URLClassLoader)Launch.class.getClassLoader();
        InputStream inputStream = null;
        try {
            String string3 = string2.replace('.', '/').concat(".class");
            inputStream = uRLClassLoader.getResourceAsStream(string3);
            byArray = IOUtils.toByteArray((InputStream)inputStream);
        }
        catch (Exception exception) {
            byte[] byArray3 = null;
            IOUtils.closeQuietly(inputStream);
            return byArray3;
        }
        IOUtils.closeQuietly((InputStream)inputStream);
        return byArray;
    }

    private static byte[] applyTransformers(String string, String string2, byte[] byArray) {
        if (LaunchClassLoaderUtil.forClassLoader(Launch.classLoader).isClassExcluded(string, string2)) {
            return byArray;
        }
        MixinEnvironment mixinEnvironment = MixinEnvironment.getCurrentEnvironment();
        for (IClassTransformer iClassTransformer : mixinEnvironment.getTransformers()) {
            if (lock != null) {
                lock.clear();
            }
            byArray = iClassTransformer.transform(string, string2, byArray);
            if (lock == null || !lock.isSet()) continue;
            mixinEnvironment.addTransformerExclusion(iClassTransformer.getClass().getName());
            lock.clear();
            logger.info("A re-entrant transformer '{}' was detected and will no longer process meta class data", new Object[]{iClassTransformer.getClass().getName()});
        }
        return byArray;
    }
}

