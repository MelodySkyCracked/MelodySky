/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class ZipUtils {
    public static List getZipEntriesFromZipInputStream(ZipInputStream zipInputStream, List list) throws IOException {
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            list.add(zipEntry);
        }
        return list;
    }

    public static List getZipEntriesFromZipFile(ZipFile zipFile, List list) {
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        while (enumeration.hasMoreElements()) {
            ZipEntry zipEntry = enumeration.nextElement();
            list.add(zipEntry);
        }
        return list;
    }

    public static void addZipEntryToZipOutputStream(ZipEntry zipEntry, byte[] byArray, ZipOutputStream zipOutputStream) throws IOException {
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(byArray);
        zipOutputStream.closeEntry();
    }
}

