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
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(ExceptionMessageHelper.class).hasOnePrivateConstructor();
    }

    /**
     * {@link ExceptionMessageHelper} class test.
     */
    @Test
    public void createMessageTest() {
        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64StringSizeMessage(10)).isEqualTo("Wrong number of symbols in base64 string (10)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64StringSizeMessage(13)).isEqualTo("Wrong number of symbols in base64 string (13)");

        Assertions.assertThat(ExceptionMessageHelper.createWrongResultArrayMessage(16, 13)).isEqualTo("Result array is too small for base64 string (13), expected size is (16)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongResultArrayMessage(20, 9)).isEqualTo("Result array is too small for base64 string (9), expected size is (20)");

        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64Symbol('-')).isEqualTo("Wrong symbol obtained: '-' (45)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64Symbol('!')).isEqualTo("Wrong symbol obtained: '!' (33)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64Symbol('#')).isEqualTo("Wrong symbol obtained: '#' (35)");

        Assertions.assertThat(ExceptionMessageHelper.createEndOfStreamMessage()).isEqualTo("Unexpected end of stream");
    }

}
