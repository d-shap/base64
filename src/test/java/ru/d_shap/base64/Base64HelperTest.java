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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link Base64Helper}.
 *
 * @author Dmitry Shapovalov
 */
public final class Base64HelperTest {

    /**
     * Test class constructor.
     */
    public Base64HelperTest() {
        super();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(Base64Helper.class).hasOnePrivateConstructor();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64EmptyArrayTest() {
        Assertions.assertThat(Base64Helper.toBase64(null)).isEqualTo("");
        Assertions.assertThat(Base64Helper.toBase64(new byte[0])).isEqualTo("");
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64ZeroByteEndingTest() {
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13})).isEqualTo("qszh6BMN");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{51, 29, 41, (byte) 133, 69, 3})).isEqualTo("Mx0phUUD");
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64OneByteEndingTest() {
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232})).isEqualTo("qszh6A==");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{51, 29, 41, (byte) 133})).isEqualTo("Mx0phQ==");
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64TwoByteEndingTest() {
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19})).isEqualTo("qszh6BM=");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{51, 29, 41, (byte) 133, 69})).isEqualTo("Mx0phUU=");
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getFirstBase64SymbolTest() {
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0x03)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0xA7)).isEqualTo('p');
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0x92)).isEqualTo('k');
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0x3B)).isEqualTo('O');
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0x3C)).isEqualTo('P');
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0x3D)).isEqualTo('P');
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0x3E)).isEqualTo('P');
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0x3F)).isEqualTo('P');
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0x40)).isEqualTo('Q');
        Assertions.assertThat(Base64Helper.getFirstBase64Symbol(0xD0)).isEqualTo('0');
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getSecondBase64SymbolTest() {
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0xA2)).isEqualTo('g');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0x02)).isEqualTo('g');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0x92)).isEqualTo('g');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0x12)).isEqualTo('g');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0xA3)).isEqualTo('w');

        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0x00, 0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0xA2, 0xF0)).isEqualTo('v');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0xA2, 0xF5)).isEqualTo('v');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0xA2, 0xFF)).isEqualTo('v');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0x02, 0xFF)).isEqualTo('v');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0x92, 0xFF)).isEqualTo('v');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0x12, 0x19)).isEqualTo('h');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0xA3, 0xF2)).isEqualTo('/');
        Assertions.assertThat(Base64Helper.getSecondBase64Symbol(0xA3, 0xC2)).isEqualTo('8');
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getThirdBase64SymbolTest() {
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x0F)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x2F)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0xAF)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x74)).isEqualTo('Q');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x90)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0xF6)).isEqualTo('Y');

        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x00, 0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x0F, 0x0F)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x0F, 0x3A)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x0F, 0x17)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x2F, 0x17)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0xAF, 0x17)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x74, 0x6A)).isEqualTo('R');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0x90, 0xC1)).isEqualTo('D');
        Assertions.assertThat(Base64Helper.getThirdBase64Symbol(0xF6, 0x89)).isEqualTo('a');
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getFourthBase64SymbolTest() {
        Assertions.assertThat(Base64Helper.getFourthBase64Symbol(0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getFourthBase64Symbol(0xCD)).isEqualTo('N');
        Assertions.assertThat(Base64Helper.getFourthBase64Symbol(0x8D)).isEqualTo('N');
        Assertions.assertThat(Base64Helper.getFourthBase64Symbol(0x4D)).isEqualTo('N');
        Assertions.assertThat(Base64Helper.getFourthBase64Symbol(0x0D)).isEqualTo('N');
        Assertions.assertThat(Base64Helper.getFourthBase64Symbol(0x3F)).isEqualTo('/');
        Assertions.assertThat(Base64Helper.getFourthBase64Symbol(0x25)).isEqualTo('l');
        Assertions.assertThat(Base64Helper.getFourthBase64Symbol(0x9A)).isEqualTo('a');
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedZeroByteEndingTest() {
        byte[] bytes = new byte[9];

        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", bytes)).isEqualTo(6);
        Assertions.assertThat(bytes).containsExactlyInOrder(104, 6, 193, 231, 166, 62, 0, 0, 0);

        Assertions.assertThat(Base64Helper.toBytes("cZ+128/A", bytes)).isEqualTo(6);
        Assertions.assertThat(bytes).containsExactlyInOrder(113, 159, 181, 219, 207, 192, 0, 0, 0);

        Assertions.assertThat(Base64Helper.toBytes("lrIb74U+4fI9", bytes)).isEqualTo(9);
        Assertions.assertThat(bytes).containsExactlyInOrder(150, 178, 27, 239, 133, 62, 225, 242, 61);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedOneByteEndingTest() {
        byte[] bytes = new byte[7];

        Assertions.assertThat(Base64Helper.toBytes("3jrR+g==", bytes)).isEqualTo(4);
        Assertions.assertThat(bytes).containsExactlyInOrder(222, 58, 209, 250, 0, 0, 0);

        Assertions.assertThat(Base64Helper.toBytes("pRv01Q==", bytes)).isEqualTo(4);
        Assertions.assertThat(bytes).containsExactlyInOrder(165, 27, 244, 213, 0, 0, 0);

        Assertions.assertThat(Base64Helper.toBytes("65UTPB34uw==", bytes)).isEqualTo(7);
        Assertions.assertThat(bytes).containsExactlyInOrder(235, 149, 19, 60, 29, 248, 187);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedTwoByteEndingTest() {
        byte[] bytes = new byte[8];

        Assertions.assertThat(Base64Helper.toBytes("TOF23Po=", bytes)).isEqualTo(5);
        Assertions.assertThat(bytes).containsExactlyInOrder(76, 225, 118, 220, 250, 0, 0, 0);

        Assertions.assertThat(Base64Helper.toBytes("++/2/9k=", bytes)).isEqualTo(5);
        Assertions.assertThat(bytes).containsExactlyInOrder(251, 239, 246, 255, 217, 0, 0, 0);

        Assertions.assertThat(Base64Helper.toBytes("2UFoe04P34o=", bytes)).isEqualTo(8);
        Assertions.assertThat(bytes).containsExactlyInOrder(217, 65, 104, 123, 78, 15, 223, 138);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedEmptyBase64Test() {
        Assertions.assertThat(Base64Helper.toBytes(null, new byte[3])).isEqualTo(0);
        Assertions.assertThat(Base64Helper.toBytes("", new byte[3])).isEqualTo(0);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWrongLengthTest() {
        try {
            Base64Helper.toBytes("1234567", new byte[6]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong number of symbols in base64 string (7)");
        }
        try {
            Base64Helper.toBytes("123456", new byte[6]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong number of symbols in base64 string (6)");
        }
        try {
            Base64Helper.toBytes("12345", new byte[6]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong number of symbols in base64 string (5)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedInsufficientResultLengthTest() {
        try {
            Base64Helper.toBytes("12345678", new byte[5]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Result array is too small for base64 string (5), expected size is (6)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedZeroByteEndingTest() {
        byte[] bytes;

        bytes = Base64Helper.toBytes("aAbB56Y+");
        Assertions.assertThat(bytes).containsExactlyInOrder(104, 6, 193, 231, 166, 62);

        bytes = Base64Helper.toBytes("cZ+128/A");
        Assertions.assertThat(bytes).containsExactlyInOrder(113, 159, 181, 219, 207, 192);

        bytes = Base64Helper.toBytes("lrIb74U+4fI9");
        Assertions.assertThat(bytes).containsExactlyInOrder(150, 178, 27, 239, 133, 62, 225, 242, 61);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedOneByteEndingTest() {
        byte[] bytes;

        bytes = Base64Helper.toBytes("3jrR+g==");
        Assertions.assertThat(bytes).containsExactlyInOrder(222, 58, 209, 250);

        bytes = Base64Helper.toBytes("pRv01Q==");
        Assertions.assertThat(bytes).containsExactlyInOrder(165, 27, 244, 213);

        bytes = Base64Helper.toBytes("65UTPB34uw==");
        Assertions.assertThat(bytes).containsExactlyInOrder(235, 149, 19, 60, 29, 248, 187);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedTwoByteEndingTest() {
        byte[] bytes;

        bytes = Base64Helper.toBytes("TOF23Po=");
        Assertions.assertThat(bytes).containsExactlyInOrder(76, 225, 118, 220, 250);

        bytes = Base64Helper.toBytes("++/2/9k=");
        Assertions.assertThat(bytes).containsExactlyInOrder(251, 239, 246, 255, 217);

        bytes = Base64Helper.toBytes("2UFoe04P34o=");
        Assertions.assertThat(bytes).containsExactlyInOrder(217, 65, 104, 123, 78, 15, 223, 138);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedEmptyBase64Test() {
        Assertions.assertThat(Base64Helper.toBytes(null)).containsExactlyInOrder();
        Assertions.assertThat(Base64Helper.toBytes("")).containsExactlyInOrder();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedWrongLengthTest() {
        try {
            Base64Helper.toBytes("1234567");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong number of symbols in base64 string (7)");
        }
        try {
            Base64Helper.toBytes("123456");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong number of symbols in base64 string (6)");
        }
        try {
            Base64Helper.toBytes("12345");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong number of symbols in base64 string (5)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesWrongSymbolTest() {
        try {
            Base64Helper.toBytes("++++,+++++++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
        try {
            Base64Helper.toBytes("+++++,++++++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
        try {
            Base64Helper.toBytes("++++++,+++++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
        try {
            Base64Helper.toBytes("+++++++,++++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
        try {
            Base64Helper.toBytes("++++++++,+++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
        try {
            Base64Helper.toBytes("+++++++++,++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
        try {
            Base64Helper.toBytes("++++++++++,+");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
        try {
            Base64Helper.toBytes("+++++++++++,");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
        try {
            Base64Helper.toBytes("++++++++++,=");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
        try {
            Base64Helper.toBytes("+++++++++++=");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '+' (43)");
        }
        try {
            Base64Helper.toBytes("+++++++++,==");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
        try {
            Base64Helper.toBytes("++++++++++==");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '+' (43)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesWrongPadPositionTest() {
        try {
            Base64Helper.toBytes("++++=+++++++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '=' (61)");
        }
        try {
            Base64Helper.toBytes("+++++=++++++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '=' (61)");
        }
        try {
            Base64Helper.toBytes("++++++=+++++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '=' (61)");
        }
        try {
            Base64Helper.toBytes("+++++++=++++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '=' (61)");
        }
        try {
            Base64Helper.toBytes("++++++++=+++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '=' (61)");
        }
        try {
            Base64Helper.toBytes("+++++++++=++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '=' (61)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesWrongSymbolAfterPadTest() {
        try {
            Base64Helper.toBytes("++++++=+");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '+' (43)");
        }
        try {
            Base64Helper.toBytes("++++++=,");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isBase64SymbolValidTest() {
        Assertions.assertThat(Base64Helper.isBase64SymbolValid(-2)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid(-1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid(0)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid(1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('a')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('A')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('z')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('Z')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('0')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('1')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('9')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('+')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('-')).isFalse();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('/')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('*')).isFalse();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid('=')).isFalse();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid(220)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid(122)).isTrue();
        Assertions.assertThat(Base64Helper.isBase64SymbolValid(123)).isFalse();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isSecondBase64ByteZeroTest() {
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('w')).isTrue();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('g')).isTrue();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('Q')).isTrue();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('A')).isTrue();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('I')).isFalse();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('Y')).isFalse();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('a')).isFalse();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('s')).isFalse();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('5')).isFalse();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('8')).isFalse();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('+')).isFalse();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero('/')).isFalse();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isThirdBase64ByteZeroTest() {
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('8')).isTrue();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('o')).isTrue();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('g')).isTrue();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('U')).isTrue();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('E')).isTrue();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('k')).isTrue();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('B')).isFalse();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('C')).isFalse();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('D')).isFalse();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('h')).isFalse();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('i')).isFalse();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero('j')).isFalse();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getFirstBase64ByteTest() {
        Assertions.assertThat(Base64Helper.getFirstBase64Byte('a', 'a')).isEqualTo(105);
        Assertions.assertThat(Base64Helper.getFirstBase64Byte('A', 'A')).isEqualTo(0);
        Assertions.assertThat(Base64Helper.getFirstBase64Byte('8', '+')).isEqualTo(243);
        Assertions.assertThat(Base64Helper.getFirstBase64Byte('F', 'e')).isEqualTo(21);
        Assertions.assertThat(Base64Helper.getFirstBase64Byte('P', '2')).isEqualTo(63);
        Assertions.assertThat(Base64Helper.getFirstBase64Byte('/', 'x')).isEqualTo(255);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getSecondBase64ByteTest() {
        Assertions.assertThat(Base64Helper.getSecondBase64Byte('a', 'a')).isEqualTo(166);
        Assertions.assertThat(Base64Helper.getSecondBase64Byte('A', 'A')).isEqualTo(0);
        Assertions.assertThat(Base64Helper.getSecondBase64Byte('8', '+')).isEqualTo(207);
        Assertions.assertThat(Base64Helper.getSecondBase64Byte('F', 'e')).isEqualTo(87);
        Assertions.assertThat(Base64Helper.getSecondBase64Byte('P', '2')).isEqualTo(253);
        Assertions.assertThat(Base64Helper.getSecondBase64Byte('/', 'x')).isEqualTo(252);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getThirdBase64ByteTest() {
        Assertions.assertThat(Base64Helper.getThirdBase64Byte('a', 'a')).isEqualTo(154);
        Assertions.assertThat(Base64Helper.getThirdBase64Byte('A', 'A')).isEqualTo(0);
        Assertions.assertThat(Base64Helper.getThirdBase64Byte('8', '+')).isEqualTo(62);
        Assertions.assertThat(Base64Helper.getThirdBase64Byte('F', 'e')).isEqualTo(94);
        Assertions.assertThat(Base64Helper.getThirdBase64Byte('P', '2')).isEqualTo(246);
        Assertions.assertThat(Base64Helper.getThirdBase64Byte('/', 'x')).isEqualTo(241);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isBase64StringTest() {
        Assertions.assertThat(Base64Helper.isBase64String("aaaaaaaa")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String(",aaaaaaa")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("a,aaaaaa")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aa,aaaaa")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaa,aaaa")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaa,aaa")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaa,aa")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaaa,a")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaaaa,")).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("12kdId65")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("43093+df")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("KLdfLe+/")).isTrue();

        Assertions.assertThat(Base64Helper.isBase64String("aaaaaaQ=")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("aaaa,aQ=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaa,Q=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaaaa=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaa,aa=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaa,a=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaaa,=")).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("aaaaaQ==")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("aaaa,Q==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaaa==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaa,a==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaa,==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaaQ=a")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaaaaQ=,")).isFalse();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isBase64EmptyStringTest() {
        Assertions.assertThat(Base64Helper.isBase64String(null)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("")).isFalse();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isBase64WrongLengthStringTest() {
        Assertions.assertThat(Base64Helper.isBase64String("+")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("++")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("+++")).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("+++++")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("++++++")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("+++++++")).isFalse();
    }

}
