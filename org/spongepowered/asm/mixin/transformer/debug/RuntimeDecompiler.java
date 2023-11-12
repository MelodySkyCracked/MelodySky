/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.io.Files
 *  org.apache.commons.io.FileUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.java.decompiler.main.Fernflower
 *  org.jetbrains.java.decompiler.main.extern.IBytecodeProvider
 *  org.jetbrains.java.decompiler.main.extern.IFernflowerLogger
 *  org.jetbrains.java.decompiler.main.extern.IFernflowerLogger$Severity
 *  org.jetbrains.java.decompiler.main.extern.IResultSaver
 */
package org.spongepowered.asm.mixin.transformer.debug;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.jar.Manifest;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;
import org.spongepowered.asm.mixin.transformer.debug.I;
import org.spongepowered.asm.mixin.transformer.debug.IDecompiler;

public class RuntimeDecompiler
extends IFernflowerLogger
implements IDecompiler,
IResultSaver {
    private final Map options = ImmutableMap.builder().put((Object)"din", (Object)"0").put((Object)"rbr", (Object)"0").put((Object)"dgs", (Object)"1").put((Object)"asc", (Object)"1").put((Object)"den", (Object)"1").put((Object)"hdc", (Object)"1").put((Object)"ind", (Object)"    ").build();
    private final File outputPath;
    protected final Logger logger = LogManager.getLogger((String)"fernflower");

    public RuntimeDecompiler(File file) {
        this.outputPath = file;
        if (this.outputPath.exists()) {
            try {
                FileUtils.deleteDirectory((File)this.outputPath);
            }
            catch (IOException iOException) {
                this.logger.warn("Error cleaning output directory: {}", new Object[]{iOException.getMessage()});
            }
        }
    }

    @Override
    public void decompile(File file) {
        try {
            Fernflower fernflower = new Fernflower((IBytecodeProvider)new I(this), (IResultSaver)this, this.options, (IFernflowerLogger)this);
            fernflower.getStructContext().addSpace(file, true);
            fernflower.decompileContext();
        }
        catch (Throwable throwable) {
            this.logger.warn("Decompilation error while processing {}", new Object[]{file.getName()});
        }
    }

    public void saveFolder(String string) {
    }

    public void saveClassFile(String string, String string2, String string3, String string4, int[] nArray) {
        File file = new File(this.outputPath, string2 + ".java");
        file.getParentFile().mkdirs();
        try {
            this.logger.info("Writing {}", new Object[]{file.getAbsolutePath()});
            Files.write((CharSequence)string4, (File)file, (Charset)Charsets.UTF_8);
        }
        catch (IOException iOException) {
            this.writeMessage("Cannot write source file " + file, iOException);
        }
    }

    public void startReadingClass(String string) {
        this.logger.info("Decompiling {}", new Object[]{string});
    }

    public void writeMessage(String string, IFernflowerLogger.Severity severity) {
        this.logger.info(string);
    }

    public void writeMessage(String string, Throwable throwable) {
        this.logger.warn("{} {}: {}", new Object[]{string, throwable.getClass().getSimpleName(), throwable.getMessage()});
    }

    public void copyFile(String string, String string2, String string3) {
    }

    public void createArchive(String string, String string2, Manifest manifest) {
    }

    public void saveDirEntry(String string, String string2, String string3) {
    }

    public void copyEntry(String string, String string2, String string3, String string4) {
    }

    public void saveClassEntry(String string, String string2, String string3, String string4, String string5) {
    }

    public void closeArchive(String string, String string2) {
    }
}

