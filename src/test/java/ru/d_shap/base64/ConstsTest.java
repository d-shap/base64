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
            int base64Value = Consts.TO_BASE64[i];
            int value = Consts.FROM_BASE64[base64Value];
            Assert.assertEquals(value, i);
        }
        for (int i = 0; i < Consts.FROM_BASE64.length; i++) {
            int value = Consts.FROM_BASE64[i];
            Assert.assertTrue(value == -1 || Consts.TO_BASE64[value] == i);
        }
    }

}