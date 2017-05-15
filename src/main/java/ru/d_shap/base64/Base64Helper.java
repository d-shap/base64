///////////////////////////////////////////////////////////////////////////////////////////////////
// Base64 library converts bytes to base64 representation and vice versa.
// Copyright (C) 2016 Dmitry Shapovalov.
//
// This file is part of base64 library.
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
        if (bytes == null) {
            return "";
        }

        int bytesLength = bytes.length / 3 + 1;
        int bytesLengthM1 = bytesLength - 1;
        int bytesMod = bytes.length % 3;

        int resultLength;
        if (bytesMod == 0) {
            resultLength = bytesLengthM1 * 4;
        } else {
            resultLength = bytesLength * 4;
        }
        StringBuilder result = new StringBuilder(resultLength);

        int bytesIndex = 0;
        int byte1;
        int byte2;
        int byte3;
        for (int i = 0; i < bytesLengthM1; i++) {
            byte1 = bytes[bytesIndex] & 0xFF;
            bytesIndex++;
            byte2 = bytes[bytesIndex] & 0xFF;
            bytesIndex++;
            byte3 = bytes[bytesIndex] & 0xFF;
            bytesIndex++;

            result.append((char) getFirstBase64Symbol(byte1));
            result.append((char) getSecondBase64Symbol(byte1, byte2));
            result.append((char) getThirdBase64Symbol(byte2, byte3));
            result.append((char) getFourthBase64Symbol(byte3));
        }

        if (bytesMod == 1) {
            byte1 = bytes[bytesIndex] & 0xFF;

            result.append((char) getFirstBase64Symbol(byte1));
            result.append((char) getSecondBase64Symbol(byte1));
            result.append((char) Consts.PAD);
            result.append((char) Consts.PAD);
        } else if (bytesMod == 2) {
            byte1 = bytes[bytesIndex] & 0xFF;
            byte2 = bytes[bytesIndex + 1] & 0xFF;

            result.append((char) getFirstBase64Symbol(byte1));
            result.append((char) getSecondBase64Symbol(byte1, byte2));
            result.append((char) getThirdBase64Symbol(byte2));
            result.append((char) Consts.PAD);
        }

        return result.toString();
    }

    static int getFirstBase64Symbol(final int byte1) {
        return Consts.TO_BASE64_FIRST_SYMBOL[byte1];
    }

    static int getSecondBase64Symbol(final int byte1) {
        int idx = Consts.TO_BASE64_SECOND_SYMBOL_INDEX_1[byte1];
        return Consts.TO_BASE64[idx];
    }

    static int getSecondBase64Symbol(final int byte1, final int byte2) {
        int idx = Consts.TO_BASE64_SECOND_SYMBOL_INDEX_1[byte1] + Consts.TO_BASE64_SECOND_SYMBOL_INDEX_2[byte2];
        return Consts.TO_BASE64[idx];
    }

    static int getThirdBase64Symbol(final int byte2) {
        int idx = Consts.TO_BASE64_THIRD_SYMBOL_INDEX_1[byte2];
        return Consts.TO_BASE64[idx];
    }

    static int getThirdBase64Symbol(final int byte2, final int byte3) {
        int idx = Consts.TO_BASE64_THIRD_SYMBOL_INDEX_1[byte2] + Consts.TO_BASE64_THIRD_SYMBOL_INDEX_2[byte3];
        return Consts.TO_BASE64[idx];
    }

    static int getFourthBase64Symbol(final int byte3) {
        return Consts.TO_BASE64_FOURTH_SYMBOL[byte3];
    }

    /**
     * Convert base64 string to byte array.
     *
     * @param base64 base64 string.
     * @param result byte array to write result.
     * @return number of bytes affected in the byte array.
     */
    public static int toBytes(final String base64, final byte[] result) {
        if (base64 == null) {
            return 0;
        }
        if ("".equals(base64)) {
            return 0;
        }

        int base64Length = base64.length();
        if (base64Length % 4 != 0) {
            throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64StringSizeMessage(base64Length));
        }
        int emptyBytesCount = getEmptyBytesCount(base64);
        int resultLength = base64Length * 3 / 4 - emptyBytesCount;
        if (result.length < resultLength) {
            throw new Base64RuntimeException(ExceptionMessageHelper.createWrongResultArrayMessage(resultLength, result.length));
        }

        convertToBytes(base64, result);
        return resultLength;
    }

    /**
     * Convert base64 string to byte array.
     *
     * @param base64 base64 string.
     * @return new byte array with the conversion result.
     */
    public static byte[] toBytes(final String base64) {
        if (base64 == null) {
            return new byte[0];
        }
        if ("".equals(base64)) {
            return new byte[0];
        }

        int base64Length = base64.length();
        if (base64Length % 4 != 0) {
            throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64StringSizeMessage(base64Length));
        }
        int emptyBytesCount = getEmptyBytesCount(base64);
        int resultLength = base64Length * 3 / 4 - emptyBytesCount;
        byte[] result = new byte[resultLength];

        convertToBytes(base64, result);
        return result;
    }

    private static int getEmptyBytesCount(final String base64) {
        int base64Length = base64.length();
        if (base64.charAt(base64Length - 1) == Consts.PAD) {
            if (base64.charAt(base64Length - 2) == Consts.PAD) {
                return 2;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    private static void convertToBytes(final String base64, final byte[] result) {
        int blockCountM1 = base64.length() / 4 - 1;

        int base64Index = 0;
        int symbol1;
        int symbol2;
        int symbol3;
        int symbol4;
        int resultIndex = 0;
        for (int i = 0; i < blockCountM1; i++) {
            symbol1 = getBase64Symbol(base64, base64Index, false);
            base64Index++;
            symbol2 = getBase64Symbol(base64, base64Index, false);
            base64Index++;
            symbol3 = getBase64Symbol(base64, base64Index, false);
            base64Index++;
            symbol4 = getBase64Symbol(base64, base64Index, false);
            base64Index++;

            result[resultIndex] = (byte) getFirstBase64Byte(symbol1, symbol2);
            resultIndex++;
            result[resultIndex] = (byte) getSecondBase64Byte(symbol2, symbol3);
            resultIndex++;
            result[resultIndex] = (byte) getThirdBase64Byte(symbol3, symbol4);
            resultIndex++;
        }

        symbol1 = getBase64Symbol(base64, base64Index, false);
        symbol2 = getBase64Symbol(base64, base64Index + 1, false);
        symbol3 = getBase64Symbol(base64, base64Index + 2, true);
        symbol4 = getBase64Symbol(base64, base64Index + 3, true);
        if (symbol4 == Consts.PAD) {
            if (symbol3 == Consts.PAD) {
                if (isSecondBase64ByteZero(symbol2)) {
                    result[resultIndex] = (byte) getFirstBase64Byte(symbol1, symbol2);
                } else {
                    throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64Symbol(symbol2));
                }
            } else {
                if (isThirdBase64ByteZero(symbol3)) {
                    result[resultIndex] = (byte) getFirstBase64Byte(symbol1, symbol2);
                    result[resultIndex + 1] = (byte) getSecondBase64Byte(symbol2, symbol3);
                } else {
                    throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64Symbol(symbol3));
                }
            }
        } else {
            if (symbol3 == Consts.PAD) {
                throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64Symbol(symbol4));
            } else {
                result[resultIndex] = (byte) getFirstBase64Byte(symbol1, symbol2);
                result[resultIndex + 1] = (byte) getSecondBase64Byte(symbol2, symbol3);
                result[resultIndex + 2] = (byte) getThirdBase64Byte(symbol3, symbol4);
            }
        }
    }

    private static int getBase64Symbol(final String base64, final int base64Index, final boolean padIsValid) {
        int symbol = base64.charAt(base64Index);
        if (isBase64SymbolValid(symbol) || padIsValid && symbol == Consts.PAD) {
            return symbol;
        } else {
            throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64Symbol(symbol));
        }
    }

    static boolean isBase64SymbolValid(final int symbol) {
        return symbol >= 0 && symbol < Consts.FROM_BASE64.length && Consts.FROM_BASE64[symbol] >= 0;
    }

    static boolean isSecondBase64ByteZero(final int symbol2) {
        return Consts.FROM_BASE64_SECOND_BYTE_1[symbol2] == 0;
    }

    static boolean isThirdBase64ByteZero(final int symbol3) {
        return Consts.FROM_BASE64_THIRD_BYTE_1[symbol3] == 0;
    }

    static int getFirstBase64Byte(final int symbol1, final int symbol2) {
        return Consts.FROM_BASE64_FIRST_BYTE_1[symbol1] + Consts.FROM_BASE64_FIRST_BYTE_2[symbol2];
    }

    static int getSecondBase64Byte(final int symbol2, final int symbol3) {
        return Consts.FROM_BASE64_SECOND_BYTE_1[symbol2] + Consts.FROM_BASE64_SECOND_BYTE_2[symbol3];
    }

    static int getThirdBase64Byte(final int symbol3, final int symbol4) {
        return Consts.FROM_BASE64_THIRD_BYTE_1[symbol3] + Consts.FROM_BASE64_THIRD_BYTE_2[symbol4];
    }

    /**
     * Define, whether input string contains only base64 symbols or not.
     *
     * @param base64 base64 string.
     * @return true, if input string contains only base64 symbols.
     */
    public static boolean isBase64String(final String base64) {
        if (base64 == null) {
            return false;
        }
        if ("".equals(base64)) {
            return false;
        }

        int base64Length = base64.length();
        if (base64Length % 4 != 0) {
            return false;
        }

        int base64LengthM4 = base64Length - 4;

        int symbol;
        for (int i = 0; i < base64LengthM4; i++) {
            symbol = base64.charAt(i);
            if (!isBase64SymbolValid(symbol)) {
                return false;
            }
        }

        int symbol1 = base64.charAt(base64LengthM4);
        int symbol2 = base64.charAt(base64LengthM4 + 1);
        int symbol3 = base64.charAt(base64LengthM4 + 2);
        int symbol4 = base64.charAt(base64LengthM4 + 3);
        if (symbol4 == Consts.PAD) {
            if (symbol3 == Consts.PAD) {
                return isBase64SymbolValid(symbol1) && isBase64SymbolValid(symbol2) && isSecondBase64ByteZero(symbol2);
            } else {
                return isBase64SymbolValid(symbol1) && isBase64SymbolValid(symbol2) && isBase64SymbolValid(symbol3) && isThirdBase64ByteZero(symbol3);
            }
        } else {
            return isBase64SymbolValid(symbol1) && isBase64SymbolValid(symbol2) && isBase64SymbolValid(symbol3) && isBase64SymbolValid(symbol4);
        }
    }

}
