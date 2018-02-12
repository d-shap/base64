///////////////////////////////////////////////////////////////////////////////////////////////////
// Base64 library converts bytes to the base64 representation and vice versa.
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
 * Class to perform the bytes-to-base64 and the base64-to-bytes conversions.
 *
 * @author Dmitry Shapovalov
 */
public final class Base64Helper {

    private Base64Helper() {
        super();
    }

    /**
     * Convert the byte array to the base64 string.
     *
     * @param bytes the byte array.
     * @return the base64 string.
     */
    public static String toBase64(final byte[] bytes) {
        return toBase64(bytes, 0, bytes.length);
    }

    /**
     * Convert the byte array to the base64 string.
     *
     * @param bytes       the byte array.
     * @param bytesOffset the offset of the first element in the byte array.
     * @param bytesLength the number of elements in the byte array.
     * @return the base64 string.
     */
    public static String toBase64(final byte[] bytes, final int bytesOffset, final int bytesLength) {
        int bytesLengthD3 = bytesLength / 3;
        int bytesLengthM3 = bytesLength % 3;

        int bufferLength;
        if (bytesLengthM3 == 0) {
            bufferLength = bytesLengthD3 * 4;
        } else {
            bufferLength = (bytesLengthD3 + 1) * 4;
        }
        StringBuilder buffer = new StringBuilder(bufferLength);

        int bytesIndex = bytesOffset;
        int byte1;
        int byte2;
        int byte3;
        for (int i = 0; i < bytesLengthD3; i++) {
            byte1 = bytes[bytesIndex] & 0xFF;
            bytesIndex++;
            byte2 = bytes[bytesIndex] & 0xFF;
            bytesIndex++;
            byte3 = bytes[bytesIndex] & 0xFF;
            bytesIndex++;

            buffer.append((char) getFirstBase64Character(byte1));
            buffer.append((char) getSecondBase64Character(byte1, byte2));
            buffer.append((char) getThirdBase64Character(byte2, byte3));
            buffer.append((char) getFourthBase64Character(byte3));
        }

        if (bytesLengthM3 == 1) {
            byte1 = bytes[bytesIndex] & 0xFF;

            buffer.append((char) getFirstBase64Character(byte1));
            buffer.append((char) getSecondBase64Character(byte1));
            buffer.append((char) Consts.PAD);
            buffer.append((char) Consts.PAD);
        }
        if (bytesLengthM3 == 2) {
            byte1 = bytes[bytesIndex] & 0xFF;
            byte2 = bytes[bytesIndex + 1] & 0xFF;

            buffer.append((char) getFirstBase64Character(byte1));
            buffer.append((char) getSecondBase64Character(byte1, byte2));
            buffer.append((char) getThirdBase64Character(byte2));
            buffer.append((char) Consts.PAD);
        }

        return buffer.toString();
    }

    static int getFirstBase64Character(final int byte1) {
        return Consts.TO_BASE64_FIRST_CHARACTER[byte1];
    }

    static int getSecondBase64Character(final int byte1) {
        int idx = Consts.TO_BASE64_SECOND_CHARACTER_INDEX_1[byte1];
        return Consts.TO_BASE64[idx];
    }

    static int getSecondBase64Character(final int byte1, final int byte2) {
        int idx = Consts.TO_BASE64_SECOND_CHARACTER_INDEX_1[byte1] + Consts.TO_BASE64_SECOND_CHARACTER_INDEX_2[byte2];
        return Consts.TO_BASE64[idx];
    }

    static int getThirdBase64Character(final int byte2) {
        int idx = Consts.TO_BASE64_THIRD_CHARACTER_INDEX_1[byte2];
        return Consts.TO_BASE64[idx];
    }

    static int getThirdBase64Character(final int byte2, final int byte3) {
        int idx = Consts.TO_BASE64_THIRD_CHARACTER_INDEX_1[byte2] + Consts.TO_BASE64_THIRD_CHARACTER_INDEX_2[byte3];
        return Consts.TO_BASE64[idx];
    }

    static int getFourthBase64Character(final int byte3) {
        return Consts.TO_BASE64_FOURTH_CHARACTER[byte3];
    }

    /**
     * Convert the base64 string to the byte array.
     *
     * @param base64 the base64 string.
     * @param bytes  the byte array to write the result.
     * @return the number of bytes affected in the byte array.
     */
    public static int toBytes(final String base64, final byte[] bytes) {
        return toBytes(base64, 0, base64.length(), bytes, 0);
    }

    /**
     * Convert the base64 string to the byte array.
     *
     * @param base64       the base64 string.
     * @param base64Offset the offset of the first element in the base64 string.
     * @param base64Length the number of elements in the base64 string.
     * @param bytes        the byte array to write the result.
     * @return the number of bytes affected in the byte array.
     */
    public static int toBytes(final String base64, final int base64Offset, final int base64Length, final byte[] bytes) {
        return toBytes(base64, base64Offset, base64Length, bytes, 0);
    }

    /**
     * Convert the base64 string to the byte array.
     *
     * @param base64      the base64 string.
     * @param bytes       the byte array to write the result.
     * @param bytesOffset the offset of the first element in the byte array.
     * @return the number of bytes affected in the byte array.
     */
    public static int toBytes(final String base64, final byte[] bytes, final int bytesOffset) {
        return toBytes(base64, 0, base64.length(), bytes, bytesOffset);
    }

    /**
     * Convert the base64 string to the byte array.
     *
     * @param base64       the base64 string.
     * @param base64Offset the offset of the first element in the base64 string.
     * @param base64Length the number of elements in the base64 string.
     * @param bytes        the byte array to write the result.
     * @param bytesOffset  the offset of the first element in the byte array.
     * @return the number of bytes affected in the byte array.
     */
    public static int toBytes(final String base64, final int base64Offset, final int base64Length, final byte[] bytes, final int bytesOffset) {
        if (base64Length % 4 != 0) {
            throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64StringSizeMessage(base64Length));
        }
        int emptyBytesCount = getEmptyBytesCount(base64, base64Offset, base64Length);
        int bytesLength = base64Length * 3 / 4 - emptyBytesCount;
        if (bytes.length - bytesOffset < bytesLength) {
            throw new Base64RuntimeException(ExceptionMessageHelper.createWrongResultArrayMessage(bytesLength, Math.max(bytes.length - bytesOffset, 0)));
        }

        convertToBytes(base64, base64Offset, base64Length, bytes, bytesOffset);
        return Math.max(bytesLength, 0);
    }

    /**
     * Convert the base64 string to the byte array.
     *
     * @param base64 the base64 string.
     * @return the byte array with the result.
     */
    public static byte[] toBytes(final String base64) {
        return toBytes(base64, 0, base64.length());
    }

    /**
     * Convert the base64 string to the byte array.
     *
     * @param base64       the base64 string.
     * @param base64Offset the offset of the first element in the base64 string.
     * @param base64Length the number of elements in the base64 string.
     * @return the byte array with the result.
     */
    public static byte[] toBytes(final String base64, final int base64Offset, final int base64Length) {
        if (base64Length % 4 != 0) {
            throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64StringSizeMessage(base64Length));
        }
        int emptyBytesCount = getEmptyBytesCount(base64, base64Offset, base64Length);
        int bytesLength = base64Length * 3 / 4 - emptyBytesCount;
        byte[] bytes = new byte[Math.max(bytesLength, 0)];

        convertToBytes(base64, base64Offset, base64Length, bytes, 0);
        return bytes;
    }

    private static int getEmptyBytesCount(final String base64, final int base64Offset, final int base64Length) {
        if (base64Offset + base64Length >= 1 && base64.charAt(base64Offset + base64Length - 1) == Consts.PAD) {
            if (base64Offset + base64Length >= 2 && base64.charAt(base64Offset + base64Length - 2) == Consts.PAD) {
                return 2;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    private static void convertToBytes(final String base64, final int base64Offset, final int base64Length, final byte[] bytes, final int bytesOffset) {
        int base64Index = base64Offset;
        int base64LengthD4M1 = base64Length / 4 - 1;
        int character1;
        int character2;
        int character3;
        int character4;
        int bytesIndex = bytesOffset;
        for (int i = 0; i < base64LengthD4M1; i++) {
            character1 = base64CharacterAt(base64, base64Index, false);
            base64Index++;
            character2 = base64CharacterAt(base64, base64Index, false);
            base64Index++;
            character3 = base64CharacterAt(base64, base64Index, false);
            base64Index++;
            character4 = base64CharacterAt(base64, base64Index, false);
            base64Index++;

            bytes[bytesIndex] = (byte) getFirstBase64Byte(character1, character2);
            bytesIndex++;
            bytes[bytesIndex] = (byte) getSecondBase64Byte(character2, character3);
            bytesIndex++;
            bytes[bytesIndex] = (byte) getThirdBase64Byte(character3, character4);
            bytesIndex++;
        }

        if (base64Index < base64Offset + base64Length) {
            character1 = base64CharacterAt(base64, base64Index, false);
            character2 = base64CharacterAt(base64, base64Index + 1, false);
            character3 = base64CharacterAt(base64, base64Index + 2, true);
            character4 = base64CharacterAt(base64, base64Index + 3, true);
            if (character4 == Consts.PAD) {
                if (character3 == Consts.PAD) {
                    if (isSecondBase64ByteZero(character2)) {
                        bytes[bytesIndex] = (byte) getFirstBase64Byte(character1, character2);
                    } else {
                        throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64CharacterMessage(character2));
                    }
                } else {
                    if (isThirdBase64ByteZero(character3)) {
                        bytes[bytesIndex] = (byte) getFirstBase64Byte(character1, character2);
                        bytes[bytesIndex + 1] = (byte) getSecondBase64Byte(character2, character3);
                    } else {
                        throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64CharacterMessage(character3));
                    }
                }
            } else {
                if (character3 == Consts.PAD) {
                    throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64CharacterMessage(character4));
                } else {
                    bytes[bytesIndex] = (byte) getFirstBase64Byte(character1, character2);
                    bytes[bytesIndex + 1] = (byte) getSecondBase64Byte(character2, character3);
                    bytes[bytesIndex + 2] = (byte) getThirdBase64Byte(character3, character4);
                }
            }
        }
    }

    private static int base64CharacterAt(final String base64, final int base64Index, final boolean padIsValid) {
        int character = base64.charAt(base64Index);
        if (isBase64CharacterValid(character) || padIsValid && character == Consts.PAD) {
            return character;
        } else {
            throw new Base64RuntimeException(ExceptionMessageHelper.createWrongBase64CharacterMessage(character));
        }
    }

    static boolean isBase64CharacterValid(final int character) {
        return character >= '+' && character < Consts.FROM_BASE64.length && Consts.FROM_BASE64[character] >= 0;
    }

    static boolean isSecondBase64ByteZero(final int character2) {
        return Consts.FROM_BASE64_SECOND_BYTE_1[character2] == 0;
    }

    static boolean isThirdBase64ByteZero(final int character3) {
        return Consts.FROM_BASE64_THIRD_BYTE_1[character3] == 0;
    }

    static int getFirstBase64Byte(final int character1, final int character2) {
        return Consts.FROM_BASE64_FIRST_BYTE_1[character1] + Consts.FROM_BASE64_FIRST_BYTE_2[character2];
    }

    static int getSecondBase64Byte(final int character2, final int character3) {
        return Consts.FROM_BASE64_SECOND_BYTE_1[character2] + Consts.FROM_BASE64_SECOND_BYTE_2[character3];
    }

    static int getThirdBase64Byte(final int character3, final int character4) {
        return Consts.FROM_BASE64_THIRD_BYTE_1[character3] + Consts.FROM_BASE64_THIRD_BYTE_2[character4];
    }

    /**
     * Define, whether the base64 string contains only the base64 characters or not.
     *
     * @param base64 the base64 string.
     * @return true, if the base64 string contains only the base64 characters.
     */
    public static boolean isBase64String(final String base64) {
        return isBase64String(base64, 0, base64.length());
    }

    /**
     * Define, whether the base64 string contains only the base64 characters or not.
     *
     * @param base64       the base64 string.
     * @param base64Offset the offset of the first element in the base64 string.
     * @param base64Length the number of elements in the base64 string.
     * @return true, if the base64 string contains only the base64 characters.
     */
    public static boolean isBase64String(final String base64, final int base64Offset, final int base64Length) {
        if (base64Length <= 0) {
            return false;
        }
        if (base64Length % 4 != 0) {
            return false;
        }

        int base64Index = base64Offset;
        int base64LengthM4 = base64Length - 4;
        int base64MaxIndex = base64Offset + base64LengthM4;
        int currentCharacter;
        while (base64Index < base64MaxIndex) {
            currentCharacter = base64.charAt(base64Index);
            if (!isBase64CharacterValid(currentCharacter)) {
                return false;
            }
            base64Index++;
        }

        int character1 = base64.charAt(base64Index);
        int character2 = base64.charAt(base64Index + 1);
        int character3 = base64.charAt(base64Index + 2);
        int character4 = base64.charAt(base64Index + 3);
        if (character4 == Consts.PAD) {
            if (character3 == Consts.PAD) {
                return isBase64CharacterValid(character1) && isBase64CharacterValid(character2) && isSecondBase64ByteZero(character2);
            } else {
                return isBase64CharacterValid(character1) && isBase64CharacterValid(character2) && isBase64CharacterValid(character3) && isThirdBase64ByteZero(character3);
            }
        } else {
            return isBase64CharacterValid(character1) && isBase64CharacterValid(character2) && isBase64CharacterValid(character3) && isBase64CharacterValid(character4);
        }
    }

}
