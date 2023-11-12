/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.LaunchClassLoader
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.launch.platform;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.Blackboard;
import org.spongepowered.asm.launch.platform.IMixinPlatformAgent;
import org.spongepowered.asm.launch.platform.MixinPlatformManager;

public class MixinContainer {
    public static final List agentClasses = new ArrayList();
    private final Logger logger = LogManager.getLogger((String)"mixin");
    private final URI uri;
    private final List agents = new ArrayList();

    public MixinContainer(MixinPlatformManager mixinPlatformManager, URI uRI) {
        this.uri = uRI;
        for (String string : agentClasses) {
            try {
                Class<?> clazz = Class.forName(string);
                Constructor<?> constructor = clazz.getDeclaredConstructor(MixinPlatformManager.class, URI.class);
                this.logger.debug("Instancing new {} for {}", new Object[]{clazz.getSimpleName(), this.uri});
                IMixinPlatformAgent iMixinPlatformAgent = (IMixinPlatformAgent)constructor.newInstance(mixinPlatformManager, uRI);
                this.agents.add(iMixinPlatformAgent);
            }
            catch (Exception exception) {
                this.logger.catching((Throwable)exception);
            }
        }
    }

    public URI getURI() {
        return this.uri;
    }

    public Collection getPhaseProviders() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
            String string = iMixinPlatformAgent.getPhaseProvider();
            if (string == null) continue;
            arrayList.add(string);
        }
        return arrayList;
    }

    public void prepare() {
        for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
            this.logger.debug("Processing prepare() for {}", new Object[]{iMixinPlatformAgent});
            iMixinPlatformAgent.prepare();
        }
    }

    public void initPrimaryContainer() {
        for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
            this.logger.debug("Processing launch tasks for {}", new Object[]{iMixinPlatformAgent});
            iMixinPlatformAgent.initPrimaryContainer();
        }
    }

    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
        for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
            this.logger.debug("Processing injectIntoClassLoader() for {}", new Object[]{iMixinPlatformAgent});
            iMixinPlatformAgent.injectIntoClassLoader(launchClassLoader);
        }
    }

    public String getLaunchTarget() {
        for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
            String string = iMixinPlatformAgent.getLaunchTarget();
            if (string == null) continue;
            return string;
        }
        return null;
    }

    static {
        Blackboard.put("mixin.agents", agentClasses);
        agentClasses.add("org.spongepowered.asm.launch.platform.MixinPlatformAgentFML");
        agentClasses.add("org.spongepowered.asm.launch.platform.MixinPlatformAgentDefault");
    }
}

