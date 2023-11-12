/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.LaunchClassLoader
 */
package org.spongepowered.asm.launch.platform;

import net.minecraft.launchwrapper.LaunchClassLoader;

public interface IMixinPlatformAgent {
    public String getPhaseProvider();

    public void prepare();

    public void initPrimaryContainer();

    public void injectIntoClassLoader(LaunchClassLoader var1);

    public String getLaunchTarget();
}

