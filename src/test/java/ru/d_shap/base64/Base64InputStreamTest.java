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
        InputStreamImpl inputStream1 = new InputStreamImpl("S8RB");
        Base64InputStream base64InputStream1 = new Base64InputStream(inputStream1);
        Assertions.assertThat(base64InputStream1).isAllBytesEqualTo(75, -60, 65);

        InputStreamImpl inputStream2 = new InputStreamImpl("/MpxS8RB");
        Base64InputStream base64InputStream2 = new Base64InputStream(inputStream2);
        Assertions.assertThat(base64InputStream2).isAllBytesEqualTo(-4, -54, 113, 75, -60, 65);
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void readOneByteEndingTest() throws IOException {
        InputStreamImpl inputStream1 = new InputStreamImpl("pw==");
        Base64InputStream base64InputStream1 = new Base64InputStream(inputStream1);
        Assertions.assertThat(base64InputStream1).isAllBytesEqualTo(-89);

        InputStreamImpl inputStream2 = new InputStreamImpl("MT+6pw==");
        Base64InputStream base64InputStream2 = new Base64InputStream(inputStream2);
        Assertions.assertThat(base64InputStream2).isAllBytesEqualTo(49, 63, -70, -89);
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void readTwoByteEndingTest() throws IOException {
        InputStreamImpl inputStream1 = new InputStreamImpl("jO4=");
        Base64InputStream base64InputStream1 = new Base64InputStream(inputStream1);
        Assertions.assertThat(base64InputStream1).isAllBytesEqualTo(-116, -18);

        InputStreamImpl inputStream2 = new InputStreamImpl("0xJQjO4=");
        Base64InputStream base64InputStream2 = new Base64InputStream(inputStream2);
        Assertions.assertThat(base64InputStream2).isAllBytesEqualTo(-45, 18, 80, -116, -18);
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void endOfStreamTest() {
        try {
            InputStreamImpl inputStream = new InputStreamImpl("a", true);
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("ab", true);
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("aQ=", true);
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abc", true);
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
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
        InputStreamImpl inputStream1 = new InputStreamImpl("abQ=abcd");
        Base64InputStream base64InputStream1 = new Base64InputStream(inputStream1);
        Assertions.assertThat(base64InputStream1).isAllBytesEqualTo(105, -76, 105, -73, 29);
        Assertions.assertThat(inputStream1).isCompleted();

        InputStreamImpl inputStream2 = new InputStreamImpl("aQ==abcd");
        Base64InputStream base64InputStream2 = new Base64InputStream(inputStream2);
        Assertions.assertThat(base64InputStream2).isAllBytesEqualTo(105, 105, -73, 29);
        Assertions.assertThat(inputStream2).isCompleted();

        InputStreamImpl inputStream3 = new InputStreamImpl("8HgPESA=sA==HsHJ");
        Base64InputStream base64InputStream3 = new Base64InputStream(inputStream3);
        Assertions.assertThat(base64InputStream3).isAllBytesEqualTo(-16, 120, 15, 17, 32, -80, 30, -63, -55);
        Assertions.assertThat(inputStream3).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void wrongCharacterTest() {
        try {
            InputStreamImpl inputStream = new InputStreamImpl("?abc");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("a?bc");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("ab?c");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abc?");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
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
            InputStreamImpl inputStream = new InputStreamImpl("\u0000");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("\u0000\u0000");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("\u0000\u0000\u0000");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("\u0000\u0000\u0000\u0000");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("\u0000abc");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("a\u0000bc");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("ab\u0000c");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abc\u0000");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
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
            InputStreamImpl inputStream = new InputStreamImpl("?Q==");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("aa==");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("?a==");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("a?==");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcd?Q==");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcdaa==");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcd?a==");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcda?==");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcdaQ=a");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcdaQ=?");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
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
            InputStreamImpl inputStream = new InputStreamImpl("?aQ=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("a?Q=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("aaa=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("?aa=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("a?a=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("aa?=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcd?aQ=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcda?Q=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcdaaa=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcd?aa=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcda?a=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcdaa?=");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
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
            InputStreamImpl inputStream = new InputStreamImpl("=abc");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("a=bc");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcd=abc");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            InputStreamImpl inputStream = new InputStreamImpl("abcda=bc");
            Base64InputStream base64InputStream = new Base64InputStream(inputStream);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(105, -73, 29);
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
        InputStreamImpl inputStream = new InputStreamImpl("");
        Base64InputStream base64InputStream = new Base64InputStream(inputStream);
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
        String base64String = "ABMN+/12tY4/vbQ7";

        Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0100 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0100).isAllBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0101 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0101.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0101).isAllBytesEqualTo(19, 13, -5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0102 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0102.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0102).isAllBytesEqualTo(13, -5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0103 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0103.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0103).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0104 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0104.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0104).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0105 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0105.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0105).isAllBytesEqualTo(118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0106 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0106.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0106).isAllBytesEqualTo(-75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0107 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0107.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0107).isAllBytesEqualTo(-114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0108 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0108.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0108).isAllBytesEqualTo(63, -67, -76, 59);

        Base64InputStream base64InputStream0109 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0109.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0109).isAllBytesEqualTo(-67, -76, 59);

        Base64InputStream base64InputStream0110 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0110.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0110).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream0111 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0111.skip(11)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0111).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0112 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0112.skip(12)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0112).isAllBytesEqualTo();

        Base64InputStream base64InputStream0113 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0113.skip(13)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0113).isAllBytesEqualTo();

        Base64InputStream base64InputStream0114 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0114.skip(14)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0114).isAllBytesEqualTo();

        Base64InputStream base64InputStream0115 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0115.skip(15)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0115).isAllBytesEqualTo();

        Base64InputStream base64InputStream0116 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0116.skip(16)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0116).isAllBytesEqualTo();

        Base64InputStream base64InputStream0117 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0117.skip(17)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0117).isAllBytesEqualTo();

        Base64InputStream base64InputStream0200 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0200).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0200.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0200).isAllBytesEqualTo(19, 13, -5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0201 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0201).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0201.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0201).isAllBytesEqualTo(13, -5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0202 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0202).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0202.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0202).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0203 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0203).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0203.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0203).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0204 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0204).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0204.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0204).isAllBytesEqualTo(118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0205 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0205).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0205.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0205).isAllBytesEqualTo(-75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0206 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0206).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0206.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0206).isAllBytesEqualTo(-114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0207 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0207).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0207.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0207).isAllBytesEqualTo(63, -67, -76, 59);

        Base64InputStream base64InputStream0208 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0208).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0208.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0208).isAllBytesEqualTo(-67, -76, 59);

        Base64InputStream base64InputStream0209 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0209).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0209.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0209).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream0210 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0210).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0210.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0210).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0211 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0211).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0211.skip(11)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0211).isAllBytesEqualTo();

        Base64InputStream base64InputStream0212 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0212).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0212.skip(12)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0212).isAllBytesEqualTo();

        Base64InputStream base64InputStream0300 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0300).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0300.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0300).isAllBytesEqualTo(13, -5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0301 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0301).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0301.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0301).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0302 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0302).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0302.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0302).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0303 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0303).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0303.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0303).isAllBytesEqualTo(118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0304 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0304).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0304.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0304).isAllBytesEqualTo(-75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0305 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0305).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0305.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0305).isAllBytesEqualTo(-114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0306 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0306).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0306.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0306).isAllBytesEqualTo(63, -67, -76, 59);

        Base64InputStream base64InputStream0307 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0307).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0307.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0307).isAllBytesEqualTo(-67, -76, 59);

        Base64InputStream base64InputStream0308 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0308).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0308.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0308).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream0309 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0309).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0309.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0309).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0310 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0310).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0310.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0310).isAllBytesEqualTo();

        Base64InputStream base64InputStream0311 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0311).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0311.skip(11)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0311).isAllBytesEqualTo();

        Base64InputStream base64InputStream0400 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0400).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0400.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0400).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0401 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0401).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0401.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0401).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0402 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0402).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0402.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0402).isAllBytesEqualTo(118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0403 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0403).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0403.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0403).isAllBytesEqualTo(-75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0404 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0404).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0404.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0404).isAllBytesEqualTo(-114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0405 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0405).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0405.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0405).isAllBytesEqualTo(63, -67, -76, 59);

        Base64InputStream base64InputStream0406 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0406).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0406.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0406).isAllBytesEqualTo(-67, -76, 59);

        Base64InputStream base64InputStream0407 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0407).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0407.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0407).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream0408 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0408).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0408.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0408).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0409 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0409).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0409.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0409).isAllBytesEqualTo();

        Base64InputStream base64InputStream0410 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0410).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0410.skip(10)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0410).isAllBytesEqualTo();

        Base64InputStream base64InputStream0500 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0500).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0500.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0500).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0501 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0501).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0501.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0501).isAllBytesEqualTo(118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0502 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0502).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0502.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0502).isAllBytesEqualTo(-75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0503 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0503).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0503.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0503).isAllBytesEqualTo(-114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0504 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0504).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0504.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0504).isAllBytesEqualTo(63, -67, -76, 59);

        Base64InputStream base64InputStream0505 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0505).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0505.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0505).isAllBytesEqualTo(-67, -76, 59);

        Base64InputStream base64InputStream0506 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0506).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0506.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0506).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream0507 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0507).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0507.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0507).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0508 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0508).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0508.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0508).isAllBytesEqualTo();

        Base64InputStream base64InputStream0509 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0509).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0509.skip(9)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0509).isAllBytesEqualTo();

        Base64InputStream base64InputStream0600 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0600).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0600.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0600).isAllBytesEqualTo(118, -75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0601 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0601).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0601.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0601).isAllBytesEqualTo(-75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0602 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0602).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0602.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0602).isAllBytesEqualTo(-114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0603 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0603).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0603.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0603).isAllBytesEqualTo(63, -67, -76, 59);

        Base64InputStream base64InputStream0604 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0604).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0604.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0604).isAllBytesEqualTo(-67, -76, 59);

        Base64InputStream base64InputStream0605 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0605).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0605.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0605).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream0606 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0606).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0606.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0606).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0607 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0607).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0607.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0607).isAllBytesEqualTo();

        Base64InputStream base64InputStream0608 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0608).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0608.skip(8)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0608).isAllBytesEqualTo();

        Base64InputStream base64InputStream0700 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0700).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0700.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0700).isAllBytesEqualTo(-75, -114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0701 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0701).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0701.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0701).isAllBytesEqualTo(-114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0702 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0702).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0702.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0702).isAllBytesEqualTo(63, -67, -76, 59);

        Base64InputStream base64InputStream0703 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0703).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0703.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0703).isAllBytesEqualTo(-67, -76, 59);

        Base64InputStream base64InputStream0704 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0704).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0704.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0704).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream0705 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0705).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0705.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0705).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0706 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0706).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0706.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0706).isAllBytesEqualTo();

        Base64InputStream base64InputStream0707 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0707).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0707.skip(7)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0707).isAllBytesEqualTo();

        Base64InputStream base64InputStream0800 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0800).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0800.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0800).isAllBytesEqualTo(-114, 63, -67, -76, 59);

        Base64InputStream base64InputStream0801 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0801).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0801.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0801).isAllBytesEqualTo(63, -67, -76, 59);

        Base64InputStream base64InputStream0802 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0802).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0802.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0802).isAllBytesEqualTo(-67, -76, 59);

        Base64InputStream base64InputStream0803 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0803).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0803.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0803).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream0804 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0804).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0804.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0804).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0805 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0805).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0805.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0805).isAllBytesEqualTo();

        Base64InputStream base64InputStream0806 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0806).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0806.skip(6)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0806).isAllBytesEqualTo();

        Base64InputStream base64InputStream0900 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0900).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0900.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0900).isAllBytesEqualTo(63, -67, -76, 59);

        Base64InputStream base64InputStream0901 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0901).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0901.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0901).isAllBytesEqualTo(-67, -76, 59);

        Base64InputStream base64InputStream0902 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0902).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0902.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0902).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream0903 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0903).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0903.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0903).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream0904 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0904).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0904.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0904).isAllBytesEqualTo();

        Base64InputStream base64InputStream0905 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0905).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0905.skip(5)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0905).isAllBytesEqualTo();

        Base64InputStream base64InputStream1000 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1000).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1000.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1000).isAllBytesEqualTo(-67, -76, 59);

        Base64InputStream base64InputStream1001 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1001).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1001.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1001).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream1002 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1002).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1002.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream1002).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream1003 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1003).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1003.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream1003).isAllBytesEqualTo();

        Base64InputStream base64InputStream1004 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1004).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1004.skip(4)).isEqualTo(3);
        Assertions.assertThat(base64InputStream1004).isAllBytesEqualTo();

        Base64InputStream base64InputStream1100 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1100).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);
        Assertions.assertThat(base64InputStream1100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1100).isAllBytesEqualTo(-76, 59);

        Base64InputStream base64InputStream1101 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1101).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);
        Assertions.assertThat(base64InputStream1101.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1101).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream1102 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1102).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);
        Assertions.assertThat(base64InputStream1102.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream1102).isAllBytesEqualTo();

        Base64InputStream base64InputStream1103 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1103).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);
        Assertions.assertThat(base64InputStream1103.skip(3)).isEqualTo(2);
        Assertions.assertThat(base64InputStream1103).isAllBytesEqualTo();

        Base64InputStream base64InputStream1200 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1200).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76);
        Assertions.assertThat(base64InputStream1200.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1200).isAllBytesEqualTo(59);

        Base64InputStream base64InputStream1201 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1201).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76);
        Assertions.assertThat(base64InputStream1201.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1201).isAllBytesEqualTo();

        Base64InputStream base64InputStream1202 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1202).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76);
        Assertions.assertThat(base64InputStream1202.skip(2)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1202).isAllBytesEqualTo();

        Base64InputStream base64InputStream1300 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1300).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76, 59);
        Assertions.assertThat(base64InputStream1300.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1300).isAllBytesEqualTo();

        Base64InputStream base64InputStream1301 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1301).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76, 59);
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
        String base64String = "ABMN+/12tY4/vQ==";

        Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0100 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0100).isAllBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0101 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0101.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0101).isAllBytesEqualTo(19, 13, -5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0102 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0102.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0102).isAllBytesEqualTo(13, -5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0103 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0103.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0103).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0104 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0104.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0104).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0105 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0105.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0105).isAllBytesEqualTo(118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0106 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0106.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0106).isAllBytesEqualTo(-75, -114, 63, -67);

        Base64InputStream base64InputStream0107 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0107.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0107).isAllBytesEqualTo(-114, 63, -67);

        Base64InputStream base64InputStream0108 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0108.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0108).isAllBytesEqualTo(63, -67);

        Base64InputStream base64InputStream0109 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0109.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0109).isAllBytesEqualTo(-67);

        Base64InputStream base64InputStream0110 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0110.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0110).isAllBytesEqualTo();

        Base64InputStream base64InputStream0111 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0111.skip(11)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0111).isAllBytesEqualTo();

        Base64InputStream base64InputStream0112 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0112.skip(12)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0112).isAllBytesEqualTo();

        Base64InputStream base64InputStream0113 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0113.skip(13)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0113).isAllBytesEqualTo();

        Base64InputStream base64InputStream0114 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0114.skip(14)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0114).isAllBytesEqualTo();

        Base64InputStream base64InputStream0115 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0115.skip(15)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0115).isAllBytesEqualTo();

        Base64InputStream base64InputStream0200 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0200).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0200.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0200).isAllBytesEqualTo(19, 13, -5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0201 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0201).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0201.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0201).isAllBytesEqualTo(13, -5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0202 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0202).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0202.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0202).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0203 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0203).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0203.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0203).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0204 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0204).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0204.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0204).isAllBytesEqualTo(118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0205 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0205).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0205.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0205).isAllBytesEqualTo(-75, -114, 63, -67);

        Base64InputStream base64InputStream0206 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0206).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0206.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0206).isAllBytesEqualTo(-114, 63, -67);

        Base64InputStream base64InputStream0207 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0207).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0207.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0207).isAllBytesEqualTo(63, -67);

        Base64InputStream base64InputStream0208 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0208).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0208.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0208).isAllBytesEqualTo(-67);

        Base64InputStream base64InputStream0209 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0209).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0209.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0209).isAllBytesEqualTo();

        Base64InputStream base64InputStream0210 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0210).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0210.skip(10)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0210).isAllBytesEqualTo();

        Base64InputStream base64InputStream0300 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0300).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0300.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0300).isAllBytesEqualTo(13, -5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0301 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0301).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0301.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0301).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0302 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0302).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0302.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0302).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0303 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0303).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0303.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0303).isAllBytesEqualTo(118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0304 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0304).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0304.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0304).isAllBytesEqualTo(-75, -114, 63, -67);

        Base64InputStream base64InputStream0305 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0305).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0305.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0305).isAllBytesEqualTo(-114, 63, -67);

        Base64InputStream base64InputStream0306 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0306).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0306.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0306).isAllBytesEqualTo(63, -67);

        Base64InputStream base64InputStream0307 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0307).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0307.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0307).isAllBytesEqualTo(-67);

        Base64InputStream base64InputStream0308 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0308).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0308.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0308).isAllBytesEqualTo();

        Base64InputStream base64InputStream0309 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0309).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0309.skip(9)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0309).isAllBytesEqualTo();

        Base64InputStream base64InputStream0400 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0400).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0400.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0400).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0401 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0401).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0401.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0401).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0402 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0402).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0402.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0402).isAllBytesEqualTo(118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0403 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0403).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0403.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0403).isAllBytesEqualTo(-75, -114, 63, -67);

        Base64InputStream base64InputStream0404 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0404).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0404.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0404).isAllBytesEqualTo(-114, 63, -67);

        Base64InputStream base64InputStream0405 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0405).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0405.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0405).isAllBytesEqualTo(63, -67);

        Base64InputStream base64InputStream0406 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0406).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0406.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0406).isAllBytesEqualTo(-67);

        Base64InputStream base64InputStream0407 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0407).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0407.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0407).isAllBytesEqualTo();

        Base64InputStream base64InputStream0408 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0408).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0408.skip(8)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0408).isAllBytesEqualTo();

        Base64InputStream base64InputStream0500 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0500).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0500.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0500).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0501 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0501).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0501.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0501).isAllBytesEqualTo(118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0502 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0502).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0502.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0502).isAllBytesEqualTo(-75, -114, 63, -67);

        Base64InputStream base64InputStream0503 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0503).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0503.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0503).isAllBytesEqualTo(-114, 63, -67);

        Base64InputStream base64InputStream0504 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0504).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0504.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0504).isAllBytesEqualTo(63, -67);

        Base64InputStream base64InputStream0505 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0505).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0505.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0505).isAllBytesEqualTo(-67);

        Base64InputStream base64InputStream0506 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0506).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0506.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0506).isAllBytesEqualTo();

        Base64InputStream base64InputStream0507 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0507).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0507.skip(7)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0507).isAllBytesEqualTo();

        Base64InputStream base64InputStream0600 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0600).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0600.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0600).isAllBytesEqualTo(118, -75, -114, 63, -67);

        Base64InputStream base64InputStream0601 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0601).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0601.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0601).isAllBytesEqualTo(-75, -114, 63, -67);

        Base64InputStream base64InputStream0602 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0602).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0602.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0602).isAllBytesEqualTo(-114, 63, -67);

        Base64InputStream base64InputStream0603 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0603).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0603.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0603).isAllBytesEqualTo(63, -67);

        Base64InputStream base64InputStream0604 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0604).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0604.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0604).isAllBytesEqualTo(-67);

        Base64InputStream base64InputStream0605 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0605).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0605.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0605).isAllBytesEqualTo();

        Base64InputStream base64InputStream0606 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0606).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0606.skip(6)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0606).isAllBytesEqualTo();

        Base64InputStream base64InputStream0700 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0700).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0700.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0700).isAllBytesEqualTo(-75, -114, 63, -67);

        Base64InputStream base64InputStream0701 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0701).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0701.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0701).isAllBytesEqualTo(-114, 63, -67);

        Base64InputStream base64InputStream0702 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0702).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0702.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0702).isAllBytesEqualTo(63, -67);

        Base64InputStream base64InputStream0703 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0703).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0703.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0703).isAllBytesEqualTo(-67);

        Base64InputStream base64InputStream0704 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0704).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0704.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0704).isAllBytesEqualTo();

        Base64InputStream base64InputStream0705 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0705).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0705.skip(5)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0705).isAllBytesEqualTo();

        Base64InputStream base64InputStream0800 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0800).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0800.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0800).isAllBytesEqualTo(-114, 63, -67);

        Base64InputStream base64InputStream0801 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0801).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0801.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0801).isAllBytesEqualTo(63, -67);

        Base64InputStream base64InputStream0802 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0802).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0802.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0802).isAllBytesEqualTo(-67);

        Base64InputStream base64InputStream0803 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0803).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0803.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0803).isAllBytesEqualTo();

        Base64InputStream base64InputStream0804 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0804).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0804.skip(4)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0804).isAllBytesEqualTo();

        Base64InputStream base64InputStream0900 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0900).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0900.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0900).isAllBytesEqualTo(63, -67);

        Base64InputStream base64InputStream0901 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0901).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0901.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0901).isAllBytesEqualTo(-67);

        Base64InputStream base64InputStream0902 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0902).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0902.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0902).isAllBytesEqualTo();

        Base64InputStream base64InputStream0903 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0903).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0903.skip(3)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0903).isAllBytesEqualTo();

        Base64InputStream base64InputStream1000 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1000).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1000.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1000).isAllBytesEqualTo(-67);

        Base64InputStream base64InputStream1001 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1001).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1001.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1001).isAllBytesEqualTo();

        Base64InputStream base64InputStream1002 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1002).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1002.skip(2)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1002).isAllBytesEqualTo();

        Base64InputStream base64InputStream1100 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1100).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);
        Assertions.assertThat(base64InputStream1100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1100).isAllBytesEqualTo();

        Base64InputStream base64InputStream1101 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1101).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);
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
        String base64String = "ABMN+/12tY4/vbQ=";

        Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0100 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0100).isAllBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0101 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0101.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0101).isAllBytesEqualTo(19, 13, -5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0102 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0102.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0102).isAllBytesEqualTo(13, -5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0103 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0103.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0103).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0104 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0104.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0104).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0105 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0105.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0105).isAllBytesEqualTo(118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0106 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0106.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0106).isAllBytesEqualTo(-75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0107 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0107.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0107).isAllBytesEqualTo(-114, 63, -67, -76);

        Base64InputStream base64InputStream0108 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0108.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0108).isAllBytesEqualTo(63, -67, -76);

        Base64InputStream base64InputStream0109 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0109.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0109).isAllBytesEqualTo(-67, -76);

        Base64InputStream base64InputStream0110 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0110.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0110).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream0111 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0111.skip(11)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0111).isAllBytesEqualTo();

        Base64InputStream base64InputStream0112 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0112.skip(12)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0112).isAllBytesEqualTo();

        Base64InputStream base64InputStream0113 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0113.skip(13)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0113).isAllBytesEqualTo();

        Base64InputStream base64InputStream0114 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0114.skip(14)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0114).isAllBytesEqualTo();

        Base64InputStream base64InputStream0115 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0115.skip(15)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0115).isAllBytesEqualTo();

        Base64InputStream base64InputStream0116 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0116.skip(16)).isEqualTo(12);
        Assertions.assertThat(base64InputStream0116).isAllBytesEqualTo();

        Base64InputStream base64InputStream0200 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0200).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0200.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0200).isAllBytesEqualTo(19, 13, -5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0201 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0201).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0201.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0201).isAllBytesEqualTo(13, -5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0202 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0202).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0202.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0202).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0203 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0203).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0203.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0203).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0204 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0204).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0204.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0204).isAllBytesEqualTo(118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0205 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0205).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0205.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0205).isAllBytesEqualTo(-75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0206 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0206).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0206.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0206).isAllBytesEqualTo(-114, 63, -67, -76);

        Base64InputStream base64InputStream0207 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0207).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0207.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0207).isAllBytesEqualTo(63, -67, -76);

        Base64InputStream base64InputStream0208 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0208).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0208.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0208).isAllBytesEqualTo(-67, -76);

        Base64InputStream base64InputStream0209 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0209).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0209.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0209).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream0210 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0210).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0210.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0210).isAllBytesEqualTo();

        Base64InputStream base64InputStream0211 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0211).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0211.skip(11)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0211).isAllBytesEqualTo();

        Base64InputStream base64InputStream0212 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0212).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream0212.skip(12)).isEqualTo(11);
        Assertions.assertThat(base64InputStream0212).isAllBytesEqualTo();

        Base64InputStream base64InputStream0300 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0300).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0300.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0300).isAllBytesEqualTo(13, -5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0301 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0301).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0301.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0301).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0302 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0302).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0302.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0302).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0303 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0303).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0303.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0303).isAllBytesEqualTo(118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0304 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0304).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0304.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0304).isAllBytesEqualTo(-75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0305 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0305).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0305.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0305).isAllBytesEqualTo(-114, 63, -67, -76);

        Base64InputStream base64InputStream0306 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0306).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0306.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0306).isAllBytesEqualTo(63, -67, -76);

        Base64InputStream base64InputStream0307 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0307).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0307.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0307).isAllBytesEqualTo(-67, -76);

        Base64InputStream base64InputStream0308 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0308).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0308.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0308).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream0309 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0309).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0309.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0309).isAllBytesEqualTo();

        Base64InputStream base64InputStream0310 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0310).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0310.skip(10)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0310).isAllBytesEqualTo();

        Base64InputStream base64InputStream0311 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0311).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream0311.skip(11)).isEqualTo(10);
        Assertions.assertThat(base64InputStream0311).isAllBytesEqualTo();

        Base64InputStream base64InputStream0400 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0400).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0400.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0400).isAllBytesEqualTo(-5, -3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0401 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0401).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0401.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0401).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0402 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0402).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0402.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0402).isAllBytesEqualTo(118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0403 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0403).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0403.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0403).isAllBytesEqualTo(-75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0404 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0404).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0404.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0404).isAllBytesEqualTo(-114, 63, -67, -76);

        Base64InputStream base64InputStream0405 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0405).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0405.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0405).isAllBytesEqualTo(63, -67, -76);

        Base64InputStream base64InputStream0406 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0406).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0406.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0406).isAllBytesEqualTo(-67, -76);

        Base64InputStream base64InputStream0407 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0407).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0407.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0407).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream0408 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0408).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0408.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0408).isAllBytesEqualTo();

        Base64InputStream base64InputStream0409 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0409).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0409.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0409).isAllBytesEqualTo();

        Base64InputStream base64InputStream0410 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0410).isNextBytesEqualTo(0, 19, 13);
        Assertions.assertThat(base64InputStream0410.skip(10)).isEqualTo(9);
        Assertions.assertThat(base64InputStream0410).isAllBytesEqualTo();

        Base64InputStream base64InputStream0500 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0500).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0500.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0500).isAllBytesEqualTo(-3, 118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0501 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0501).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0501.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0501).isAllBytesEqualTo(118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0502 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0502).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0502.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0502).isAllBytesEqualTo(-75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0503 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0503).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0503.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0503).isAllBytesEqualTo(-114, 63, -67, -76);

        Base64InputStream base64InputStream0504 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0504).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0504.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0504).isAllBytesEqualTo(63, -67, -76);

        Base64InputStream base64InputStream0505 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0505).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0505.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0505).isAllBytesEqualTo(-67, -76);

        Base64InputStream base64InputStream0506 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0506).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0506.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0506).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream0507 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0507).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0507.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0507).isAllBytesEqualTo();

        Base64InputStream base64InputStream0508 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0508).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0508.skip(8)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0508).isAllBytesEqualTo();

        Base64InputStream base64InputStream0509 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0509).isNextBytesEqualTo(0, 19, 13, -5);
        Assertions.assertThat(base64InputStream0509.skip(9)).isEqualTo(8);
        Assertions.assertThat(base64InputStream0509).isAllBytesEqualTo();

        Base64InputStream base64InputStream0600 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0600).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0600.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0600).isAllBytesEqualTo(118, -75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0601 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0601).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0601.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0601).isAllBytesEqualTo(-75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0602 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0602).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0602.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0602).isAllBytesEqualTo(-114, 63, -67, -76);

        Base64InputStream base64InputStream0603 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0603).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0603.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0603).isAllBytesEqualTo(63, -67, -76);

        Base64InputStream base64InputStream0604 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0604).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0604.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0604).isAllBytesEqualTo(-67, -76);

        Base64InputStream base64InputStream0605 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0605).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0605.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0605).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream0606 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0606).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0606.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0606).isAllBytesEqualTo();

        Base64InputStream base64InputStream0607 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0607).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0607.skip(7)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0607).isAllBytesEqualTo();

        Base64InputStream base64InputStream0608 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0608).isNextBytesEqualTo(0, 19, 13, -5, -3);
        Assertions.assertThat(base64InputStream0608.skip(8)).isEqualTo(7);
        Assertions.assertThat(base64InputStream0608).isAllBytesEqualTo();

        Base64InputStream base64InputStream0700 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0700).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0700.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0700).isAllBytesEqualTo(-75, -114, 63, -67, -76);

        Base64InputStream base64InputStream0701 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0701).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0701.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0701).isAllBytesEqualTo(-114, 63, -67, -76);

        Base64InputStream base64InputStream0702 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0702).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0702.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0702).isAllBytesEqualTo(63, -67, -76);

        Base64InputStream base64InputStream0703 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0703).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0703.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0703).isAllBytesEqualTo(-67, -76);

        Base64InputStream base64InputStream0704 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0704).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0704.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0704).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream0705 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0705).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0705.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0705).isAllBytesEqualTo();

        Base64InputStream base64InputStream0706 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0706).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0706.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0706).isAllBytesEqualTo();

        Base64InputStream base64InputStream0707 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0707).isNextBytesEqualTo(0, 19, 13, -5, -3, 118);
        Assertions.assertThat(base64InputStream0707.skip(7)).isEqualTo(6);
        Assertions.assertThat(base64InputStream0707).isAllBytesEqualTo();

        Base64InputStream base64InputStream0800 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0800).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0800.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0800).isAllBytesEqualTo(-114, 63, -67, -76);

        Base64InputStream base64InputStream0801 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0801).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0801.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0801).isAllBytesEqualTo(63, -67, -76);

        Base64InputStream base64InputStream0802 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0802).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0802.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0802).isAllBytesEqualTo(-67, -76);

        Base64InputStream base64InputStream0803 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0803).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0803.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0803).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream0804 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0804).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0804.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0804).isAllBytesEqualTo();

        Base64InputStream base64InputStream0805 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0805).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0805.skip(5)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0805).isAllBytesEqualTo();

        Base64InputStream base64InputStream0806 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0806).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75);
        Assertions.assertThat(base64InputStream0806.skip(6)).isEqualTo(5);
        Assertions.assertThat(base64InputStream0806).isAllBytesEqualTo();

        Base64InputStream base64InputStream0900 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0900).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0900.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream0900).isAllBytesEqualTo(63, -67, -76);

        Base64InputStream base64InputStream0901 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0901).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0901.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream0901).isAllBytesEqualTo(-67, -76);

        Base64InputStream base64InputStream0902 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0902).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0902.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream0902).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream0903 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0903).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0903.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream0903).isAllBytesEqualTo();

        Base64InputStream base64InputStream0904 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0904).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0904.skip(4)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0904).isAllBytesEqualTo();

        Base64InputStream base64InputStream0905 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream0905).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114);
        Assertions.assertThat(base64InputStream0905.skip(5)).isEqualTo(4);
        Assertions.assertThat(base64InputStream0905).isAllBytesEqualTo();

        Base64InputStream base64InputStream1000 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1000).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1000.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1000).isAllBytesEqualTo(-67, -76);

        Base64InputStream base64InputStream1001 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1001).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1001.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1001).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream1002 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1002).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1002.skip(2)).isEqualTo(2);
        Assertions.assertThat(base64InputStream1002).isAllBytesEqualTo();

        Base64InputStream base64InputStream1003 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1003).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1003.skip(3)).isEqualTo(3);
        Assertions.assertThat(base64InputStream1003).isAllBytesEqualTo();

        Base64InputStream base64InputStream1004 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1004).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63);
        Assertions.assertThat(base64InputStream1004.skip(4)).isEqualTo(3);
        Assertions.assertThat(base64InputStream1004).isAllBytesEqualTo();

        Base64InputStream base64InputStream1100 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1100).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);
        Assertions.assertThat(base64InputStream1100.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1100).isAllBytesEqualTo(-76);

        Base64InputStream base64InputStream1101 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1101).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);
        Assertions.assertThat(base64InputStream1101.skip(1)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1101).isAllBytesEqualTo();

        Base64InputStream base64InputStream1102 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1102).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67);
        Assertions.assertThat(base64InputStream1102.skip(2)).isEqualTo(1);
        Assertions.assertThat(base64InputStream1102).isAllBytesEqualTo();

        Base64InputStream base64InputStream1200 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1200).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76);
        Assertions.assertThat(base64InputStream1200.skip(0)).isEqualTo(0);
        Assertions.assertThat(base64InputStream1200).isAllBytesEqualTo();

        Base64InputStream base64InputStream1201 = new Base64InputStream(new InputStreamImpl(base64String));
        Assertions.assertThat(base64InputStream1201).isNextBytesEqualTo(0, 19, 13, -5, -3, 118, -75, -114, 63, -67, -76);
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
        Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("ABMN+/12tY4/vbQ7"));
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
        Base64InputStream base64InputStream1 = new Base64InputStream(new InputStreamImpl("12345678abc"));
        Assertions.assertThat(base64InputStream1.skip(9)).isEqualTo(6);
        Assertions.assertThat(base64InputStream1).isCompleted();

        Base64InputStream base64InputStream2 = new Base64InputStream(new InputStreamImpl("12345678ab"));
        Assertions.assertThat(base64InputStream2.skip(9)).isEqualTo(6);
        Assertions.assertThat(base64InputStream2).isCompleted();

        Base64InputStream base64InputStream3 = new Base64InputStream(new InputStreamImpl("12345678a"));
        Assertions.assertThat(base64InputStream3.skip(9)).isEqualTo(6);
        Assertions.assertThat(base64InputStream3).isCompleted();

        Base64InputStream base64InputStream4 = new Base64InputStream(new InputStreamImpl("12345678abc"));
        Assertions.assertThat(base64InputStream4).isNextBytesEqualTo(-41, 109, -8);
        Assertions.assertThat(base64InputStream4.skip(6)).isEqualTo(3);
        Assertions.assertThat(base64InputStream4).isCompleted();

        Base64InputStream base64InputStream5 = new Base64InputStream(new InputStreamImpl("12345678ab"));
        Assertions.assertThat(base64InputStream5).isNextBytesEqualTo(-41, 109, -8);
        Assertions.assertThat(base64InputStream5.skip(6)).isEqualTo(3);
        Assertions.assertThat(base64InputStream5).isCompleted();

        Base64InputStream base64InputStream6 = new Base64InputStream(new InputStreamImpl("12345678a"));
        Assertions.assertThat(base64InputStream6).isNextBytesEqualTo(-41, 109, -8);
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
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("12345678abc"));
            base64InputStream.skip(8);
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("12345678ab"));
            base64InputStream.skip(7);
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("12345678a"));
            Assertions.assertThat(base64InputStream.skip(6)).isEqualTo(6);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("12345678abc"));
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(-41, 109, -8);
            base64InputStream.skip(5);
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("12345678ab"));
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(-41, 109, -8);
            base64InputStream.skip(4);
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("12345678a"));
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(-41, 109, -8);
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
        Base64InputStream base64InputStream1 = new Base64InputStream(new InputStreamImpl("1234?+++1234"));
        Assertions.assertThat(base64InputStream1.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream1).isCompleted();

        Base64InputStream base64InputStream2 = new Base64InputStream(new InputStreamImpl("1234+?++1234"));
        Assertions.assertThat(base64InputStream2.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream2).isCompleted();

        Base64InputStream base64InputStream3 = new Base64InputStream(new InputStreamImpl("1234++?+1234"));
        Assertions.assertThat(base64InputStream3.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream3).isCompleted();

        Base64InputStream base64InputStream4 = new Base64InputStream(new InputStreamImpl("1234+++?1234"));
        Assertions.assertThat(base64InputStream4.skip(9)).isEqualTo(9);
        Assertions.assertThat(base64InputStream4).isCompleted();

        Base64InputStream base64InputStream5 = new Base64InputStream(new InputStreamImpl("1234?+++1234"));
        Assertions.assertThat(base64InputStream5).isNextBytesEqualTo(-41, 109, -8);
        Assertions.assertThat(base64InputStream5.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream5).isCompleted();

        Base64InputStream base64InputStream6 = new Base64InputStream(new InputStreamImpl("1234+?++1234"));
        Assertions.assertThat(base64InputStream6).isNextBytesEqualTo(-41, 109, -8);
        Assertions.assertThat(base64InputStream6.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream6).isCompleted();

        Base64InputStream base64InputStream7 = new Base64InputStream(new InputStreamImpl("1234++?+1234"));
        Assertions.assertThat(base64InputStream7).isNextBytesEqualTo(-41, 109, -8);
        Assertions.assertThat(base64InputStream7.skip(6)).isEqualTo(6);
        Assertions.assertThat(base64InputStream7).isCompleted();

        Base64InputStream base64InputStream8 = new Base64InputStream(new InputStreamImpl("1234+++?1234"));
        Assertions.assertThat(base64InputStream8).isNextBytesEqualTo(-41, 109, -8);
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
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("1234?+++1234"));
            Assertions.assertThat(base64InputStream.skip(3)).isEqualTo(3);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("1234+?++1234"));
            Assertions.assertThat(base64InputStream.skip(3)).isEqualTo(3);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("1234++?+1234"));
            Assertions.assertThat(base64InputStream.skip(3)).isEqualTo(3);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("1234+++?1234"));
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
        String base64String1 = "ABMN";
        Base64InputStream base64InputStream1 = new Base64InputStream(new InputStreamImpl(base64String1));
        Assertions.assertThat(base64InputStream1.available()).isEqualTo(3);
        Assertions.assertThat(base64InputStream1).isNextBytesEqualTo(0);
        Assertions.assertThat(base64InputStream1.available()).isEqualTo(2);
        Assertions.assertThat(base64InputStream1).isNextBytesEqualTo(19);
        Assertions.assertThat(base64InputStream1.available()).isEqualTo(1);
        Assertions.assertThat(base64InputStream1).isNextBytesEqualTo(13);
        Assertions.assertThat(base64InputStream1.available()).isEqualTo(0);
        Assertions.assertThat(base64InputStream1).isCompleted();

        String base64String2 = "ABMN+/12tY4/vbQ7";
        Base64InputStream base64InputStream2 = new Base64InputStream(new InputStreamImpl(base64String2));
        Assertions.assertThat(base64InputStream2.available()).isEqualTo(12);
        Assertions.assertThat(base64InputStream2).isNextBytesEqualTo(0, 19);
        Assertions.assertThat(base64InputStream2.available()).isEqualTo(10);
        Assertions.assertThat(base64InputStream2).isNextBytesEqualTo(13, -5, -3, 118);
        Assertions.assertThat(base64InputStream2.available()).isEqualTo(6);
        Assertions.assertThat(base64InputStream2).isNextBytesEqualTo(-75, -114, 63);
        Assertions.assertThat(base64InputStream2.available()).isEqualTo(3);
        Assertions.assertThat(base64InputStream2).isNextBytesEqualTo(-67);
        Assertions.assertThat(base64InputStream2.available()).isEqualTo(2);
        Assertions.assertThat(base64InputStream2).isNextBytesEqualTo(-76, 59);
        Assertions.assertThat(base64InputStream2.available()).isEqualTo(0);
        Assertions.assertThat(base64InputStream2).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void availableWrongCharacterCountTest() throws IOException {
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("1234aaaa123"));
            Assertions.assertThat(base64InputStream.available()).isEqualTo(6);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(-41, 109, -8, 105, -90, -102);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("1234aaaa12"));
            Assertions.assertThat(base64InputStream.available()).isEqualTo(6);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(-41, 109, -8, 105, -90, -102);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            Base64InputStream base64InputStream = new Base64InputStream(new InputStreamImpl("1234aaaa1"));
            Assertions.assertThat(base64InputStream.available()).isEqualTo(6);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(-41, 109, -8, 105, -90, -102);
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
        InputStreamImpl inputStream = new InputStreamImpl("MT+6pw==");
        Base64InputStream base64InputStream = new Base64InputStream(inputStream);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(49, 63);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(-70, -89);
        Assertions.assertThat(inputStream).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void closeClosedTest() throws IOException {
        InputStreamImpl inputStream = new InputStreamImpl("MT+6pw==");
        Base64InputStream base64InputStream = new Base64InputStream(inputStream);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(49);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(63);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(-70);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(-89);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isCompleted();
        Assertions.assertThat(inputStream).isCompleted();
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class InputStreamImpl extends InputStream {

        private final ByteArrayInputStream _inputStream;

        private final boolean _failAfterEndOfStream;

        private int _lastByte;

        InputStreamImpl(final String base64String) throws IOException {
            this(base64String, false);
        }

        InputStreamImpl(final String base64String, final boolean failAfterEndOfStream) throws IOException {
            super();
            byte[] base64Bytes = base64String.getBytes(ENCODING);
            _inputStream = new ByteArrayInputStream(base64Bytes);
            _failAfterEndOfStream = failAfterEndOfStream;
        }

        @Override
        public int read() throws IOException {
            if (_lastByte < 0 && _failAfterEndOfStream) {
                throw new IOException("ERROR");
            } else {
                _lastByte = _inputStream.read();
                return _lastByte;
            }
        }

        @Override
        public long skip(final long count) throws IOException {
            return _inputStream.skip(count);
        }

        @Override
        public int available() throws IOException {
            return _inputStream.available();
        }

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
