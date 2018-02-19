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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link Base64InputStream}.
 *
 * @author Dmitry Shapovalov
 */
public final class Base64InputStreamTest {

    private static final String ENCODING = "US-ASCII";

    /**
     * Test class constructor.
     */
    public Base64InputStreamTest() {
        super();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void readZeroByteEndingTest() throws IOException {
        byte[] base64Bytes = "/MpxS8RB".getBytes(ENCODING);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(252, 202, 113, 75, 196, 65);
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void readOneByteEndingTest() throws IOException {
        byte[] base64Bytes = "MT+6pw==".getBytes(ENCODING);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(49, 63, 186, 167);
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void readTwoByteEndingTest() throws IOException {
        byte[] base64Bytes = "0xJQjO4=".getBytes(ENCODING);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(211, 18, 80, 140, 238);
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void endOfStreamTest() {
        try {
            byte[] base64Bytes = "a".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            byte[] base64Bytes = "ab".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            byte[] base64Bytes = "aQ=".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            byte[] base64Bytes = "abc".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void charactersAfterPadTest() throws IOException {
        byte[] base64Bytes1 = "abQ=abcd".getBytes(ENCODING);
        ByteArrayInputStream bais1 = new ByteArrayInputStream(base64Bytes1);
        Base64InputStream base64InputStream1 = new Base64InputStream(bais1);
        Assertions.assertThat(base64InputStream1).isAllBytesEqualTo(105, 180, 105, 183, 29);
        Assertions.assertThat(bais1).isCompleted();

        byte[] base64Bytes2 = "aQ==abcd".getBytes(ENCODING);
        ByteArrayInputStream bais2 = new ByteArrayInputStream(base64Bytes2);
        Base64InputStream base64InputStream2 = new Base64InputStream(bais2);
        Assertions.assertThat(base64InputStream2).isAllBytesEqualTo(105, 105, 183, 29);
        Assertions.assertThat(bais2).isCompleted();

        byte[] base64Bytes3 = "8HgPESA=sA==HsHJ".getBytes(ENCODING);
        ByteArrayInputStream bais3 = new ByteArrayInputStream(base64Bytes3);
        Base64InputStream base64InputStream3 = new Base64InputStream(bais3);
        Assertions.assertThat(base64InputStream3).isAllBytesEqualTo(240, 120, 15, 17, 32, 176, 30, 193, 201);
        Assertions.assertThat(bais3).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void wrongCharacterTest() {
        try {
            byte[] base64Bytes = "?abc".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = "a?bc".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = "ab?c".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = "abc?".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void zeroCharacterTest() {
        try {
            byte[] base64Bytes = "\u0000".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            byte[] base64Bytes = "\u0000\u0000".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            byte[] base64Bytes = "\u0000\u0000\u0000".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            byte[] base64Bytes = "\u0000\u0000\u0000\u0000".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            byte[] base64Bytes = "\u0000abc".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            byte[] base64Bytes = "a\u0000bc".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            byte[] base64Bytes = "ab\u0000c".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            byte[] base64Bytes = "abc\u0000".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void wrongOneByteEndingCharacterTest() {
        try {
            byte[] base64Bytes = "abcd?Q==".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = "abcdaa==".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            byte[] base64Bytes = "abcd?a==".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = "abcda?==".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = "abcdaQ=a".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            byte[] base64Bytes = "abcdaQ=?".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void wrongTwoByteEndingCharacterTest() {
        try {
            byte[] base64Bytes = "abcd?aQ=".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = "abcda?Q=".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = "abcdaaa=".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            byte[] base64Bytes = "abcd?aa=".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = "abcda?a=".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = "abcdaa?=".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void wrongPadPositionTest() {
        try {
            byte[] base64Bytes = "=abc".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            byte[] base64Bytes = "a=bc".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            byte[] base64Bytes = "abcd=abc".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            byte[] base64Bytes = "abcda=bc".getBytes(ENCODING);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, 183, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void emptyStreamTest() throws IOException {
        byte[] base64Bytes = new byte[0];
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isCompleted();
        Assertions.assertThat(base64InputStream).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void skipZeroByteEndingTest() throws IOException {
        byte[] base64Bytes = "ABMN+/12tY4/vbQ7".getBytes(ENCODING);

        Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0100 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0100).isAllBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0101 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0101.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0101).isAllBytesEqualTo(19, 13, 251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0102 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0102.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0102).isAllBytesEqualTo(13, 251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0103 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0103.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0103).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0104 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0104.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0104).isAllBytesEqualTo(253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0105 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0105.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0105).isAllBytesEqualTo(118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0106 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0106.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0106).isAllBytesEqualTo(181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0107 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0107.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0107).isAllBytesEqualTo(142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0108 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0108.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0108).isAllBytesEqualTo(63, 189, 180, 59);

        Base64InputStream base64InputStream0109 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0109.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0109).isAllBytesEqualTo(189, 180, 59);

        Base64InputStream base64InputStream0110 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0110.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0110).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream0111 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0111.skip(11)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0111).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0112 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0112.skip(12)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0112).isAllBytesEqualTo();

        Base64InputStream base64InputStream0113 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0113.skip(13)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0113).isAllBytesEqualTo();

        Base64InputStream base64InputStream0114 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0114.skip(14)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0114).isAllBytesEqualTo();

        Base64InputStream base64InputStream0115 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0115.skip(15)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0115).isAllBytesEqualTo();

        Base64InputStream base64InputStream0116 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0116.skip(16)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0116).isAllBytesEqualTo();

        Base64InputStream base64InputStream0200 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0200).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0200.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0200).isAllBytesEqualTo(19, 13, 251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0201 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0201).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0201.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0201).isAllBytesEqualTo(13, 251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0202 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0202).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0202.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0202).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0203 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0203).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0203.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0203).isAllBytesEqualTo(253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0204 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0204).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0204.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0204).isAllBytesEqualTo(118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0205 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0205).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0205.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0205).isAllBytesEqualTo(181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0206 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0206).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0206.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0206).isAllBytesEqualTo(142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0207 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0207).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0207.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0207).isAllBytesEqualTo(63, 189, 180, 59);

        Base64InputStream base64InputStream0208 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0208).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0208.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0208).isAllBytesEqualTo(189, 180, 59);

        Base64InputStream base64InputStream0209 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0209).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0209.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0209).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream0210 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0210).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0210.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0210).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0211 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0211).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0211.skip(11)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0211).isAllBytesEqualTo();

        Base64InputStream base64InputStream0212 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0212).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0212.skip(12)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0212).isAllBytesEqualTo();

        Base64InputStream base64InputStream0300 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0300).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0300.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0300).isAllBytesEqualTo(13, 251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0301 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0301).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0301.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0301).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0302 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0302).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0302.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0302).isAllBytesEqualTo(253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0303 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0303).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0303.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0303).isAllBytesEqualTo(118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0304 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0304).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0304.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0304).isAllBytesEqualTo(181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0305 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0305).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0305.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0305).isAllBytesEqualTo(142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0306 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0306).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0306.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0306).isAllBytesEqualTo(63, 189, 180, 59);

        Base64InputStream base64InputStream0307 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0307).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0307.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0307).isAllBytesEqualTo(189, 180, 59);

        Base64InputStream base64InputStream0308 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0308).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0308.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0308).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream0309 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0309).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0309.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0309).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0310 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0310).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0310.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0310).isAllBytesEqualTo();

        Base64InputStream base64InputStream0311 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0311).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0311.skip(11)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0311).isAllBytesEqualTo();

        Base64InputStream base64InputStream0400 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0400).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0400.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0400).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0401 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0401).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0401.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0401).isAllBytesEqualTo(253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0402 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0402).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0402.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0402).isAllBytesEqualTo(118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0403 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0403).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0403.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0403).isAllBytesEqualTo(181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0404 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0404).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0404.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0404).isAllBytesEqualTo(142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0405 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0405).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0405.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0405).isAllBytesEqualTo(63, 189, 180, 59);

        Base64InputStream base64InputStream0406 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0406).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0406.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0406).isAllBytesEqualTo(189, 180, 59);

        Base64InputStream base64InputStream0407 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0407).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0407.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0407).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream0408 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0408).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0408.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0408).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0409 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0409).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0409.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0409).isAllBytesEqualTo();

        Base64InputStream base64InputStream0410 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0410).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0410.skip(10)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0410).isAllBytesEqualTo();

        Base64InputStream base64InputStream0500 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0500).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0500.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0500).isAllBytesEqualTo(253, 118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0501 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0501).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0501.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0501).isAllBytesEqualTo(118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0502 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0502).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0502.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0502).isAllBytesEqualTo(181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0503 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0503).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0503.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0503).isAllBytesEqualTo(142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0504 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0504).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0504.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0504).isAllBytesEqualTo(63, 189, 180, 59);

        Base64InputStream base64InputStream0505 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0505).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0505.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0505).isAllBytesEqualTo(189, 180, 59);

        Base64InputStream base64InputStream0506 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0506).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0506.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0506).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream0507 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0507).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0507.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0507).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0508 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0508).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0508.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0508).isAllBytesEqualTo();

        Base64InputStream base64InputStream0509 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0509).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0509.skip(9)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0509).isAllBytesEqualTo();

        Base64InputStream base64InputStream0600 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0600).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0600.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0600).isAllBytesEqualTo(118, 181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0601 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0601).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0601.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0601).isAllBytesEqualTo(181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0602 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0602).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0602.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0602).isAllBytesEqualTo(142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0603 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0603).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0603.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0603).isAllBytesEqualTo(63, 189, 180, 59);

        Base64InputStream base64InputStream0604 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0604).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0604.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0604).isAllBytesEqualTo(189, 180, 59);

        Base64InputStream base64InputStream0605 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0605).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0605.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0605).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream0606 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0606).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0606.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0606).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0607 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0607).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0607.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0607).isAllBytesEqualTo();

        Base64InputStream base64InputStream0608 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0608).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0608.skip(8)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0608).isAllBytesEqualTo();

        Base64InputStream base64InputStream0700 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0700).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0700.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0700).isAllBytesEqualTo(181, 142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0701 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0701).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0701.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0701).isAllBytesEqualTo(142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0702 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0702).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0702.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0702).isAllBytesEqualTo(63, 189, 180, 59);

        Base64InputStream base64InputStream0703 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0703).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0703.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0703).isAllBytesEqualTo(189, 180, 59);

        Base64InputStream base64InputStream0704 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0704).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0704.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0704).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream0705 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0705).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0705.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0705).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0706 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0706).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0706.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0706).isAllBytesEqualTo();

        Base64InputStream base64InputStream0707 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0707).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0707.skip(7)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0707).isAllBytesEqualTo();

        Base64InputStream base64InputStream0800 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0800).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0800.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0800).isAllBytesEqualTo(142, 63, 189, 180, 59);

        Base64InputStream base64InputStream0801 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0801).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0801.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0801).isAllBytesEqualTo(63, 189, 180, 59);

        Base64InputStream base64InputStream0802 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0802).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0802.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0802).isAllBytesEqualTo(189, 180, 59);

        Base64InputStream base64InputStream0803 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0803).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0803.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0803).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream0804 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0804).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0804.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0804).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0805 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0805).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0805.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0805).isAllBytesEqualTo();

        Base64InputStream base64InputStream0806 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0806).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0806.skip(6)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0806).isAllBytesEqualTo();

        Base64InputStream base64InputStream0900 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0900).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0900.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0900).isAllBytesEqualTo(63, 189, 180, 59);

        Base64InputStream base64InputStream0901 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0901).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0901.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0901).isAllBytesEqualTo(189, 180, 59);

        Base64InputStream base64InputStream0902 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0902).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0902.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0902).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream0903 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0903).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0903.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0903).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0904 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0904).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0904.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0904).isAllBytesEqualTo();

        Base64InputStream base64InputStream0905 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0905).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0905.skip(5)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0905).isAllBytesEqualTo();

        Base64InputStream base64InputStream1000 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1000).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1000.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1000).isAllBytesEqualTo(189, 180, 59);

        Base64InputStream base64InputStream1001 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1001).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1001.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1001).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream1002 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1002).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1002.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream1002).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream1003 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1003).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1003.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream1003).isAllBytesEqualTo();

        Base64InputStream base64InputStream1004 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1004).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1004.skip(4)).isEqualTo(3);
        Assertions.assertThat(base64InputStream1004).isAllBytesEqualTo();

        Base64InputStream base64InputStream1100 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1100).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);
        Assertions.assertThat(base64InputStream1100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1100).isAllBytesEqualTo(180, 59);

        Base64InputStream base64InputStream1101 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1101).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);
        Assertions.assertThat(base64InputStream1101.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1101).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream1102 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1102).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);
        Assertions.assertThat(base64InputStream1102.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream1102).isAllBytesEqualTo();

        Base64InputStream base64InputStream1103 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1103).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);
        Assertions.assertThat(base64InputStream1103.skip(3)).isEqualTo(2);
        Assertions.assertThat(base64InputStream1103).isAllBytesEqualTo();

        Base64InputStream base64InputStream1200 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1200).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180);
        Assertions.assertThat(base64InputStream1200.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1200).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream1201 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1201).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180);
        Assertions.assertThat(base64InputStream1201.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1201).isAllBytesEqualTo();

        Base64InputStream base64InputStream1202 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1202).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180);
        Assertions.assertThat(base64InputStream1202.skip(2)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1202).isAllBytesEqualTo();

        Base64InputStream base64InputStream1300 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1300).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180, 59);
        Assertions.assertThat(base64InputStream1300.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1300).isAllBytesEqualTo();

        Base64InputStream base64InputStream1301 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1301).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180, 59);
        Assertions.assertThat(base64InputStream1301.skip(1)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1301).isAllBytesEqualTo();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void skipOneByteEndingTest() throws IOException {
        byte[] base64Bytes = "ABMN+/12tY4/vQ==".getBytes(ENCODING);

        Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0100 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0100).isAllBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0101 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0101.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0101).isAllBytesEqualTo(19, 13, 251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0102 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0102.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0102).isAllBytesEqualTo(13, 251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0103 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0103.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0103).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0104 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0104.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0104).isAllBytesEqualTo(253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0105 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0105.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0105).isAllBytesEqualTo(118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0106 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0106.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0106).isAllBytesEqualTo(181, 142, 63, 189);

        Base64InputStream base64InputStream0107 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0107.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0107).isAllBytesEqualTo(142, 63, 189);

        Base64InputStream base64InputStream0108 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0108.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0108).isAllBytesEqualTo(63, 189);

        Base64InputStream base64InputStream0109 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0109.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0109).isAllBytesEqualTo(189);

        Base64InputStream base64InputStream0110 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0110.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0110).isAllBytesEqualTo();

        Base64InputStream base64InputStream0111 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0111.skip(11)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0111).isAllBytesEqualTo();

        Base64InputStream base64InputStream0112 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0112.skip(12)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0112).isAllBytesEqualTo();

        Base64InputStream base64InputStream0113 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0113.skip(13)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0113).isAllBytesEqualTo();

        Base64InputStream base64InputStream0114 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0114.skip(14)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0114).isAllBytesEqualTo();

        Base64InputStream base64InputStream0200 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0200).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0200.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0200).isAllBytesEqualTo(19, 13, 251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0201 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0201).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0201.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0201).isAllBytesEqualTo(13, 251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0202 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0202).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0202.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0202).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0203 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0203).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0203.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0203).isAllBytesEqualTo(253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0204 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0204).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0204.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0204).isAllBytesEqualTo(118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0205 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0205).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0205.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0205).isAllBytesEqualTo(181, 142, 63, 189);

        Base64InputStream base64InputStream0206 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0206).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0206.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0206).isAllBytesEqualTo(142, 63, 189);

        Base64InputStream base64InputStream0207 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0207).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0207.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0207).isAllBytesEqualTo(63, 189);

        Base64InputStream base64InputStream0208 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0208).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0208.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0208).isAllBytesEqualTo(189);

        Base64InputStream base64InputStream0209 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0209).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0209.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0209).isAllBytesEqualTo();

        Base64InputStream base64InputStream0210 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0210).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0210.skip(10)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0210).isAllBytesEqualTo();

        Base64InputStream base64InputStream0300 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0300).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0300.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0300).isAllBytesEqualTo(13, 251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0301 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0301).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0301.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0301).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0302 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0302).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0302.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0302).isAllBytesEqualTo(253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0303 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0303).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0303.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0303).isAllBytesEqualTo(118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0304 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0304).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0304.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0304).isAllBytesEqualTo(181, 142, 63, 189);

        Base64InputStream base64InputStream0305 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0305).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0305.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0305).isAllBytesEqualTo(142, 63, 189);

        Base64InputStream base64InputStream0306 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0306).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0306.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0306).isAllBytesEqualTo(63, 189);

        Base64InputStream base64InputStream0307 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0307).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0307.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0307).isAllBytesEqualTo(189);

        Base64InputStream base64InputStream0308 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0308).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0308.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0308).isAllBytesEqualTo();

        Base64InputStream base64InputStream0309 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0309).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0309.skip(9)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0309).isAllBytesEqualTo();

        Base64InputStream base64InputStream0400 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0400).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0400.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0400).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0401 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0401).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0401.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0401).isAllBytesEqualTo(253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0402 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0402).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0402.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0402).isAllBytesEqualTo(118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0403 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0403).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0403.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0403).isAllBytesEqualTo(181, 142, 63, 189);

        Base64InputStream base64InputStream0404 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0404).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0404.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0404).isAllBytesEqualTo(142, 63, 189);

        Base64InputStream base64InputStream0405 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0405).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0405.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0405).isAllBytesEqualTo(63, 189);

        Base64InputStream base64InputStream0406 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0406).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0406.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0406).isAllBytesEqualTo(189);

        Base64InputStream base64InputStream0407 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0407).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0407.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0407).isAllBytesEqualTo();

        Base64InputStream base64InputStream0408 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0408).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0408.skip(8)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0408).isAllBytesEqualTo();

        Base64InputStream base64InputStream0500 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0500).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0500.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0500).isAllBytesEqualTo(253, 118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0501 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0501).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0501.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0501).isAllBytesEqualTo(118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0502 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0502).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0502.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0502).isAllBytesEqualTo(181, 142, 63, 189);

        Base64InputStream base64InputStream0503 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0503).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0503.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0503).isAllBytesEqualTo(142, 63, 189);

        Base64InputStream base64InputStream0504 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0504).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0504.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0504).isAllBytesEqualTo(63, 189);

        Base64InputStream base64InputStream0505 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0505).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0505.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0505).isAllBytesEqualTo(189);

        Base64InputStream base64InputStream0506 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0506).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0506.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0506).isAllBytesEqualTo();

        Base64InputStream base64InputStream0507 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0507).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0507.skip(7)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0507).isAllBytesEqualTo();

        Base64InputStream base64InputStream0600 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0600).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0600.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0600).isAllBytesEqualTo(118, 181, 142, 63, 189);

        Base64InputStream base64InputStream0601 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0601).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0601.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0601).isAllBytesEqualTo(181, 142, 63, 189);

        Base64InputStream base64InputStream0602 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0602).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0602.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0602).isAllBytesEqualTo(142, 63, 189);

        Base64InputStream base64InputStream0603 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0603).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0603.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0603).isAllBytesEqualTo(63, 189);

        Base64InputStream base64InputStream0604 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0604).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0604.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0604).isAllBytesEqualTo(189);

        Base64InputStream base64InputStream0605 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0605).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0605.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0605).isAllBytesEqualTo();

        Base64InputStream base64InputStream0606 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0606).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0606.skip(6)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0606).isAllBytesEqualTo();

        Base64InputStream base64InputStream0700 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0700).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0700.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0700).isAllBytesEqualTo(181, 142, 63, 189);

        Base64InputStream base64InputStream0701 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0701).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0701.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0701).isAllBytesEqualTo(142, 63, 189);

        Base64InputStream base64InputStream0702 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0702).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0702.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0702).isAllBytesEqualTo(63, 189);

        Base64InputStream base64InputStream0703 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0703).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0703.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0703).isAllBytesEqualTo(189);

        Base64InputStream base64InputStream0704 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0704).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0704.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0704).isAllBytesEqualTo();

        Base64InputStream base64InputStream0705 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0705).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0705.skip(5)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0705).isAllBytesEqualTo();

        Base64InputStream base64InputStream0800 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0800).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0800.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0800).isAllBytesEqualTo(142, 63, 189);

        Base64InputStream base64InputStream0801 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0801).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0801.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0801).isAllBytesEqualTo(63, 189);

        Base64InputStream base64InputStream0802 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0802).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0802.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0802).isAllBytesEqualTo(189);

        Base64InputStream base64InputStream0803 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0803).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0803.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0803).isAllBytesEqualTo();

        Base64InputStream base64InputStream0804 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0804).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0804.skip(4)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0804).isAllBytesEqualTo();

        Base64InputStream base64InputStream0900 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0900).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0900.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0900).isAllBytesEqualTo(63, 189);

        Base64InputStream base64InputStream0901 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0901).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0901.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0901).isAllBytesEqualTo(189);

        Base64InputStream base64InputStream0902 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0902).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0902.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0902).isAllBytesEqualTo();

        Base64InputStream base64InputStream0903 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0903).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0903.skip(3)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0903).isAllBytesEqualTo();

        Base64InputStream base64InputStream1000 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1000).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1000.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1000).isAllBytesEqualTo(189);

        Base64InputStream base64InputStream1001 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1001).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1001.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1001).isAllBytesEqualTo();

        Base64InputStream base64InputStream1002 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1002).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1002.skip(2)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1002).isAllBytesEqualTo();

        Base64InputStream base64InputStream1100 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1100).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);
        Assertions.assertThat(base64InputStream1100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1100).isAllBytesEqualTo();

        Base64InputStream base64InputStream1101 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1101).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);
        Assertions.assertThat(base64InputStream1101.skip(1)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1101).isAllBytesEqualTo();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void skipTwoByteEndingTest() throws IOException {
        byte[] base64Bytes = "ABMN+/12tY4/vbQ=".getBytes(ENCODING);

        Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0100 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0100).isAllBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0101 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0101.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0101).isAllBytesEqualTo(19, 13, 251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0102 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0102.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0102).isAllBytesEqualTo(13, 251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0103 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0103.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0103).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0104 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0104.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0104).isAllBytesEqualTo(253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0105 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0105.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0105).isAllBytesEqualTo(118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0106 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0106.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0106).isAllBytesEqualTo(181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0107 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0107.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0107).isAllBytesEqualTo(142, 63, 189, 180);

        Base64InputStream base64InputStream0108 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0108.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0108).isAllBytesEqualTo(63, 189, 180);

        Base64InputStream base64InputStream0109 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0109.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0109).isAllBytesEqualTo(189, 180);

        Base64InputStream base64InputStream0110 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0110.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0110).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream0111 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0111.skip(11)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0111).isAllBytesEqualTo();

        Base64InputStream base64InputStream0112 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0112.skip(12)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0112).isAllBytesEqualTo();

        Base64InputStream base64InputStream0113 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0113.skip(13)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0113).isAllBytesEqualTo();

        Base64InputStream base64InputStream0114 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0114.skip(14)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0114).isAllBytesEqualTo();

        Base64InputStream base64InputStream0115 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0115.skip(15)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0115).isAllBytesEqualTo();

        Base64InputStream base64InputStream0116 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0116.skip(16)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0116).isAllBytesEqualTo();

        Base64InputStream base64InputStream0200 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0200).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0200.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0200).isAllBytesEqualTo(19, 13, 251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0201 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0201).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0201.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0201).isAllBytesEqualTo(13, 251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0202 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0202).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0202.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0202).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0203 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0203).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0203.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0203).isAllBytesEqualTo(253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0204 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0204).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0204.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0204).isAllBytesEqualTo(118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0205 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0205).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0205.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0205).isAllBytesEqualTo(181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0206 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0206).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0206.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0206).isAllBytesEqualTo(142, 63, 189, 180);

        Base64InputStream base64InputStream0207 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0207).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0207.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0207).isAllBytesEqualTo(63, 189, 180);

        Base64InputStream base64InputStream0208 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0208).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0208.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0208).isAllBytesEqualTo(189, 180);

        Base64InputStream base64InputStream0209 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0209).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0209.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0209).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream0210 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0210).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0210.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0210).isAllBytesEqualTo();

        Base64InputStream base64InputStream0211 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0211).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0211.skip(11)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0211).isAllBytesEqualTo();

        Base64InputStream base64InputStream0212 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0212).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0212.skip(12)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0212).isAllBytesEqualTo();

        Base64InputStream base64InputStream0300 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0300).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0300.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0300).isAllBytesEqualTo(13, 251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0301 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0301).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0301.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0301).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0302 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0302).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0302.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0302).isAllBytesEqualTo(253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0303 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0303).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0303.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0303).isAllBytesEqualTo(118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0304 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0304).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0304.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0304).isAllBytesEqualTo(181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0305 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0305).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0305.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0305).isAllBytesEqualTo(142, 63, 189, 180);

        Base64InputStream base64InputStream0306 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0306).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0306.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0306).isAllBytesEqualTo(63, 189, 180);

        Base64InputStream base64InputStream0307 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0307).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0307.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0307).isAllBytesEqualTo(189, 180);

        Base64InputStream base64InputStream0308 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0308).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0308.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0308).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream0309 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0309).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0309.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0309).isAllBytesEqualTo();

        Base64InputStream base64InputStream0310 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0310).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0310.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0310).isAllBytesEqualTo();

        Base64InputStream base64InputStream0311 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0311).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0311.skip(11)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0311).isAllBytesEqualTo();

        Base64InputStream base64InputStream0400 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0400).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0400.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0400).isAllBytesEqualTo(251, 253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0401 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0401).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0401.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0401).isAllBytesEqualTo(253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0402 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0402).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0402.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0402).isAllBytesEqualTo(118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0403 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0403).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0403.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0403).isAllBytesEqualTo(181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0404 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0404).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0404.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0404).isAllBytesEqualTo(142, 63, 189, 180);

        Base64InputStream base64InputStream0405 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0405).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0405.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0405).isAllBytesEqualTo(63, 189, 180);

        Base64InputStream base64InputStream0406 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0406).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0406.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0406).isAllBytesEqualTo(189, 180);

        Base64InputStream base64InputStream0407 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0407).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0407.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0407).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream0408 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0408).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0408.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0408).isAllBytesEqualTo();

        Base64InputStream base64InputStream0409 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0409).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0409.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0409).isAllBytesEqualTo();

        Base64InputStream base64InputStream0410 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0410).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0410.skip(10)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0410).isAllBytesEqualTo();

        Base64InputStream base64InputStream0500 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0500).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0500.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0500).isAllBytesEqualTo(253, 118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0501 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0501).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0501.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0501).isAllBytesEqualTo(118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0502 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0502).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0502.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0502).isAllBytesEqualTo(181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0503 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0503).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0503.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0503).isAllBytesEqualTo(142, 63, 189, 180);

        Base64InputStream base64InputStream0504 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0504).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0504.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0504).isAllBytesEqualTo(63, 189, 180);

        Base64InputStream base64InputStream0505 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0505).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0505.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0505).isAllBytesEqualTo(189, 180);

        Base64InputStream base64InputStream0506 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0506).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0506.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0506).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream0507 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0507).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0507.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0507).isAllBytesEqualTo();

        Base64InputStream base64InputStream0508 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0508).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0508.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0508).isAllBytesEqualTo();

        Base64InputStream base64InputStream0509 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0509).isNextBytesEqualTo(0, 19, 13, 251);
        Assertions.assertThat(base64InputStream0509.skip(9)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0509).isAllBytesEqualTo();

        Base64InputStream base64InputStream0600 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0600).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0600.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0600).isAllBytesEqualTo(118, 181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0601 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0601).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0601.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0601).isAllBytesEqualTo(181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0602 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0602).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0602.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0602).isAllBytesEqualTo(142, 63, 189, 180);

        Base64InputStream base64InputStream0603 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0603).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0603.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0603).isAllBytesEqualTo(63, 189, 180);

        Base64InputStream base64InputStream0604 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0604).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0604.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0604).isAllBytesEqualTo(189, 180);

        Base64InputStream base64InputStream0605 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0605).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0605.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0605).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream0606 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0606).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0606.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0606).isAllBytesEqualTo();

        Base64InputStream base64InputStream0607 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0607).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0607.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0607).isAllBytesEqualTo();

        Base64InputStream base64InputStream0608 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0608).isNextBytesEqualTo(0, 19, 13, 251, 253);
        Assertions.assertThat(base64InputStream0608.skip(8)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0608).isAllBytesEqualTo();

        Base64InputStream base64InputStream0700 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0700).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0700.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0700).isAllBytesEqualTo(181, 142, 63, 189, 180);

        Base64InputStream base64InputStream0701 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0701).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0701.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0701).isAllBytesEqualTo(142, 63, 189, 180);

        Base64InputStream base64InputStream0702 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0702).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0702.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0702).isAllBytesEqualTo(63, 189, 180);

        Base64InputStream base64InputStream0703 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0703).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0703.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0703).isAllBytesEqualTo(189, 180);

        Base64InputStream base64InputStream0704 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0704).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0704.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0704).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream0705 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0705).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0705.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0705).isAllBytesEqualTo();

        Base64InputStream base64InputStream0706 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0706).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0706.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0706).isAllBytesEqualTo();

        Base64InputStream base64InputStream0707 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0707).isNextBytesEqualTo(0, 19, 13, 251, 253, 118);
        Assertions.assertThat(base64InputStream0707.skip(7)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0707).isAllBytesEqualTo();

        Base64InputStream base64InputStream0800 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0800).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0800.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0800).isAllBytesEqualTo(142, 63, 189, 180);

        Base64InputStream base64InputStream0801 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0801).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0801.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0801).isAllBytesEqualTo(63, 189, 180);

        Base64InputStream base64InputStream0802 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0802).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0802.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0802).isAllBytesEqualTo(189, 180);

        Base64InputStream base64InputStream0803 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0803).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0803.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0803).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream0804 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0804).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0804.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0804).isAllBytesEqualTo();

        Base64InputStream base64InputStream0805 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0805).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0805.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0805).isAllBytesEqualTo();

        Base64InputStream base64InputStream0806 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0806).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181);
        Assertions.assertThat(base64InputStream0806.skip(6)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0806).isAllBytesEqualTo();

        Base64InputStream base64InputStream0900 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0900).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0900.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0900).isAllBytesEqualTo(63, 189, 180);

        Base64InputStream base64InputStream0901 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0901).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0901.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0901).isAllBytesEqualTo(189, 180);

        Base64InputStream base64InputStream0902 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0902).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0902.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0902).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream0903 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0903).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0903.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0903).isAllBytesEqualTo();

        Base64InputStream base64InputStream0904 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0904).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0904.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0904).isAllBytesEqualTo();

        Base64InputStream base64InputStream0905 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream0905).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142);
        Assertions.assertThat(base64InputStream0905.skip(5)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0905).isAllBytesEqualTo();

        Base64InputStream base64InputStream1000 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1000).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1000.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1000).isAllBytesEqualTo(189, 180);

        Base64InputStream base64InputStream1001 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1001).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1001.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1001).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream1002 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1002).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1002.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream1002).isAllBytesEqualTo();

        Base64InputStream base64InputStream1003 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1003).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1003.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream1003).isAllBytesEqualTo();

        Base64InputStream base64InputStream1004 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1004).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63);
        Assertions.assertThat(base64InputStream1004.skip(4)).isEqualTo(3);
        Assertions.assertThat(base64InputStream1004).isAllBytesEqualTo();

        Base64InputStream base64InputStream1100 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1100).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);
        Assertions.assertThat(base64InputStream1100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1100).isAllBytesEqualTo(180);

        Base64InputStream base64InputStream1101 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1101).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);
        Assertions.assertThat(base64InputStream1101.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1101).isAllBytesEqualTo();

        Base64InputStream base64InputStream1102 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1102).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189);
        Assertions.assertThat(base64InputStream1102.skip(2)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1102).isAllBytesEqualTo();

        Base64InputStream base64InputStream1200 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1200).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180);
        Assertions.assertThat(base64InputStream1200.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1200).isAllBytesEqualTo();

        Base64InputStream base64InputStream1201 = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream1201).isNextBytesEqualTo(0, 19, 13, 251, 253, 118, 181, 142, 63, 189, 180);
        Assertions.assertThat(base64InputStream1201.skip(1)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1201).isAllBytesEqualTo();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void skipNegativeCountTest() throws IOException {
        byte[] base64Bytes = "ABMN+/12tY4/vbQ7".getBytes(ENCODING);
        Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream.skip(-1)).isEqualTo(-1);
        Assertions.assertThat(base64InputStream.skip(-2)).isEqualTo(-1);
        Assertions.assertThat(base64InputStream.skip(-512)).isEqualTo(-1);
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void skipWrongCharacterCountBlockModTest() throws IOException {
        Base64InputStream base64InputStream1 = new Base64InputStream(new ByteArrayInputStream("12345678abc".getBytes()));
        Assertions.assertThat(base64InputStream1.skip(9)).isEqualTo(8);
        Assertions.assertThat(base64InputStream1).isCompleted();

        Base64InputStream base64InputStream2 = new Base64InputStream(new ByteArrayInputStream("12345678ab".getBytes()));
        Assertions.assertThat(base64InputStream2.skip(9)).isEqualTo(7);
        Assertions.assertThat(base64InputStream2).isCompleted();

        Base64InputStream base64InputStream3 = new Base64InputStream(new ByteArrayInputStream("12345678a".getBytes()));
        Assertions.assertThat(base64InputStream3.skip(9)).isEqualTo(6);
        Assertions.assertThat(base64InputStream3).isCompleted();

        Base64InputStream base64InputStream4 = new Base64InputStream(new ByteArrayInputStream("12345678abc".getBytes()));
        Assertions.assertThat(base64InputStream4).isNextBytesEqualTo(215, 109, 248);
        Assertions.assertThat(base64InputStream4.skip(6)).isEqualTo(5);
        Assertions.assertThat(base64InputStream4).isCompleted();

        Base64InputStream base64InputStream5 = new Base64InputStream(new ByteArrayInputStream("12345678ab".getBytes()));
        Assertions.assertThat(base64InputStream5).isNextBytesEqualTo(215, 109, 248);
        Assertions.assertThat(base64InputStream5.skip(6)).isEqualTo(4);
        Assertions.assertThat(base64InputStream5).isCompleted();

        Base64InputStream base64InputStream6 = new Base64InputStream(new ByteArrayInputStream("12345678a".getBytes()));
        Assertions.assertThat(base64InputStream6).isNextBytesEqualTo(215, 109, 248);
        Assertions.assertThat(base64InputStream6.skip(6)).isEqualTo(3);
        Assertions.assertThat(base64InputStream6).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void skipWrongCharacterCountNonBlockModTest() throws IOException {
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream("12345678abc".getBytes()));
            base64InputStream.skip(8);
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream("12345678ab".getBytes()));
            base64InputStream.skip(7);
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream("12345678a".getBytes()));
            Assertions.assertThat(base64InputStream.skip(6)).isEqualTo(6);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream("12345678abc".getBytes()));
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(215, 109, 248);
            base64InputStream.skip(5);
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream("12345678ab".getBytes()));
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(215, 109, 248);
            base64InputStream.skip(4);
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream("12345678a".getBytes()));
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(215, 109, 248);
            Assertions.assertThat(base64InputStream.skip(3)).isEqualTo(3);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void skipWrongCharacterTest() throws IOException {
        Base64InputStream base64InputStream1 = new Base64InputStream(new ByteArrayInputStream("1234?+++1234".getBytes()));
        Assertions.assertThat(base64InputStream1.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream1).isCompleted();

        Base64InputStream base64InputStream2 = new Base64InputStream(new ByteArrayInputStream("1234+?++1234".getBytes()));
        Assertions.assertThat(base64InputStream2.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream2).isCompleted();

        Base64InputStream base64InputStream3 = new Base64InputStream(new ByteArrayInputStream("1234++?+1234".getBytes()));
        Assertions.assertThat(base64InputStream3.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream3).isCompleted();

        Base64InputStream base64InputStream4 = new Base64InputStream(new ByteArrayInputStream("1234+++?1234".getBytes()));
        Assertions.assertThat(base64InputStream4.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream4).isCompleted();

        Base64InputStream base64InputStream5 = new Base64InputStream(new ByteArrayInputStream("1234?+++1234".getBytes()));
        Assertions.assertThat(base64InputStream5).isNextBytesEqualTo(215, 109, 248);
        Assertions.assertThat(base64InputStream5.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream5).isCompleted();

        Base64InputStream base64InputStream6 = new Base64InputStream(new ByteArrayInputStream("1234+?++1234".getBytes()));
        Assertions.assertThat(base64InputStream6).isNextBytesEqualTo(215, 109, 248);
        Assertions.assertThat(base64InputStream6.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream6).isCompleted();

        Base64InputStream base64InputStream7 = new Base64InputStream(new ByteArrayInputStream("1234++?+1234".getBytes()));
        Assertions.assertThat(base64InputStream7).isNextBytesEqualTo(215, 109, 248);
        Assertions.assertThat(base64InputStream7.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream7).isCompleted();

        Base64InputStream base64InputStream8 = new Base64InputStream(new ByteArrayInputStream("1234+++?1234".getBytes()));
        Assertions.assertThat(base64InputStream8).isNextBytesEqualTo(215, 109, 248);
        Assertions.assertThat(base64InputStream8.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream8).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void skipWrongCharacterReadTest() throws IOException {
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream("1234?+++1234".getBytes()));
            Assertions.assertThat(base64InputStream.skip(3)).isEqualTo(3);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream("1234+?++1234".getBytes()));
            Assertions.assertThat(base64InputStream.skip(3)).isEqualTo(3);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream("1234++?+1234".getBytes()));
            Assertions.assertThat(base64InputStream.skip(3)).isEqualTo(3);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream("1234+++?1234".getBytes()));
            Assertions.assertThat(base64InputStream.skip(3)).isEqualTo(3);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void availableTest() throws IOException {
        byte[] base64Bytes = "ABMN+/12tY4/vbQ7".getBytes(ENCODING);
        Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
        Assertions.assertThat(base64InputStream.available()).isEqualTo(12);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream.available()).isEqualTo(10);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(13, 251, 253, 118);
        Assertions.assertThat(base64InputStream.available()).isEqualTo(6);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(181, 142, 63);
        Assertions.assertThat(base64InputStream.available()).isEqualTo(3);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(189);
        Assertions.assertThat(base64InputStream.available()).isEqualTo(2);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(180, 59);
        Assertions.assertThat(base64InputStream.available()).isEqualTo(0);
        Assertions.assertThat(base64InputStream).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void availableWrongCharacterCountTest() throws IOException {
        try {
            byte[] base64Bytes = "1234aaaa123".getBytes(ENCODING);
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
            Assertions.assertThat(base64InputStream.available()).isEqualTo(8);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(215, 109, 248, 105, 166, 154);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            byte[] base64Bytes = "1234aaaa12".getBytes(ENCODING);
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
            Assertions.assertThat(base64InputStream.available()).isEqualTo(7);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(215, 109, 248, 105, 166, 154);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            byte[] base64Bytes = "1234aaaa1".getBytes(ENCODING);
            Base64InputStream base64InputStream = new Base64InputStream(new ByteArrayInputStream(base64Bytes));
            Assertions.assertThat(base64InputStream.available()).isEqualTo(6);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(215, 109, 248, 105, 166, 154);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void closeTest() throws IOException {
        CloseStream closeStream = new CloseStream();
        Base64InputStream base64InputStream = new Base64InputStream(closeStream);
        Assertions.assertThat(base64InputStream).isCompleted();

        Assertions.assertThat(closeStream.isClosed()).isFalse();
        base64InputStream.close();
        Assertions.assertThat(closeStream.isClosed()).isTrue();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void readFromClosedTest() throws IOException {
        byte[] base64Bytes = "MT+6pw==".getBytes(ENCODING);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(49, 63);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(186, 167);
        Assertions.assertThat(bais).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void closeClosedTest() throws IOException {
        byte[] base64Bytes = "MT+6pw==".getBytes(ENCODING);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(49);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(63);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(186);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(167);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isCompleted();
        Assertions.assertThat(bais).isCompleted();
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class CloseStream extends InputStream {

        private boolean _closed;

        CloseStream() {
            super();
            _closed = false;
        }

        @Override
        public int read() throws IOException {
            return -1;
        }

        boolean isClosed() {
            return _closed;
        }

        @Override
        public void close() throws IOException {
            super.close();
            _closed = true;
        }

    }

}
