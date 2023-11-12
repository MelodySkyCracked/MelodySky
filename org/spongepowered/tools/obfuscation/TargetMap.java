/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 */
package org.spongepowered.tools.obfuscation;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.AnnotatedMixin;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;

public class TargetMap
extends HashMap {
    private static final long serialVersionUID = 1L;
    private final String sessionId;

    private TargetMap() {
        this(String.valueOf(System.currentTimeMillis()));
    }

    private TargetMap(String string) {
        this.sessionId = string;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void registerTargets(AnnotatedMixin annotatedMixin) {
        this.registerTargets(annotatedMixin.getTargets(), annotatedMixin.getHandle());
    }

    public void registerTargets(List list, TypeHandle typeHandle) {
        for (TypeHandle typeHandle2 : list) {
            this.addMixin(typeHandle2, typeHandle);
        }
    }

    public void addMixin(TypeHandle typeHandle, TypeHandle typeHandle2) {
        this.addMixin(typeHandle.getReference(), typeHandle2.getReference());
    }

    public void addMixin(String string, String string2) {
        this.addMixin(new TypeReference(string), new TypeReference(string2));
    }

    public void addMixin(TypeReference typeReference, TypeReference typeReference2) {
        Set set = this.getMixinsFor(typeReference);
        set.add(typeReference2);
    }

    public Collection getMixinsTargeting(TypeElement typeElement) {
        return this.getMixinsTargeting(new TypeHandle(typeElement));
    }

    public Collection getMixinsTargeting(TypeHandle typeHandle) {
        return this.getMixinsTargeting(typeHandle.getReference());
    }

    public Collection getMixinsTargeting(TypeReference typeReference) {
        return Collections.unmodifiableCollection(this.getMixinsFor(typeReference));
    }

    private Set getMixinsFor(TypeReference typeReference) {
        HashSet hashSet = (HashSet)this.get(typeReference);
        if (hashSet == null) {
            hashSet = new HashSet();
            this.put(typeReference, hashSet);
        }
        return hashSet;
    }

    public void readImports(File file) throws IOException {
        if (!file.isFile()) {
            return;
        }
        for (String string : Files.readLines((File)file, (Charset)Charset.defaultCharset())) {
            String[] stringArray = string.split("\t");
            if (stringArray.length != 2) continue;
            this.addMixin(stringArray[1], stringArray[0]);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void write(boolean bl) {
        ObjectOutputStream objectOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            File file = TargetMap.getSessionFile(this.sessionId);
            if (bl) {
                file.deleteOnExit();
            }
            fileOutputStream = new FileOutputStream(file, true);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            if (objectOutputStream == null) return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            if (objectOutputStream == null) return;
            try {
                objectOutputStream.close();
                return;
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            return;
        }
        try {
            objectOutputStream.close();
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    private static TargetMap read(File file) {
        TargetMap targetMap;
        block7: {
            ObjectInputStream objectInputStream = null;
            FileInputStream fileInputStream = null;
            try {
                TargetMap targetMap2;
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                targetMap = targetMap2 = (TargetMap)objectInputStream.readObject();
                if (objectInputStream == null) break block7;
            }
            catch (Exception exception) {
                block8: {
                    exception.printStackTrace();
                    if (objectInputStream == null) break block8;
                    try {
                        objectInputStream.close();
                    }
                    catch (IOException iOException) {
                        iOException.printStackTrace();
                    }
                }
                return null;
            }
            try {
                objectInputStream.close();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        return targetMap;
    }

    public static TargetMap create(String string) {
        TargetMap targetMap;
        File file;
        if (string != null && (file = TargetMap.getSessionFile(string)).exists() && (targetMap = TargetMap.read(file)) != null) {
            return targetMap;
        }
        return new TargetMap();
    }

    private static File getSessionFile(String string) {
        File file = new File(System.getProperty("java.io.tmpdir"));
        return new File(file, String.format("mixin-targetdb-%s.tmp", string));
    }
}

