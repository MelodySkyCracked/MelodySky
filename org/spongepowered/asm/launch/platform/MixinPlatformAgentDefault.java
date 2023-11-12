/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.LaunchClassLoader
 */
package org.spongepowered.asm.launch.platform;

import java.net.URI;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.platform.MixinPlatformAgentAbstract;
import org.spongepowered.asm.launch.platform.MixinPlatformManager;

public class MixinPlatformAgentDefault
extends MixinPlatformAgentAbstract {
    public MixinPlatformAgentDefault(MixinPlatformManager mixinPlatformManager, URI uRI) {
        super(mixinPlatformManager, uRI);
    }

    @Override
    public void prepare() {
        Object object;
        String string;
        String string2 = this.attributes.get("MixinCompatibilityLevel");
        if (string2 != null) {
            this.manager.setCompatibilityLevel(string2);
        }
        if ((string = this.attributes.get("MixinConfigs")) != null) {
            object = string.split(",");
            int n = ((String[])object).length;
            for (int i = 0; i < n; ++i) {
                Object object2 = object[i];
                this.manager.addConfig(((String)object2).trim());
            }
        }
        if ((object = this.attributes.get("MixinTokenProviders")) != null) {
            for (String string3 : ((String)object).split(",")) {
                this.manager.addTokenProvider(string3.trim());
            }
        }
    }

    @Override
    public void initPrimaryContainer() {
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
    }

    @Override
    public String getLaunchTarget() {
        return this.attributes.get("Main-Class");
    }
}

