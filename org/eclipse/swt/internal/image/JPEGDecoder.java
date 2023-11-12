/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.image;

import java.io.InputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.image.LEDataInputStream;

public class JPEGDecoder {
    static final int DCTSIZE = 8;
    static final int DCTSIZE2 = 64;
    static final int NUM_QUANT_TBLS = 4;
    static final int NUM_HUFF_TBLS = 4;
    static final int NUM_ARITH_TBLS = 16;
    static final int MAX_COMPS_IN_SCAN = 4;
    static final int MAX_COMPONENTS = 10;
    static final int MAX_SAMP_FACTOR = 4;
    static final int D_MAX_BLOCKS_IN_MCU = 10;
    static final int HUFF_LOOKAHEAD = 8;
    static final int MAX_Q_COMPS = 4;
    static final int IFAST_SCALE_BITS = 2;
    static final int MAXJSAMPLE = 255;
    static final int CENTERJSAMPLE = 128;
    static final int MIN_GET_BITS = 25;
    static final int INPUT_BUFFER_SIZE = 4096;
    static final int SCALEBITS = 16;
    static final int ONE_HALF = 32768;
    static final int RGB_RED = 2;
    static final int RGB_GREEN = 1;
    static final int RGB_BLUE = 0;
    static final int RGB_PIXELSIZE = 3;
    static final int JBUF_PASS_THRU = 0;
    static final int JBUF_SAVE_SOURCE = 1;
    static final int JBUF_CRANK_DEST = 2;
    static final int JBUF_SAVE_AND_PASS = 3;
    static final int JPEG_MAX_DIMENSION = 65500;
    static final int BITS_IN_JSAMPLE = 8;
    static final int JDITHER_NONE = 0;
    static final int JDITHER_ORDERED = 1;
    static final int JDITHER_FS = 2;
    static final int JDCT_ISLOW = 0;
    static final int JDCT_IFAST = 1;
    static final int JDCT_FLOAT = 2;
    static final int JDCT_DEFAULT = 0;
    static final int JCS_UNKNOWN = 0;
    static final int JCS_GRAYSCALE = 1;
    static final int JCS_RGB = 2;
    static final int JCS_YCbCr = 3;
    static final int JCS_CMYK = 4;
    static final int JCS_YCCK = 5;
    static final int SAVED_COEFS = 6;
    static final int Q01_POS = 1;
    static final int Q10_POS = 8;
    static final int Q20_POS = 16;
    static final int Q11_POS = 9;
    static final int Q02_POS = 2;
    static final int CTX_PREPARE_FOR_IMCU = 0;
    static final int CTX_PROCESS_IMCU = 1;
    static final int CTX_POSTPONED_ROW = 2;
    static final int APP0_DATA_LEN = 14;
    static final int APP14_DATA_LEN = 12;
    static final int APPN_DATA_LEN = 14;
    static final int M_SOF0 = 192;
    static final int M_SOF1 = 193;
    static final int M_SOF2 = 194;
    static final int M_SOF3 = 195;
    static final int M_SOF5 = 197;
    static final int M_SOF6 = 198;
    static final int M_SOF7 = 199;
    static final int M_JPG = 200;
    static final int M_SOF9 = 201;
    static final int M_SOF10 = 202;
    static final int M_SOF11 = 203;
    static final int M_SOF13 = 205;
    static final int M_SOF14 = 206;
    static final int M_SOF15 = 207;
    static final int M_DHT = 196;
    static final int M_DAC = 204;
    static final int M_RST0 = 208;
    static final int M_RST1 = 209;
    static final int M_RST2 = 210;
    static final int M_RST3 = 211;
    static final int M_RST4 = 212;
    static final int M_RST5 = 213;
    static final int M_RST6 = 214;
    static final int M_RST7 = 215;
    static final int M_SOI = 216;
    static final int M_EOI = 217;
    static final int M_SOS = 218;
    static final int M_DQT = 219;
    static final int M_DNL = 220;
    static final int M_DRI = 221;
    static final int M_DHP = 222;
    static final int M_EXP = 223;
    static final int M_APP0 = 224;
    static final int M_APP1 = 225;
    static final int M_APP2 = 226;
    static final int M_APP3 = 227;
    static final int M_APP4 = 228;
    static final int M_APP5 = 229;
    static final int M_APP6 = 230;
    static final int M_APP7 = 231;
    static final int M_APP8 = 232;
    static final int M_APP9 = 233;
    static final int M_APP10 = 234;
    static final int M_APP11 = 235;
    static final int M_APP12 = 236;
    static final int M_APP13 = 237;
    static final int M_APP14 = 238;
    static final int M_APP15 = 239;
    static final int M_JPG0 = 240;
    static final int M_JPG13 = 253;
    static final int M_COM = 254;
    static final int M_TEM = 1;
    static final int M_ERROR = 256;
    static final int CSTATE_START = 100;
    static final int CSTATE_SCANNING = 101;
    static final int CSTATE_RAW_OK = 102;
    static final int CSTATE_WRCOEFS = 103;
    static final int DSTATE_START = 200;
    static final int DSTATE_INHEADER = 201;
    static final int DSTATE_READY = 202;
    static final int DSTATE_PRELOAD = 203;
    static final int DSTATE_PRESCAN = 204;
    static final int DSTATE_SCANNING = 205;
    static final int DSTATE_RAW_OK = 206;
    static final int DSTATE_BUFIMAGE = 207;
    static final int DSTATE_BUFPOST = 208;
    static final int DSTATE_RDCOEFS = 209;
    static final int DSTATE_STOPPING = 210;
    static final int JPEG_REACHED_SOS = 1;
    static final int JPEG_REACHED_EOI = 2;
    static final int JPEG_ROW_COMPLETED = 3;
    static final int JPEG_SCAN_COMPLETED = 4;
    static final int JPEG_SUSPENDED = 0;
    static final int JPEG_HEADER_OK = 1;
    static final int JPEG_HEADER_TABLES_ONLY = 2;
    static final int DECOMPRESS_DATA = 0;
    static final int DECOMPRESS_SMOOTH_DATA = 1;
    static final int DECOMPRESS_ONEPASS = 2;
    static final int CONSUME_DATA = 0;
    static final int DUMMY_CONSUME_DATA = 1;
    static final int PROCESS_DATA_SIMPLE_MAIN = 0;
    static final int PROCESS_DATA_CONTEXT_MAIN = 1;
    static final int PROCESS_DATA_CRANK_POST = 2;
    static final int POST_PROCESS_1PASS = 0;
    static final int POST_PROCESS_DATA_UPSAMPLE = 1;
    static final int NULL_CONVERT = 0;
    static final int GRAYSCALE_CONVERT = 1;
    static final int YCC_RGB_CONVERT = 2;
    static final int GRAY_RGB_CONVERT = 3;
    static final int YCCK_CMYK_CONVERT = 4;
    static final int NOOP_UPSAMPLE = 0;
    static final int FULLSIZE_UPSAMPLE = 1;
    static final int H2V1_FANCY_UPSAMPLE = 2;
    static final int H2V1_UPSAMPLE = 3;
    static final int H2V2_FANCY_UPSAMPLE = 4;
    static final int H2V2_UPSAMPLE = 5;
    static final int INT_UPSAMPLE = 6;
    static final int INPUT_CONSUME_INPUT = 0;
    static final int COEF_CONSUME_INPUT = 1;
    static int[] extend_test = new int[]{0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384};
    static int[] extend_offset = new int[]{0, -1, -3, -7, -15, -31, -63, -127, -255, -511, -1023, -2047, -4095, -8191, -16383, -32767};
    static int[] jpeg_natural_order = new int[]{0, 1, 8, 16, 9, 2, 3, 10, 17, 24, 32, 25, 18, 11, 4, 5, 12, 19, 26, 33, 40, 48, 41, 34, 27, 20, 13, 6, 7, 14, 21, 28, 35, 42, 49, 56, 57, 50, 43, 36, 29, 22, 15, 23, 30, 37, 44, 51, 58, 59, 52, 45, 38, 31, 39, 46, 53, 60, 61, 54, 47, 55, 62, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63};
    static final int CONST_BITS = 13;
    static final int PASS1_BITS = 2;
    static final int RANGE_MASK = 1023;

    static void error() {
        SWT.error(40);
    }

    static void error(int n) {
        SWT.error(n);
    }

    static void error(String string) {
        SWT.error(40, null, string);
    }

    static void jinit_marker_reader(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_marker_reader jpeg_marker_reader2;
        jpeg_decompress_struct2.marker = jpeg_marker_reader2 = new jpeg_marker_reader();
        jpeg_marker_reader jpeg_marker_reader3 = jpeg_marker_reader2;
        jpeg_marker_reader3.length_limit_COM = 0;
        JPEGDecoder.reset_marker_reader(jpeg_decompress_struct2);
    }

    static void jinit_d_coef_controller(jpeg_decompress_struct jpeg_decompress_struct2, boolean bl) {
        jpeg_d_coef_controller jpeg_d_coef_controller2;
        jpeg_decompress_struct2.coef = jpeg_d_coef_controller2 = new jpeg_d_coef_controller();
        jpeg_d_coef_controller2.coef_bits_latch = null;
        if (bl) {
            for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
                jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[i];
                jpeg_d_coef_controller2.whole_image[i] = new short[(int)JPEGDecoder.jround_up(jpeg_component_info2.height_in_blocks, jpeg_component_info2.v_samp_factor)][(int)JPEGDecoder.jround_up(jpeg_component_info2.width_in_blocks, jpeg_component_info2.h_samp_factor)][64];
            }
            jpeg_d_coef_controller2.decompress_data = 0;
            jpeg_d_coef_controller2.coef_arrays = jpeg_d_coef_controller2.whole_image[0];
        } else {
            jpeg_d_coef_controller2.MCU_buffer = new short[10][64];
            jpeg_d_coef_controller2.decompress_data = 2;
            jpeg_d_coef_controller2.coef_arrays = null;
        }
    }

    static void start_output_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_d_coef_controller jpeg_d_coef_controller2 = jpeg_decompress_struct2.coef;
        if (jpeg_d_coef_controller2.coef_arrays != null) {
            jpeg_d_coef_controller2.decompress_data = jpeg_decompress_struct2.do_block_smoothing && jpeg_decompress_struct2 != false ? 1 : 0;
        }
        jpeg_decompress_struct2.output_iMCU_row = 0;
    }

    static void jpeg_create_decompress(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decompress_struct2.is_decompressor = true;
        jpeg_decompress_struct2.marker_list = null;
        JPEGDecoder.jinit_marker_reader(jpeg_decompress_struct2);
        JPEGDecoder.jinit_input_controller(jpeg_decompress_struct2);
        jpeg_decompress_struct2.global_state = 200;
    }

    static void jpeg_calc_output_dimensions(jpeg_decompress_struct jpeg_decompress_struct2) {
        if (jpeg_decompress_struct2.global_state != 202) {
            JPEGDecoder.error();
        }
        jpeg_decompress_struct2.output_width = jpeg_decompress_struct2.image_width;
        jpeg_decompress_struct2.output_height = jpeg_decompress_struct2.image_height;
        switch (jpeg_decompress_struct2.out_color_space) {
            case 1: {
                jpeg_decompress_struct2.out_color_components = 1;
                break;
            }
            case 2: 
            case 3: {
                jpeg_decompress_struct2.out_color_components = 3;
                break;
            }
            case 4: 
            case 5: {
                jpeg_decompress_struct2.out_color_components = 4;
                break;
            }
            default: {
                jpeg_decompress_struct2.out_color_components = jpeg_decompress_struct2.num_components;
            }
        }
        jpeg_decompress_struct2.output_components = jpeg_decompress_struct2.quantize_colors ? 1 : jpeg_decompress_struct2.out_color_components;
        jpeg_decompress_struct2.rec_outbuf_height = jpeg_decompress_struct2 == false ? jpeg_decompress_struct2.max_v_samp_factor : 1;
    }

    static void prepare_range_limit_table(jpeg_decompress_struct jpeg_decompress_struct2) {
        int n;
        int n2;
        byte[] byArray = new byte[1408];
        jpeg_decompress_struct2.sample_range_limit_offset = n2 = 256;
        jpeg_decompress_struct2.sample_range_limit = byArray;
        for (n = 0; n <= 255; ++n) {
            byArray[n + n2] = (byte)n;
        }
        n2 += 128;
        for (n = 128; n < 512; ++n) {
            byArray[n + n2] = -1;
        }
        System.arraycopy(jpeg_decompress_struct2.sample_range_limit, jpeg_decompress_struct2.sample_range_limit_offset, byArray, n2 + 896, 128);
    }

    static void build_ycc_rgb_table(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_color_deconverter jpeg_color_deconverter2 = jpeg_decompress_struct2.cconvert;
        jpeg_color_deconverter2.Cr_r_tab = new int[256];
        jpeg_color_deconverter2.Cb_b_tab = new int[256];
        jpeg_color_deconverter2.Cr_g_tab = new int[256];
        jpeg_color_deconverter2.Cb_g_tab = new int[256];
        int n = 0;
        int n2 = -128;
        while (n <= 255) {
            jpeg_color_deconverter2.Cr_r_tab[n] = 91881 * n2 + 32768 >> 16;
            jpeg_color_deconverter2.Cb_b_tab[n] = 116130 * n2 + 32768 >> 16;
            jpeg_color_deconverter2.Cr_g_tab[n] = -46802 * n2;
            jpeg_color_deconverter2.Cb_g_tab[n] = -22554 * n2 + 32768;
            ++n;
            ++n2;
        }
    }

    static void jinit_color_deconverter(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_color_deconverter jpeg_color_deconverter2;
        jpeg_decompress_struct2.cconvert = jpeg_color_deconverter2 = new jpeg_color_deconverter();
        jpeg_color_deconverter jpeg_color_deconverter3 = jpeg_color_deconverter2;
        switch (jpeg_decompress_struct2.jpeg_color_space) {
            case 1: {
                if (jpeg_decompress_struct2.num_components == 1) break;
                JPEGDecoder.error();
                break;
            }
            case 2: 
            case 3: {
                if (jpeg_decompress_struct2.num_components == 3) break;
                JPEGDecoder.error();
                break;
            }
            case 4: 
            case 5: {
                if (jpeg_decompress_struct2.num_components == 4) break;
                JPEGDecoder.error();
                break;
            }
            default: {
                if (jpeg_decompress_struct2.num_components >= 1) break;
                JPEGDecoder.error();
            }
        }
        switch (jpeg_decompress_struct2.out_color_space) {
            case 1: {
                jpeg_decompress_struct2.out_color_components = 1;
                if (jpeg_decompress_struct2.jpeg_color_space == 1 || jpeg_decompress_struct2.jpeg_color_space == 3) {
                    jpeg_color_deconverter3.color_convert = 1;
                    for (int i = 1; i < jpeg_decompress_struct2.num_components; ++i) {
                        jpeg_decompress_struct2.comp_info[i].component_needed = false;
                    }
                    break;
                }
                JPEGDecoder.error();
                break;
            }
            case 2: {
                jpeg_decompress_struct2.out_color_components = 3;
                if (jpeg_decompress_struct2.jpeg_color_space == 3) {
                    jpeg_color_deconverter3.color_convert = 2;
                    JPEGDecoder.build_ycc_rgb_table(jpeg_decompress_struct2);
                    break;
                }
                if (jpeg_decompress_struct2.jpeg_color_space == 1) {
                    jpeg_color_deconverter3.color_convert = 3;
                    break;
                }
                if (jpeg_decompress_struct2.jpeg_color_space == 2) {
                    jpeg_color_deconverter3.color_convert = 0;
                    break;
                }
                JPEGDecoder.error();
                break;
            }
            case 4: {
                jpeg_decompress_struct2.out_color_components = 4;
                if (jpeg_decompress_struct2.jpeg_color_space == 5) {
                    jpeg_color_deconverter3.color_convert = 4;
                    JPEGDecoder.build_ycc_rgb_table(jpeg_decompress_struct2);
                    break;
                }
                if (jpeg_decompress_struct2.jpeg_color_space == 4) {
                    jpeg_color_deconverter3.color_convert = 0;
                    break;
                }
                JPEGDecoder.error();
                break;
            }
            default: {
                if (jpeg_decompress_struct2.out_color_space == jpeg_decompress_struct2.jpeg_color_space) {
                    jpeg_decompress_struct2.out_color_components = jpeg_decompress_struct2.num_components;
                    jpeg_color_deconverter3.color_convert = 0;
                    break;
                }
                JPEGDecoder.error();
            }
        }
        jpeg_decompress_struct2.output_components = jpeg_decompress_struct2.quantize_colors ? 1 : jpeg_decompress_struct2.out_color_components;
    }

    static void jinit_d_post_controller(jpeg_decompress_struct jpeg_decompress_struct2, boolean bl) {
        jpeg_d_post_controller jpeg_d_post_controller2;
        jpeg_decompress_struct2.post = jpeg_d_post_controller2 = new jpeg_d_post_controller();
        jpeg_d_post_controller jpeg_d_post_controller3 = jpeg_d_post_controller2;
        jpeg_d_post_controller3.whole_image = null;
        jpeg_d_post_controller3.buffer = null;
        if (jpeg_decompress_struct2.quantize_colors) {
            JPEGDecoder.error(20);
        }
    }

    static void make_funny_pointers(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_d_main_controller jpeg_d_main_controller2 = jpeg_decompress_struct2.main;
        int n = jpeg_decompress_struct2.min_DCT_scaled_size;
        for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
            int n2;
            jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[i];
            int n3 = jpeg_component_info2.v_samp_factor * jpeg_component_info2.DCT_scaled_size / jpeg_decompress_struct2.min_DCT_scaled_size;
            byte[][] byArray = jpeg_d_main_controller2.xbuffer[0][i];
            int n4 = jpeg_d_main_controller2.xbuffer_offset[0][i];
            byte[][] byArray2 = jpeg_d_main_controller2.xbuffer[1][i];
            int n5 = jpeg_d_main_controller2.xbuffer_offset[1][i];
            byte[][] byArray3 = jpeg_d_main_controller2.buffer[i];
            for (n2 = 0; n2 < n3 * (n + 2); ++n2) {
                byte[] byArray4 = byArray3[n2];
                byArray2[n2 + n5] = byArray4;
                byArray[n2 + n4] = byArray4;
            }
            for (n2 = 0; n2 < n3 * 2; ++n2) {
                byArray2[n3 * (n - 2) + n2 + n5] = byArray3[n3 * n + n2];
                byArray2[n3 * n + n2 + n5] = byArray3[n3 * (n - 2) + n2];
            }
            for (n2 = 0; n2 < n3; ++n2) {
                byArray[n2 - n3 + n4] = byArray[0 + n4];
            }
        }
    }

    static void alloc_funny_pointers(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_d_main_controller jpeg_d_main_controller2 = jpeg_decompress_struct2.main;
        int n = jpeg_decompress_struct2.min_DCT_scaled_size;
        jpeg_d_main_controller2.xbuffer[0] = new byte[jpeg_decompress_struct2.num_components][][];
        jpeg_d_main_controller2.xbuffer[1] = new byte[jpeg_decompress_struct2.num_components][][];
        jpeg_d_main_controller2.xbuffer_offset[0] = new int[jpeg_decompress_struct2.num_components];
        jpeg_d_main_controller2.xbuffer_offset[1] = new int[jpeg_decompress_struct2.num_components];
        for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
            int n2;
            jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[i];
            int n3 = jpeg_component_info2.v_samp_factor * jpeg_component_info2.DCT_scaled_size / jpeg_decompress_struct2.min_DCT_scaled_size;
            byte[][] byArrayArray = new byte[2 * (n3 * (n + 4))][];
            jpeg_d_main_controller2.xbuffer_offset[0][i] = n2 = n3;
            jpeg_d_main_controller2.xbuffer[0][i] = byArrayArray;
            jpeg_d_main_controller2.xbuffer_offset[1][i] = n2 += n3 * (n + 4);
            jpeg_d_main_controller2.xbuffer[1][i] = byArrayArray;
        }
    }

    static void jinit_d_main_controller(jpeg_decompress_struct jpeg_decompress_struct2, boolean bl) {
        int n;
        jpeg_d_main_controller jpeg_d_main_controller2;
        jpeg_decompress_struct2.main = jpeg_d_main_controller2 = new jpeg_d_main_controller();
        jpeg_d_main_controller jpeg_d_main_controller3 = jpeg_d_main_controller2;
        if (bl) {
            JPEGDecoder.error();
        }
        if (jpeg_decompress_struct2.upsample.need_context_rows) {
            if (jpeg_decompress_struct2.min_DCT_scaled_size < 2) {
                JPEGDecoder.error();
            }
            JPEGDecoder.alloc_funny_pointers(jpeg_decompress_struct2);
            n = jpeg_decompress_struct2.min_DCT_scaled_size + 2;
        } else {
            n = jpeg_decompress_struct2.min_DCT_scaled_size;
        }
        for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
            jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[i];
            int n2 = jpeg_component_info2.v_samp_factor * jpeg_component_info2.DCT_scaled_size / jpeg_decompress_struct2.min_DCT_scaled_size;
            jpeg_d_main_controller3.buffer[i] = new byte[n2 * n][jpeg_component_info2.width_in_blocks * jpeg_component_info2.DCT_scaled_size];
        }
    }

    static long jround_up(long l2, long l3) {
        return (l2 += l3 - 1L) - l2 % l3;
    }

    static void jinit_upsampler(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_upsampler jpeg_upsampler2;
        jpeg_decompress_struct2.upsample = jpeg_upsampler2 = new jpeg_upsampler();
        jpeg_upsampler2.need_context_rows = false;
        if (jpeg_decompress_struct2.CCIR601_sampling) {
            JPEGDecoder.error();
        }
        boolean bl = jpeg_decompress_struct2.do_fancy_upsampling && jpeg_decompress_struct2.min_DCT_scaled_size > 1;
        for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
            jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[i];
            int n = jpeg_component_info2.h_samp_factor * jpeg_component_info2.DCT_scaled_size / jpeg_decompress_struct2.min_DCT_scaled_size;
            int n2 = jpeg_component_info2.v_samp_factor * jpeg_component_info2.DCT_scaled_size / jpeg_decompress_struct2.min_DCT_scaled_size;
            int n3 = jpeg_decompress_struct2.max_h_samp_factor;
            int n4 = jpeg_decompress_struct2.max_v_samp_factor;
            jpeg_upsampler2.rowgroup_height[i] = n2;
            boolean bl2 = true;
            if (!jpeg_component_info2.component_needed) {
                jpeg_upsampler2.methods[i] = 0;
                bl2 = false;
            } else if (n == n3 && n2 == n4) {
                jpeg_upsampler2.methods[i] = 1;
                bl2 = false;
            } else if (n * 2 == n3 && n2 == n4) {
                jpeg_upsampler2.methods[i] = bl && jpeg_component_info2.downsampled_width > 2 ? 2 : 3;
            } else if (n * 2 == n3 && n2 * 2 == n4) {
                if (bl && jpeg_component_info2.downsampled_width > 2) {
                    jpeg_upsampler2.methods[i] = 4;
                    jpeg_upsampler2.need_context_rows = true;
                } else {
                    jpeg_upsampler2.methods[i] = 5;
                }
            } else if (n3 % n == 0 && n4 % n2 == 0) {
                jpeg_upsampler2.methods[i] = 6;
                jpeg_upsampler2.h_expand[i] = (byte)(n3 / n);
                jpeg_upsampler2.v_expand[i] = (byte)(n4 / n2);
            } else {
                JPEGDecoder.error();
            }
            if (!bl2) continue;
            jpeg_upsampler2.color_buf[i] = new byte[jpeg_decompress_struct2.max_v_samp_factor][(int)JPEGDecoder.jround_up(jpeg_decompress_struct2.output_width, jpeg_decompress_struct2.max_h_samp_factor)];
        }
    }

    static void jinit_phuff_decoder(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decompress_struct2.entropy = new phuff_entropy_decoder();
        int[][] nArray = jpeg_decompress_struct2.coef_bits = new int[jpeg_decompress_struct2.num_components][64];
        for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
            for (int j = 0; j < 64; ++j) {
                nArray[i][j] = -1;
            }
        }
    }

    static void jinit_huff_decoder(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decompress_struct2.entropy = new huff_entropy_decoder();
    }

    static void jinit_inverse_dct(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_inverse_dct jpeg_inverse_dct2;
        jpeg_decompress_struct2.idct = jpeg_inverse_dct2 = new jpeg_inverse_dct();
        jpeg_inverse_dct jpeg_inverse_dct3 = jpeg_inverse_dct2;
        for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
            jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[i];
            jpeg_component_info2.dct_table = new int[64];
            jpeg_inverse_dct3.cur_method[i] = -1;
        }
    }

    static void jpeg_idct_islow(jpeg_decompress_struct jpeg_decompress_struct2, jpeg_component_info jpeg_component_info2, short[] sArray, byte[][] byArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14;
        int n15;
        byte[] byArray2 = jpeg_decompress_struct2.sample_range_limit;
        int n16 = jpeg_decompress_struct2.sample_range_limit_offset + 128;
        int[] nArray = jpeg_decompress_struct2.workspace;
        short[] sArray2 = sArray;
        int[] nArray2 = jpeg_component_info2.dct_table;
        int[] nArray3 = nArray;
        int n17 = 0;
        int n18 = 0;
        int n19 = 0;
        for (n15 = 8; n15 > 0; --n15) {
            if (sArray2[8 + n17] == 0 && sArray2[16 + n17] == 0 && sArray2[24 + n17] == 0 && sArray2[32 + n17] == 0 && sArray2[40 + n17] == 0 && sArray2[48 + n17] == 0 && sArray2[56 + n17] == 0) {
                int n20 = n14 = sArray2[0 + n17] * nArray2[0 + n18] << 2;
                nArray3[0 + n19] = n20;
                nArray3[8 + n19] = n20;
                int n21 = n14;
                nArray3[16 + n19] = n21;
                nArray3[24 + n19] = n21;
                int n22 = n14;
                nArray3[32 + n19] = n22;
                nArray3[40 + n19] = n22;
                int n23 = n14;
                nArray3[48 + n19] = n23;
                nArray3[56 + n19] = n23;
                ++n17;
                ++n18;
                ++n19;
                continue;
            }
            n14 = sArray2[16 + n17] * nArray2[16 + n18];
            int n24 = sArray2[48 + n17] * nArray2[48 + n18];
            n13 = (n14 + n24) * 4433;
            n12 = n13 + n24 * -15137;
            n11 = n13 + n14 * 6270;
            n14 = sArray2[0 + n17] * nArray2[0 + n18];
            n24 = sArray2[32 + n17] * nArray2[32 + n18];
            n10 = n14 + n24 << 13;
            n9 = n14 - n24 << 13;
            n8 = n10 + n11;
            n7 = n10 - n11;
            n6 = n9 + n12;
            n5 = n9 - n12;
            n10 = sArray2[56 + n17] * nArray2[56 + n18];
            n9 = sArray2[40 + n17] * nArray2[40 + n18];
            n12 = sArray2[24 + n17] * nArray2[24 + n18];
            n11 = sArray2[8 + n17] * nArray2[8 + n18];
            n13 = n10 + n11;
            n14 = n9 + n12;
            n24 = n10 + n12;
            n4 = n9 + n11;
            n3 = (n24 + n4) * 9633;
            n10 *= 2446;
            n9 *= 16819;
            n12 *= 25172;
            n11 *= 12299;
            n24 *= -16069;
            n4 *= -3196;
            n10 += (n13 *= -7373) + (n24 += n3);
            nArray3[0 + n19] = n8 + (n11 += n13 + n4) + 1024 >> 11;
            nArray3[56 + n19] = n8 - n11 + 1024 >> 11;
            nArray3[8 + n19] = n6 + (n12 += n14 + n24) + 1024 >> 11;
            nArray3[48 + n19] = n6 - n12 + 1024 >> 11;
            nArray3[16 + n19] = n5 + (n9 += (n14 *= -20995) + (n4 += n3)) + 1024 >> 11;
            nArray3[40 + n19] = n5 - n9 + 1024 >> 11;
            nArray3[24 + n19] = n7 + n10 + 1024 >> 11;
            nArray3[32 + n19] = n7 - n10 + 1024 >> 11;
            ++n17;
            ++n18;
            ++n19;
        }
        n15 = 0;
        nArray3 = nArray;
        n19 = 0;
        for (n14 = 0; n14 < 8; ++n14) {
            byte[] byArray3 = byArray[n14 + n];
            n15 = n2;
            if (nArray3[1 + n19] == 0 && nArray3[2 + n19] == 0 && nArray3[3 + n19] == 0 && nArray3[4 + n19] == 0 && nArray3[5 + n19] == 0 && nArray3[6 + n19] == 0 && nArray3[7 + n19] == 0) {
                int n25 = n13 = byArray2[n16 + (nArray3[0 + n19] + 16 >> 5 & 0x3FF)];
                byArray3[0 + n15] = n25;
                byArray3[1 + n15] = n25;
                int n26 = n13;
                byArray3[2 + n15] = n26;
                byArray3[3 + n15] = n26;
                int n27 = n13;
                byArray3[4 + n15] = n27;
                byArray3[5 + n15] = n27;
                int n28 = n13;
                byArray3[6 + n15] = n28;
                byArray3[7 + n15] = n28;
                n19 += 8;
                continue;
            }
            n13 = nArray3[2 + n19];
            n12 = nArray3[6 + n19];
            n11 = (n13 + n12) * 4433;
            n10 = n11 + n12 * -15137;
            n9 = n11 + n13 * 6270;
            n8 = nArray3[0 + n19] + nArray3[4 + n19] << 13;
            n7 = nArray3[0 + n19] - nArray3[4 + n19] << 13;
            n6 = n8 + n9;
            n5 = n8 - n9;
            n4 = n7 + n10;
            n3 = n7 - n10;
            n8 = nArray3[7 + n19];
            n7 = nArray3[5 + n19];
            n10 = nArray3[3 + n19];
            n9 = nArray3[1 + n19];
            n11 = n8 + n9;
            n13 = n7 + n10;
            n12 = n8 + n10;
            int n29 = n7 + n9;
            int n30 = (n12 + n29) * 9633;
            n8 *= 2446;
            n7 *= 16819;
            n10 *= 25172;
            n9 *= 12299;
            n12 *= -16069;
            n29 *= -3196;
            n8 += (n11 *= -7373) + (n12 += n30);
            byArray3[0 + n15] = byArray2[n16 + (n6 + (n9 += n11 + n29) + 131072 >> 18 & 0x3FF)];
            byArray3[7 + n15] = byArray2[n16 + (n6 - n9 + 131072 >> 18 & 0x3FF)];
            byArray3[1 + n15] = byArray2[n16 + (n4 + (n10 += n13 + n12) + 131072 >> 18 & 0x3FF)];
            byArray3[6 + n15] = byArray2[n16 + (n4 - n10 + 131072 >> 18 & 0x3FF)];
            byArray3[2 + n15] = byArray2[n16 + (n3 + (n7 += (n13 *= -20995) + (n29 += n30)) + 131072 >> 18 & 0x3FF)];
            byArray3[5 + n15] = byArray2[n16 + (n3 - n7 + 131072 >> 18 & 0x3FF)];
            byArray3[3 + n15] = byArray2[n16 + (n5 + n8 + 131072 >> 18 & 0x3FF)];
            byArray3[4 + n15] = byArray2[n16 + (n5 - n8 + 131072 >> 18 & 0x3FF)];
            n19 += 8;
        }
    }

    static void upsample(jpeg_decompress_struct jpeg_decompress_struct2, byte[][][] byArray, int[] nArray, int[] nArray2, int n, byte[][] byArray2, int[] nArray3, int n2) {
        JPEGDecoder.sep_upsample(jpeg_decompress_struct2, byArray, nArray, nArray2, n, byArray2, nArray3, n2);
    }

    static void master_selection(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decomp_master jpeg_decomp_master2 = jpeg_decompress_struct2.master;
        JPEGDecoder.jpeg_calc_output_dimensions(jpeg_decompress_struct2);
        JPEGDecoder.prepare_range_limit_table(jpeg_decompress_struct2);
        long l2 = (long)jpeg_decompress_struct2.output_width * (long)jpeg_decompress_struct2.out_color_components;
        int n = (int)l2;
        if ((long)n != l2) {
            JPEGDecoder.error();
        }
        jpeg_decomp_master2.pass_number = 0;
        jpeg_decomp_master2.using_merged_upsample = JPEGDecoder.use_merged_upsample((jpeg_decompress_struct)jpeg_decompress_struct2);
        jpeg_decomp_master2.quantizer_1pass = null;
        jpeg_decomp_master2.quantizer_2pass = null;
        if (!jpeg_decompress_struct2.quantize_colors || !jpeg_decompress_struct2.buffered_image) {
            jpeg_decompress_struct2.enable_1pass_quant = false;
            jpeg_decompress_struct2.enable_external_quant = false;
            jpeg_decompress_struct2.enable_2pass_quant = false;
        }
        if (jpeg_decompress_struct2.quantize_colors) {
            JPEGDecoder.error(20);
        }
        if (!jpeg_decompress_struct2.raw_data_out) {
            if (jpeg_decomp_master2.using_merged_upsample) {
                JPEGDecoder.error();
            } else {
                JPEGDecoder.jinit_color_deconverter(jpeg_decompress_struct2);
                JPEGDecoder.jinit_upsampler(jpeg_decompress_struct2);
            }
            JPEGDecoder.jinit_d_post_controller(jpeg_decompress_struct2, jpeg_decompress_struct2.enable_2pass_quant);
        }
        JPEGDecoder.jinit_inverse_dct(jpeg_decompress_struct2);
        if (jpeg_decompress_struct2.arith_code) {
            JPEGDecoder.error();
        } else if (jpeg_decompress_struct2.progressive_mode) {
            JPEGDecoder.jinit_phuff_decoder(jpeg_decompress_struct2);
        } else {
            JPEGDecoder.jinit_huff_decoder(jpeg_decompress_struct2);
        }
        boolean bl = jpeg_decompress_struct2.inputctl.has_multiple_scans || jpeg_decompress_struct2.buffered_image;
        JPEGDecoder.jinit_d_coef_controller(jpeg_decompress_struct2, bl);
        if (!jpeg_decompress_struct2.raw_data_out) {
            JPEGDecoder.jinit_d_main_controller(jpeg_decompress_struct2, false);
        }
        JPEGDecoder.start_input_pass(jpeg_decompress_struct2);
    }

    static void jinit_master_decompress(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decomp_master jpeg_decomp_master2;
        jpeg_decompress_struct2.master = jpeg_decomp_master2 = new jpeg_decomp_master();
        jpeg_decomp_master2.is_dummy_pass = false;
        JPEGDecoder.master_selection(jpeg_decompress_struct2);
    }

    static void jcopy_sample_rows(byte[][] byArray, int n, byte[][] byArray2, int n2, int n3, int n4) {
        int n5 = n4;
        int n6 = n;
        int n7 = n2;
        for (int i = n3; i > 0; --i) {
            byte[] byArray3 = byArray[n6++];
            byte[] byArray4 = byArray2[n7++];
            System.arraycopy(byArray3, 0, byArray4, 0, n5);
        }
    }

    static boolean jpeg_start_decompress(jpeg_decompress_struct jpeg_decompress_struct2) {
        if (jpeg_decompress_struct2.global_state == 202) {
            JPEGDecoder.jinit_master_decompress(jpeg_decompress_struct2);
            if (jpeg_decompress_struct2.buffered_image) {
                jpeg_decompress_struct2.global_state = 207;
                return true;
            }
            jpeg_decompress_struct2.global_state = 203;
        }
        if (jpeg_decompress_struct2.global_state == 203) {
            if (jpeg_decompress_struct2.inputctl.has_multiple_scans) {
                int n;
                do {
                    if ((n = JPEGDecoder.consume_input(jpeg_decompress_struct2)) != 0) continue;
                    return false;
                } while (n != 2);
            }
            jpeg_decompress_struct2.output_scan_number = jpeg_decompress_struct2.input_scan_number;
        } else if (jpeg_decompress_struct2.global_state != 204) {
            JPEGDecoder.error();
        }
        return JPEGDecoder.output_pass_setup(jpeg_decompress_struct2);
    }

    static void prepare_for_output_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decomp_master jpeg_decomp_master2 = jpeg_decompress_struct2.master;
        if (jpeg_decomp_master2.is_dummy_pass) {
            JPEGDecoder.error(20);
        } else {
            if (jpeg_decompress_struct2.quantize_colors && jpeg_decompress_struct2.colormap == null) {
                if (jpeg_decompress_struct2.two_pass_quantize && jpeg_decompress_struct2.enable_2pass_quant) {
                    jpeg_decompress_struct2.cquantize = jpeg_decomp_master2.quantizer_2pass;
                    jpeg_decomp_master2.is_dummy_pass = true;
                } else if (jpeg_decompress_struct2.enable_1pass_quant) {
                    jpeg_decompress_struct2.cquantize = jpeg_decomp_master2.quantizer_1pass;
                } else {
                    JPEGDecoder.error();
                }
            }
            jpeg_decompress_struct2.idct.start_pass(jpeg_decompress_struct2);
            JPEGDecoder.start_output_pass(jpeg_decompress_struct2);
            if (!jpeg_decompress_struct2.raw_data_out) {
                if (!jpeg_decomp_master2.using_merged_upsample) {
                    jpeg_decompress_struct2.cconvert.start_pass(jpeg_decompress_struct2);
                }
                jpeg_decompress_struct2.upsample.start_pass(jpeg_decompress_struct2);
                if (jpeg_decompress_struct2.quantize_colors) {
                    jpeg_decompress_struct2.cquantize.start_pass(jpeg_decompress_struct2, jpeg_decomp_master2.is_dummy_pass);
                }
                jpeg_decompress_struct2.post.start_pass(jpeg_decompress_struct2, jpeg_decomp_master2.is_dummy_pass ? 3 : 0);
                jpeg_decompress_struct2.main.start_pass(jpeg_decompress_struct2, 0);
            }
        }
    }

    /*
     * Exception decompiling
     */
    static boolean read_restart_marker(jpeg_decompress_struct var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl5 : IF_ICMPNE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    static int jpeg_huff_decode(bitread_working_state var0, int var1, int var2, d_derived_tbl var3, int var4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl33 : ILOAD - null : trying to set 6 previously set to 3
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    static int decompress_onepass(jpeg_decompress_struct jpeg_decompress_struct2, byte[][][] byArray, int[] nArray) {
        jpeg_d_coef_controller jpeg_d_coef_controller2 = jpeg_decompress_struct2.coef;
        int n = jpeg_decompress_struct2.MCUs_per_row - 1;
        int n2 = jpeg_decompress_struct2.total_iMCU_rows - 1;
        for (int i = jpeg_d_coef_controller2.MCU_vert_offset; i < jpeg_d_coef_controller2.MCU_rows_per_iMCU_row; ++i) {
            for (int j = jpeg_d_coef_controller2.MCU_ctr; j <= n; ++j) {
                int n3;
                for (n3 = 0; n3 < jpeg_decompress_struct2.blocks_in_MCU; ++n3) {
                    short[] sArray = jpeg_d_coef_controller2.MCU_buffer[n3];
                    for (int k = 0; k < sArray.length; ++k) {
                        sArray[k] = 0;
                    }
                }
                if (!jpeg_decompress_struct2.entropy.decode_mcu(jpeg_decompress_struct2, jpeg_d_coef_controller2.MCU_buffer)) {
                    jpeg_d_coef_controller2.MCU_vert_offset = i;
                    jpeg_d_coef_controller2.MCU_ctr = j;
                    return 0;
                }
                n3 = 0;
                for (int k = 0; k < jpeg_decompress_struct2.comps_in_scan; ++k) {
                    jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.cur_comp_info[k];
                    if (!jpeg_component_info2.component_needed) {
                        n3 += jpeg_component_info2.MCU_blocks;
                        continue;
                    }
                    int n4 = j < n ? jpeg_component_info2.MCU_width : jpeg_component_info2.last_col_width;
                    byte[][] byArray2 = byArray[jpeg_component_info2.component_index];
                    int n5 = nArray[jpeg_component_info2.component_index] + i * jpeg_component_info2.DCT_scaled_size;
                    int n6 = j * jpeg_component_info2.MCU_sample_width;
                    for (int i2 = 0; i2 < jpeg_component_info2.MCU_height; ++i2) {
                        if (jpeg_decompress_struct2.input_iMCU_row < n2 || i + i2 < jpeg_component_info2.last_row_height) {
                            int n7 = n6;
                            for (int i3 = 0; i3 < n4; ++i3) {
                                JPEGDecoder.jpeg_idct_islow(jpeg_decompress_struct2, jpeg_component_info2, jpeg_d_coef_controller2.MCU_buffer[n3 + i3], byArray2, n5, n7);
                                n7 += jpeg_component_info2.DCT_scaled_size;
                            }
                        }
                        n3 += jpeg_component_info2.MCU_width;
                        n5 += jpeg_component_info2.DCT_scaled_size;
                    }
                }
            }
            jpeg_d_coef_controller2.MCU_ctr = 0;
        }
        ++jpeg_decompress_struct2.output_iMCU_row;
        if (++jpeg_decompress_struct2.input_iMCU_row < jpeg_decompress_struct2.total_iMCU_rows) {
            jpeg_d_coef_controller2.start_iMCU_row(jpeg_decompress_struct2);
            return 3;
        }
        JPEGDecoder.finish_input_pass(jpeg_decompress_struct2);
        return 4;
    }

    static int decompress_smooth_data(jpeg_decompress_struct jpeg_decompress_struct2, byte[][][] byArray, int[] nArray) {
        Object object;
        jpeg_d_coef_controller jpeg_d_coef_controller2 = jpeg_decompress_struct2.coef;
        int n = jpeg_decompress_struct2.total_iMCU_rows - 1;
        short[] sArray = jpeg_d_coef_controller2.workspace;
        if (sArray == null) {
            jpeg_d_coef_controller jpeg_d_coef_controller3 = jpeg_d_coef_controller2;
            object = new short[64];
            jpeg_d_coef_controller3.workspace = object;
            sArray = object;
        }
        while (jpeg_decompress_struct2.input_scan_number <= jpeg_decompress_struct2.output_scan_number && !jpeg_decompress_struct2.inputctl.eoi_reached) {
            if (jpeg_decompress_struct2.input_scan_number == jpeg_decompress_struct2.output_scan_number) {
                int n2;
                int n3 = n2 = jpeg_decompress_struct2.Ss == 0 ? 1 : 0;
                if (jpeg_decompress_struct2.input_iMCU_row > jpeg_decompress_struct2.output_iMCU_row + n2) break;
            }
            if (JPEGDecoder.consume_input(jpeg_decompress_struct2) != 0) continue;
            return 0;
        }
        for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
            boolean bl;
            int n4;
            short[][][] sArray2;
            boolean bl2;
            int n5;
            object = jpeg_decompress_struct2.comp_info[i];
            if (!object.component_needed) continue;
            if (jpeg_decompress_struct2.output_iMCU_row < n) {
                n5 = object.v_samp_factor;
                bl2 = false;
            } else {
                n5 = object.height_in_blocks % object.v_samp_factor;
                if (n5 == 0) {
                    n5 = object.v_samp_factor;
                }
                bl2 = true;
            }
            if (jpeg_decompress_struct2.output_iMCU_row > 0) {
                sArray2 = jpeg_d_coef_controller2.whole_image[i];
                n4 = (jpeg_decompress_struct2.output_iMCU_row - 1) * object.v_samp_factor;
                n4 += object.v_samp_factor;
                bl = false;
            } else {
                sArray2 = jpeg_d_coef_controller2.whole_image[i];
                n4 = 0;
                bl = true;
            }
            int[] nArray2 = jpeg_d_coef_controller2.coef_bits_latch;
            int n6 = i * 6;
            JQUANT_TBL jQUANT_TBL = object.quant_table;
            short s = jQUANT_TBL.quantval[0];
            short s2 = jQUANT_TBL.quantval[1];
            short s3 = jQUANT_TBL.quantval[8];
            short s4 = jQUANT_TBL.quantval[16];
            short s5 = jQUANT_TBL.quantval[9];
            short s6 = jQUANT_TBL.quantval[2];
            byte[][] byArray2 = byArray[i];
            int n7 = nArray[i];
            for (int j = 0; j < n5; ++j) {
                short s7;
                short s8;
                short s9;
                short[][] sArray3;
                short[][] sArray4;
                short[][] sArray5 = sArray2[j + n4];
                int n8 = 0;
                int n9 = 0;
                int n10 = 0;
                if (bl && j == 0) {
                    sArray4 = sArray5;
                    n9 = n8;
                } else {
                    sArray4 = sArray2[j - 1 + n4];
                    n9 = 0;
                }
                if (bl2 && j == n5 - 1) {
                    sArray3 = sArray5;
                    n10 = n8;
                } else {
                    sArray3 = sArray2[j + 1 + n4];
                    n10 = 0;
                }
                short s10 = s9 = sArray4[0 + n9][0];
                short s11 = s9;
                short s12 = s8 = sArray5[0 + n8][0];
                short s13 = s8;
                short s14 = s7 = sArray3[0 + n10][0];
                short s15 = s7;
                int n11 = 0;
                int n12 = object.width_in_blocks - 1;
                for (int k = 0; k <= n12; ++k) {
                    int n13;
                    int n14;
                    int n15;
                    System.arraycopy(sArray5[n8], 0, sArray, 0, sArray.length);
                    if (k < n12) {
                        s9 = sArray4[1 + n9][0];
                        s8 = sArray5[1 + n8][0];
                        s7 = sArray3[1 + n10][0];
                    }
                    if ((n15 = nArray2[1 + n6]) != 0 && sArray[1] == 0) {
                        n14 = 36 * s * (s12 - s8);
                        if (n14 >= 0) {
                            n13 = ((s2 << 7) + n14) / (s2 << 8);
                            if (n15 > 0 && n13 >= 1 << n15) {
                                n13 = (1 << n15) - 1;
                            }
                        } else {
                            n13 = ((s2 << 7) - n14) / (s2 << 8);
                            if (n15 > 0 && n13 >= 1 << n15) {
                                n13 = (1 << n15) - 1;
                            }
                            n13 = -n13;
                        }
                        sArray[1] = (short)n13;
                    }
                    if ((n15 = nArray2[2 + n6]) != 0 && sArray[8] == 0) {
                        n14 = 36 * s * (s11 - s15);
                        if (n14 >= 0) {
                            n13 = ((s3 << 7) + n14) / (s3 << 8);
                            if (n15 > 0 && n13 >= 1 << n15) {
                                n13 = (1 << n15) - 1;
                            }
                        } else {
                            n13 = ((s3 << 7) - n14) / (s3 << 8);
                            if (n15 > 0 && n13 >= 1 << n15) {
                                n13 = (1 << n15) - 1;
                            }
                            n13 = -n13;
                        }
                        sArray[8] = (short)n13;
                    }
                    if ((n15 = nArray2[3 + n6]) != 0 && sArray[16] == 0) {
                        n14 = 9 * s * (s11 + s15 - 2 * s13);
                        if (n14 >= 0) {
                            n13 = ((s4 << 7) + n14) / (s4 << 8);
                            if (n15 > 0 && n13 >= 1 << n15) {
                                n13 = (1 << n15) - 1;
                            }
                        } else {
                            n13 = ((s4 << 7) - n14) / (s4 << 8);
                            if (n15 > 0 && n13 >= 1 << n15) {
                                n13 = (1 << n15) - 1;
                            }
                            n13 = -n13;
                        }
                        sArray[16] = (short)n13;
                    }
                    if ((n15 = nArray2[4 + n6]) != 0 && sArray[9] == 0) {
                        n14 = 5 * s * (s10 - s9 - s14 + s7);
                        if (n14 >= 0) {
                            n13 = ((s5 << 7) + n14) / (s5 << 8);
                            if (n15 > 0 && n13 >= 1 << n15) {
                                n13 = (1 << n15) - 1;
                            }
                        } else {
                            n13 = ((s5 << 7) - n14) / (s5 << 8);
                            if (n15 > 0 && n13 >= 1 << n15) {
                                n13 = (1 << n15) - 1;
                            }
                            n13 = -n13;
                        }
                        sArray[9] = (short)n13;
                    }
                    if ((n15 = nArray2[5 + n6]) != 0 && sArray[2] == 0) {
                        n14 = 9 * s * (s12 + s8 - 2 * s13);
                        if (n14 >= 0) {
                            n13 = ((s6 << 7) + n14) / (s6 << 8);
                            if (n15 > 0 && n13 >= 1 << n15) {
                                n13 = (1 << n15) - 1;
                            }
                        } else {
                            n13 = ((s6 << 7) - n14) / (s6 << 8);
                            if (n15 > 0 && n13 >= 1 << n15) {
                                n13 = (1 << n15) - 1;
                            }
                            n13 = -n13;
                        }
                        sArray[2] = (short)n13;
                    }
                    JPEGDecoder.jpeg_idct_islow(jpeg_decompress_struct2, (jpeg_component_info)object, sArray, byArray2, n7, n11);
                    s10 = s11;
                    s11 = s9;
                    s12 = s13;
                    s13 = s8;
                    s14 = s15;
                    s15 = s7;
                    ++n8;
                    ++n9;
                    ++n10;
                    n11 += object.DCT_scaled_size;
                }
                n7 += object.DCT_scaled_size;
            }
        }
        if (++jpeg_decompress_struct2.output_iMCU_row < jpeg_decompress_struct2.total_iMCU_rows) {
            return 3;
        }
        return 4;
    }

    static int decompress_data(jpeg_decompress_struct jpeg_decompress_struct2, byte[][][] byArray, int[] nArray) {
        jpeg_d_coef_controller jpeg_d_coef_controller2 = jpeg_decompress_struct2.coef;
        int n = jpeg_decompress_struct2.total_iMCU_rows - 1;
        while (jpeg_decompress_struct2.input_scan_number < jpeg_decompress_struct2.output_scan_number || jpeg_decompress_struct2.input_scan_number == jpeg_decompress_struct2.output_scan_number && jpeg_decompress_struct2.input_iMCU_row <= jpeg_decompress_struct2.output_iMCU_row) {
            if (JPEGDecoder.consume_input(jpeg_decompress_struct2) != 0) continue;
            return 0;
        }
        for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
            int n2;
            jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[i];
            if (!jpeg_component_info2.component_needed) continue;
            short[][][] sArray = jpeg_d_coef_controller2.whole_image[i];
            int n3 = jpeg_decompress_struct2.output_iMCU_row * jpeg_component_info2.v_samp_factor;
            if (jpeg_decompress_struct2.output_iMCU_row < n) {
                n2 = jpeg_component_info2.v_samp_factor;
            } else {
                n2 = jpeg_component_info2.height_in_blocks % jpeg_component_info2.v_samp_factor;
                if (n2 == 0) {
                    n2 = jpeg_component_info2.v_samp_factor;
                }
            }
            byte[][] byArray2 = byArray[i];
            int n4 = nArray[i];
            for (int j = 0; j < n2; ++j) {
                short[][] sArray2 = sArray[j + n3];
                int n5 = 0;
                int n6 = 0;
                for (int k = 0; k < jpeg_component_info2.width_in_blocks; ++k) {
                    JPEGDecoder.jpeg_idct_islow(jpeg_decompress_struct2, jpeg_component_info2, sArray2[n5], byArray2, n4, n6);
                    ++n5;
                    n6 += jpeg_component_info2.DCT_scaled_size;
                }
                n4 += jpeg_component_info2.DCT_scaled_size;
            }
        }
        if (++jpeg_decompress_struct2.output_iMCU_row < jpeg_decompress_struct2.total_iMCU_rows) {
            return 3;
        }
        return 4;
    }

    static void post_process_data(jpeg_decompress_struct jpeg_decompress_struct2, byte[][][] byArray, int[] nArray, int[] nArray2, int n, byte[][] byArray2, int[] nArray3, int n2) {
        JPEGDecoder.upsample(jpeg_decompress_struct2, byArray, nArray, nArray2, n, byArray2, nArray3, n2);
    }

    static void set_bottom_pointers(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_d_main_controller jpeg_d_main_controller2 = jpeg_decompress_struct2.main;
        for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
            jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[i];
            int n = jpeg_component_info2.v_samp_factor * jpeg_component_info2.DCT_scaled_size;
            int n2 = n / jpeg_decompress_struct2.min_DCT_scaled_size;
            int n3 = jpeg_component_info2.downsampled_height % n;
            if (n3 == 0) {
                n3 = n;
            }
            if (i == 0) {
                jpeg_d_main_controller2.rowgroups_avail = (n3 - 1) / n2 + 1;
            }
            byte[][] byArray = jpeg_d_main_controller2.xbuffer[jpeg_d_main_controller2.whichptr][i];
            int n4 = jpeg_d_main_controller2.xbuffer_offset[jpeg_d_main_controller2.whichptr][i];
            for (int j = 0; j < n2 * 2; ++j) {
                byArray[n3 + j + n4] = byArray[n3 - 1 + n4];
            }
        }
    }

    static void set_wraparound_pointers(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_d_main_controller jpeg_d_main_controller2 = jpeg_decompress_struct2.main;
        int n = jpeg_decompress_struct2.min_DCT_scaled_size;
        for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
            jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[i];
            int n2 = jpeg_component_info2.v_samp_factor * jpeg_component_info2.DCT_scaled_size / jpeg_decompress_struct2.min_DCT_scaled_size;
            byte[][] byArray = jpeg_d_main_controller2.xbuffer[0][i];
            int n3 = jpeg_d_main_controller2.xbuffer_offset[0][i];
            byte[][] byArray2 = jpeg_d_main_controller2.xbuffer[1][i];
            int n4 = jpeg_d_main_controller2.xbuffer_offset[1][i];
            for (int j = 0; j < n2; ++j) {
                byArray[j - n2 + n3] = byArray[n2 * (n + 1) + j + n3];
                byArray2[j - n2 + n4] = byArray2[n2 * (n + 1) + j + n4];
                byArray[n2 * (n + 2) + j + n3] = byArray[j + n3];
                byArray2[n2 * (n + 2) + j + n4] = byArray2[j + n4];
            }
        }
    }

    static void process_data_crank_post(jpeg_decompress_struct jpeg_decompress_struct2, byte[][] byArray, int[] nArray, int n) {
        JPEGDecoder.error();
    }

    static void process_data_context_main(jpeg_decompress_struct jpeg_decompress_struct2, byte[][] byArray, int[] nArray, int n) {
        jpeg_d_main_controller jpeg_d_main_controller2 = jpeg_decompress_struct2.main;
        if (!jpeg_d_main_controller2.buffer_full) {
            int n2 = 0;
            switch (jpeg_decompress_struct2.coef.decompress_data) {
                case 0: {
                    n2 = JPEGDecoder.decompress_data(jpeg_decompress_struct2, jpeg_d_main_controller2.xbuffer[jpeg_d_main_controller2.whichptr], jpeg_d_main_controller2.xbuffer_offset[jpeg_d_main_controller2.whichptr]);
                    break;
                }
                case 1: {
                    n2 = JPEGDecoder.decompress_smooth_data(jpeg_decompress_struct2, jpeg_d_main_controller2.xbuffer[jpeg_d_main_controller2.whichptr], jpeg_d_main_controller2.xbuffer_offset[jpeg_d_main_controller2.whichptr]);
                    break;
                }
                case 2: {
                    n2 = JPEGDecoder.decompress_onepass(jpeg_decompress_struct2, jpeg_d_main_controller2.xbuffer[jpeg_d_main_controller2.whichptr], jpeg_d_main_controller2.xbuffer_offset[jpeg_d_main_controller2.whichptr]);
                    break;
                }
                default: {
                    n2 = 0;
                }
            }
            if (n2 == 0) {
                return;
            }
            jpeg_d_main_controller2.buffer_full = true;
            jpeg_d_main_controller jpeg_d_main_controller3 = jpeg_d_main_controller2;
            ++jpeg_d_main_controller3.iMCU_row_ctr;
        }
        switch (jpeg_d_main_controller2.context_state) {
            case 2: {
                JPEGDecoder.post_process_data(jpeg_decompress_struct2, jpeg_d_main_controller2.xbuffer[jpeg_d_main_controller2.whichptr], jpeg_d_main_controller2.xbuffer_offset[jpeg_d_main_controller2.whichptr], jpeg_d_main_controller2.rowgroup_ctr, jpeg_d_main_controller2.rowgroups_avail, byArray, nArray, n);
                if (jpeg_d_main_controller2.rowgroup_ctr[0] < jpeg_d_main_controller2.rowgroups_avail) {
                    return;
                }
                jpeg_d_main_controller2.context_state = 0;
                if (nArray[0] >= n) {
                    return;
                }
            }
            case 0: {
                jpeg_d_main_controller2.rowgroup_ctr[0] = 0;
                jpeg_d_main_controller2.rowgroups_avail = jpeg_decompress_struct2.min_DCT_scaled_size - 1;
                if (jpeg_d_main_controller2.iMCU_row_ctr == jpeg_decompress_struct2.total_iMCU_rows) {
                    JPEGDecoder.set_bottom_pointers(jpeg_decompress_struct2);
                }
                jpeg_d_main_controller2.context_state = 1;
            }
            case 1: {
                JPEGDecoder.post_process_data(jpeg_decompress_struct2, jpeg_d_main_controller2.xbuffer[jpeg_d_main_controller2.whichptr], jpeg_d_main_controller2.xbuffer_offset[jpeg_d_main_controller2.whichptr], jpeg_d_main_controller2.rowgroup_ctr, jpeg_d_main_controller2.rowgroups_avail, byArray, nArray, n);
                if (jpeg_d_main_controller2.rowgroup_ctr[0] < jpeg_d_main_controller2.rowgroups_avail) {
                    return;
                }
                if (jpeg_d_main_controller2.iMCU_row_ctr == 1) {
                    JPEGDecoder.set_wraparound_pointers(jpeg_decompress_struct2);
                }
                jpeg_d_main_controller jpeg_d_main_controller4 = jpeg_d_main_controller2;
                jpeg_d_main_controller4.whichptr ^= 1;
                jpeg_d_main_controller2.buffer_full = false;
                jpeg_d_main_controller2.rowgroup_ctr[0] = jpeg_decompress_struct2.min_DCT_scaled_size + 1;
                jpeg_d_main_controller2.rowgroups_avail = jpeg_decompress_struct2.min_DCT_scaled_size + 2;
                jpeg_d_main_controller2.context_state = 2;
                break;
            }
        }
    }

    static void process_data_simple_main(jpeg_decompress_struct jpeg_decompress_struct2, byte[][] byArray, int[] nArray, int n) {
        int n2;
        jpeg_d_main_controller jpeg_d_main_controller2 = jpeg_decompress_struct2.main;
        if (!jpeg_d_main_controller2.buffer_full) {
            n2 = 0;
            switch (jpeg_decompress_struct2.coef.decompress_data) {
                case 0: {
                    n2 = JPEGDecoder.decompress_data(jpeg_decompress_struct2, jpeg_d_main_controller2.buffer, jpeg_d_main_controller2.buffer_offset);
                    break;
                }
                case 1: {
                    n2 = JPEGDecoder.decompress_smooth_data(jpeg_decompress_struct2, jpeg_d_main_controller2.buffer, jpeg_d_main_controller2.buffer_offset);
                    break;
                }
                case 2: {
                    n2 = JPEGDecoder.decompress_onepass(jpeg_decompress_struct2, jpeg_d_main_controller2.buffer, jpeg_d_main_controller2.buffer_offset);
                    break;
                }
                default: {
                    n2 = 0;
                }
            }
            if (n2 == 0) {
                return;
            }
            jpeg_d_main_controller2.buffer_full = true;
        }
        n2 = jpeg_decompress_struct2.min_DCT_scaled_size;
        JPEGDecoder.post_process_data(jpeg_decompress_struct2, jpeg_d_main_controller2.buffer, jpeg_d_main_controller2.buffer_offset, jpeg_d_main_controller2.rowgroup_ctr, n2, byArray, nArray, n);
        if (jpeg_d_main_controller2.rowgroup_ctr[0] >= n2) {
            jpeg_d_main_controller2.buffer_full = false;
            jpeg_d_main_controller2.rowgroup_ctr[0] = 0;
        }
    }

    static int jpeg_read_scanlines(jpeg_decompress_struct jpeg_decompress_struct2, byte[][] byArray, int n) {
        if (jpeg_decompress_struct2.global_state != 205) {
            JPEGDecoder.error();
        }
        if (jpeg_decompress_struct2.output_scanline >= jpeg_decompress_struct2.output_height) {
            return 0;
        }
        jpeg_decompress_struct2.row_ctr[0] = 0;
        switch (jpeg_decompress_struct2.main.process_data) {
            case 0: {
                JPEGDecoder.process_data_simple_main(jpeg_decompress_struct2, byArray, jpeg_decompress_struct2.row_ctr, n);
                break;
            }
            case 1: {
                JPEGDecoder.process_data_context_main(jpeg_decompress_struct2, byArray, jpeg_decompress_struct2.row_ctr, n);
                break;
            }
            case 2: {
                JPEGDecoder.process_data_crank_post(jpeg_decompress_struct2, byArray, jpeg_decompress_struct2.row_ctr, n);
                break;
            }
            default: {
                JPEGDecoder.error();
            }
        }
        jpeg_decompress_struct2.output_scanline += jpeg_decompress_struct2.row_ctr[0];
        return jpeg_decompress_struct2.row_ctr[0];
    }

    static boolean output_pass_setup(jpeg_decompress_struct jpeg_decompress_struct2) {
        if (jpeg_decompress_struct2.global_state != 204) {
            JPEGDecoder.prepare_for_output_pass(jpeg_decompress_struct2);
            jpeg_decompress_struct2.output_scanline = 0;
            jpeg_decompress_struct2.global_state = 204;
        }
        while (jpeg_decompress_struct2.master.is_dummy_pass) {
            JPEGDecoder.error();
        }
        jpeg_decompress_struct2.global_state = jpeg_decompress_struct2.raw_data_out ? 206 : 205;
        return true;
    }

    static void sep_upsample(jpeg_decompress_struct jpeg_decompress_struct2, byte[][][] byArray, int[] nArray, int[] nArray2, int n, byte[][] byArray2, int[] nArray3, int n2) {
        int n3;
        jpeg_upsampler jpeg_upsampler2 = jpeg_decompress_struct2.upsample;
        if (jpeg_upsampler2.next_row_out >= jpeg_decompress_struct2.max_v_samp_factor) {
            block16: for (n3 = 0; n3 < jpeg_decompress_struct2.num_components; ++n3) {
                jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[n3];
                int n4 = nArray[n3] + nArray2[0] * jpeg_upsampler2.rowgroup_height[n3];
                switch (jpeg_upsampler2.methods[n3]) {
                    case 0: {
                        JPEGDecoder.noop_upsample(jpeg_decompress_struct2, jpeg_component_info2, byArray[n3], n4, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, n3);
                        continue block16;
                    }
                    case 1: {
                        JPEGDecoder.fullsize_upsample(jpeg_decompress_struct2, jpeg_component_info2, byArray[n3], n4, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, n3);
                        continue block16;
                    }
                    case 2: {
                        JPEGDecoder.h2v1_fancy_upsample(jpeg_decompress_struct2, jpeg_component_info2, byArray[n3], n4, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, n3);
                        continue block16;
                    }
                    case 3: {
                        JPEGDecoder.h2v1_upsample(jpeg_decompress_struct2, jpeg_component_info2, byArray[n3], n4, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, n3);
                        continue block16;
                    }
                    case 4: {
                        JPEGDecoder.h2v2_fancy_upsample(jpeg_decompress_struct2, jpeg_component_info2, byArray[n3], n4, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, n3);
                        continue block16;
                    }
                    case 5: {
                        JPEGDecoder.h2v2_upsample(jpeg_decompress_struct2, jpeg_component_info2, byArray[n3], n4, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, n3);
                        continue block16;
                    }
                    case 6: {
                        JPEGDecoder.int_upsample(jpeg_decompress_struct2, jpeg_component_info2, byArray[n3], n4, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, n3);
                    }
                }
            }
            jpeg_upsampler2.next_row_out = 0;
        }
        if ((n3 = jpeg_decompress_struct2.max_v_samp_factor - jpeg_upsampler2.next_row_out) > jpeg_upsampler2.rows_to_go) {
            n3 = jpeg_upsampler2.rows_to_go;
        }
        if (n3 > (n2 -= nArray3[0])) {
            n3 = n2;
        }
        switch (jpeg_decompress_struct2.cconvert.color_convert) {
            case 0: {
                JPEGDecoder.null_convert(jpeg_decompress_struct2, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, jpeg_upsampler2.next_row_out, byArray2, nArray3[0], n3);
                break;
            }
            case 1: {
                JPEGDecoder.grayscale_convert(jpeg_decompress_struct2, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, jpeg_upsampler2.next_row_out, byArray2, nArray3[0], n3);
                break;
            }
            case 2: {
                JPEGDecoder.ycc_rgb_convert(jpeg_decompress_struct2, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, jpeg_upsampler2.next_row_out, byArray2, nArray3[0], n3);
                break;
            }
            case 3: {
                JPEGDecoder.gray_rgb_convert(jpeg_decompress_struct2, jpeg_upsampler2.color_buf, jpeg_upsampler2.color_buf_offset, jpeg_upsampler2.next_row_out, byArray2, nArray3[0], n3);
                break;
            }
            case 4: {
                JPEGDecoder.error();
            }
        }
        boolean bl = false;
        nArray3[0] = nArray3[0] + n3;
        jpeg_upsampler jpeg_upsampler3 = jpeg_upsampler2;
        jpeg_upsampler3.rows_to_go -= n3;
        jpeg_upsampler jpeg_upsampler4 = jpeg_upsampler2;
        jpeg_upsampler4.next_row_out += n3;
        if (jpeg_upsampler2.next_row_out >= jpeg_decompress_struct2.max_v_samp_factor) {
            boolean bl2 = false;
            nArray2[0] = nArray2[0] + 1;
        }
    }

    static void noop_upsample(jpeg_decompress_struct jpeg_decompress_struct2, jpeg_component_info jpeg_component_info2, byte[][] byArray, int n, byte[][][] byArray2, int[] nArray, int n2) {
        byArray2[n2] = null;
    }

    static void fullsize_upsample(jpeg_decompress_struct jpeg_decompress_struct2, jpeg_component_info jpeg_component_info2, byte[][] byArray, int n, byte[][][] byArray2, int[] nArray, int n2) {
        byArray2[n2] = byArray;
        nArray[n2] = n;
    }

    static void h2v1_upsample(jpeg_decompress_struct jpeg_decompress_struct2, jpeg_component_info jpeg_component_info2, byte[][] byArray, int n, byte[][][] byArray2, int[] nArray, int n2) {
        byte[][] byArray3 = byArray2[n2];
        nArray[n2] = 0;
        for (int i = 0; i < jpeg_decompress_struct2.max_v_samp_factor; ++i) {
            byte[] byArray4 = byArray[i + n];
            byte[] byArray5 = byArray3[i];
            int n3 = 0;
            int n4 = 0;
            int n5 = n4 + jpeg_decompress_struct2.output_width;
            while (n4 < n5) {
                byte by = byArray4[n3++];
                byArray5[n4++] = by;
                byArray5[n4++] = by;
            }
        }
    }

    static void h2v2_upsample(jpeg_decompress_struct jpeg_decompress_struct2, jpeg_component_info jpeg_component_info2, byte[][] byArray, int n, byte[][][] byArray2, int[] nArray, int n2) {
        byte[][] byArray3 = byArray2[n2];
        nArray[n2] = 0;
        int n3 = 0;
        for (int i = 0; i < jpeg_decompress_struct2.max_v_samp_factor; i += 2) {
            byte[] byArray4 = byArray[n3 + n];
            byte[] byArray5 = byArray3[i];
            int n4 = 0;
            int n5 = 0;
            int n6 = n5 + jpeg_decompress_struct2.output_width;
            while (n5 < n6) {
                byte by = byArray4[n4++];
                byArray5[n5++] = by;
                byArray5[n5++] = by;
            }
            JPEGDecoder.jcopy_sample_rows(byArray3, i, byArray3, i + 1, 1, jpeg_decompress_struct2.output_width);
            ++n3;
        }
    }

    static void h2v1_fancy_upsample(jpeg_decompress_struct jpeg_decompress_struct2, jpeg_component_info jpeg_component_info2, byte[][] byArray, int n, byte[][][] byArray2, int[] nArray, int n2) {
        byte[][] byArray3 = byArray2[n2];
        nArray[n2] = 0;
        for (int i = 0; i < jpeg_decompress_struct2.max_v_samp_factor; ++i) {
            byte[] byArray4 = byArray[i + n];
            byte[] byArray5 = byArray3[i];
            int n3 = 0;
            int n4 = 0;
            int n5 = byArray4[n3++] & 0xFF;
            byArray5[n4++] = (byte)n5;
            byArray5[n4++] = (byte)(n5 * 3 + (byArray4[n3] & 0xFF) + 2 >> 2);
            for (int j = jpeg_component_info2.downsampled_width - 2; j > 0; --j) {
                n5 = (byArray4[n3++] & 0xFF) * 3;
                byArray5[n4++] = (byte)(n5 + (byArray4[n3 - 2] & 0xFF) + 1 >> 2);
                byArray5[n4++] = (byte)(n5 + (byArray4[n3] & 0xFF) + 2 >> 2);
            }
            n5 = byArray4[n3] & 0xFF;
            byArray5[n4++] = (byte)(n5 * 3 + (byArray4[n3 - 1] & 0xFF) + 1 >> 2);
            byArray5[n4++] = (byte)n5;
        }
    }

    static void h2v2_fancy_upsample(jpeg_decompress_struct jpeg_decompress_struct2, jpeg_component_info jpeg_component_info2, byte[][] byArray, int n, byte[][][] byArray2, int[] nArray, int n2) {
        byte[][] byArray3 = byArray2[n2];
        nArray[n2] = 0;
        int n3 = 0;
        int n4 = 0;
        while (n4 < jpeg_decompress_struct2.max_v_samp_factor) {
            for (int i = 0; i < 2; ++i) {
                byte[] byArray4 = byArray[n3 + n];
                byte[] byArray5 = i == 0 ? byArray[n3 - 1 + n] : byArray[n3 + 1 + n];
                byte[] byArray6 = byArray3[n4++];
                int n5 = 0;
                int n6 = 0;
                int n7 = 0;
                int n8 = (byArray4[n5++] & 0xFF) * 3 + (byArray5[n6++] & 0xFF);
                int n9 = (byArray4[n5++] & 0xFF) * 3 + (byArray5[n6++] & 0xFF);
                byArray6[n7++] = (byte)(n8 * 4 + 8 >> 4);
                byArray6[n7++] = (byte)(n8 * 3 + n9 + 7 >> 4);
                int n10 = n8;
                n8 = n9;
                for (int j = jpeg_component_info2.downsampled_width - 2; j > 0; --j) {
                    n9 = (byArray4[n5++] & 0xFF) * 3 + (byArray5[n6++] & 0xFF);
                    byArray6[n7++] = (byte)(n8 * 3 + n10 + 8 >> 4);
                    byArray6[n7++] = (byte)(n8 * 3 + n9 + 7 >> 4);
                    n10 = n8;
                    n8 = n9;
                }
                byArray6[n7++] = (byte)(n8 * 3 + n10 + 8 >> 4);
                byArray6[n7++] = (byte)(n8 * 4 + 7 >> 4);
            }
            ++n3;
        }
    }

    static void int_upsample(jpeg_decompress_struct jpeg_decompress_struct2, jpeg_component_info jpeg_component_info2, byte[][] byArray, int n, byte[][][] byArray2, int[] nArray, int n2) {
        jpeg_upsampler jpeg_upsampler2 = jpeg_decompress_struct2.upsample;
        byte[][] byArray3 = byArray2[n2];
        nArray[n2] = 0;
        int n3 = jpeg_upsampler2.h_expand[jpeg_component_info2.component_index];
        byte by = jpeg_upsampler2.v_expand[jpeg_component_info2.component_index];
        int n4 = 0;
        for (int i = 0; i < jpeg_decompress_struct2.max_v_samp_factor; i += by) {
            byte[] byArray4 = byArray[n4 + n];
            int n5 = 0;
            byte[] byArray5 = byArray3[i];
            int n6 = 0;
            int n7 = n6 + jpeg_decompress_struct2.output_width;
            while (n6 < n7) {
                byte by2 = byArray4[n5++];
                for (int j = n3; j > 0; --j) {
                    byArray5[n6++] = by2;
                }
            }
            if (by > 1) {
                JPEGDecoder.jcopy_sample_rows(byArray3, i, byArray3, i + 1, by - 1, jpeg_decompress_struct2.output_width);
            }
            ++n4;
        }
    }

    static void null_convert(jpeg_decompress_struct jpeg_decompress_struct2, byte[][][] byArray, int[] nArray, int n, byte[][] byArray2, int n2, int n3) {
        int n4 = jpeg_decompress_struct2.num_components;
        int n5 = jpeg_decompress_struct2.output_width;
        while (--n3 >= 0) {
            for (int i = 0; i < n4; ++i) {
                byte[] byArray3 = byArray[i][n + nArray[0]];
                byte[] byArray4 = byArray2[n2];
                int n6 = 0;
                switch (i) {
                    case 2: {
                        n6 = 0;
                        break;
                    }
                    case 1: {
                        n6 = 1;
                        break;
                    }
                    case 0: {
                        n6 = 2;
                    }
                }
                int n7 = n6;
                int n8 = 0;
                for (int j = n5; j > 0; --j) {
                    byArray4[n7] = byArray3[n8++];
                    n7 += n4;
                }
            }
            ++n;
            ++n2;
        }
    }

    static void grayscale_convert(jpeg_decompress_struct jpeg_decompress_struct2, byte[][][] byArray, int[] nArray, int n, byte[][] byArray2, int n2, int n3) {
        JPEGDecoder.jcopy_sample_rows(byArray[0], n + nArray[0], byArray2, n2, n3, jpeg_decompress_struct2.output_width);
    }

    static void gray_rgb_convert(jpeg_decompress_struct jpeg_decompress_struct2, byte[][][] byArray, int[] nArray, int n, byte[][] byArray2, int n2, int n3) {
        int n4 = jpeg_decompress_struct2.output_width;
        while (--n3 >= 0) {
            byte[] byArray3 = byArray[0][n++ + nArray[0]];
            byte[] byArray4 = byArray2[n2++];
            int n5 = 0;
            for (int i = 0; i < n4; ++i) {
                byte by;
                byte[] byArray5 = byArray4;
                int n6 = 2 + n5;
                byte[] byArray6 = byArray4;
                int n7 = 1 + n5;
                byte[] byArray7 = byArray4;
                int n8 = 0 + n5;
                byArray7[n8] = by = byArray3[i];
                byArray5[n6] = byArray6[n7] = by;
                n5 += 3;
            }
        }
    }

    static void ycc_rgb_convert(jpeg_decompress_struct jpeg_decompress_struct2, byte[][][] byArray, int[] nArray, int n, byte[][] byArray2, int n2, int n3) {
        jpeg_color_deconverter jpeg_color_deconverter2 = jpeg_decompress_struct2.cconvert;
        int n4 = jpeg_decompress_struct2.output_width;
        byte[] byArray3 = jpeg_decompress_struct2.sample_range_limit;
        int n5 = jpeg_decompress_struct2.sample_range_limit_offset;
        int[] nArray2 = jpeg_color_deconverter2.Cr_r_tab;
        int[] nArray3 = jpeg_color_deconverter2.Cb_b_tab;
        int[] nArray4 = jpeg_color_deconverter2.Cr_g_tab;
        int[] nArray5 = jpeg_color_deconverter2.Cb_g_tab;
        while (--n3 >= 0) {
            byte[] byArray4 = byArray[0][n + nArray[0]];
            byte[] byArray5 = byArray[1][n + nArray[1]];
            byte[] byArray6 = byArray[2][n + nArray[2]];
            ++n;
            byte[] byArray7 = byArray2[n2++];
            int n6 = 0;
            for (int i = 0; i < n4; ++i) {
                int n7 = byArray4[i] & 0xFF;
                int n8 = byArray5[i] & 0xFF;
                int n9 = byArray6[i] & 0xFF;
                byArray7[n6 + 2] = byArray3[n7 + nArray2[n9] + n5];
                byArray7[n6 + 1] = byArray3[n7 + (nArray5[n8] + nArray4[n9] >> 16) + n5];
                byArray7[n6 + 0] = byArray3[n7 + nArray3[n8] + n5];
                n6 += 3;
            }
        }
    }

    static boolean process_COM(jpeg_decompress_struct jpeg_decompress_struct2) {
        return JPEGDecoder.skip_variable((jpeg_decompress_struct)jpeg_decompress_struct2);
    }

    static void skip_input_data(jpeg_decompress_struct jpeg_decompress_struct2, int n) {
        if (n > 0) {
            while (n > jpeg_decompress_struct2.bytes_in_buffer - jpeg_decompress_struct2.bytes_offset) {
                n -= jpeg_decompress_struct2.bytes_in_buffer - jpeg_decompress_struct2.bytes_offset;
                if (jpeg_decompress_struct2 > 0) continue;
                JPEGDecoder.error();
            }
            jpeg_decompress_struct2.bytes_offset += n;
        }
    }

    static boolean get_interesting_appn(jpeg_decompress_struct jpeg_decompress_struct2) {
        byte[] byArray = new byte[14];
        if (jpeg_decompress_struct2.bytes_offset == jpeg_decompress_struct2.bytes_in_buffer) {
            JPEGDecoder.fill_input_buffer((jpeg_decompress_struct)jpeg_decompress_struct2);
        }
        int n = (jpeg_decompress_struct2.buffer[jpeg_decompress_struct2.bytes_offset++] & 0xFF) << 8;
        if (jpeg_decompress_struct2.bytes_offset == jpeg_decompress_struct2.bytes_in_buffer) {
            JPEGDecoder.fill_input_buffer((jpeg_decompress_struct)jpeg_decompress_struct2);
        }
        n |= jpeg_decompress_struct2.buffer[jpeg_decompress_struct2.bytes_offset++] & 0xFF;
        int n2 = (n -= 2) >= 14 ? 14 : (n > 0 ? n : 0);
        for (int i = 0; i < n2; ++i) {
            if (jpeg_decompress_struct2.bytes_offset == jpeg_decompress_struct2.bytes_in_buffer) {
                JPEGDecoder.fill_input_buffer((jpeg_decompress_struct)jpeg_decompress_struct2);
            }
            byArray[i] = jpeg_decompress_struct2.buffer[jpeg_decompress_struct2.bytes_offset++];
        }
        n -= n2;
        switch (jpeg_decompress_struct2.unread_marker) {
            case 224: {
                JPEGDecoder.examine_app0(jpeg_decompress_struct2, byArray, n2, n);
                break;
            }
            case 238: {
                JPEGDecoder.examine_app14(jpeg_decompress_struct2, byArray, n2, n);
                break;
            }
            default: {
                JPEGDecoder.error();
            }
        }
        if (n > 0) {
            JPEGDecoder.skip_input_data(jpeg_decompress_struct2, n);
        }
        return true;
    }

    static void examine_app0(jpeg_decompress_struct jpeg_decompress_struct2, byte[] byArray, int n, int n2) {
        int n3 = n + n2;
        if (n >= 14 && (byArray[0] & 0xFF) == 74 && (byArray[1] & 0xFF) == 70 && (byArray[2] & 0xFF) == 73 && (byArray[3] & 0xFF) == 70 && (byArray[4] & 0xFF) == 0) {
            jpeg_decompress_struct2.saw_JFIF_marker = true;
            jpeg_decompress_struct2.JFIF_major_version = byArray[5];
            jpeg_decompress_struct2.JFIF_minor_version = (byte)(byArray[6] & 0xFF);
            jpeg_decompress_struct2.density_unit = (byte)(byArray[7] & 0xFF);
            jpeg_decompress_struct2.X_density = (short)(((byArray[8] & 0xFF) << 8) + (byArray[9] & 0xFF));
            jpeg_decompress_struct2.Y_density = (short)(((byArray[10] & 0xFF) << 8) + (byArray[11] & 0xFF));
            if (jpeg_decompress_struct2.JFIF_major_version != 1) {
                // empty if block
            }
            if ((byArray[12] & 0xFF | byArray[13] & 0xFF) != 0) {
                // empty if block
            }
            if ((n3 -= 14) != (byArray[12] & 0xFF) * (byArray[13] & 0xFF) * 3) {
                // empty if block
            }
        } else if (n >= 6 && (byArray[0] & 0xFF) == 74 && (byArray[1] & 0xFF) == 70 && (byArray[2] & 0xFF) == 88 && (byArray[3] & 0xFF) == 88 && (byArray[4] & 0xFF) == 0) {
            switch (byArray[5] & 0xFF) {
                default: 
            }
        }
    }

    static void examine_app14(jpeg_decompress_struct jpeg_decompress_struct2, byte[] byArray, int n, int n2) {
        if (n >= 12 && (byArray[0] & 0xFF) == 65 && (byArray[1] & 0xFF) == 100 && (byArray[2] & 0xFF) == 111 && (byArray[3] & 0xFF) == 98 && (byArray[4] & 0xFF) == 101) {
            int n3 = byArray[11] & 0xFF;
            jpeg_decompress_struct2.saw_Adobe_marker = true;
            jpeg_decompress_struct2.Adobe_transform = (byte)n3;
        }
    }

    static void jinit_input_controller(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_input_controller jpeg_input_controller2;
        jpeg_decompress_struct2.inputctl = jpeg_input_controller2 = new jpeg_input_controller();
        jpeg_input_controller jpeg_input_controller3 = jpeg_input_controller2;
        jpeg_input_controller3.has_multiple_scans = false;
        jpeg_input_controller3.eoi_reached = false;
        jpeg_input_controller3.inheaders = true;
    }

    static void reset_marker_reader(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_marker_reader jpeg_marker_reader2 = jpeg_decompress_struct2.marker;
        jpeg_decompress_struct2.comp_info = null;
        jpeg_decompress_struct2.input_scan_number = 0;
        jpeg_decompress_struct2.unread_marker = 0;
        jpeg_marker_reader2.saw_SOI = false;
        jpeg_marker_reader2.saw_SOF = false;
        jpeg_marker_reader2.discarded_bytes = 0;
    }

    static void reset_input_controller(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_input_controller jpeg_input_controller2 = jpeg_decompress_struct2.inputctl;
        jpeg_input_controller2.has_multiple_scans = false;
        jpeg_input_controller2.eoi_reached = false;
        jpeg_input_controller2.inheaders = true;
        JPEGDecoder.reset_marker_reader(jpeg_decompress_struct2);
        jpeg_decompress_struct2.coef_bits = null;
    }

    static void finish_output_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decomp_master jpeg_decomp_master2 = jpeg_decompress_struct2.master;
        if (jpeg_decompress_struct2.quantize_colors) {
            JPEGDecoder.error(20);
        }
        jpeg_decomp_master jpeg_decomp_master3 = jpeg_decomp_master2;
        ++jpeg_decomp_master3.pass_number;
    }

    static void jpeg_destroy(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decompress_struct2.global_state = 0;
    }

    static void jpeg_destroy_decompress(jpeg_decompress_struct jpeg_decompress_struct2) {
        JPEGDecoder.jpeg_destroy(jpeg_decompress_struct2);
    }

    static boolean jpeg_input_complete(jpeg_decompress_struct jpeg_decompress_struct2) {
        if (jpeg_decompress_struct2.global_state < 200 || jpeg_decompress_struct2.global_state > 210) {
            JPEGDecoder.error();
        }
        return jpeg_decompress_struct2.inputctl.eoi_reached;
    }

    static boolean jpeg_start_output(jpeg_decompress_struct jpeg_decompress_struct2, int n) {
        if (jpeg_decompress_struct2.global_state != 207 && jpeg_decompress_struct2.global_state != 204) {
            JPEGDecoder.error();
        }
        if (n <= 0) {
            n = 1;
        }
        if (jpeg_decompress_struct2.inputctl.eoi_reached && n > jpeg_decompress_struct2.input_scan_number) {
            n = jpeg_decompress_struct2.input_scan_number;
        }
        jpeg_decompress_struct2.output_scan_number = n;
        return JPEGDecoder.output_pass_setup(jpeg_decompress_struct2);
    }

    static boolean jpeg_finish_output(jpeg_decompress_struct jpeg_decompress_struct2) {
        if ((jpeg_decompress_struct2.global_state == 205 || jpeg_decompress_struct2.global_state == 206) && jpeg_decompress_struct2.buffered_image) {
            JPEGDecoder.finish_output_pass(jpeg_decompress_struct2);
            jpeg_decompress_struct2.global_state = 208;
        } else if (jpeg_decompress_struct2.global_state != 208) {
            JPEGDecoder.error();
        }
        while (jpeg_decompress_struct2.input_scan_number <= jpeg_decompress_struct2.output_scan_number && !jpeg_decompress_struct2.inputctl.eoi_reached) {
            if (JPEGDecoder.consume_input(jpeg_decompress_struct2) != 0) continue;
            return false;
        }
        jpeg_decompress_struct2.global_state = 207;
        return true;
    }

    static boolean jpeg_finish_decompress(jpeg_decompress_struct jpeg_decompress_struct2) {
        if (!(jpeg_decompress_struct2.global_state != 205 && jpeg_decompress_struct2.global_state != 206 || jpeg_decompress_struct2.buffered_image)) {
            if (jpeg_decompress_struct2.output_scanline < jpeg_decompress_struct2.output_height) {
                JPEGDecoder.error();
            }
            JPEGDecoder.finish_output_pass(jpeg_decompress_struct2);
            jpeg_decompress_struct2.global_state = 210;
        } else if (jpeg_decompress_struct2.global_state == 207) {
            jpeg_decompress_struct2.global_state = 210;
        } else if (jpeg_decompress_struct2.global_state != 210) {
            JPEGDecoder.error();
        }
        while (!jpeg_decompress_struct2.inputctl.eoi_reached) {
            if (JPEGDecoder.consume_input(jpeg_decompress_struct2) != 0) continue;
            return false;
        }
        JPEGDecoder.jpeg_abort(jpeg_decompress_struct2);
        return true;
    }

    static int jpeg_read_header(jpeg_decompress_struct jpeg_decompress_struct2, boolean bl) {
        if (jpeg_decompress_struct2.global_state != 200 && jpeg_decompress_struct2.global_state != 201) {
            JPEGDecoder.error();
        }
        int n = JPEGDecoder.jpeg_consume_input(jpeg_decompress_struct2);
        switch (n) {
            case 1: {
                n = 1;
                break;
            }
            case 2: {
                if (bl) {
                    JPEGDecoder.error();
                }
                JPEGDecoder.jpeg_abort(jpeg_decompress_struct2);
                n = 2;
            }
        }
        return n;
    }

    static int dummy_consume_data(jpeg_decompress_struct jpeg_decompress_struct2) {
        return 0;
    }

    static int consume_data(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_d_coef_controller jpeg_d_coef_controller2 = jpeg_decompress_struct2.coef;
        for (int i = jpeg_d_coef_controller2.MCU_vert_offset; i < jpeg_d_coef_controller2.MCU_rows_per_iMCU_row; ++i) {
            for (int j = jpeg_d_coef_controller2.MCU_ctr; j < jpeg_decompress_struct2.MCUs_per_row; ++j) {
                int n = 0;
                for (int k = 0; k < jpeg_decompress_struct2.comps_in_scan; ++k) {
                    jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.cur_comp_info[k];
                    int n2 = j * jpeg_component_info2.MCU_width;
                    for (int i2 = 0; i2 < jpeg_component_info2.MCU_height; ++i2) {
                        short[][] sArray = jpeg_d_coef_controller2.whole_image[jpeg_component_info2.component_index][i2 + i + jpeg_decompress_struct2.input_iMCU_row * jpeg_component_info2.v_samp_factor];
                        int n3 = n2;
                        for (int i3 = 0; i3 < jpeg_component_info2.MCU_width; ++i3) {
                            jpeg_d_coef_controller2.MCU_buffer[n++] = sArray[n3++];
                        }
                    }
                }
                if (jpeg_decompress_struct2.entropy.decode_mcu(jpeg_decompress_struct2, jpeg_d_coef_controller2.MCU_buffer)) continue;
                jpeg_d_coef_controller2.MCU_vert_offset = i;
                jpeg_d_coef_controller2.MCU_ctr = j;
                return 0;
            }
            jpeg_d_coef_controller2.MCU_ctr = 0;
        }
        if (++jpeg_decompress_struct2.input_iMCU_row < jpeg_decompress_struct2.total_iMCU_rows) {
            jpeg_d_coef_controller2.start_iMCU_row(jpeg_decompress_struct2);
            return 3;
        }
        JPEGDecoder.finish_input_pass(jpeg_decompress_struct2);
        return 4;
    }

    static int consume_input(jpeg_decompress_struct jpeg_decompress_struct2) {
        switch (jpeg_decompress_struct2.inputctl.consume_input) {
            case 1: {
                switch (jpeg_decompress_struct2.coef.consume_data) {
                    case 0: {
                        return JPEGDecoder.consume_data(jpeg_decompress_struct2);
                    }
                    case 1: {
                        return JPEGDecoder.dummy_consume_data(jpeg_decompress_struct2);
                    }
                }
                JPEGDecoder.error();
                break;
            }
            case 0: {
                return JPEGDecoder.consume_markers(jpeg_decompress_struct2);
            }
            default: {
                JPEGDecoder.error();
            }
        }
        return 0;
    }

    /*
     * Exception decompiling
     */
    static int read_markers(jpeg_decompress_struct var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl9 : IF_ICMPNE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    static long jdiv_round_up(long l2, long l3) {
        return (l2 + l3 - 1L) / l3;
    }

    static void initial_setup(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_component_info jpeg_component_info2;
        int n;
        if (jpeg_decompress_struct2.image_height > 65500 || jpeg_decompress_struct2.image_width > 65500) {
            JPEGDecoder.error();
        }
        if (jpeg_decompress_struct2.data_precision != 8) {
            JPEGDecoder.error(" [data precision=" + jpeg_decompress_struct2.data_precision);
        }
        if (jpeg_decompress_struct2.num_components > 10) {
            JPEGDecoder.error();
        }
        jpeg_decompress_struct2.max_h_samp_factor = 1;
        jpeg_decompress_struct2.max_v_samp_factor = 1;
        for (n = 0; n < jpeg_decompress_struct2.num_components; ++n) {
            jpeg_component_info2 = jpeg_decompress_struct2.comp_info[n];
            if (jpeg_component_info2.h_samp_factor <= 0 || jpeg_component_info2.h_samp_factor > 4 || jpeg_component_info2.v_samp_factor <= 0 || jpeg_component_info2.v_samp_factor > 4) {
                JPEGDecoder.error();
            }
            jpeg_decompress_struct2.max_h_samp_factor = Math.max(jpeg_decompress_struct2.max_h_samp_factor, jpeg_component_info2.h_samp_factor);
            jpeg_decompress_struct2.max_v_samp_factor = Math.max(jpeg_decompress_struct2.max_v_samp_factor, jpeg_component_info2.v_samp_factor);
        }
        jpeg_decompress_struct2.min_DCT_scaled_size = 8;
        for (n = 0; n < jpeg_decompress_struct2.num_components; ++n) {
            jpeg_component_info2 = jpeg_decompress_struct2.comp_info[n];
            jpeg_component_info2.DCT_scaled_size = 8;
            jpeg_component_info2.width_in_blocks = (int)JPEGDecoder.jdiv_round_up((long)jpeg_decompress_struct2.image_width * (long)jpeg_component_info2.h_samp_factor, jpeg_decompress_struct2.max_h_samp_factor * 8);
            jpeg_component_info2.height_in_blocks = (int)JPEGDecoder.jdiv_round_up((long)jpeg_decompress_struct2.image_height * (long)jpeg_component_info2.v_samp_factor, jpeg_decompress_struct2.max_v_samp_factor * 8);
            jpeg_component_info2.downsampled_width = (int)JPEGDecoder.jdiv_round_up((long)jpeg_decompress_struct2.image_width * (long)jpeg_component_info2.h_samp_factor, jpeg_decompress_struct2.max_h_samp_factor);
            jpeg_component_info2.downsampled_height = (int)JPEGDecoder.jdiv_round_up((long)jpeg_decompress_struct2.image_height * (long)jpeg_component_info2.v_samp_factor, jpeg_decompress_struct2.max_v_samp_factor);
            jpeg_component_info2.component_needed = true;
            jpeg_component_info2.quant_table = null;
        }
        jpeg_decompress_struct2.total_iMCU_rows = (int)JPEGDecoder.jdiv_round_up(jpeg_decompress_struct2.image_height, jpeg_decompress_struct2.max_v_samp_factor * 8);
        jpeg_decompress_struct2.inputctl.has_multiple_scans = jpeg_decompress_struct2.comps_in_scan < jpeg_decompress_struct2.num_components || jpeg_decompress_struct2.progressive_mode;
    }

    static void per_scan_setup(jpeg_decompress_struct jpeg_decompress_struct2) {
        int n = 0;
        if (jpeg_decompress_struct2.comps_in_scan == 1) {
            jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.cur_comp_info[0];
            jpeg_decompress_struct2.MCUs_per_row = jpeg_component_info2.width_in_blocks;
            jpeg_decompress_struct2.MCU_rows_in_scan = jpeg_component_info2.height_in_blocks;
            jpeg_component_info2.MCU_width = 1;
            jpeg_component_info2.MCU_height = 1;
            jpeg_component_info2.MCU_blocks = 1;
            jpeg_component_info2.MCU_sample_width = jpeg_component_info2.DCT_scaled_size;
            jpeg_component_info2.last_col_width = 1;
            n = jpeg_component_info2.height_in_blocks % jpeg_component_info2.v_samp_factor;
            if (n == 0) {
                n = jpeg_component_info2.v_samp_factor;
            }
            jpeg_component_info2.last_row_height = n;
            jpeg_decompress_struct2.blocks_in_MCU = 1;
            jpeg_decompress_struct2.MCU_membership[0] = 0;
        } else {
            if (jpeg_decompress_struct2.comps_in_scan <= 0 || jpeg_decompress_struct2.comps_in_scan > 4) {
                JPEGDecoder.error();
            }
            jpeg_decompress_struct2.MCUs_per_row = (int)JPEGDecoder.jdiv_round_up(jpeg_decompress_struct2.image_width, jpeg_decompress_struct2.max_h_samp_factor * 8);
            jpeg_decompress_struct2.MCU_rows_in_scan = (int)JPEGDecoder.jdiv_round_up(jpeg_decompress_struct2.image_height, jpeg_decompress_struct2.max_v_samp_factor * 8);
            jpeg_decompress_struct2.blocks_in_MCU = 0;
            for (int i = 0; i < jpeg_decompress_struct2.comps_in_scan; ++i) {
                jpeg_component_info jpeg_component_info3 = jpeg_decompress_struct2.cur_comp_info[i];
                jpeg_component_info3.MCU_width = jpeg_component_info3.h_samp_factor;
                jpeg_component_info3.MCU_height = jpeg_component_info3.v_samp_factor;
                jpeg_component_info3.MCU_blocks = jpeg_component_info3.MCU_width * jpeg_component_info3.MCU_height;
                jpeg_component_info3.MCU_sample_width = jpeg_component_info3.MCU_width * jpeg_component_info3.DCT_scaled_size;
                n = jpeg_component_info3.width_in_blocks % jpeg_component_info3.MCU_width;
                if (n == 0) {
                    n = jpeg_component_info3.MCU_width;
                }
                jpeg_component_info3.last_col_width = n;
                n = jpeg_component_info3.height_in_blocks % jpeg_component_info3.MCU_height;
                if (n == 0) {
                    n = jpeg_component_info3.MCU_height;
                }
                jpeg_component_info3.last_row_height = n;
                int n2 = jpeg_component_info3.MCU_blocks;
                if (jpeg_decompress_struct2.blocks_in_MCU + n2 > 10) {
                    JPEGDecoder.error();
                }
                while (n2-- > 0) {
                    jpeg_decompress_struct2.MCU_membership[jpeg_decompress_struct2.blocks_in_MCU++] = i;
                }
            }
        }
    }

    static void latch_quant_tables(jpeg_decompress_struct jpeg_decompress_struct2) {
        for (int i = 0; i < jpeg_decompress_struct2.comps_in_scan; ++i) {
            jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.cur_comp_info[i];
            if (jpeg_component_info2.quant_table != null) continue;
            int n = jpeg_component_info2.quant_tbl_no;
            if (n < 0 || n >= 4 || jpeg_decompress_struct2.quant_tbl_ptrs[n] == null) {
                JPEGDecoder.error();
            }
            JQUANT_TBL jQUANT_TBL = new JQUANT_TBL();
            System.arraycopy(jpeg_decompress_struct2.quant_tbl_ptrs[n].quantval, 0, jQUANT_TBL.quantval, 0, jQUANT_TBL.quantval.length);
            jQUANT_TBL.sent_table = jpeg_decompress_struct2.quant_tbl_ptrs[n].sent_table;
            jpeg_component_info2.quant_table = jQUANT_TBL;
        }
    }

    static void jpeg_make_d_derived_tbl(jpeg_decompress_struct jpeg_decompress_struct2, boolean bl, int n, d_derived_tbl d_derived_tbl2) {
        int n2;
        int n3;
        JHUFF_TBL jHUFF_TBL;
        int n4 = 0;
        byte[] byArray = new byte[257];
        int[] nArray = new int[257];
        if (n < 0 || n >= 4) {
            JPEGDecoder.error();
        }
        JHUFF_TBL jHUFF_TBL2 = jHUFF_TBL = bl ? jpeg_decompress_struct2.dc_huff_tbl_ptrs[n] : jpeg_decompress_struct2.ac_huff_tbl_ptrs[n];
        if (jHUFF_TBL == null) {
            JPEGDecoder.error();
        }
        d_derived_tbl2.pub = jHUFF_TBL;
        int n5 = 0;
        for (n3 = 1; n3 <= 16; ++n3) {
            n4 = jHUFF_TBL.bits[n3] & 0xFF;
            if (n4 < 0 || n5 + n4 > 256) {
                JPEGDecoder.error();
            }
            while (n4-- != 0) {
                byArray[n5++] = (byte)n3;
            }
        }
        byArray[n5] = 0;
        n3 = n5;
        int n6 = 0;
        int n7 = byArray[0];
        n5 = 0;
        while (byArray[n5] != 0) {
            while (byArray[n5] == n7) {
                nArray[n5++] = n6++;
            }
            if (n6 >= 1 << n7) {
                JPEGDecoder.error();
            }
            n6 <<= 1;
            ++n7;
        }
        n5 = 0;
        for (n2 = 1; n2 <= 16; ++n2) {
            if ((jHUFF_TBL.bits[n2] & 0xFF) != 0) {
                d_derived_tbl2.valoffset[n2] = n5 - nArray[n5];
                d_derived_tbl2.maxcode[n2] = nArray[(n5 += jHUFF_TBL.bits[n2] & 0xFF) - 1];
                continue;
            }
            d_derived_tbl2.maxcode[n2] = -1;
        }
        d_derived_tbl2.maxcode[17] = 1048575;
        for (n2 = 0; n2 < d_derived_tbl2.look_nbits.length; ++n2) {
            d_derived_tbl2.look_nbits[n2] = 0;
        }
        n5 = 0;
        for (n2 = 1; n2 <= 8; ++n2) {
            n4 = 1;
            while (n4 <= (jHUFF_TBL.bits[n2] & 0xFF)) {
                int n8 = nArray[n5] << 8 - n2;
                for (int i = 1 << 8 - n2; i > 0; --i) {
                    d_derived_tbl2.look_nbits[n8] = n2;
                    d_derived_tbl2.look_sym[n8] = jHUFF_TBL.huffval[n5];
                    ++n8;
                }
                ++n4;
                ++n5;
            }
        }
        if (bl) {
            for (n4 = 0; n4 < n3; ++n4) {
                n2 = jHUFF_TBL.huffval[n4] & 0xFF;
                if (n2 >= 0 && n2 <= 15) continue;
                JPEGDecoder.error();
            }
        }
    }

    static void start_input_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
        JPEGDecoder.per_scan_setup(jpeg_decompress_struct2);
        JPEGDecoder.latch_quant_tables(jpeg_decompress_struct2);
        jpeg_decompress_struct2.entropy.start_pass(jpeg_decompress_struct2);
        jpeg_decompress_struct2.coef.start_input_pass(jpeg_decompress_struct2);
        jpeg_decompress_struct2.inputctl.consume_input = 1;
    }

    static void finish_input_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decompress_struct2.inputctl.consume_input = 0;
    }

    static int consume_markers(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_input_controller jpeg_input_controller2 = jpeg_decompress_struct2.inputctl;
        if (jpeg_input_controller2.eoi_reached) {
            return 2;
        }
        int n = JPEGDecoder.read_markers(jpeg_decompress_struct2);
        switch (n) {
            case 1: {
                if (jpeg_input_controller2.inheaders) {
                    JPEGDecoder.initial_setup(jpeg_decompress_struct2);
                    jpeg_input_controller2.inheaders = false;
                    break;
                }
                if (!jpeg_input_controller2.has_multiple_scans) {
                    JPEGDecoder.error();
                }
                JPEGDecoder.start_input_pass(jpeg_decompress_struct2);
                break;
            }
            case 2: {
                jpeg_input_controller2.eoi_reached = true;
                if (jpeg_input_controller2.inheaders) {
                    if (!jpeg_decompress_struct2.marker.saw_SOF) break;
                    JPEGDecoder.error();
                    break;
                }
                if (jpeg_decompress_struct2.output_scan_number <= jpeg_decompress_struct2.input_scan_number) break;
                jpeg_decompress_struct2.output_scan_number = jpeg_decompress_struct2.input_scan_number;
            }
        }
        return n;
    }

    static void default_decompress_parms(jpeg_decompress_struct jpeg_decompress_struct2) {
        switch (jpeg_decompress_struct2.num_components) {
            case 1: {
                jpeg_decompress_struct2.jpeg_color_space = 1;
                jpeg_decompress_struct2.out_color_space = 1;
                break;
            }
            case 3: {
                if (jpeg_decompress_struct2.saw_JFIF_marker) {
                    jpeg_decompress_struct2.jpeg_color_space = 3;
                } else if (jpeg_decompress_struct2.saw_Adobe_marker) {
                    switch (jpeg_decompress_struct2.Adobe_transform) {
                        case 0: {
                            jpeg_decompress_struct2.jpeg_color_space = 2;
                            break;
                        }
                        case 1: {
                            jpeg_decompress_struct2.jpeg_color_space = 3;
                            break;
                        }
                        default: {
                            jpeg_decompress_struct2.jpeg_color_space = 3;
                            break;
                        }
                    }
                } else {
                    int n = jpeg_decompress_struct2.comp_info[0].component_id;
                    int n2 = jpeg_decompress_struct2.comp_info[1].component_id;
                    int n3 = jpeg_decompress_struct2.comp_info[2].component_id;
                    jpeg_decompress_struct2.jpeg_color_space = n == 1 && n2 == 2 && n3 == 3 ? 3 : (n == 82 && n2 == 71 && n3 == 66 ? 2 : 3);
                }
                jpeg_decompress_struct2.out_color_space = 2;
                break;
            }
            case 4: {
                if (jpeg_decompress_struct2.saw_Adobe_marker) {
                    switch (jpeg_decompress_struct2.Adobe_transform) {
                        case 0: {
                            jpeg_decompress_struct2.jpeg_color_space = 4;
                            break;
                        }
                        case 2: {
                            jpeg_decompress_struct2.jpeg_color_space = 5;
                            break;
                        }
                        default: {
                            jpeg_decompress_struct2.jpeg_color_space = 5;
                            break;
                        }
                    }
                } else {
                    jpeg_decompress_struct2.jpeg_color_space = 4;
                }
                jpeg_decompress_struct2.out_color_space = 4;
                break;
            }
            default: {
                jpeg_decompress_struct2.jpeg_color_space = 0;
                jpeg_decompress_struct2.out_color_space = 0;
            }
        }
        jpeg_decompress_struct2.scale_num = 1;
        jpeg_decompress_struct2.scale_denom = 1;
        jpeg_decompress_struct2.output_gamma = 1.0;
        jpeg_decompress_struct2.buffered_image = false;
        jpeg_decompress_struct2.raw_data_out = false;
        jpeg_decompress_struct2.dct_method = 0;
        jpeg_decompress_struct2.do_fancy_upsampling = true;
        jpeg_decompress_struct2.do_block_smoothing = true;
        jpeg_decompress_struct2.quantize_colors = false;
        jpeg_decompress_struct2.dither_mode = 2;
        jpeg_decompress_struct2.two_pass_quantize = true;
        jpeg_decompress_struct2.desired_number_of_colors = 256;
        jpeg_decompress_struct2.colormap = null;
        jpeg_decompress_struct2.enable_1pass_quant = false;
        jpeg_decompress_struct2.enable_external_quant = false;
        jpeg_decompress_struct2.enable_2pass_quant = false;
    }

    static void init_source(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decompress_struct2.buffer = new byte[4096];
        jpeg_decompress_struct2.bytes_in_buffer = 0;
        jpeg_decompress_struct2.bytes_offset = 0;
        jpeg_decompress_struct2.start_of_file = true;
    }

    static int jpeg_consume_input(jpeg_decompress_struct jpeg_decompress_struct2) {
        int n = 0;
        switch (jpeg_decompress_struct2.global_state) {
            case 200: {
                JPEGDecoder.reset_input_controller(jpeg_decompress_struct2);
                JPEGDecoder.init_source(jpeg_decompress_struct2);
                jpeg_decompress_struct2.global_state = 201;
            }
            case 201: {
                n = JPEGDecoder.consume_input(jpeg_decompress_struct2);
                if (n != 1) break;
                JPEGDecoder.default_decompress_parms(jpeg_decompress_struct2);
                jpeg_decompress_struct2.global_state = 202;
                break;
            }
            case 202: {
                n = 1;
                break;
            }
            case 203: 
            case 204: 
            case 205: 
            case 206: 
            case 207: 
            case 208: 
            case 210: {
                n = JPEGDecoder.consume_input(jpeg_decompress_struct2);
                break;
            }
            default: {
                JPEGDecoder.error();
            }
        }
        return n;
    }

    static void jpeg_abort(jpeg_decompress_struct jpeg_decompress_struct2) {
        jpeg_decompress_struct2.global_state = jpeg_decompress_struct2.is_decompressor ? 200 : 100;
    }

    static boolean isFileFormat(LEDataInputStream lEDataInputStream) {
        try {
            byte[] byArray = new byte[2];
            lEDataInputStream.read(byArray);
            lEDataInputStream.unread(byArray);
            return (byArray[0] & 0xFF) == 255 && (byArray[1] & 0xFF) == 216;
        }
        catch (Exception exception) {
            return false;
        }
    }

    static ImageData[] loadFromByteStream(InputStream inputStream, ImageLoader imageLoader) {
        int n;
        jpeg_decompress_struct jpeg_decompress_struct2 = new jpeg_decompress_struct();
        jpeg_decompress_struct2.inputStream = inputStream;
        JPEGDecoder.jpeg_create_decompress(jpeg_decompress_struct2);
        JPEGDecoder.jpeg_read_header(jpeg_decompress_struct2, true);
        jpeg_decompress_struct2.buffered_image = jpeg_decompress_struct2.progressive_mode && imageLoader.hasListeners();
        JPEGDecoder.jpeg_start_decompress(jpeg_decompress_struct2);
        PaletteData paletteData = null;
        switch (jpeg_decompress_struct2.out_color_space) {
            case 2: {
                paletteData = new PaletteData(255, 65280, 0xFF0000);
                break;
            }
            case 1: {
                RGB[] rGBArray = new RGB[256];
                for (n = 0; n < rGBArray.length; ++n) {
                    rGBArray[n] = new RGB(n, n, n);
                }
                paletteData = new PaletteData(rGBArray);
                break;
            }
            default: {
                JPEGDecoder.error();
            }
        }
        int n2 = 4;
        n = ((jpeg_decompress_struct2.output_width * jpeg_decompress_struct2.out_color_components * 8 + 7) / 8 + 3) / 4 * 4;
        byte[][] byArray = new byte[1][n];
        byte[] byArray2 = new byte[n * jpeg_decompress_struct2.output_height];
        ImageData imageData = ImageData.internal_new(jpeg_decompress_struct2.output_width, jpeg_decompress_struct2.output_height, paletteData.isDirect ? 24 : 8, paletteData, 4, byArray2, 0, null, null, -1, -1, 4, 0, 0, 0, 0);
        if (jpeg_decompress_struct2.buffered_image) {
            boolean bl;
            do {
                int n3 = jpeg_decompress_struct2.input_scan_number - 1;
                JPEGDecoder.jpeg_start_output(jpeg_decompress_struct2, jpeg_decompress_struct2.input_scan_number);
                while (jpeg_decompress_struct2.output_scanline < jpeg_decompress_struct2.output_height) {
                    int n4 = n * jpeg_decompress_struct2.output_scanline;
                    JPEGDecoder.jpeg_read_scanlines(jpeg_decompress_struct2, byArray, 1);
                    System.arraycopy(byArray[0], 0, byArray2, n4, n);
                }
                JPEGDecoder.jpeg_finish_output(jpeg_decompress_struct2);
                bl = JPEGDecoder.jpeg_input_complete(jpeg_decompress_struct2);
                imageLoader.notifyListeners(new ImageLoaderEvent(imageLoader, (ImageData)imageData.clone(), n3, bl));
            } while (!bl);
        } else {
            while (jpeg_decompress_struct2.output_scanline < jpeg_decompress_struct2.output_height) {
                int n5 = n * jpeg_decompress_struct2.output_scanline;
                JPEGDecoder.jpeg_read_scanlines(jpeg_decompress_struct2, byArray, 1);
                System.arraycopy(byArray[0], 0, byArray2, n5, n);
            }
        }
        JPEGDecoder.jpeg_finish_decompress(jpeg_decompress_struct2);
        JPEGDecoder.jpeg_destroy_decompress(jpeg_decompress_struct2);
        return new ImageData[]{imageData};
    }

    static final class jpeg_decompress_struct {
        boolean is_decompressor;
        int global_state;
        InputStream inputStream;
        byte[] buffer;
        int bytes_in_buffer;
        int bytes_offset;
        boolean start_of_file;
        int image_width;
        int image_height;
        int num_components;
        int jpeg_color_space;
        int out_color_space;
        int scale_num;
        int scale_denom;
        double output_gamma;
        boolean buffered_image;
        boolean raw_data_out;
        int dct_method;
        boolean do_fancy_upsampling;
        boolean do_block_smoothing;
        boolean quantize_colors;
        int dither_mode;
        boolean two_pass_quantize;
        int desired_number_of_colors;
        boolean enable_1pass_quant;
        boolean enable_external_quant;
        boolean enable_2pass_quant;
        int output_width;
        int output_height;
        int out_color_components;
        int output_components;
        int rec_outbuf_height;
        int actual_number_of_colors;
        int[] colormap;
        int output_scanline;
        int input_scan_number;
        int input_iMCU_row;
        int output_scan_number;
        int output_iMCU_row;
        int[][] coef_bits;
        JQUANT_TBL[] quant_tbl_ptrs = new JQUANT_TBL[4];
        JHUFF_TBL[] dc_huff_tbl_ptrs = new JHUFF_TBL[4];
        JHUFF_TBL[] ac_huff_tbl_ptrs = new JHUFF_TBL[4];
        int data_precision;
        jpeg_component_info[] comp_info;
        boolean progressive_mode;
        boolean arith_code;
        byte[] arith_dc_L = new byte[16];
        byte[] arith_dc_U = new byte[16];
        byte[] arith_ac_K = new byte[16];
        int restart_interval;
        boolean saw_JFIF_marker;
        byte JFIF_major_version;
        byte JFIF_minor_version;
        byte density_unit;
        short X_density;
        short Y_density;
        boolean saw_Adobe_marker;
        byte Adobe_transform;
        boolean CCIR601_sampling;
        jpeg_marker_reader marker_list;
        int max_h_samp_factor;
        int max_v_samp_factor;
        int min_DCT_scaled_size;
        int total_iMCU_rows;
        byte[] sample_range_limit;
        int sample_range_limit_offset;
        int comps_in_scan;
        jpeg_component_info[] cur_comp_info = new jpeg_component_info[4];
        int MCUs_per_row;
        int MCU_rows_in_scan;
        int blocks_in_MCU;
        int[] MCU_membership = new int[10];
        int Ss;
        int Se;
        int Ah;
        int Al;
        int unread_marker;
        int[] workspace = new int[64];
        int[] row_ctr = new int[1];
        jpeg_decomp_master master;
        jpeg_d_main_controller main;
        jpeg_d_coef_controller coef;
        jpeg_d_post_controller post;
        jpeg_input_controller inputctl;
        jpeg_marker_reader marker;
        jpeg_entropy_decoder entropy;
        jpeg_inverse_dct idct;
        jpeg_upsampler upsample;
        jpeg_color_deconverter cconvert;
        jpeg_color_quantizer cquantize;

        jpeg_decompress_struct() {
        }
    }

    static final class jpeg_d_post_controller {
        int post_process_data;
        int[] whole_image;
        int[][] buffer;
        int strip_height;
        int starting_row;
        int next_row;

        jpeg_d_post_controller() {
        }

        void start_pass(jpeg_decompress_struct jpeg_decompress_struct2, int n) {
            jpeg_d_post_controller jpeg_d_post_controller2 = jpeg_decompress_struct2.post;
            switch (n) {
                case 0: {
                    if (jpeg_decompress_struct2.quantize_colors) {
                        JPEGDecoder.error(20);
                        break;
                    }
                    jpeg_d_post_controller2.post_process_data = 1;
                    break;
                }
                default: {
                    JPEGDecoder.error();
                }
            }
            jpeg_d_post_controller jpeg_d_post_controller3 = jpeg_d_post_controller2;
            jpeg_d_post_controller jpeg_d_post_controller4 = jpeg_d_post_controller2;
            boolean bl = false;
            jpeg_d_post_controller4.next_row = 0;
            jpeg_d_post_controller3.starting_row = 0;
        }
    }

    static final class jpeg_color_deconverter {
        int color_convert;
        int[] Cr_r_tab;
        int[] Cb_b_tab;
        int[] Cr_g_tab;
        int[] Cb_g_tab;

        jpeg_color_deconverter() {
        }

        void start_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
        }
    }

    static final class jpeg_input_controller {
        int consume_input;
        boolean has_multiple_scans;
        boolean eoi_reached;
        boolean inheaders;

        jpeg_input_controller() {
        }
    }

    static final class jpeg_inverse_dct {
        int[] cur_method = new int[10];

        jpeg_inverse_dct() {
        }

        void start_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
            jpeg_inverse_dct jpeg_inverse_dct2 = jpeg_decompress_struct2.idct;
            int n = 0;
            block9: for (int i = 0; i < jpeg_decompress_struct2.num_components; ++i) {
                JQUANT_TBL jQUANT_TBL;
                jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.comp_info[i];
                block0 : switch (jpeg_component_info2.DCT_scaled_size) {
                    case 8: {
                        switch (jpeg_decompress_struct2.dct_method) {
                            case 0: {
                                n = 0;
                                break block0;
                            }
                        }
                        JPEGDecoder.error();
                        break;
                    }
                    default: {
                        JPEGDecoder.error();
                    }
                }
                if (!jpeg_component_info2.component_needed || jpeg_inverse_dct2.cur_method[i] == n || (jQUANT_TBL = jpeg_component_info2.quant_table) == null) continue;
                jpeg_inverse_dct2.cur_method[i] = n;
                switch (jpeg_inverse_dct2.cur_method[i]) {
                    case 0: {
                        int[] nArray = jpeg_component_info2.dct_table;
                        for (int j = 0; j < 64; ++j) {
                            nArray[j] = jQUANT_TBL.quantval[j];
                        }
                        continue block9;
                    }
                    default: {
                        JPEGDecoder.error();
                    }
                }
            }
        }
    }

    static final class jpeg_decomp_master {
        boolean is_dummy_pass;
        int pass_number;
        boolean using_merged_upsample;
        jpeg_color_quantizer quantizer_1pass;
        jpeg_color_quantizer quantizer_2pass;

        jpeg_decomp_master() {
        }
    }

    static final class jpeg_d_main_controller {
        int process_data;
        byte[][][] buffer = new byte[10][][];
        int[] buffer_offset = new int[10];
        boolean buffer_full;
        int[] rowgroup_ctr = new int[1];
        byte[][][][] xbuffer = new byte[2][][][];
        int[][] xbuffer_offset = new int[2][];
        int whichptr;
        int context_state;
        int rowgroups_avail;
        int iMCU_row_ctr;

        jpeg_d_main_controller() {
        }

        void start_pass(jpeg_decompress_struct jpeg_decompress_struct2, int n) {
            jpeg_d_main_controller jpeg_d_main_controller2 = jpeg_decompress_struct2.main;
            switch (n) {
                case 0: {
                    if (jpeg_decompress_struct2.upsample.need_context_rows) {
                        jpeg_d_main_controller2.process_data = 1;
                        JPEGDecoder.make_funny_pointers(jpeg_decompress_struct2);
                        jpeg_d_main_controller2.whichptr = 0;
                        jpeg_d_main_controller2.context_state = 0;
                        jpeg_d_main_controller2.iMCU_row_ctr = 0;
                    } else {
                        jpeg_d_main_controller2.process_data = 0;
                    }
                    jpeg_d_main_controller2.buffer_full = false;
                    jpeg_d_main_controller2.rowgroup_ctr[0] = 0;
                    break;
                }
                default: {
                    JPEGDecoder.error();
                }
            }
        }
    }

    static final class jpeg_marker_reader {
        boolean saw_SOI;
        boolean saw_SOF;
        int next_restart_num;
        int discarded_bytes;
        int length_limit_COM;
        int[] length_limit_APPn = new int[16];

        jpeg_marker_reader() {
        }
    }

    static final class jpeg_upsampler {
        boolean need_context_rows;
        byte[][][] color_buf = new byte[10][][];
        int[] color_buf_offset = new int[10];
        int[] methods = new int[10];
        int next_row_out;
        int rows_to_go;
        int[] rowgroup_height = new int[10];
        byte[] h_expand = new byte[10];
        byte[] v_expand = new byte[10];

        jpeg_upsampler() {
        }

        void start_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
            jpeg_upsampler jpeg_upsampler2 = jpeg_decompress_struct2.upsample;
            jpeg_upsampler2.next_row_out = jpeg_decompress_struct2.max_v_samp_factor;
            jpeg_upsampler2.rows_to_go = jpeg_decompress_struct2.output_height;
        }
    }

    static final class jpeg_color_quantizer {
        int[][] sv_colormap;
        int sv_actual;
        int[][] colorindex;
        boolean is_padded;
        int[] Ncolors = new int[4];
        int row_index;
        boolean on_odd_row;

        jpeg_color_quantizer() {
        }

        void start_pass(jpeg_decompress_struct jpeg_decompress_struct2, boolean bl) {
            JPEGDecoder.error();
        }
    }

    static final class jpeg_component_info {
        int component_id;
        int component_index;
        int h_samp_factor;
        int v_samp_factor;
        int quant_tbl_no;
        int dc_tbl_no;
        int ac_tbl_no;
        int width_in_blocks;
        int height_in_blocks;
        int DCT_scaled_size;
        int downsampled_width;
        int downsampled_height;
        boolean component_needed;
        int MCU_width;
        int MCU_height;
        int MCU_blocks;
        int MCU_sample_width;
        int last_col_width;
        int last_row_height;
        JQUANT_TBL quant_table;
        int[] dct_table;

        jpeg_component_info() {
        }
    }

    static final class phuff_entropy_decoder
    extends jpeg_entropy_decoder {
        bitread_perm_state bitstate = new bitread_perm_state();
        savable_state saved = new savable_state();
        int restarts_to_go;
        d_derived_tbl[] derived_tbls = new d_derived_tbl[4];
        d_derived_tbl ac_derived_tbl;
        int[] newnz_pos = new int[64];

        phuff_entropy_decoder() {
        }

        @Override
        void start_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
            this.start_pass_phuff_decoder(jpeg_decompress_struct2);
        }

        @Override
        boolean decode_mcu(jpeg_decompress_struct jpeg_decompress_struct2, short[][] sArray) {
            boolean bl;
            boolean bl2 = bl = jpeg_decompress_struct2.Ss == 0;
            if (jpeg_decompress_struct2.Ah == 0) {
                if (bl) {
                    return this.decode_mcu_DC_first(jpeg_decompress_struct2, sArray);
                }
                return this.decode_mcu_AC_first(jpeg_decompress_struct2, sArray);
            }
            if (bl) {
                return this.decode_mcu_DC_refine(jpeg_decompress_struct2, sArray);
            }
            return this.decode_mcu_AC_refine(jpeg_decompress_struct2, sArray);
        }

        /*
         * Exception decompiling
         */
        boolean decode_mcu_DC_refine(jpeg_decompress_struct var1, short[][] var2) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl22 : ALOAD - null : trying to set 0 previously set to 1
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Exception decompiling
         */
        boolean decode_mcu_AC_refine(jpeg_decompress_struct var1, short[][] var2) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl35 : ALOAD_3 - null : trying to set 0 previously set to 1
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Exception decompiling
         */
        boolean decode_mcu_AC_first(jpeg_decompress_struct var1, short[][] var2) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl25 : ALOAD_3 - null : trying to set 0 previously set to 1
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Exception decompiling
         */
        boolean decode_mcu_DC_first(jpeg_decompress_struct var1, short[][] var2) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl25 : ALOAD_3 - null : trying to set 0 previously set to 1
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        void start_pass_phuff_decoder(jpeg_decompress_struct jpeg_decompress_struct2) {
            int n;
            phuff_entropy_decoder phuff_entropy_decoder2 = this;
            boolean bl = jpeg_decompress_struct2.Ss == 0;
            boolean bl2 = false;
            if (bl) {
                if (jpeg_decompress_struct2.Se != 0) {
                    bl2 = true;
                }
            } else {
                if (jpeg_decompress_struct2.Ss > jpeg_decompress_struct2.Se || jpeg_decompress_struct2.Se >= 64) {
                    bl2 = true;
                }
                if (jpeg_decompress_struct2.comps_in_scan != 1) {
                    bl2 = true;
                }
            }
            if (jpeg_decompress_struct2.Ah != 0 && jpeg_decompress_struct2.Al != jpeg_decompress_struct2.Ah - 1) {
                bl2 = true;
            }
            if (jpeg_decompress_struct2.Al > 13) {
                bl2 = true;
            }
            if (bl2) {
                JPEGDecoder.error();
            }
            for (n = 0; n < jpeg_decompress_struct2.comps_in_scan; ++n) {
                int n2 = jpeg_decompress_struct2.cur_comp_info[n].component_index;
                int[] nArray = jpeg_decompress_struct2.coef_bits[n2];
                if (bl || nArray[0] < 0) {
                    // empty if block
                }
                for (int i = jpeg_decompress_struct2.Ss; i <= jpeg_decompress_struct2.Se; ++i) {
                    int n3;
                    int n4 = n3 = nArray[i] < 0 ? 0 : nArray[i];
                    if (jpeg_decompress_struct2.Ah != n3) {
                        // empty if block
                    }
                    nArray[i] = jpeg_decompress_struct2.Al;
                }
            }
            for (n = 0; n < jpeg_decompress_struct2.comps_in_scan; ++n) {
                int n5;
                jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.cur_comp_info[n];
                if (bl) {
                    if (jpeg_decompress_struct2.Ah == 0) {
                        n5 = jpeg_component_info2.dc_tbl_no;
                        phuff_entropy_decoder2.derived_tbls[n5] = new d_derived_tbl();
                        JPEGDecoder.jpeg_make_d_derived_tbl(jpeg_decompress_struct2, true, n5, phuff_entropy_decoder2.derived_tbls[n5]);
                    }
                } else {
                    n5 = jpeg_component_info2.ac_tbl_no;
                    phuff_entropy_decoder2.derived_tbls[n5] = new d_derived_tbl();
                    JPEGDecoder.jpeg_make_d_derived_tbl(jpeg_decompress_struct2, false, n5, phuff_entropy_decoder2.derived_tbls[n5]);
                    phuff_entropy_decoder2.ac_derived_tbl = phuff_entropy_decoder2.derived_tbls[n5];
                }
                phuff_entropy_decoder2.saved.last_dc_val[n] = 0;
            }
            phuff_entropy_decoder2.bitstate.bits_left = 0;
            phuff_entropy_decoder2.bitstate.get_buffer = 0;
            phuff_entropy_decoder2.insufficient_data = false;
            phuff_entropy_decoder2.saved.EOBRUN = 0;
            phuff_entropy_decoder2.restarts_to_go = jpeg_decompress_struct2.restart_interval;
        }
    }

    static final class huff_entropy_decoder
    extends jpeg_entropy_decoder {
        bitread_perm_state bitstate = new bitread_perm_state();
        savable_state saved = new savable_state();
        int restarts_to_go;
        d_derived_tbl[] dc_derived_tbls = new d_derived_tbl[4];
        d_derived_tbl[] ac_derived_tbls = new d_derived_tbl[4];
        d_derived_tbl[] dc_cur_tbls = new d_derived_tbl[10];
        d_derived_tbl[] ac_cur_tbls = new d_derived_tbl[10];
        boolean[] dc_needed = new boolean[10];
        boolean[] ac_needed = new boolean[10];

        huff_entropy_decoder() {
        }

        @Override
        void start_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
            this.start_pass_huff_decoder(jpeg_decompress_struct2);
        }

        /*
         * Exception decompiling
         */
        @Override
        boolean decode_mcu(jpeg_decompress_struct var1, short[][] var2) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl20 : ALOAD_3 - null : trying to set 0 previously set to 1
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        void start_pass_huff_decoder(jpeg_decompress_struct jpeg_decompress_struct2) {
            int n;
            huff_entropy_decoder huff_entropy_decoder2 = this;
            if (jpeg_decompress_struct2.Ss != 0 || jpeg_decompress_struct2.Se != 63 || jpeg_decompress_struct2.Ah != 0 || jpeg_decompress_struct2.Al != 0) {
                // empty if block
            }
            for (n = 0; n < jpeg_decompress_struct2.comps_in_scan; ++n) {
                jpeg_component_info jpeg_component_info2 = jpeg_decompress_struct2.cur_comp_info[n];
                int n2 = jpeg_component_info2.dc_tbl_no;
                int n3 = jpeg_component_info2.ac_tbl_no;
                huff_entropy_decoder2.dc_derived_tbls[n2] = new d_derived_tbl();
                JPEGDecoder.jpeg_make_d_derived_tbl(jpeg_decompress_struct2, true, n2, huff_entropy_decoder2.dc_derived_tbls[n2]);
                huff_entropy_decoder2.ac_derived_tbls[n3] = new d_derived_tbl();
                JPEGDecoder.jpeg_make_d_derived_tbl(jpeg_decompress_struct2, false, n3, huff_entropy_decoder2.ac_derived_tbls[n3]);
                huff_entropy_decoder2.saved.last_dc_val[n] = 0;
            }
            for (n = 0; n < jpeg_decompress_struct2.blocks_in_MCU; ++n) {
                int n4 = jpeg_decompress_struct2.MCU_membership[n];
                jpeg_component_info jpeg_component_info3 = jpeg_decompress_struct2.cur_comp_info[n4];
                huff_entropy_decoder2.dc_cur_tbls[n] = huff_entropy_decoder2.dc_derived_tbls[jpeg_component_info3.dc_tbl_no];
                huff_entropy_decoder2.ac_cur_tbls[n] = huff_entropy_decoder2.ac_derived_tbls[jpeg_component_info3.ac_tbl_no];
                if (jpeg_component_info3.component_needed) {
                    huff_entropy_decoder2.dc_needed[n] = true;
                    huff_entropy_decoder2.ac_needed[n] = jpeg_component_info3.DCT_scaled_size > 1;
                    continue;
                }
                huff_entropy_decoder2.ac_needed[n] = false;
                huff_entropy_decoder2.dc_needed[n] = false;
            }
            huff_entropy_decoder2.bitstate.bits_left = 0;
            huff_entropy_decoder2.bitstate.get_buffer = 0;
            huff_entropy_decoder2.insufficient_data = false;
            huff_entropy_decoder2.restarts_to_go = jpeg_decompress_struct2.restart_interval;
        }
    }

    static abstract class jpeg_entropy_decoder {
        boolean insufficient_data;
        bitread_working_state br_state_local = new bitread_working_state();
        savable_state state_local = new savable_state();

        jpeg_entropy_decoder() {
        }

        abstract void start_pass(jpeg_decompress_struct var1);

        abstract boolean decode_mcu(jpeg_decompress_struct var1, short[][] var2);
    }

    static final class jpeg_d_coef_controller {
        int consume_data;
        int decompress_data;
        short[][][] coef_arrays;
        int MCU_ctr;
        int MCU_vert_offset;
        int MCU_rows_per_iMCU_row;
        short[][] MCU_buffer = new short[10][];
        short[][][][] whole_image = new short[10][][][];
        int[] coef_bits_latch;
        short[] workspace;

        jpeg_d_coef_controller() {
        }

        void start_input_pass(jpeg_decompress_struct jpeg_decompress_struct2) {
            jpeg_decompress_struct2.input_iMCU_row = 0;
            this.start_iMCU_row(jpeg_decompress_struct2);
        }

        void start_iMCU_row(jpeg_decompress_struct jpeg_decompress_struct2) {
            jpeg_d_coef_controller jpeg_d_coef_controller2 = jpeg_decompress_struct2.coef;
            jpeg_d_coef_controller2.MCU_rows_per_iMCU_row = jpeg_decompress_struct2.comps_in_scan > 1 ? 1 : (jpeg_decompress_struct2.input_iMCU_row < jpeg_decompress_struct2.total_iMCU_rows - 1 ? jpeg_decompress_struct2.cur_comp_info[0].v_samp_factor : jpeg_decompress_struct2.cur_comp_info[0].last_row_height);
            jpeg_d_coef_controller2.MCU_ctr = 0;
            jpeg_d_coef_controller2.MCU_vert_offset = 0;
        }
    }

    static final class d_derived_tbl {
        int[] maxcode = new int[18];
        int[] valoffset = new int[17];
        JHUFF_TBL pub;
        int[] look_nbits = new int[256];
        byte[] look_sym = new byte[256];

        d_derived_tbl() {
        }
    }

    static final class savable_state {
        int EOBRUN;
        int[] last_dc_val = new int[4];

        savable_state() {
        }
    }

    static final class bitread_working_state {
        byte[] buffer;
        int bytes_offset;
        int bytes_in_buffer;
        int get_buffer;
        int bits_left;
        jpeg_decompress_struct cinfo;

        bitread_working_state() {
        }
    }

    static final class bitread_perm_state {
        int get_buffer;
        int bits_left;

        bitread_perm_state() {
        }
    }

    static final class JHUFF_TBL {
        byte[] bits = new byte[17];
        byte[] huffval = new byte[256];
        boolean sent_table;

        JHUFF_TBL() {
        }
    }

    static final class JQUANT_TBL {
        short[] quantval = new short[64];
        boolean sent_table;

        JQUANT_TBL() {
        }
    }
}

