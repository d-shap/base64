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
 * Predefined values for the bytes-to-base64 and the base64-to-bytes conversions.
 *
 * @author Dmitry Shapovalov
 */
final class Consts {

    static final int[] TO_BASE64;

    static {
        TO_BASE64 = new int[64];

        TO_BASE64[0] = 'A';
        TO_BASE64[1] = 'B';
        TO_BASE64[2] = 'C';
        TO_BASE64[3] = 'D';
        TO_BASE64[4] = 'E';
        TO_BASE64[5] = 'F';
        TO_BASE64[6] = 'G';
        TO_BASE64[7] = 'H';
        TO_BASE64[8] = 'I';
        TO_BASE64[9] = 'J';
        TO_BASE64[10] = 'K';
        TO_BASE64[11] = 'L';
        TO_BASE64[12] = 'M';
        TO_BASE64[13] = 'N';
        TO_BASE64[14] = 'O';
        TO_BASE64[15] = 'P';
        TO_BASE64[16] = 'Q';
        TO_BASE64[17] = 'R';
        TO_BASE64[18] = 'S';
        TO_BASE64[19] = 'T';
        TO_BASE64[20] = 'U';
        TO_BASE64[21] = 'V';
        TO_BASE64[22] = 'W';
        TO_BASE64[23] = 'X';
        TO_BASE64[24] = 'Y';
        TO_BASE64[25] = 'Z';
        TO_BASE64[26] = 'a';
        TO_BASE64[27] = 'b';
        TO_BASE64[28] = 'c';
        TO_BASE64[29] = 'd';
        TO_BASE64[30] = 'e';
        TO_BASE64[31] = 'f';
        TO_BASE64[32] = 'g';
        TO_BASE64[33] = 'h';
        TO_BASE64[34] = 'i';
        TO_BASE64[35] = 'j';
        TO_BASE64[36] = 'k';
        TO_BASE64[37] = 'l';
        TO_BASE64[38] = 'm';
        TO_BASE64[39] = 'n';
        TO_BASE64[40] = 'o';
        TO_BASE64[41] = 'p';
        TO_BASE64[42] = 'q';
        TO_BASE64[43] = 'r';
        TO_BASE64[44] = 's';
        TO_BASE64[45] = 't';
        TO_BASE64[46] = 'u';
        TO_BASE64[47] = 'v';
        TO_BASE64[48] = 'w';
        TO_BASE64[49] = 'x';
        TO_BASE64[50] = 'y';
        TO_BASE64[51] = 'z';
        TO_BASE64[52] = '0';
        TO_BASE64[53] = '1';
        TO_BASE64[54] = '2';
        TO_BASE64[55] = '3';
        TO_BASE64[56] = '4';
        TO_BASE64[57] = '5';
        TO_BASE64[58] = '6';
        TO_BASE64[59] = '7';
        TO_BASE64[60] = '8';
        TO_BASE64[61] = '9';
        TO_BASE64[62] = '+';
        TO_BASE64[63] = '/';
    }

    static final int[] TO_BASE64_FIRST_CHARACTER;

    static {
        TO_BASE64_FIRST_CHARACTER = new int[256];
        for (int i = 0; i < TO_BASE64_FIRST_CHARACTER.length; i++) {
            TO_BASE64_FIRST_CHARACTER[i] = TO_BASE64[(i & 0xFC) >> 2];
        }
    }

    static final int[] TO_BASE64_SECOND_CHARACTER_INDEX_1;

    static {
        TO_BASE64_SECOND_CHARACTER_INDEX_1 = new int[256];
        for (int i = 0; i < TO_BASE64_SECOND_CHARACTER_INDEX_1.length; i++) {
            TO_BASE64_SECOND_CHARACTER_INDEX_1[i] = (i & 0x3) << 4;
        }
    }

    static final int[] TO_BASE64_SECOND_CHARACTER_INDEX_2;

    static {
        TO_BASE64_SECOND_CHARACTER_INDEX_2 = new int[256];
        for (int i = 0; i < TO_BASE64_SECOND_CHARACTER_INDEX_2.length; i++) {
            TO_BASE64_SECOND_CHARACTER_INDEX_2[i] = (i & 0xF0) >> 4;
        }
    }

    static final int[] TO_BASE64_THIRD_CHARACTER_INDEX_1;

    static {
        TO_BASE64_THIRD_CHARACTER_INDEX_1 = new int[256];
        for (int i = 0; i < TO_BASE64_THIRD_CHARACTER_INDEX_1.length; i++) {
            TO_BASE64_THIRD_CHARACTER_INDEX_1[i] = (i & 0xF) << 2;
        }
    }

    static final int[] TO_BASE64_THIRD_CHARACTER_INDEX_2;

    static {
        TO_BASE64_THIRD_CHARACTER_INDEX_2 = new int[256];
        for (int i = 0; i < TO_BASE64_THIRD_CHARACTER_INDEX_2.length; i++) {
            TO_BASE64_THIRD_CHARACTER_INDEX_2[i] = (i & 0xC0) >> 6;
        }
    }

    static final int[] TO_BASE64_FOURTH_CHARACTER;

    static {
        TO_BASE64_FOURTH_CHARACTER = new int[256];
        for (int i = 0; i < TO_BASE64_FOURTH_CHARACTER.length; i++) {
            TO_BASE64_FOURTH_CHARACTER[i] = TO_BASE64[i & 0x3F];
        }
    }

    static final int[] FROM_BASE64;

    static {
        int length = Math.max('Z', 'z');
        length = Math.max(length, '9');
        length = Math.max(length, '+');
        length = Math.max(length, '/');
        length++;

        FROM_BASE64 = new int[length];
        for (int i = 0; i < length; i++) {
            FROM_BASE64[i] = -1;
        }

        FROM_BASE64['A'] = 0;
        FROM_BASE64['B'] = 1;
        FROM_BASE64['C'] = 2;
        FROM_BASE64['D'] = 3;
        FROM_BASE64['E'] = 4;
        FROM_BASE64['F'] = 5;
        FROM_BASE64['G'] = 6;
        FROM_BASE64['H'] = 7;
        FROM_BASE64['I'] = 8;
        FROM_BASE64['J'] = 9;
        FROM_BASE64['K'] = 10;
        FROM_BASE64['L'] = 11;
        FROM_BASE64['M'] = 12;
        FROM_BASE64['N'] = 13;
        FROM_BASE64['O'] = 14;
        FROM_BASE64['P'] = 15;
        FROM_BASE64['Q'] = 16;
        FROM_BASE64['R'] = 17;
        FROM_BASE64['S'] = 18;
        FROM_BASE64['T'] = 19;
        FROM_BASE64['U'] = 20;
        FROM_BASE64['V'] = 21;
        FROM_BASE64['W'] = 22;
        FROM_BASE64['X'] = 23;
        FROM_BASE64['Y'] = 24;
        FROM_BASE64['Z'] = 25;
        FROM_BASE64['a'] = 26;
        FROM_BASE64['b'] = 27;
        FROM_BASE64['c'] = 28;
        FROM_BASE64['d'] = 29;
        FROM_BASE64['e'] = 30;
        FROM_BASE64['f'] = 31;
        FROM_BASE64['g'] = 32;
        FROM_BASE64['h'] = 33;
        FROM_BASE64['i'] = 34;
        FROM_BASE64['j'] = 35;
        FROM_BASE64['k'] = 36;
        FROM_BASE64['l'] = 37;
        FROM_BASE64['m'] = 38;
        FROM_BASE64['n'] = 39;
        FROM_BASE64['o'] = 40;
        FROM_BASE64['p'] = 41;
        FROM_BASE64['q'] = 42;
        FROM_BASE64['r'] = 43;
        FROM_BASE64['s'] = 44;
        FROM_BASE64['t'] = 45;
        FROM_BASE64['u'] = 46;
        FROM_BASE64['v'] = 47;
        FROM_BASE64['w'] = 48;
        FROM_BASE64['x'] = 49;
        FROM_BASE64['y'] = 50;
        FROM_BASE64['z'] = 51;
        FROM_BASE64['0'] = 52;
        FROM_BASE64['1'] = 53;
        FROM_BASE64['2'] = 54;
        FROM_BASE64['3'] = 55;
        FROM_BASE64['4'] = 56;
        FROM_BASE64['5'] = 57;
        FROM_BASE64['6'] = 58;
        FROM_BASE64['7'] = 59;
        FROM_BASE64['8'] = 60;
        FROM_BASE64['9'] = 61;
        FROM_BASE64['+'] = 62;
        FROM_BASE64['/'] = 63;
    }

    static final int[] FROM_BASE64_FIRST_BYTE_1;

    static {
        FROM_BASE64_FIRST_BYTE_1 = new int[FROM_BASE64.length];
        for (int i = 0; i < FROM_BASE64_FIRST_BYTE_1.length; i++) {
            if (FROM_BASE64[i] == -1) {
                FROM_BASE64_FIRST_BYTE_1[i] = -1;
            } else {
                FROM_BASE64_FIRST_BYTE_1[i] = FROM_BASE64[i] << 2;
            }
        }
    }

    static final int[] FROM_BASE64_FIRST_BYTE_2;

    static {
        FROM_BASE64_FIRST_BYTE_2 = new int[FROM_BASE64.length];
        for (int i = 0; i < FROM_BASE64_FIRST_BYTE_2.length; i++) {
            if (FROM_BASE64[i] == -1) {
                FROM_BASE64_FIRST_BYTE_2[i] = -1;
            } else {
                FROM_BASE64_FIRST_BYTE_2[i] = (FROM_BASE64[i] & 0x30) >> 4;
            }
        }
    }

    static final int[] FROM_BASE64_SECOND_BYTE_1;

    static {
        FROM_BASE64_SECOND_BYTE_1 = new int[FROM_BASE64.length];
        for (int i = 0; i < FROM_BASE64_SECOND_BYTE_1.length; i++) {
            if (FROM_BASE64[i] == -1) {
                FROM_BASE64_SECOND_BYTE_1[i] = -1;
            } else {
                FROM_BASE64_SECOND_BYTE_1[i] = (FROM_BASE64[i] & 0x0F) << 4;
            }
        }
    }

    static final int[] FROM_BASE64_SECOND_BYTE_2;

    static {
        FROM_BASE64_SECOND_BYTE_2 = new int[FROM_BASE64.length];
        for (int i = 0; i < FROM_BASE64_SECOND_BYTE_2.length; i++) {
            if (FROM_BASE64[i] == -1) {
                FROM_BASE64_SECOND_BYTE_2[i] = -1;
            } else {
                FROM_BASE64_SECOND_BYTE_2[i] = (FROM_BASE64[i] & 0x3C) >> 2;
            }
        }
    }

    static final int[] FROM_BASE64_THIRD_BYTE_1;

    static {
        FROM_BASE64_THIRD_BYTE_1 = new int[FROM_BASE64.length];
        for (int i = 0; i < FROM_BASE64_THIRD_BYTE_1.length; i++) {
            if (FROM_BASE64[i] == -1) {
                FROM_BASE64_THIRD_BYTE_1[i] = -1;
            } else {
                FROM_BASE64_THIRD_BYTE_1[i] = (FROM_BASE64[i] & 0x03) << 6;
            }
        }
    }

    static final int[] FROM_BASE64_THIRD_BYTE_2;

    static {
        FROM_BASE64_THIRD_BYTE_2 = new int[FROM_BASE64.length];
        for (int i = 0; i < FROM_BASE64_THIRD_BYTE_2.length; i++) {
            FROM_BASE64_THIRD_BYTE_2[i] = FROM_BASE64[i];
        }
    }

    static final int PAD = '=';

    private Consts() {
        super();
    }

}
