package com.ecodigital.smartcardapdupro.utils;

import java.math.BigInteger;

/**
 * Created by gluque on 06/08/15.
 */
public final class HexUtils {

    public static final char[] HEX_CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    private HexUtils() {
    }

    public static boolean arrayEquals(final byte[] v, final byte[] w) {
        return HexUtils.arrayEquals(v, 0, v.length, w, 0, w.length);
    }

    public static boolean arrayEquals(final byte[] v, final int vOffset, final int vLen, final byte[] w, final int wOffset, final int wLen) {
        if (vLen != wLen || v.length < vOffset + vLen || w.length < wOffset + wLen) {
            return false;
        }

        for (int i = 0; i < vLen; i++) {
            if (v[i + vOffset] != w[i + wOffset]) {
                return false;
            }
        }
        return true;
    }

    public static short getShort(final byte[] data, final int offset) {
        return (short) HexUtils.getUnsignedInt(data, offset);
    }

    public static int getUnsignedInt(final byte[] data, final int offset) {
        return (data[offset] & 0xff) << 8 | data[offset + 1] & 0xff;
    }

    public static String hexify(final byte abyte[], final boolean separator) {
        if (abyte == null) {
            return "null";
        }
        final StringBuffer stringbuffer = new StringBuffer(256);
        boolean hasSalt = false;
        int i = 0;
        for (final byte element : abyte) {
            if(hasSalt){
                stringbuffer.append('\n');
                hasSalt = false;
            }
            if (separator && i > 0) {
                stringbuffer.append(' ');
            }
            stringbuffer.append(HexUtils.HEX_CHARS[element >> 4 & 0xf]);
            stringbuffer.append(HexUtils.HEX_CHARS[element & 0xf]);
            if (++i == 16) {
                if (separator) {
                    hasSalt = true;
                }
                i = 0;
            }
        }
        return stringbuffer.toString();
    }
    
    
    public static String hexify(final byte abyte[], final boolean separator, int pad) {
        if (abyte == null) {
            return "null";
        }
        final StringBuffer stringbuffer = new StringBuffer(256);
        boolean hasSalt = false;
        int i = 0;
        for (final byte element : abyte) {
            if(hasSalt){
                stringbuffer.append('\n');
                for(int k=0; k < pad; k++)
                    stringbuffer.append(' ');
                hasSalt = false;
            }
            if (separator && i > 0) {
                stringbuffer.append(' ');
            }
            stringbuffer.append(HexUtils.HEX_CHARS[element >> 4 & 0xf]);
            stringbuffer.append(HexUtils.HEX_CHARS[element & 0xf]);
            if (++i == 16) {
                if (separator) {
                    hasSalt = true;
                }
                i = 0;
            }
        }
        return stringbuffer.toString();
    }
    
    
    public static String hexify(final byte data) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HexUtils.HEX_CHARS[data >> 4 & 0xf]);
        stringBuilder.append(HexUtils.HEX_CHARS[data & 0xf]);
        return stringBuilder.toString();
    }

    
    public static byte[] subArray(final byte[] src, final int srcPos, final int length) {
        if (length == 0) {
            return null;
        }
        if (src.length < srcPos + length) {
            return null;
        }
        final byte[] temp = new byte[length];
        System.arraycopy(src, srcPos, temp, 0, length);
        return temp;
    }

    
    public static byte[] xor(final byte[] v, final byte[] w) {
        byte[] xored = null;
        byte[] trimmedXor = null;
        xored = new BigInteger(1, v).xor(new BigInteger(1, w)).toByteArray();
        trimmedXor = new byte[v.length];
        if (xored.length >= trimmedXor.length) {
            System.arraycopy(xored, xored.length - trimmedXor.length, trimmedXor, 0, trimmedXor.length);
        }
        else {
            System.arraycopy(xored, 0, trimmedXor, trimmedXor.length - xored.length, xored.length);
        }
        return trimmedXor;
    }

    
    public static byte[] intToByteArray(final int value) {
        final byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            final int offset = (b.length - 1 - i) * 8;
            b[3 - i] = (byte) (value >>> offset & 0xFF);
        }
        return b;
    }
    
    
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    
    
    public static byte HexStringToByte(String s) {
        return (byte) ((Character.digit(s.charAt(0), 16) << 4) + Character.digit(s.charAt(1), 16));
    }

}