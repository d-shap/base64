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
        Assertions.assertThat(ExceptionMessageHelper.createWrongByteArrayIndexMessage(-1)).isEqualTo("Wrong byte array index (-1)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongByteArrayIndexMessage(17)).isEqualTo("Wrong byte array index (17)");

        Assertions.assertThat(ExceptionMessageHelper.createWrongByteArrayLengthMessage(-1)).isEqualTo("Wrong byte array length (-1)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongByteArrayLengthMessage(17)).isEqualTo("Wrong byte array length (17)");

        Assertions.assertThat(ExceptionMessageHelper.createWrongByteArrayLengthMessage(13, 16)).isEqualTo("Wrong byte array length (13), expected length is (16)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongByteArrayLengthMessage(9, 20)).isEqualTo("Wrong byte array length (9), expected length is (20)");

        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64StringIndexMessage(-1)).isEqualTo("Wrong base64 string index (-1)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64StringIndexMessage(17)).isEqualTo("Wrong base64 string index (17)");

        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64StringLengthMessage(-1)).isEqualTo("Wrong base64 string length (-1)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64StringLengthMessage(17)).isEqualTo("Wrong base64 string length (17)");

        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64StringCharacterMessage('-')).isEqualTo("Wrong character obtained ('-', 45)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64StringCharacterMessage('!')).isEqualTo("Wrong character obtained ('!', 33)");
        Assertions.assertThat(ExceptionMessageHelper.createWrongBase64StringCharacterMessage('#')).isEqualTo("Wrong character obtained ('#', 35)");

        Assertions.assertThat(ExceptionMessageHelper.createEndOfStreamMessage()).isEqualTo("Unexpected end of stream");
    }

}
