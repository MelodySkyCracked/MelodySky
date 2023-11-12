/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.chunk.storage.RegionFile
 */
package xyz.Melody.injection.mixins.optimizations;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import net.minecraft.world.chunk.storage.RegionFile;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={RegionFile.class})
public abstract class MixinRegionFile {
    @Shadow
    @Final
    private RandomAccessFile field_76719_c;
    @Shadow
    @Final
    private List field_76714_f;

    @Shadow
    protected abstract int func_76707_e(int var1, int var2);

    @Shadow
    protected abstract boolean func_76705_d(int var1, int var2);

    @Overwrite
    public synchronized DataInputStream func_76704_a(int n, int n2) {
        if (this.func_76705_d(n, n2)) {
            return null;
        }
        try {
            int n3 = this.func_76707_e(n, n2);
            if (n3 == 0) {
                return null;
            }
            int n4 = n3 >> 8;
            int n5 = n3 & 0xFF;
            if (n4 + n5 > this.field_76714_f.size()) {
                return null;
            }
            byte[] byArray = new byte[4096 * n5];
            this.field_76719_c.seek(n4 * 4096);
            this.field_76719_c.readFully(byArray);
            ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
            int n6 = byteBuffer.getInt();
            if (n6 > byArray.length) {
                return null;
            }
            if (n6 <= 0) {
                return null;
            }
            byte by = byteBuffer.get();
            if (by == 1) {
                return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(byArray, byteBuffer.position(), byArray.length))));
            }
            if (by == 2) {
                return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(byArray, byteBuffer.position(), byArray.length))));
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return null;
    }

    @Overwrite
    private void func_76712_a(int n, byte[] byArray, int n2) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2 + 5);
        byteBuffer.putInt(n2 + 1);
        byteBuffer.put((byte)2);
        byteBuffer.put(byArray, 0, n2);
        this.field_76719_c.seek(n * 4096);
        this.field_76719_c.write(byteBuffer.array());
    }
}

