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
 * Tests for {@link Consts}.
 *
 * @author Dmitry Shapovalov
 */
public final class ConstsTest {

    /**
     * Test class constructor.
     */
    public ConstsTest() {
        super();
    }

    /**
     * {@link Consts} class test.
     *
     * @throws IllegalAccessException exception in test.
     * @throws InstantiationException exception in test.
     */
    @Test(expected = IllegalAccessException.class)
    public void constructorPrivateTest() throws IllegalAccessException, InstantiationException {
        Consts.class.newInstance();
    }

    /**
     * {@link Consts} class test.
     *
     * @throws IllegalAccessException    exception in test.
     * @throws InstantiationException    exception in test.
     * @throws InvocationTargetException exception in test.
     */
    @Test
    public void constructorInaccessibleTest() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor[] ctors = Consts.class.getDeclaredConstructors();
        Assert.assertEquals(1, ctors.length);
        Constructor ctor = ctors[0];
        Assert.assertFalse(ctor.isAccessible());
        ctor.setAccessible(true);
        Assert.assertEquals(Consts.class, ctor.newInstance().getClass());
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void valueConsistencyTest() {
        for (int i = 0; i < Consts.TO_BASE64.length; i++) {
            int toBase64Value = Consts.TO_BASE64[i];
            int fromBase64Value = Consts.FROM_BASE64[toBase64Value];
            Assert.assertEquals(fromBase64Value, i);
        }
        for (int i = 0; i < Consts.FROM_BASE64.length; i++) {
            int fromBase64Value = Consts.FROM_BASE64[i];
            Assert.assertTrue(fromBase64Value == -1 || Consts.TO_BASE64[fromBase64Value] == i);
        }
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void toBase64FirstSymbolTest() {
        Assert.assertEquals('/', Consts.TO_BASE64_FIRST_SYMBOL[0xFF]);
        Assert.assertEquals('/', Consts.TO_BASE64_FIRST_SYMBOL[0xFD]);
        Assert.assertEquals('q', Consts.TO_BASE64_FIRST_SYMBOL[0xAA]);
        Assert.assertEquals('8', Consts.TO_BASE64_FIRST_SYMBOL[0xF0]);
        Assert.assertEquals('8', Consts.TO_BASE64_FIRST_SYMBOL[0xF3]);
        Assert.assertEquals('D', Consts.TO_BASE64_FIRST_SYMBOL[0x0C]);
        Assert.assertEquals('D', Consts.TO_BASE64_FIRST_SYMBOL[0x0D]);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void toBase64SecondSymbolIndexTest() {
        Assert.assertEquals(0x30, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_1[0xFF]);
        Assert.assertEquals(0x10, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_1[0xFD]);
        Assert.assertEquals(0x20, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_1[0xAA]);
        Assert.assertEquals(0x00, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_1[0xF0]);
        Assert.assertEquals(0x30, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_1[0xF3]);
        Assert.assertEquals(0x10, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_1[0x0D]);

        Assert.assertEquals(0x0F, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_2[0xFF]);
        Assert.assertEquals(0x0F, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_2[0xFD]);
        Assert.assertEquals(0x0A, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_2[0xAA]);
        Assert.assertEquals(0x0F, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_2[0xF0]);
        Assert.assertEquals(0x0F, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_2[0xF3]);
        Assert.assertEquals(0x00, Consts.TO_BASE64_SECOND_SYMBOL_INDEX_2[0x0D]);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void toBase64ThirdSymbolIndexTest() {
        Assert.assertEquals(0x3C, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_1[0xFF]);
        Assert.assertEquals(0x34, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_1[0xFD]);
        Assert.assertEquals(0x28, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_1[0xAA]);
        Assert.assertEquals(0x00, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_1[0xF0]);
        Assert.assertEquals(0x0C, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_1[0xF3]);
        Assert.assertEquals(0x34, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_1[0x0D]);

        Assert.assertEquals(0x03, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_2[0xFF]);
        Assert.assertEquals(0x03, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_2[0xFD]);
        Assert.assertEquals(0x02, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_2[0xAA]);
        Assert.assertEquals(0x03, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_2[0xF0]);
        Assert.assertEquals(0x03, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_2[0xF3]);
        Assert.assertEquals(0x00, Consts.TO_BASE64_THIRD_SYMBOL_INDEX_2[0x0D]);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void toBase64FourthSymbolTest() {
        Assert.assertEquals('/', Consts.TO_BASE64_FOURTH_SYMBOL[0xFF]);
        Assert.assertEquals('9', Consts.TO_BASE64_FOURTH_SYMBOL[0xFD]);
        Assert.assertEquals('q', Consts.TO_BASE64_FOURTH_SYMBOL[0xAA]);
        Assert.assertEquals('w', Consts.TO_BASE64_FOURTH_SYMBOL[0xF0]);
        Assert.assertEquals('z', Consts.TO_BASE64_FOURTH_SYMBOL[0xF3]);
        Assert.assertEquals('M', Consts.TO_BASE64_FOURTH_SYMBOL[0x0C]);
        Assert.assertEquals('N', Consts.TO_BASE64_FOURTH_SYMBOL[0x0D]);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void fromBase64FirstByteTest() {
        Assert.assertEquals(0x7C, Consts.FROM_BASE64_FIRST_BYTE_1['f']);
        Assert.assertEquals(0x10, Consts.FROM_BASE64_FIRST_BYTE_1['E']);
        Assert.assertEquals(0xC4, Consts.FROM_BASE64_FIRST_BYTE_1['x']);
        Assert.assertEquals(0x5C, Consts.FROM_BASE64_FIRST_BYTE_1['X']);
        Assert.assertEquals(0xD4, Consts.FROM_BASE64_FIRST_BYTE_1['1']);
        Assert.assertEquals(0xE4, Consts.FROM_BASE64_FIRST_BYTE_1['5']);
        Assert.assertEquals(0xF8, Consts.FROM_BASE64_FIRST_BYTE_1['+']);
        Assert.assertEquals(0xFC, Consts.FROM_BASE64_FIRST_BYTE_1['/']);
        Assert.assertEquals(-1, Consts.FROM_BASE64_FIRST_BYTE_1[5]);
        Assert.assertEquals(-1, Consts.FROM_BASE64_FIRST_BYTE_1[10]);

        Assert.assertEquals(0x01, Consts.FROM_BASE64_FIRST_BYTE_2['f']);
        Assert.assertEquals(0x00, Consts.FROM_BASE64_FIRST_BYTE_2['E']);
        Assert.assertEquals(0x03, Consts.FROM_BASE64_FIRST_BYTE_2['x']);
        Assert.assertEquals(0x01, Consts.FROM_BASE64_FIRST_BYTE_2['X']);
        Assert.assertEquals(0x03, Consts.FROM_BASE64_FIRST_BYTE_2['1']);
        Assert.assertEquals(0x03, Consts.FROM_BASE64_FIRST_BYTE_2['5']);
        Assert.assertEquals(0x03, Consts.FROM_BASE64_FIRST_BYTE_2['+']);
        Assert.assertEquals(0x03, Consts.FROM_BASE64_FIRST_BYTE_2['/']);
        Assert.assertEquals(-1, Consts.FROM_BASE64_FIRST_BYTE_2[5]);
        Assert.assertEquals(-1, Consts.FROM_BASE64_FIRST_BYTE_2[10]);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void fromBase64SecondByteTest() {
        Assert.assertEquals(0xF0, Consts.FROM_BASE64_SECOND_BYTE_1['f']);
        Assert.assertEquals(0x40, Consts.FROM_BASE64_SECOND_BYTE_1['E']);
        Assert.assertEquals(0x10, Consts.FROM_BASE64_SECOND_BYTE_1['x']);
        Assert.assertEquals(0x70, Consts.FROM_BASE64_SECOND_BYTE_1['X']);
        Assert.assertEquals(0x50, Consts.FROM_BASE64_SECOND_BYTE_1['1']);
        Assert.assertEquals(0x90, Consts.FROM_BASE64_SECOND_BYTE_1['5']);
        Assert.assertEquals(0xE0, Consts.FROM_BASE64_SECOND_BYTE_1['+']);
        Assert.assertEquals(0xF0, Consts.FROM_BASE64_SECOND_BYTE_1['/']);
        Assert.assertEquals(-1, Consts.FROM_BASE64_SECOND_BYTE_1[5]);
        Assert.assertEquals(-1, Consts.FROM_BASE64_SECOND_BYTE_1[10]);

        Assert.assertEquals(0x07, Consts.FROM_BASE64_SECOND_BYTE_2['f']);
        Assert.assertEquals(0x01, Consts.FROM_BASE64_SECOND_BYTE_2['E']);
        Assert.assertEquals(0x0C, Consts.FROM_BASE64_SECOND_BYTE_2['x']);
        Assert.assertEquals(0x05, Consts.FROM_BASE64_SECOND_BYTE_2['X']);
        Assert.assertEquals(0x0D, Consts.FROM_BASE64_SECOND_BYTE_2['1']);
        Assert.assertEquals(0x0E, Consts.FROM_BASE64_SECOND_BYTE_2['5']);
        Assert.assertEquals(0x0F, Consts.FROM_BASE64_SECOND_BYTE_2['+']);
        Assert.assertEquals(0x0F, Consts.FROM_BASE64_SECOND_BYTE_2['/']);
        Assert.assertEquals(-1, Consts.FROM_BASE64_SECOND_BYTE_2[5]);
        Assert.assertEquals(-1, Consts.FROM_BASE64_SECOND_BYTE_2[10]);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void fromBase64ThirdByteTest() {
        Assert.assertEquals(0xC0, Consts.FROM_BASE64_THIRD_BYTE_1['f']);
        Assert.assertEquals(0x00, Consts.FROM_BASE64_THIRD_BYTE_1['E']);
        Assert.assertEquals(0x40, Consts.FROM_BASE64_THIRD_BYTE_1['x']);
        Assert.assertEquals(0xC0, Consts.FROM_BASE64_THIRD_BYTE_1['X']);
        Assert.assertEquals(0x40, Consts.FROM_BASE64_THIRD_BYTE_1['1']);
        Assert.assertEquals(0x40, Consts.FROM_BASE64_THIRD_BYTE_1['5']);
        Assert.assertEquals(0x80, Consts.FROM_BASE64_THIRD_BYTE_1['+']);
        Assert.assertEquals(0xC0, Consts.FROM_BASE64_THIRD_BYTE_1['/']);
        Assert.assertEquals(-1, Consts.FROM_BASE64_THIRD_BYTE_1[5]);
        Assert.assertEquals(-1, Consts.FROM_BASE64_THIRD_BYTE_1[10]);

        Assert.assertEquals(0x1F, Consts.FROM_BASE64_THIRD_BYTE_2['f']);
        Assert.assertEquals(0x04, Consts.FROM_BASE64_THIRD_BYTE_2['E']);
        Assert.assertEquals(0x31, Consts.FROM_BASE64_THIRD_BYTE_2['x']);
        Assert.assertEquals(0x17, Consts.FROM_BASE64_THIRD_BYTE_2['X']);
        Assert.assertEquals(0x35, Consts.FROM_BASE64_THIRD_BYTE_2['1']);
        Assert.assertEquals(0x39, Consts.FROM_BASE64_THIRD_BYTE_2['5']);
        Assert.assertEquals(0x3E, Consts.FROM_BASE64_THIRD_BYTE_2['+']);
        Assert.assertEquals(0x3F, Consts.FROM_BASE64_THIRD_BYTE_2['/']);
        Assert.assertEquals(-1, Consts.FROM_BASE64_THIRD_BYTE_2[5]);
        Assert.assertEquals(-1, Consts.FROM_BASE64_THIRD_BYTE_2[10]);
    }

}
