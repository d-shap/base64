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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link Base64OutputStream}.
 *
 * @author Dmitry Shapovalov
 */
public final class Base64OutputStreamTest {

    private static final String ENCODING = "US-ASCII";

    /**
     * Test class constructor.
     */
    public Base64OutputStreamTest() {
        super();
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void writeZeroByteEndingTest() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);
        base64OutputStream.write(new byte[]{(byte) 240, 120, 15, 30, (byte) 193, (byte) 201});
        base64OutputStream.close();
        Assert.assertEquals("8HgPHsHJ", new String(baos.toByteArray(), ENCODING));
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void writeOneByteEndingTest() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);
        base64OutputStream.write(new byte[]{(byte) 240, 120, 15, 30, (byte) 193});
        base64OutputStream.close();
        Assert.assertEquals("8HgPHsE=", new String(baos.toByteArray(), ENCODING));
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void writeTwoByteEndingTest() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);
        base64OutputStream.write(new byte[]{(byte) 240, 120, 15, 30});
        base64OutputStream.close();
        Assert.assertEquals("8HgPHg==", new String(baos.toByteArray(), ENCODING));
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void emptyStreamTest() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);
        base64OutputStream.write(new byte[0]);
        base64OutputStream.close();
        Assert.assertEquals("", new String(baos.toByteArray(), ENCODING));
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws IOException IO exception.
     */
    @Test
    public void closeTest() throws IOException {
        CloseStream closeStream = new CloseStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(closeStream);
        base64OutputStream.write(123);

        Assert.assertFalse(closeStream.isClosed());
        base64OutputStream.close();
        Assert.assertTrue(closeStream.isClosed());
    }

    /**
     * Output stream to test close method.
     *
     * @author Dmitry Shapovalov
     */
    private static final class CloseStream extends OutputStream {

        private boolean _closed;

        CloseStream() {
            super();
            _closed = false;
        }

        @Override
        public void write(final int value) throws IOException {
            // Ignore
        }

        boolean isClosed() {
            return _closed;
        }

        @Override
        public void close() throws IOException {
            _closed = true;
        }

    }

}
