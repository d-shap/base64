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
        byte[] base64Bytes = new byte[]{'/', 'M', 'p', 'x', 'S', '8', 'R', 'B'};
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(0xFC, 0xCA, 0x71, 0x4B, 0xC4, 0x41);
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void readOneByteEndingTest() throws IOException {
        byte[] base64Bytes = new byte[]{'M', 'T', '+', '6', 'p', 'w', '=', '='};
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(0x31, 0x3F, 0xBA, 0xA7);
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void readTwoByteEndingTest() throws IOException {
        byte[] base64Bytes = new byte[]{'0', 'x', 'J', 'Q', 'j', 'O', '4', '='};
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(0xD3, 0x12, 0x50, 0x8C, 0xEE);
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void endOfStreamTest() {
        try {
            byte[] base64Bytes = new byte[]{'a'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'Q', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Unexpected end of stream");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c'};
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
    public void symbolsAfterPadTest() throws IOException {
        byte[] base64Bytes1 = new byte[]{'a', 'b', 'Q', '=', 'a', 'b', 'c', 'd'};
        ByteArrayInputStream bais1 = new ByteArrayInputStream(base64Bytes1);
        Base64InputStream base64InputStream1 = new Base64InputStream(bais1);
        Assertions.assertThat(base64InputStream1).isAllBytesEqualTo(0x69, 0xB4, 0x69, 0xB7, 0x1D);
        Assertions.assertThat(bais1).isCompleted();

        byte[] base64Bytes2 = new byte[]{'a', 'Q', '=', '=', 'a', 'b', 'c', 'd'};
        ByteArrayInputStream bais2 = new ByteArrayInputStream(base64Bytes2);
        Base64InputStream base64InputStream2 = new Base64InputStream(bais2);
        Assertions.assertThat(base64InputStream2).isAllBytesEqualTo(0x69, 0x69, 0xB7, 0x1D);
        Assertions.assertThat(bais2).isCompleted();

        byte[] base64Bytes3 = new byte[]{'8', 'H', 'g', 'P', 'E', 'S', 'A', '=', 's', 'A', '=', '=', 'H', 's', 'H', 'J'};
        ByteArrayInputStream bais3 = new ByteArrayInputStream(base64Bytes3);
        Base64InputStream base64InputStream3 = new Base64InputStream(bais3);
        Assertions.assertThat(base64InputStream3).isAllBytesEqualTo(0xF0, 0x78, 0x0F, 0x11, 0x20, 0xB0, 0x1E, 0xC1, 0xC9);
        Assertions.assertThat(bais3).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void wrongSymbolTest() {
        try {
            byte[] base64Bytes = new byte[]{'?', 'a', 'b', 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', '?', 'b', 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', '?', 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', '?'};
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
    public void zeroSymbolTest() {
        try {
            byte[] base64Bytes = new byte[]{0, 'a', 'b', 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 0, 'b', 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 0, 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('\u0000', 0)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 0};
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
    public void wrongOneByteEndingSymbolTest() {
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', '?', 'Q', '=', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', 'a', 'a', '=', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', '?', 'a', '=', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', 'a', '?', '=', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', 'a', 'Q', '=', 'a'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', 'a', 'Q', '=', '?'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
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
    public void wrongTwoByteEndingSymbolTest() {
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', '?', 'a', 'Q', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', 'a', '?', 'Q', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', 'a', 'a', 'a', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('a', 97)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', '?', 'a', 'a', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', 'a', '?', 'a', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('?', 63)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', 'a', 'a', '?', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
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
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', '=', 'a', 'b', 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
            base64InputStream.read();
            Assertions.fail("Base64InputStream test fail");
        } catch (IOException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained ('=', 61)");
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', 'd', 'a', '=', 'b', 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x69, 0xB7, 0x1D);
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
        byte[] base64Bytes = new byte[]{'M', 'T', '+', '6', 'p', 'w', '=', '='};
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x31, 0x3F);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isAllBytesEqualTo(0xBA, 0xA7);
        Assertions.assertThat(bais).isCompleted();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void closeClosedTest() throws IOException {
        byte[] base64Bytes = new byte[]{'M', 'T', '+', '6', 'p', 'w', '=', '='};
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x31);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0x3F);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0xBA);

        base64InputStream.close();
        Assertions.assertThat(base64InputStream).isNextBytesEqualTo(0xA7);

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
