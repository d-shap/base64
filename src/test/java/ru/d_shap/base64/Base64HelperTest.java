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
    public void toBase64Test() {
        Assertions.assertThat(Base64Helper.toBase64(new byte[0])).isEqualTo("");

        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13})).isEqualTo("qszh6BMN");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{51, 29, 41, (byte) 133, 69, 3})).isEqualTo("Mx0phUUD");

        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19})).isEqualTo("qszh6BM=");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{51, 29, 41, (byte) 133, 69})).isEqualTo("Mx0phUU=");

        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232})).isEqualTo("qszh6A==");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{51, 29, 41, (byte) 133})).isEqualTo("Mx0phQ==");
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBase64WithNullByteArrayTest() {
        Base64Helper.toBase64(null);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64WithBoundsTest() {
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 0, 0)).isEqualTo("");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 0, 1)).isEqualTo("qg==");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 0, 2)).isEqualTo("qsw=");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 0, 3)).isEqualTo("qszh");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 0, 4)).isEqualTo("qszh6A==");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 0, 5)).isEqualTo("qszh6BM=");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 0, 6)).isEqualTo("qszh6BMN");

        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 1, 0)).isEqualTo("");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 1, 1)).isEqualTo("zA==");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 1, 2)).isEqualTo("zOE=");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 1, 3)).isEqualTo("zOHo");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 1, 4)).isEqualTo("zOHoEw==");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 1, 5)).isEqualTo("zOHoEw0=");

        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 2, 0)).isEqualTo("");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 2, 1)).isEqualTo("4Q==");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 2, 2)).isEqualTo("4eg=");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 2, 3)).isEqualTo("4egT");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 2, 4)).isEqualTo("4egTDQ==");

        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 3, 0)).isEqualTo("");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 3, 1)).isEqualTo("6A==");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 3, 2)).isEqualTo("6BM=");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 3, 3)).isEqualTo("6BMN");

        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 4, 0)).isEqualTo("");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 4, 1)).isEqualTo("Ew==");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 4, 2)).isEqualTo("Ew0=");

        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 5, 0)).isEqualTo("");
        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 5, 1)).isEqualTo("DQ==");

        Assertions.assertThat(Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 6, 0)).isEqualTo("");
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBase64WithBoundsAndNullByteArrayTest() {
        Base64Helper.toBase64(null, 0, 6);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64WithBoundsAndNegativeOffsetTest() {
        try {
            Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, -1, 6);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array index (-1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64WithBoundsAndNegativeLengthTest() {
        try {
            Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 0, -1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array length (-1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64WithBoundsAndTooLargeLengthTest() {
        try {
            Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}, 0, 10);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array index (10)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getFirstBase64CharacterTest() {
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0x03)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0xA7)).isEqualTo('p');
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0x92)).isEqualTo('k');
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0x3B)).isEqualTo('O');
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0x3C)).isEqualTo('P');
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0x3D)).isEqualTo('P');
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0x3E)).isEqualTo('P');
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0x3F)).isEqualTo('P');
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0x40)).isEqualTo('Q');
        Assertions.assertThat(Base64Helper.getFirstBase64Character(0xD0)).isEqualTo('0');
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getSecondBase64CharacterTest() {
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0xA2)).isEqualTo('g');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0x02)).isEqualTo('g');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0x92)).isEqualTo('g');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0x12)).isEqualTo('g');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0xA3)).isEqualTo('w');

        Assertions.assertThat(Base64Helper.getSecondBase64Character(0x00, 0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0xA2, 0xF0)).isEqualTo('v');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0xA2, 0xF5)).isEqualTo('v');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0xA2, 0xFF)).isEqualTo('v');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0x02, 0xFF)).isEqualTo('v');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0x92, 0xFF)).isEqualTo('v');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0x12, 0x19)).isEqualTo('h');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0xA3, 0xF2)).isEqualTo('/');
        Assertions.assertThat(Base64Helper.getSecondBase64Character(0xA3, 0xC2)).isEqualTo('8');
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getThirdBase64CharacterTest() {
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x0F)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x2F)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0xAF)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x74)).isEqualTo('Q');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x90)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0xF6)).isEqualTo('Y');

        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x00, 0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x0F, 0x0F)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x0F, 0x3A)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x0F, 0x17)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x2F, 0x17)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0xAF, 0x17)).isEqualTo('8');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x74, 0x6A)).isEqualTo('R');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0x90, 0xC1)).isEqualTo('D');
        Assertions.assertThat(Base64Helper.getThirdBase64Character(0xF6, 0x89)).isEqualTo('a');
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getFourthBase64CharacterTest() {
        Assertions.assertThat(Base64Helper.getFourthBase64Character(0x00)).isEqualTo('A');
        Assertions.assertThat(Base64Helper.getFourthBase64Character(0xCD)).isEqualTo('N');
        Assertions.assertThat(Base64Helper.getFourthBase64Character(0x8D)).isEqualTo('N');
        Assertions.assertThat(Base64Helper.getFourthBase64Character(0x4D)).isEqualTo('N');
        Assertions.assertThat(Base64Helper.getFourthBase64Character(0x0D)).isEqualTo('N');
        Assertions.assertThat(Base64Helper.getFourthBase64Character(0x3F)).isEqualTo('/');
        Assertions.assertThat(Base64Helper.getFourthBase64Character(0x25)).isEqualTo('l');
        Assertions.assertThat(Base64Helper.getFourthBase64Character(0x9A)).isEqualTo('a');
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedTest() {
        byte[] bytes01 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("", bytes01)).isEqualTo(0);
        Assertions.assertThat(bytes01).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes02 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", bytes02)).isEqualTo(6);
        Assertions.assertThat(bytes02).containsExactlyInOrder(104, 6, 193, 231, 166, 62, 0, 0, 0);

        byte[] bytes03 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("cZ+128/A", bytes03)).isEqualTo(6);
        Assertions.assertThat(bytes03).containsExactlyInOrder(113, 159, 181, 219, 207, 192, 0, 0, 0);

        byte[] bytes04 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("lrIb74U+4fI9", bytes04)).isEqualTo(9);
        Assertions.assertThat(bytes04).containsExactlyInOrder(150, 178, 27, 239, 133, 62, 225, 242, 61);

        byte[] bytes05 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("TOF23Po=", bytes05)).isEqualTo(5);
        Assertions.assertThat(bytes05).containsExactlyInOrder(76, 225, 118, 220, 250, 0, 0, 0, 0);

        byte[] bytes06 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("++/2/9k=", bytes06)).isEqualTo(5);
        Assertions.assertThat(bytes06).containsExactlyInOrder(251, 239, 246, 255, 217, 0, 0, 0, 0);

        byte[] bytes07 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("2UFoe04P34o=", bytes07)).isEqualTo(8);
        Assertions.assertThat(bytes07).containsExactlyInOrder(217, 65, 104, 123, 78, 15, 223, 138, 0);

        byte[] bytes08 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("3jrR+g==", bytes08)).isEqualTo(4);
        Assertions.assertThat(bytes08).containsExactlyInOrder(222, 58, 209, 250, 0, 0, 0, 0, 0);

        byte[] bytes09 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("pRv01Q==", bytes09)).isEqualTo(4);
        Assertions.assertThat(bytes09).containsExactlyInOrder(165, 27, 244, 213, 0, 0, 0, 0, 0);

        byte[] bytes10 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("65UTPB34uw==", bytes10)).isEqualTo(7);
        Assertions.assertThat(bytes10).containsExactlyInOrder(235, 149, 19, 60, 29, 248, 187, 0, 0);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBytesSpecifiedWithNullStringTest() {
        Base64Helper.toBytes(null, new byte[9]);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithWrongStringLengthTest() {
        try {
            Base64Helper.toBytes("1234567", new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (7)");
        }
        try {
            Base64Helper.toBytes("123456", new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (6)");
        }
        try {
            Base64Helper.toBytes("12345", new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (5)");
        }
        try {
            Base64Helper.toBytes("123", new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (3)");
        }
        try {
            Base64Helper.toBytes("12", new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (2)");
        }
        try {
            Base64Helper.toBytes("1", new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBytesSpecifiedWithNullByteArrayTest() {
        Base64Helper.toBytes("aAbB56Y+", null);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithInsufficientByteArrayLengthTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", new byte[5]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array length (5), expected length is (6)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsTest() {
        byte[] bytes11 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 0, bytes11)).isEqualTo(0);
        Assertions.assertThat(bytes11).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes12 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 4, bytes12)).isEqualTo(3);
        Assertions.assertThat(bytes12).containsExactlyInOrder(104, 6, 193, 0, 0, 0, 0, 0, 0);

        byte[] bytes13 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 8, bytes13)).isEqualTo(6);
        Assertions.assertThat(bytes13).containsExactlyInOrder(104, 6, 193, 231, 166, 62, 0, 0, 0);

        byte[] bytes21 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 4, 0, bytes21)).isEqualTo(0);
        Assertions.assertThat(bytes21).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes22 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 4, 4, bytes22)).isEqualTo(3);
        Assertions.assertThat(bytes22).containsExactlyInOrder(231, 166, 62, 0, 0, 0, 0, 0, 0);

        byte[] bytes31 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 8, 0, bytes31)).isEqualTo(0);
        Assertions.assertThat(bytes31).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBytesSpecifiedWithBase64BoundsAndNullStringTest() {
        Base64Helper.toBytes(null, 4, 4, new byte[9]);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndWrongStringLengthTest() {
        try {
            Base64Helper.toBytes("1234567", 4, 3, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (3)");
        }
        try {
            Base64Helper.toBytes("1234567", 4, 2, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (2)");
        }
        try {
            Base64Helper.toBytes("1234567", 4, 1, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndNegativeOffsetTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", -1, 4, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (-1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndNegativeLengthTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -1, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-1)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -2, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-2)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -3, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-3)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -4, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-4)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndTooLargeLengthTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 9, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (13)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 10, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (14)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 11, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (15)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 12, new byte[9]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (16)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBytesSpecifiedWithBase64BoundsAndNullByteArrayTest() {
        Base64Helper.toBytes("aAbB56Y+", 4, 4, null);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndInsufficientByteArrayLengthTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 4, new byte[2]);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array length (2), expected length is (3)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithByteArrayOffsetTest() {
        byte[] bytes1 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", bytes1, 0)).isEqualTo(6);
        Assertions.assertThat(bytes1).containsExactlyInOrder(104, 6, 193, 231, 166, 62, 0, 0, 0);

        byte[] bytes2 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", bytes2, 1)).isEqualTo(6);
        Assertions.assertThat(bytes2).containsExactlyInOrder(0, 104, 6, 193, 231, 166, 62, 0, 0);

        byte[] bytes3 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", bytes3, 2)).isEqualTo(6);
        Assertions.assertThat(bytes3).containsExactlyInOrder(0, 0, 104, 6, 193, 231, 166, 62, 0);

        byte[] bytes4 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", bytes4, 3)).isEqualTo(6);
        Assertions.assertThat(bytes4).containsExactlyInOrder(0, 0, 0, 104, 6, 193, 231, 166, 62);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBytesSpecifiedWithByteArrayOffsetAndNullStringTest() {
        Base64Helper.toBytes(null, new byte[9], 2);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithByteArrayOffsetAndWrongStringLengthTest() {
        try {
            Base64Helper.toBytes("1234567", new byte[9], 2);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (7)");
        }
        try {
            Base64Helper.toBytes("123456", new byte[9], 2);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (6)");
        }
        try {
            Base64Helper.toBytes("12345", new byte[9], 2);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (5)");
        }
        try {
            Base64Helper.toBytes("123", new byte[9], 2);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (3)");
        }
        try {
            Base64Helper.toBytes("12", new byte[9], 2);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (2)");
        }
        try {
            Base64Helper.toBytes("1", new byte[9], 2);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBytesSpecifiedWithByteArrayOffsetAndNullByteArrayTest() {
        Base64Helper.toBytes("aAbB56Y+", null, 2);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithByteArrayOffsetAndInsufficientByteArrayLengthTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", new byte[9], 4);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array length (5), expected length is (6)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithByteArrayOffsetAndNegativeByteArrayOffsetTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", new byte[9], -1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array index (-1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithByteArrayOffsetAndTooLargeByteArrayOffsetTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", new byte[9], 10);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array index (10)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithByteArrayOffsetAndUpperBoundTest() {
        byte[] bytes1 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("", bytes1, 9)).isEqualTo(0);
        Assertions.assertThat(bytes1).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        try {
            Base64Helper.toBytes("", new byte[9], 10);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array index (10)");
        }

        try {
            Base64Helper.toBytes("aQ==", new byte[9], 9);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array length (0), expected length is (1)");
        }

        byte[] bytes2 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aQ==", bytes2, 8)).isEqualTo(1);
        Assertions.assertThat(bytes2).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 105);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetTest() {
        byte[] bytes11 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 0, bytes11, 0)).isEqualTo(0);
        Assertions.assertThat(bytes11).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes12 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 4, bytes12, 0)).isEqualTo(3);
        Assertions.assertThat(bytes12).containsExactlyInOrder(104, 6, 193, 0, 0, 0, 0, 0, 0);

        byte[] bytes13 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 8, bytes13, 0)).isEqualTo(6);
        Assertions.assertThat(bytes13).containsExactlyInOrder(104, 6, 193, 231, 166, 62, 0, 0, 0);

        byte[] bytes21 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 4, 0, bytes21, 0)).isEqualTo(0);
        Assertions.assertThat(bytes21).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes22 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 4, 4, bytes22, 0)).isEqualTo(3);
        Assertions.assertThat(bytes22).containsExactlyInOrder(231, 166, 62, 0, 0, 0, 0, 0, 0);

        byte[] bytes31 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 0, bytes31, 1)).isEqualTo(0);
        Assertions.assertThat(bytes31).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes32 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 4, bytes32, 1)).isEqualTo(3);
        Assertions.assertThat(bytes32).containsExactlyInOrder(0, 104, 6, 193, 0, 0, 0, 0, 0);

        byte[] bytes33 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 8, bytes33, 1)).isEqualTo(6);
        Assertions.assertThat(bytes33).containsExactlyInOrder(0, 104, 6, 193, 231, 166, 62, 0, 0);

        byte[] bytes41 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 4, 0, bytes41, 1)).isEqualTo(0);
        Assertions.assertThat(bytes41).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes42 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 4, 4, bytes42, 1)).isEqualTo(3);
        Assertions.assertThat(bytes42).containsExactlyInOrder(0, 231, 166, 62, 0, 0, 0, 0, 0);

        byte[] bytes51 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 0, bytes51, 3)).isEqualTo(0);
        Assertions.assertThat(bytes51).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes52 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 4, bytes52, 3)).isEqualTo(3);
        Assertions.assertThat(bytes52).containsExactlyInOrder(0, 0, 0, 104, 6, 193, 0, 0, 0);

        byte[] bytes53 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 8, bytes53, 3)).isEqualTo(6);
        Assertions.assertThat(bytes53).containsExactlyInOrder(0, 0, 0, 104, 6, 193, 231, 166, 62);

        byte[] bytes61 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 4, 0, bytes61, 3)).isEqualTo(0);
        Assertions.assertThat(bytes61).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes62 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 4, 4, bytes62, 3)).isEqualTo(3);
        Assertions.assertThat(bytes62).containsExactlyInOrder(0, 0, 0, 231, 166, 62, 0, 0, 0);

        byte[] bytes71 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aAbB56Y+", 0, 4, bytes71, 6)).isEqualTo(3);
        Assertions.assertThat(bytes71).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 104, 6, 193);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetAndNullStringTest() {
        Base64Helper.toBytes(null, 4, 4, new byte[9], 1);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetAndWrongStringLengthTest() {
        try {
            Base64Helper.toBytes("1234567", 4, 3, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (3)");
        }
        try {
            Base64Helper.toBytes("1234567", 4, 2, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (2)");
        }
        try {
            Base64Helper.toBytes("1234567", 4, 1, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetAndNegativeOffsetTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", -1, 4, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (-1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetAndNegativeLengthTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -1, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-1)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -2, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-2)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -3, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-3)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -4, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-4)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetAndTooLargeLengthTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 9, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (13)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 10, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (14)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 11, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (15)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 12, new byte[9], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (16)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetAndNullByteArrayTest() {
        Base64Helper.toBytes("aAbB56Y+", 4, 4, null, 1);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetAndInsufficientByteArrayLengthTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 4, new byte[3], 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array length (2), expected length is (3)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetAndNegativeByteArrayOffsetTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 4, new byte[9], -1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array index (-1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetAndTooLargeByteArrayOffsetTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 4, new byte[9], 10);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array index (10)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWithBase64BoundsAndByteArrayOffsetAndUpperBoundTest() {
        byte[] bytes1 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("", 0, 0, bytes1, 9)).isEqualTo(0);
        Assertions.assertThat(bytes1).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes2 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("1234aQ==", 0, 0, bytes2, 9)).isEqualTo(0);
        Assertions.assertThat(bytes2).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes3 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("1234aQ==", 2, 0, bytes3, 9)).isEqualTo(0);
        Assertions.assertThat(bytes3).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        byte[] bytes4 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("1234aQ==", 5, 0, bytes4, 9)).isEqualTo(0);
        Assertions.assertThat(bytes4).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 0);

        try {
            Base64Helper.toBytes("", 0, 0, new byte[9], 10);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array index (10)");
        }

        try {
            Base64Helper.toBytes("aQ==", 0, 4, new byte[9], 9);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong byte array length (0), expected length is (1)");
        }

        byte[] bytes5 = new byte[9];
        Assertions.assertThat(Base64Helper.toBytes("aQ==", 0, 4, bytes5, 8)).isEqualTo(1);
        Assertions.assertThat(bytes5).containsExactlyInOrder(0, 0, 0, 0, 0, 0, 0, 0, 105);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedTest() {
        byte[] bytes01 = Base64Helper.toBytes("");
        Assertions.assertThat(bytes01).containsExactlyInOrder();

        byte[] bytes02 = Base64Helper.toBytes("aAbB56Y+");
        Assertions.assertThat(bytes02).containsExactlyInOrder(104, 6, 193, 231, 166, 62);

        byte[] bytes03 = Base64Helper.toBytes("cZ+128/A");
        Assertions.assertThat(bytes03).containsExactlyInOrder(113, 159, 181, 219, 207, 192);

        byte[] bytes04 = Base64Helper.toBytes("lrIb74U+4fI9");
        Assertions.assertThat(bytes04).containsExactlyInOrder(150, 178, 27, 239, 133, 62, 225, 242, 61);

        byte[] bytes05 = Base64Helper.toBytes("TOF23Po=");
        Assertions.assertThat(bytes05).containsExactlyInOrder(76, 225, 118, 220, 250);

        byte[] bytes06 = Base64Helper.toBytes("++/2/9k=");
        Assertions.assertThat(bytes06).containsExactlyInOrder(251, 239, 246, 255, 217);

        byte[] bytes07 = Base64Helper.toBytes("2UFoe04P34o=");
        Assertions.assertThat(bytes07).containsExactlyInOrder(217, 65, 104, 123, 78, 15, 223, 138);

        byte[] bytes08 = Base64Helper.toBytes("3jrR+g==");
        Assertions.assertThat(bytes08).containsExactlyInOrder(222, 58, 209, 250);

        byte[] bytes09 = Base64Helper.toBytes("pRv01Q==");
        Assertions.assertThat(bytes09).containsExactlyInOrder(165, 27, 244, 213);

        byte[] bytes10 = Base64Helper.toBytes("65UTPB34uw==");
        Assertions.assertThat(bytes10).containsExactlyInOrder(235, 149, 19, 60, 29, 248, 187);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBytesCreatedWithNullStringTest() {
        Base64Helper.toBytes(null);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedWithWrongStringLengthTest() {
        try {
            Base64Helper.toBytes("1234567");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (7)");
        }
        try {
            Base64Helper.toBytes("123456");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (6)");
        }
        try {
            Base64Helper.toBytes("12345");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (5)");
        }
        try {
            Base64Helper.toBytes("123");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (3)");
        }
        try {
            Base64Helper.toBytes("12");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (2)");
        }
        try {
            Base64Helper.toBytes("1");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedWithBase64BoundsTest() {
        byte[] bytes11 = Base64Helper.toBytes("aAbB56Y+", 0, 0);
        Assertions.assertThat(bytes11).containsExactlyInOrder();

        byte[] bytes12 = Base64Helper.toBytes("aAbB56Y+", 0, 4);
        Assertions.assertThat(bytes12).containsExactlyInOrder(104, 6, 193);

        byte[] bytes13 = Base64Helper.toBytes("aAbB56Y+", 0, 8);
        Assertions.assertThat(bytes13).containsExactlyInOrder(104, 6, 193, 231, 166, 62);

        byte[] bytes21 = Base64Helper.toBytes("aAbB56Y+", 4, 0);
        Assertions.assertThat(bytes21).containsExactlyInOrder();

        byte[] bytes22 = Base64Helper.toBytes("aAbB56Y+", 4, 4);
        Assertions.assertThat(bytes22).containsExactlyInOrder(231, 166, 62);

        byte[] bytes31 = Base64Helper.toBytes("aAbB56Y+", 8, 0);
        Assertions.assertThat(bytes31).containsExactlyInOrder();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void toBytesCreatedWithBase64BoundsAndNullStringTest() {
        Base64Helper.toBytes(null, 4, 4);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedWithBase64BoundsAndWrongStringLengthTest() {
        try {
            Base64Helper.toBytes("1234567", 4, 3);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (3)");
        }
        try {
            Base64Helper.toBytes("1234567", 4, 2);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (2)");
        }
        try {
            Base64Helper.toBytes("1234567", 4, 1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedWithBase64BoundsAndNegativeOffsetTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", -1, 4);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (-1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedWithBase64BoundsAndNegativeLengthTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-1)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -2);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-2)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -3);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-3)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, -4);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-4)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesCreatedWithBase64BoundsAndTooLargeLengthTest() {
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 9);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (13)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 10);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (14)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 11);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (15)");
        }
        try {
            Base64Helper.toBytes("aAbB56Y+", 4, 12);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (16)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesWithWrongCharacterTest() {
        try {
            Base64Helper.toBytes(",+++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("+,++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("++,+");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("+++,");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes(",+++1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("+,++1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("++,+1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("+++,1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("1234,+++1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("1234+,++1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("1234++,+1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("1234+++,1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("12341234,+++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("12341234+,++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("12341234++,+");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("12341234+++,");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("12341234++,=");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("12341234+++=");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('+', 43)");
        }
        try {
            Base64Helper.toBytes("12341234+,==");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("12341234++==");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('+', 43)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesWithWrongPadPositionTest() {
        try {
            Base64Helper.toBytes("=+++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("+=++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("++=+");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('+', 43)");
        }
        try {
            Base64Helper.toBytes("+++=");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('+', 43)");
        }
        try {
            Base64Helper.toBytes("=+++1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("+=++1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("++=+1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("+++=1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("1234=+++1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("1234+=++1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("1234++=+1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("1234+++=1234");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("12341234=+++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            Base64Helper.toBytes("12341234+=++");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesWithWrongCharacterAfterPadTest() {
        try {
            Base64Helper.toBytes("++=+");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('+', 43)");
        }
        try {
            Base64Helper.toBytes("1234++=+");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('+', 43)");
        }
        try {
            Base64Helper.toBytes("++=,");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
        try {
            Base64Helper.toBytes("1234++=,");
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained (',', 44)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isBase64CharacterValidTest() {
        Assertions.assertThat(Base64Helper.isBase64CharacterValid(-2)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid(-1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid(0)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid(1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('a')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('A')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('z')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('Z')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('0')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('1')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('9')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('+')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('-')).isFalse();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('/')).isTrue();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('*')).isFalse();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid('=')).isFalse();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid(220)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid(122)).isTrue();
        Assertions.assertThat(Base64Helper.isBase64CharacterValid(123)).isFalse();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isSecondBase64ByteZeroTest() {
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero(0)).isFalse();
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero(1)).isFalse();
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
        Assertions.assertThat(Base64Helper.isSecondBase64ByteZero(122)).isFalse();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isThirdBase64ByteZeroTest() {
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero(0)).isFalse();
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero(1)).isFalse();
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
        Assertions.assertThat(Base64Helper.isThirdBase64ByteZero(122)).isFalse();
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
        Assertions.assertThat(Base64Helper.isBase64String("")).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String(",aaa")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("a,aa")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aa,a")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaa,")).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("12341234")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String(",aaa1234")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("a,aa1234")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aa,a1234")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaa,1234")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234,aaa")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234a,aa")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234aa,a")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234aaa,")).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("12kdId65")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("43093+df")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("KLdfLe+/")).isTrue();

        Assertions.assertThat(Base64Helper.isBase64String("aaQ=")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String(",aQ=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("a,Q=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aaa=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String(",aa=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("a,a=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aa,=")).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("1234aaQ=")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("1234,aQ=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234a,Q=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234aaa=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234,aa=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234a,a=")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234aa,=")).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("aQ==")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String(",Q==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aa==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String(",a==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("a,==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aQ=a")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("aQ=,")).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("1234aQ==")).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("1234,Q==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234aa==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234,a==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234a,==")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234aQ=a")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234aQ=,")).isFalse();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void isBase64StringWithNullStringTest() {
        Base64Helper.isBase64String(null);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isBase64StringWithWrongStringLengthTest() {
        Assertions.assertThat(Base64Helper.isBase64String("+")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("++")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("+++")).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("1234+")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234++")).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("1234+++")).isFalse();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isBase64StringWithBoundsTest() {
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 0, 0)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 0, 1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 0, 2)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 0, 3)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 0, 4)).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 0, 5)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 0, 6)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 0, 7)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 0, 8)).isTrue();

        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 1, 0)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 1, 1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 1, 2)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 1, 3)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 1, 4)).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 1, 5)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 1, 6)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 1, 7)).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 2, 0)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 2, 1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 2, 2)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 2, 3)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 2, 4)).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 2, 5)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 2, 6)).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 3, 0)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 3, 1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 3, 2)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 3, 3)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 3, 4)).isTrue();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 3, 5)).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 4, 0)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 4, 1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 4, 2)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 4, 3)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 4, 4)).isTrue();

        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 5, 0)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 5, 1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 5, 2)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 5, 3)).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 6, 0)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 6, 1)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 6, 2)).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 7, 0)).isFalse();
        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 7, 1)).isFalse();

        Assertions.assertThat(Base64Helper.isBase64String("12kdId65", 8, 0)).isFalse();
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void isBase64StringWithBoundsAndNullStringTest() {
        Base64Helper.isBase64String(null, 0, 4);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isBase64StringWithBoundsAndNegativeOffsetTest() {
        try {
            Base64Helper.isBase64String("12kdId65", -1, 4);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (-1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isBase64StringWithBoundsAndNegativeLengthTest() {
        try {
            Base64Helper.isBase64String("12kdId65", 0, -1);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string length (-1)");
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void isBase64StringWithBoundsAndTooLargeLengthTest() {
        try {
            Base64Helper.isBase64String("12kdId65", 0, 9);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (9)");
        }
        try {
            Base64Helper.isBase64String("12kdId65", 0, 10);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (10)");
        }
        try {
            Base64Helper.isBase64String("12kdId65", 0, 11);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (11)");
        }
        try {
            Base64Helper.isBase64String("12kdId65", 0, 12);
            Assertions.fail("Base64Helper test fail");
        } catch (Base64RuntimeException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong base64 string index (12)");
        }
    }

}
