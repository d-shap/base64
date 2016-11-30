///////////////////////////////////////////////////////////////////////////////////////////////////
// Base64 library converts bytes to base64 representation and vice versa.
// Copyright (C) 2016 Dmitry Shapovalov.
//
// This file is part of Base64 library.
//
// Base64 library is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Base64 library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.base64;

/**
 * Class to perform bytes to base64 and base64 to bytes conversions.
 *
 * @author Dmitry Shapovalov
 */
public final class Base64Helper {

    private Base64Helper() {
        super();
    }

    /**
     * Convert byte array to base64 string.
     *
     * @param bytes byte array.
     * @return base64 string.
     */
    public static String toBase64(final byte[] bytes) {
        int length = bytes.length / 3 + 1;
        int lengthM1 = length - 1;
        int mod = bytes.length % 3;

        int resultLength = length * 4;
        StringBuilder buffer = new StringBuilder(resultLength);

        int index = 0;
        int byte1;
        int byte2;
        int byte3;
        for (int i = 0; i < lengthM1; i++) {
            byte1 = bytes[index];
            index++;
            byte2 = bytes[index];
            index++;
            byte3 = bytes[index];
            index++;

            buffer.append((char) getFirstBase64Byte(byte1));
            buffer.append((char) getSecondBase64Byte(byte1, byte2));
            buffer.append((char) getThirdBase64Byte(byte2, byte3));
            buffer.append((char) getFourthBase64Byte(byte3));
        }

        switch (mod) {
            case 1:
                byte1 = bytes[index];
                buffer.append((char) getFirstBase64Byte(byte1));
                buffer.append((char) getSecondBase64Byte(byte1, 0));
                buffer.append((char) Consts.PAD);
                buffer.append((char) Consts.PAD);
                break;
            case 2:
                byte1 = bytes[index];
                index++;
                byte2 = bytes[index];
                buffer.append((char) getFirstBase64Byte(byte1));
                buffer.append((char) getSecondBase64Byte(byte1, byte2));
                buffer.append((char) getThirdBase64Byte(byte2, 0));
                buffer.append((char) Consts.PAD);
                break;
            default:
                break;
        }

        return buffer.toString();
    }

    static int getFirstBase64Byte(final int byte1) {
        int idx = Consts.TO_BASE64_FIRST_BYTE_POSITION[byte1];
        return Consts.TO_BASE64[idx];
    }

    static int getSecondBase64Byte(final int byte1, final int byte2) {
        int idx = Consts.TO_BASE64_SECOND_BYTE_POSITION_1[byte1] + Consts.TO_BASE64_SECOND_BYTE_POSITION_2[byte2];
        return Consts.TO_BASE64[idx];
    }

    static int getThirdBase64Byte(final int byte2, final int byte3) {
        int idx = Consts.TO_BASE64_THIRD_BYTE_POSITION_1[byte2] + Consts.TO_BASE64_THIRD_BYTE_POSITION_2[byte3];
        return Consts.TO_BASE64[idx];
    }

    static int getFourthBase64Byte(final int byte3) {
        int idx = Consts.TO_BASE64_FOURTH_BYTE_POSITION[byte3];
        return Consts.TO_BASE64[idx];
    }

    public static byte[] toBytes(final String base64) {
        int length = base64.length() / 4;
        byte[] result = new byte[length * 3];

        int index = 0;
        int resultIndex = 0;
        int symbol1;
        int symbol2;
        int symbol3;
        int symbol4;

        for (int i = 0; i < length; i++) {
            symbol1 = base64.charAt(index);
            index++;
            symbol2 = base64.charAt(index);
            index++;
            symbol3 = base64.charAt(index);
            index++;
            symbol4 = base64.charAt(index);
            index++;

            result[resultIndex] = (byte) getFirstOriginalByte(symbol1, symbol2);
            resultIndex++;
            result[resultIndex] = (byte) getSecondOriginalByte(symbol2, symbol3);
            resultIndex++;
            result[resultIndex] = (byte) getThirdOriginalByte(symbol3, symbol4);
            resultIndex++;
        }

        return result;
    }

    static int getFirstOriginalByte(final int byte1, final int byte2) {
        int value1 = Consts.FROM_BASE64[byte1];
        int value2 = Consts.FROM_BASE64[byte2];
        return (value1 << 2) + ((value2 & 0x30) >> 4);
    }

    static int getSecondOriginalByte(final int byte2, final int byte3) {
        int value2 = Consts.FROM_BASE64[byte2];
        int value3 = Consts.FROM_BASE64[byte3];
        return ((value2 & 0x0F) << 4) + ((value3 & 0x3C) >> 2);
    }

    static int getThirdOriginalByte(final int byte3, final int byte4) {
        int value3 = Consts.FROM_BASE64[byte3];
        int value4 = Consts.FROM_BASE64[byte4];
        return ((value3 & 0x03) << 6) + value4;
    }

}
