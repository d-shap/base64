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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(0xFC, base64InputStream.read());
        Assert.assertEquals(0xCA, base64InputStream.read());
        Assert.assertEquals(0x71, base64InputStream.read());
        Assert.assertEquals(0x4B, base64InputStream.read());
        Assert.assertEquals(0xC4, base64InputStream.read());
        Assert.assertEquals(0x41, base64InputStream.read());
        Assert.assertEquals(-1, base64InputStream.read());
        Assert.assertEquals(-1, base64InputStream.read());
        Assert.assertEquals(-1, base64InputStream.read());
        base64InputStream.close();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void readOneByteEndingTest() throws IOException {
        byte[] base64Bytes = new byte[]{'0', 'x', 'J', 'Q', 'j', 'O', '4', '='};
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assert.assertEquals(0xD3, base64InputStream.read());
        Assert.assertEquals(0x12, base64InputStream.read());
        Assert.assertEquals(0x50, base64InputStream.read());
        Assert.assertEquals(0x8C, base64InputStream.read());
        Assert.assertEquals(0xEE, base64InputStream.read());
        Assert.assertEquals(-1, base64InputStream.read());
        Assert.assertEquals(-1, base64InputStream.read());
        Assert.assertEquals(-1, base64InputStream.read());
        base64InputStream.close();
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void readTwoByteEndingTest() throws IOException {
        byte[] base64Bytes = new byte[]{'M', 'T', '+', '6', 'p', 'w', '=', '='};
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Base64InputStream base64InputStream = new Base64InputStream(bais);
        Assert.assertEquals(0x31, base64InputStream.read());
        Assert.assertEquals(0x3F, base64InputStream.read());
        Assert.assertEquals(0xBA, base64InputStream.read());
        Assert.assertEquals(0xA7, base64InputStream.read());
        Assert.assertEquals(-1, base64InputStream.read());
        Assert.assertEquals(-1, base64InputStream.read());
        Assert.assertEquals(-1, base64InputStream.read());
        base64InputStream.close();
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
            Assert.fail("End of stream not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Unexpected end of stream", ex.getMessage());
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assert.assertEquals(0x69, base64InputStream.read());
            base64InputStream.read();
            Assert.fail("End of stream not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Unexpected end of stream", ex.getMessage());
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'Q', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assert.assertEquals(0x69, base64InputStream.read());
            base64InputStream.read();
            Assert.fail("End of stream not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Unexpected end of stream", ex.getMessage());
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assert.assertEquals(0x69, base64InputStream.read());
            Assert.assertEquals(0xB7, base64InputStream.read());
            base64InputStream.read();
            Assert.fail("End of stream not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Unexpected end of stream", ex.getMessage());
        }
    }

    /**
     * {@link Base64InputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void symbolsAfterEndTest() throws IOException {
        byte[] base64Bytes1 = new byte[]{'a', 'b', 'Q', '=', 'a'};
        ByteArrayInputStream bais1 = new ByteArrayInputStream(base64Bytes1);
        Base64InputStream base64InputStream1 = new Base64InputStream(bais1);
        Assert.assertEquals(0x69, base64InputStream1.read());
        Assert.assertEquals(0xB4, base64InputStream1.read());
        Assert.assertEquals(-1, base64InputStream1.read());
        Assert.assertEquals(-1, base64InputStream1.read());
        Assert.assertEquals(-1, base64InputStream1.read());
        Assert.assertEquals('a', bais1.read());
        Assert.assertEquals(-1, bais1.read());
        Assert.assertEquals(-1, bais1.read());
        Assert.assertEquals(-1, bais1.read());

        byte[] base64Bytes2 = new byte[]{'a', 'Q', '=', '=', 'a'};
        ByteArrayInputStream bais2 = new ByteArrayInputStream(base64Bytes2);
        Base64InputStream base64InputStream2 = new Base64InputStream(bais2);
        Assert.assertEquals(0x69, base64InputStream2.read());
        Assert.assertEquals(-1, base64InputStream2.read());
        Assert.assertEquals(-1, base64InputStream2.read());
        Assert.assertEquals(-1, base64InputStream2.read());
        Assert.assertEquals('a', bais2.read());
        Assert.assertEquals(-1, bais2.read());
        Assert.assertEquals(-1, bais2.read());
        Assert.assertEquals(-1, bais2.read());
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void symbolInsteadOfSecondPadTest() {
        try {
            byte[] base64Bytes = new byte[]{'a', 'Q', '=', 'b'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assert.assertEquals(0x69, base64InputStream.read());
            base64InputStream.read();
            Assert.fail("Symbol instead of second pad not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Wrong symbol obtained: 'b' (98)", ex.getMessage());
        }
    }

    /**
     * {@link Base64InputStream} class test.
     */
    @Test
    public void incorrectBytesBeforePadTest() {
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', '=', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assert.assertEquals(0x69, base64InputStream.read());
            base64InputStream.read();
            Assert.fail("Incorrect bytes before pad not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Wrong symbol obtained: 'b' (98)", ex.getMessage());
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'd', '='};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assert.assertEquals(0x69, base64InputStream.read());
            Assert.assertEquals(0xB7, base64InputStream.read());
            base64InputStream.read();
            Assert.fail("Incorrect bytes before pad not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Wrong symbol obtained: 'd' (100)", ex.getMessage());
        }
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
            Assert.fail("Wrong symbol not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Wrong symbol obtained: '?' (63)", ex.getMessage());
        }
        try {
            byte[] base64Bytes = new byte[]{'a', '?', 'b', 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            base64InputStream.read();
            Assert.fail("Wrong symbol not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Wrong symbol obtained: '?' (63)", ex.getMessage());
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', '?', 'c'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assert.assertEquals(0x69, base64InputStream.read());
            base64InputStream.read();
            Assert.fail("Wrong symbol not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Wrong symbol obtained: '?' (63)", ex.getMessage());
        }
        try {
            byte[] base64Bytes = new byte[]{'a', 'b', 'c', '?'};
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Base64InputStream base64InputStream = new Base64InputStream(bais);
            Assert.assertEquals(0x69, base64InputStream.read());
            Assert.assertEquals(0xB7, base64InputStream.read());
            base64InputStream.read();
            Assert.fail("Wrong symbol not processed");
        } catch (IOException ex) {
            Assert.assertEquals("Wrong symbol obtained: '?' (63)", ex.getMessage());
        }
    }

}
