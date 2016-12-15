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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

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
     *
     * @throws IllegalAccessException exception in test.
     * @throws InstantiationException exception in test.
     */
    @Test(expected = IllegalAccessException.class)
    public void constructorPrivateTest() throws IllegalAccessException, InstantiationException {
        Base64Helper.class.newInstance();
    }

    /**
     * {@link Base64Helper} class test.
     *
     * @throws IllegalAccessException    exception in test.
     * @throws InstantiationException    exception in test.
     * @throws InvocationTargetException exception in test.
     */
    @Test
    public void constructorInaccessibleTest() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor[] ctors = Base64Helper.class.getDeclaredConstructors();
        Assert.assertEquals(1, ctors.length);
        Constructor ctor = ctors[0];
        Assert.assertFalse(ctor.isAccessible());
        ctor.setAccessible(true);
        Assert.assertEquals(Base64Helper.class, ctor.newInstance().getClass());
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64EmptyArrayTest() {
        Assert.assertEquals("", Base64Helper.toBase64(null));
        Assert.assertEquals("", Base64Helper.toBase64(new byte[0]));
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64ZeroByteEndingTest() {
        Assert.assertEquals("qszh6BMN", Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19, 13}));
        Assert.assertEquals("Mx0phUUD", Base64Helper.toBase64(new byte[]{51, 29, 41, (byte) 133, 69, 3}));
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64OneByteEndingTest() {
        Assert.assertEquals("qszh6A==", Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232}));
        Assert.assertEquals("Mx0phQ==", Base64Helper.toBase64(new byte[]{51, 29, 41, (byte) 133}));
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBase64TwoByteEndingTest() {
        Assert.assertEquals("qszh6BM=", Base64Helper.toBase64(new byte[]{(byte) 170, (byte) 204, (byte) 225, (byte) 232, 19}));
        Assert.assertEquals("Mx0phUU=", Base64Helper.toBase64(new byte[]{51, 29, 41, (byte) 133, 69}));
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getFirstBase64SymbolTest() {
        Assert.assertEquals('A', Base64Helper.getFirstBase64Symbol(0x00));
        Assert.assertEquals('A', Base64Helper.getFirstBase64Symbol(0x03));
        Assert.assertEquals('p', Base64Helper.getFirstBase64Symbol(0xA7));
        Assert.assertEquals('k', Base64Helper.getFirstBase64Symbol(0x92));
        Assert.assertEquals('O', Base64Helper.getFirstBase64Symbol(0x3B));
        Assert.assertEquals('P', Base64Helper.getFirstBase64Symbol(0x3C));
        Assert.assertEquals('P', Base64Helper.getFirstBase64Symbol(0x3D));
        Assert.assertEquals('P', Base64Helper.getFirstBase64Symbol(0x3E));
        Assert.assertEquals('P', Base64Helper.getFirstBase64Symbol(0x3F));
        Assert.assertEquals('Q', Base64Helper.getFirstBase64Symbol(0x40));
        Assert.assertEquals('0', Base64Helper.getFirstBase64Symbol(0xD0));
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getSecondBase64SymbolTest() {
        Assert.assertEquals('A', Base64Helper.getSecondBase64Symbol(0x00, 0x00));
        Assert.assertEquals('v', Base64Helper.getSecondBase64Symbol(0xA2, 0xF0));
        Assert.assertEquals('v', Base64Helper.getSecondBase64Symbol(0xA2, 0xF5));
        Assert.assertEquals('v', Base64Helper.getSecondBase64Symbol(0xA2, 0xFF));
        Assert.assertEquals('v', Base64Helper.getSecondBase64Symbol(0x02, 0xFF));
        Assert.assertEquals('v', Base64Helper.getSecondBase64Symbol(0x92, 0xFF));
        Assert.assertEquals('h', Base64Helper.getSecondBase64Symbol(0x12, 0x19));
        Assert.assertEquals('/', Base64Helper.getSecondBase64Symbol(0xA3, 0xF2));
        Assert.assertEquals('8', Base64Helper.getSecondBase64Symbol(0xA3, 0xC2));
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getThirdBase64SymbolTest() {
        Assert.assertEquals('A', Base64Helper.getThirdBase64Symbol(0x00, 0x00));
        Assert.assertEquals('8', Base64Helper.getThirdBase64Symbol(0x0F, 0x0F));
        Assert.assertEquals('8', Base64Helper.getThirdBase64Symbol(0x0F, 0x3A));
        Assert.assertEquals('8', Base64Helper.getThirdBase64Symbol(0x0F, 0x17));
        Assert.assertEquals('8', Base64Helper.getThirdBase64Symbol(0x2F, 0x17));
        Assert.assertEquals('8', Base64Helper.getThirdBase64Symbol(0xAF, 0x17));
        Assert.assertEquals('R', Base64Helper.getThirdBase64Symbol(0x74, 0x6A));
        Assert.assertEquals('D', Base64Helper.getThirdBase64Symbol(0x90, 0xC1));
        Assert.assertEquals('a', Base64Helper.getThirdBase64Symbol(0xF6, 0x89));
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void getFourthBase64SymbolTest() {
        Assert.assertEquals('A', Base64Helper.getFourthBase64Symbol(0x00));
        Assert.assertEquals('N', Base64Helper.getFourthBase64Symbol(0xCD));
        Assert.assertEquals('N', Base64Helper.getFourthBase64Symbol(0x8D));
        Assert.assertEquals('N', Base64Helper.getFourthBase64Symbol(0x4D));
        Assert.assertEquals('N', Base64Helper.getFourthBase64Symbol(0x0D));
        Assert.assertEquals('/', Base64Helper.getFourthBase64Symbol(0x3F));
        Assert.assertEquals('l', Base64Helper.getFourthBase64Symbol(0x25));
        Assert.assertEquals('a', Base64Helper.getFourthBase64Symbol(0x9A));
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedZeroByteEndingTest() {
        byte[] bytes = new byte[9];

        Assert.assertEquals(6, Base64Helper.toBytes("aAbB56Y+", bytes));
        Assert.assertArrayEquals(new byte[]{104, 6, (byte) 193, (byte) 231, (byte) 166, 62, 0, 0, 0}, bytes);

        Assert.assertEquals(6, Base64Helper.toBytes("cZ+128/A", bytes));
        Assert.assertArrayEquals(new byte[]{113, (byte) 159, (byte) 181, (byte) 219, (byte) 207, (byte) 192, 0, 0, 0}, bytes);

        Assert.assertEquals(9, Base64Helper.toBytes("lrIb74U+4fI9", bytes));
        Assert.assertArrayEquals(new byte[]{(byte) 150, (byte) 178, 27, (byte) 239, (byte) 133, 62, (byte) 225, (byte) 242, 61}, bytes);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedOneByteEndingTest() {
        byte[] bytes = new byte[7];

        Assert.assertEquals(4, Base64Helper.toBytes("3jrR+g==", bytes));
        Assert.assertArrayEquals(new byte[]{(byte) 222, 58, (byte) 209, (byte) 250, 0, 0, 0}, bytes);

        Assert.assertEquals(4, Base64Helper.toBytes("pRv01Q==", bytes));
        Assert.assertArrayEquals(new byte[]{(byte) 165, 27, (byte) 244, (byte) 213, 0, 0, 0}, bytes);

        Assert.assertEquals(7, Base64Helper.toBytes("65UTPB34uw==", bytes));
        Assert.assertArrayEquals(new byte[]{(byte) 235, (byte) 149, 19, 60, 29, (byte) 248, (byte) 187}, bytes);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedTwoByteEndingTest() {
        byte[] bytes = new byte[8];

        Assert.assertEquals(5, Base64Helper.toBytes("TOF23Po=", bytes));
        Assert.assertArrayEquals(new byte[]{76, (byte) 225, 118, (byte) 220, (byte) 250, 0, 0, 0}, bytes);

        Assert.assertEquals(5, Base64Helper.toBytes("++/2/9k=", bytes));
        Assert.assertArrayEquals(new byte[]{(byte) 251, (byte) 239, (byte) 246, (byte) 255, (byte) 217, 0, 0, 0}, bytes);

        Assert.assertEquals(8, Base64Helper.toBytes("2UFoe04P34o=", bytes));
        Assert.assertArrayEquals(new byte[]{(byte) 217, 65, 104, 123, 78, 15, (byte) 223, (byte) 138}, bytes);
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedEmptyHexTest() {
        Assert.assertEquals(0, Base64Helper.toBytes(null, new byte[3]));
        Assert.assertEquals(0, Base64Helper.toBytes("", new byte[3]));
    }


    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedWrongLengthTest() {
        try {
            Base64Helper.toBytes("1234567", new byte[6]);
            Assert.fail("Length check is wrong");
        } catch (Base64RuntimeException ex) {
            Assert.assertEquals("Wrong number of symbols in base64 string (7)", ex.getMessage());
        }
        try {
            Base64Helper.toBytes("123456", new byte[6]);
            Assert.fail("Length check is wrong");
        } catch (Base64RuntimeException ex) {
            Assert.assertEquals("Wrong number of symbols in base64 string (6)", ex.getMessage());
        }
        try {
            Base64Helper.toBytes("12345", new byte[6]);
            Assert.fail("Length check is wrong");
        } catch (Base64RuntimeException ex) {
            Assert.assertEquals("Wrong number of symbols in base64 string (5)", ex.getMessage());
        }
    }

    /**
     * {@link Base64Helper} class test.
     */
    @Test
    public void toBytesSpecifiedInsufficientResultLengthTest() {
        try {
            Base64Helper.toBytes("12345678", new byte[5]);
            Assert.fail("Array size check fail");
        } catch (Base64RuntimeException ex) {
            Assert.assertEquals("Result array is too small for base64 string (5), expected size is (6)", ex.getMessage());
        }
    }

}
