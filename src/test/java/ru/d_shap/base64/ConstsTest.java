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
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(Consts.class).hasOnePrivateConstructor();
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void lengthTest() {
        Assertions.assertThat(Consts.TO_BASE64).hasLength(64);
        Assertions.assertThat(Consts.TO_BASE64_FIRST_CHARACTER).hasLength(256);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_1).hasLength(256);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_2).hasLength(256);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_1).hasLength(256);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_2).hasLength(256);
        Assertions.assertThat(Consts.TO_BASE64_FOURTH_CHARACTER).hasLength(256);
        Assertions.assertThat(Consts.FROM_BASE64).hasLength(123);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1).hasLength(123);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2).hasLength(123);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1).hasLength(123);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2).hasLength(123);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1).hasLength(123);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2).hasLength(123);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void valueConsistencyTest() {
        for (int i = 0; i < Consts.TO_BASE64.length; i++) {
            int toBase64Value = Consts.TO_BASE64[i];
            int fromBase64Value = Consts.FROM_BASE64[toBase64Value];
            Assertions.assertThat(fromBase64Value).isEqualTo(i);
        }
        for (int i = 0; i < Consts.FROM_BASE64.length; i++) {
            int fromBase64Value = Consts.FROM_BASE64[i];
            Assertions.assertThat(fromBase64Value == -1 || Consts.TO_BASE64[fromBase64Value] == i).isTrue();
        }
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void toBase64FirstCharacterTest() {
        Assertions.assertThat(Consts.TO_BASE64_FIRST_CHARACTER[0xFF]).isEqualTo('/');
        Assertions.assertThat(Consts.TO_BASE64_FIRST_CHARACTER[0xFD]).isEqualTo('/');
        Assertions.assertThat(Consts.TO_BASE64_FIRST_CHARACTER[0xAA]).isEqualTo('q');
        Assertions.assertThat(Consts.TO_BASE64_FIRST_CHARACTER[0xF0]).isEqualTo('8');
        Assertions.assertThat(Consts.TO_BASE64_FIRST_CHARACTER[0xF3]).isEqualTo('8');
        Assertions.assertThat(Consts.TO_BASE64_FIRST_CHARACTER[0x0C]).isEqualTo('D');
        Assertions.assertThat(Consts.TO_BASE64_FIRST_CHARACTER[0x0D]).isEqualTo('D');
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void toBase64SecondCharacterIndexTest() {
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_1[0xFF]).isEqualTo(0x30);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_1[0xFD]).isEqualTo(0x10);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_1[0xAA]).isEqualTo(0x20);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_1[0xF0]).isEqualTo(0x00);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_1[0xF3]).isEqualTo(0x30);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_1[0x0D]).isEqualTo(0x10);

        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_2[0xFF]).isEqualTo(0x0F);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_2[0xFD]).isEqualTo(0x0F);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_2[0xAA]).isEqualTo(0x0A);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_2[0xF0]).isEqualTo(0x0F);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_2[0xF3]).isEqualTo(0x0F);
        Assertions.assertThat(Consts.TO_BASE64_SECOND_CHARACTER_INDEX_2[0x0D]).isEqualTo(0x00);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void toBase64ThirdCharacterIndexTest() {
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_1[0xFF]).isEqualTo(0x3C);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_1[0xFD]).isEqualTo(0x34);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_1[0xAA]).isEqualTo(0x28);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_1[0xF0]).isEqualTo(0x00);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_1[0xF3]).isEqualTo(0x0C);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_1[0x0D]).isEqualTo(0x34);

        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_2[0xFF]).isEqualTo(0x03);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_2[0xFD]).isEqualTo(0x03);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_2[0xAA]).isEqualTo(0x02);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_2[0xF0]).isEqualTo(0x03);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_2[0xF3]).isEqualTo(0x03);
        Assertions.assertThat(Consts.TO_BASE64_THIRD_CHARACTER_INDEX_2[0x0D]).isEqualTo(0x00);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void toBase64FourthCharacterTest() {
        Assertions.assertThat(Consts.TO_BASE64_FOURTH_CHARACTER[0xFF]).isEqualTo('/');
        Assertions.assertThat(Consts.TO_BASE64_FOURTH_CHARACTER[0xFD]).isEqualTo('9');
        Assertions.assertThat(Consts.TO_BASE64_FOURTH_CHARACTER[0xAA]).isEqualTo('q');
        Assertions.assertThat(Consts.TO_BASE64_FOURTH_CHARACTER[0xF0]).isEqualTo('w');
        Assertions.assertThat(Consts.TO_BASE64_FOURTH_CHARACTER[0xF3]).isEqualTo('z');
        Assertions.assertThat(Consts.TO_BASE64_FOURTH_CHARACTER[0x0C]).isEqualTo('M');
        Assertions.assertThat(Consts.TO_BASE64_FOURTH_CHARACTER[0x0D]).isEqualTo('N');
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void fromBase64FirstByteTest() {
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1['f']).isEqualTo(0x7C);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1['E']).isEqualTo(0x10);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1['x']).isEqualTo(0xC4);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1['X']).isEqualTo(0x5C);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1['1']).isEqualTo(0xD4);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1['5']).isEqualTo(0xE4);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1['+']).isEqualTo(0xF8);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1['/']).isEqualTo(0xFC);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1[5]).isEqualTo(-1);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_1[10]).isEqualTo(-1);

        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2['f']).isEqualTo(0x01);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2['E']).isEqualTo(0x00);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2['x']).isEqualTo(0x03);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2['X']).isEqualTo(0x01);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2['1']).isEqualTo(0x03);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2['5']).isEqualTo(0x03);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2['+']).isEqualTo(0x03);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2['/']).isEqualTo(0x03);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2[5]).isEqualTo(-1);
        Assertions.assertThat(Consts.FROM_BASE64_FIRST_BYTE_2[10]).isEqualTo(-1);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void fromBase64SecondByteTest() {
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1['f']).isEqualTo(0xF0);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1['E']).isEqualTo(0x40);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1['x']).isEqualTo(0x10);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1['X']).isEqualTo(0x70);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1['1']).isEqualTo(0x50);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1['5']).isEqualTo(0x90);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1['+']).isEqualTo(0xE0);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1['/']).isEqualTo(0xF0);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1[5]).isEqualTo(-1);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_1[10]).isEqualTo(-1);

        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2['f']).isEqualTo(0x07);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2['E']).isEqualTo(0x01);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2['x']).isEqualTo(0x0C);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2['X']).isEqualTo(0x05);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2['1']).isEqualTo(0x0D);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2['5']).isEqualTo(0x0E);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2['+']).isEqualTo(0x0F);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2['/']).isEqualTo(0x0F);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2[5]).isEqualTo(-1);
        Assertions.assertThat(Consts.FROM_BASE64_SECOND_BYTE_2[10]).isEqualTo(-1);
    }

    /**
     * {@link Consts} class test.
     */
    @Test
    public void fromBase64ThirdByteTest() {
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1['f']).isEqualTo(0xC0);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1['E']).isEqualTo(0x00);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1['x']).isEqualTo(0x40);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1['X']).isEqualTo(0xC0);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1['1']).isEqualTo(0x40);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1['5']).isEqualTo(0x40);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1['+']).isEqualTo(0x80);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1['/']).isEqualTo(0xC0);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1[5]).isEqualTo(-1);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_1[10]).isEqualTo(-1);

        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2['f']).isEqualTo(0x1F);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2['E']).isEqualTo(0x04);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2['x']).isEqualTo(0x31);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2['X']).isEqualTo(0x17);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2['1']).isEqualTo(0x35);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2['5']).isEqualTo(0x39);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2['+']).isEqualTo(0x3E);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2['/']).isEqualTo(0x3F);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2[5]).isEqualTo(-1);
        Assertions.assertThat(Consts.FROM_BASE64_THIRD_BYTE_2[10]).isEqualTo(-1);
    }

}
