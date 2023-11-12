/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  com.google.common.io.Files
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Charsets;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.IMixinTransformerModule;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.mixin.transformer.TargetClassContext;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class MixinTransformerModuleInterfaceChecker
implements IMixinTransformerModule {
    private static final Logger logger = LogManager.getLogger((String)"mixin");
    private final File csv;
    private final File report;
    private final Multimap interfaceMethods = HashMultimap.create();

    public MixinTransformerModuleInterfaceChecker() {
        File file = new File(MixinTransformer.DEBUG_OUTPUT, "audit");
        file.mkdirs();
        this.csv = new File(file, "mixin_implementation_report.csv");
        this.report = new File(file, "mixin_implementation_report.txt");
        try {
            Files.write((CharSequence)"Class,Method,Signature,Interface\n", (File)this.csv, (Charset)Charsets.ISO_8859_1);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        try {
            String string = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Files.write((CharSequence)("Mixin Implementation Report generated on " + string + "\n"), (File)this.report, (Charset)Charsets.ISO_8859_1);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public void preApply(TargetClassContext targetClassContext) {
        ClassInfo classInfo = targetClassContext.getClassInfo();
        for (ClassInfo.Method method : classInfo.getInterfaceMethods(false)) {
            this.interfaceMethods.put((Object)classInfo, (Object)method);
        }
    }

    @Override
    public void postApply(TargetClassContext targetClassContext) {
        ClassInfo classInfo = targetClassContext.getClassInfo();
        if (classInfo.isAbstract() && !MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS_STRICT)) {
            logger.info("{} is skipping abstract target {}", new Object[]{this.getClass().getSimpleName(), targetClassContext});
            return;
        }
        String string = classInfo.getName().replace('/', '.');
        int n = 0;
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        prettyPrinter.add("Class: %s", string).hr();
        prettyPrinter.add("%-32s %-47s  %s", "Return Type", "Missing Method", "From Interface").hr();
        Set set = classInfo.getInterfaceMethods(true);
        HashSet hashSet = new HashSet(classInfo.getSuperClass().getInterfaceMethods(true));
        hashSet.addAll(this.interfaceMethods.removeAll((Object)classInfo));
        for (ClassInfo.Method method : set) {
            ClassInfo.Method method2 = classInfo.findMethodInHierarchy(method.getName(), method.getDesc(), ClassInfo.SearchType.ALL_CLASSES, ClassInfo.Traversal.ALL);
            if (method2 != null && !method2.isAbstract() || hashSet.contains(method)) continue;
            if (n > 0) {
                prettyPrinter.add();
            }
            SignaturePrinter signaturePrinter = new SignaturePrinter(method.getName(), method.getDesc()).setModifiers("");
            String string2 = method.getOwner().getName().replace('/', '.');
            ++n;
            prettyPrinter.add("%-32s%s", signaturePrinter.getReturnType(), signaturePrinter);
            prettyPrinter.add("%-80s  %s", "", string2);
            this.appendToCSVReport(string, method, string2);
        }
        if (n > 0) {
            prettyPrinter.hr().add("%82s%s: %d", "", "Total unimplemented", n);
            prettyPrinter.print(System.err);
            this.appendToTextReport(prettyPrinter);
        }
    }

    private void appendToCSVReport(String string, ClassInfo.Method method, String string2) {
        try {
            Files.append((CharSequence)String.format("%s,%s,%s,%s\n", string, method.getName(), method.getDesc(), string2), (File)this.csv, (Charset)Charsets.ISO_8859_1);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void appendToTextReport(PrettyPrinter prettyPrinter) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(this.report, true);
            PrintStream printStream = new PrintStream(fileOutputStream);
            printStream.print("\n");
            prettyPrinter.print(printStream);
        }
        catch (Exception exception) {
            IOUtils.closeQuietly(fileOutputStream);
        }
        IOUtils.closeQuietly((OutputStream)fileOutputStream);
    }
}

