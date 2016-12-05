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
 * Tests for {@link ExceptionMessageHelper}.
 *
 * @author Dmitry Shapovalov
 */
public final class ExceptionMessageHelperTest {

    /**
     * Test class constructor.
     */
    public ExceptionMessageHelperTest() {
        super();
    }

    /**
     * {@link ExceptionMessageHelper} class test.
     *
     * @throws IllegalAccessException exception in test.
     * @throws InstantiationException exception in test.
     */
    @Test(expected = IllegalAccessException.class)
    public void constructorPrivateTest() throws IllegalAccessException, InstantiationException {
        ExceptionMessageHelper.class.newInstance();
    }

    /**
     * {@link ExceptionMessageHelper} class test.
     *
     * @throws IllegalAccessException    exception in test.
     * @throws InstantiationException    exception in test.
     * @throws InvocationTargetException exception in test.
     */
    @Test
    public void constructorInaccessibleTest() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor[] ctors = ExceptionMessageHelper.class.getDeclaredConstructors();
        Assert.assertEquals(1, ctors.length);
        Constructor ctor = ctors[0];
        Assert.assertFalse(ctor.isAccessible());
        ctor.setAccessible(true);
        Assert.assertEquals(ExceptionMessageHelper.class, ctor.newInstance().getClass());
    }

    /**
     * {@link ExceptionMessageHelper} class test.
     */
    @Test
    public void createMessageTest() {
        Assert.assertEquals("Wrong number of symbols in base64 string (10)", ExceptionMessageHelper.createWrongBase64StringSizeMessage(10));
        Assert.assertEquals("Wrong number of symbols in base64 string (13)", ExceptionMessageHelper.createWrongBase64StringSizeMessage(13));

        Assert.assertEquals("Result array is too small for base64 string (13), expected size is (16)", ExceptionMessageHelper.createWrongResultArrayMessage(16, 13));
        Assert.assertEquals("Result array is too small for base64 string (9), expected size is (20)", ExceptionMessageHelper.createWrongResultArrayMessage(20, 9));

        Assert.assertEquals("Wrong symbol obtained: '-' (45)", ExceptionMessageHelper.createWrongBase64Symbol('-'));
        Assert.assertEquals("Wrong symbol obtained: '!' (33)", ExceptionMessageHelper.createWrongBase64Symbol('!'));
        Assert.assertEquals("Wrong symbol obtained: '#' (35)", ExceptionMessageHelper.createWrongBase64Symbol('#'));

        Assert.assertEquals("Unexpected end of stream", ExceptionMessageHelper.createEndOfStreamMessage());
    }

}
