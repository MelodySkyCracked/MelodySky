/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jcraft.jogg.Packet
 *  com.jcraft.jogg.Page
 *  com.jcraft.jogg.StreamState
 *  com.jcraft.jogg.SyncState
 *  com.jcraft.jorbis.Block
 *  com.jcraft.jorbis.Comment
 *  com.jcraft.jorbis.DspState
 *  com.jcraft.jorbis.Info
 *  org.lwjgl.BufferUtils
 */
package org.newdawn.slick.openal;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.openal.AudioInputStream;
import org.newdawn.slick.util.Log;

public class OggInputStream
extends InputStream
implements AudioInputStream {
    private int convsize = 16384;
    private byte[] convbuffer = new byte[this.convsize];
    private InputStream input;
    private Info oggInfo = new Info();
    private boolean endOfStream;
    private SyncState syncState = new SyncState();
    private StreamState streamState = new StreamState();
    private Page page = new Page();
    private Packet packet = new Packet();
    private Comment comment = new Comment();
    private DspState dspState = new DspState();
    private Block vorbisBlock = new Block(this.dspState);
    byte[] buffer;
    int bytes = 0;
    boolean bigEndian = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
    boolean endOfBitStream = true;
    boolean inited = false;
    private int readIndex;
    private ByteBuffer pcmBuffer = BufferUtils.createByteBuffer((int)2048000);
    private int total;

    public OggInputStream(InputStream inputStream) throws IOException {
        this.input = inputStream;
        this.total = inputStream.available();
        this.init();
    }

    public int getLength() {
        return this.total;
    }

    @Override
    public int getChannels() {
        return this.oggInfo.channels;
    }

    @Override
    public int getRate() {
        return this.oggInfo.rate;
    }

    private void init() throws IOException {
        this.initVorbis();
        this.readPCM();
    }

    @Override
    public int available() {
        return this.endOfStream ? 0 : 1;
    }

    private void initVorbis() {
        this.syncState.init();
    }

    private void readPCM() throws IOException {
        boolean bl = false;
        while (true) {
            if (this.endOfBitStream) {
                if (this == null) break;
                this.endOfBitStream = false;
            }
            if (!this.inited) {
                this.inited = true;
                return;
            }
            float[][][] fArrayArray = new float[1][][];
            int[] nArray = new int[this.oggInfo.channels];
            while (!this.endOfBitStream) {
                int n;
                while (!this.endOfBitStream && (n = this.syncState.pageout(this.page)) != 0) {
                    if (n == -1) {
                        Log.error("Corrupt or missing data in bitstream; continuing...");
                        continue;
                    }
                    this.streamState.pagein(this.page);
                    while ((n = this.streamState.packetout(this.packet)) != 0) {
                        int n2;
                        if (n == -1) continue;
                        if (this.vorbisBlock.synthesis(this.packet) == 0) {
                            this.dspState.synthesis_blockin(this.vorbisBlock);
                        }
                        while ((n2 = this.dspState.synthesis_pcmout((float[][][])fArrayArray, nArray)) > 0) {
                            int n3;
                            float[][] fArray = fArrayArray[0];
                            int n4 = n2 < this.convsize ? n2 : this.convsize;
                            for (n3 = 0; n3 < this.oggInfo.channels; ++n3) {
                                int n5 = n3 * 2;
                                int n6 = nArray[n3];
                                for (int i = 0; i < n4; ++i) {
                                    int n7 = (int)((double)fArray[n3][n6 + i] * 32767.0);
                                    if (n7 > Short.MAX_VALUE) {
                                        n7 = Short.MAX_VALUE;
                                    }
                                    if (n7 < Short.MIN_VALUE) {
                                        n7 = Short.MIN_VALUE;
                                    }
                                    if (n7 < 0) {
                                        n7 |= 0x8000;
                                    }
                                    if (this.bigEndian) {
                                        this.convbuffer[n5] = (byte)(n7 >>> 8);
                                        this.convbuffer[n5 + 1] = (byte)n7;
                                    } else {
                                        this.convbuffer[n5] = (byte)n7;
                                        this.convbuffer[n5 + 1] = (byte)(n7 >>> 8);
                                    }
                                    n5 += 2 * this.oggInfo.channels;
                                }
                            }
                            n3 = 2 * this.oggInfo.channels * n4;
                            if (n3 >= this.pcmBuffer.remaining()) {
                                Log.warn("Read block from OGG that was too big to be buffered: " + n3);
                            } else {
                                this.pcmBuffer.put(this.convbuffer, 0, n3);
                            }
                            bl = true;
                            this.dspState.synthesis_read(n4);
                        }
                    }
                    if (this.page.eos() != 0) {
                        this.endOfBitStream = true;
                    }
                    if (this.endOfBitStream || !bl) continue;
                    return;
                }
                if (this.endOfBitStream) continue;
                this.bytes = 0;
                n = this.syncState.buffer(4096);
                if (n >= 0) {
                    this.buffer = this.syncState.data;
                    try {
                        this.bytes = this.input.read(this.buffer, n, 4096);
                    }
                    catch (Exception exception) {
                        Log.error("Failure during vorbis decoding");
                        Log.error(exception);
                        this.endOfStream = true;
                        return;
                    }
                } else {
                    this.bytes = 0;
                }
                this.syncState.wrote(this.bytes);
                if (this.bytes != 0) continue;
                this.endOfBitStream = true;
            }
            this.streamState.clear();
            this.vorbisBlock.clear();
            this.dspState.clear();
            this.oggInfo.clear();
        }
        this.syncState.clear();
        this.endOfStream = true;
    }

    @Override
    public int read() throws IOException {
        if (this.readIndex >= this.pcmBuffer.position()) {
            this.pcmBuffer.clear();
            this.readPCM();
            this.readIndex = 0;
        }
        if (this.readIndex >= this.pcmBuffer.position()) {
            return -1;
        }
        int n = this.pcmBuffer.get(this.readIndex);
        if (n < 0) {
            n = 256 + n;
        }
        ++this.readIndex;
        return n;
    }

    @Override
    public boolean atEnd() {
        return this.endOfStream && this.readIndex >= this.pcmBuffer.position();
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        for (int i = 0; i < n2; ++i) {
            try {
                int n3 = this.read();
                if (n3 < 0) {
                    if (i == 0) {
                        return -1;
                    }
                    return i;
                }
                byArray[i] = (byte)n3;
                continue;
            }
            catch (IOException iOException) {
                Log.error(iOException);
                return i;
            }
        }
        return n2;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    @Override
    public void close() throws IOException {
    }
}

