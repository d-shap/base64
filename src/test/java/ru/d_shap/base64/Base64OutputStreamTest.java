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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.assertions.mock.IsCloseable;
import ru.d_shap.assertions.util.DataHelper;

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
     * @throws Exception exception in test.
     */
    @Test
    public void writeZeroByteEndingTest() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);
        base64OutputStream.write(new byte[]{(byte) 240, 120, 15, 30, (byte) 193, (byte) 201});
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgPHsHJ");
        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgPHsHJ");
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws Exception exception in test.
     */
    @Test
    public void writeOneByteEndingTest() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);
        base64OutputStream.write(new byte[]{(byte) 240, 120, 15, 30});
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgP");
        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgPHg==");
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws Exception exception in test.
     */
    @Test
    public void writeTwoByteEndingTest() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);
        base64OutputStream.write(new byte[]{(byte) 240, 120, 15, 30, (byte) 193});
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgP");
        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgPHsE=");
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws Exception exception in test.
     */
    @Test
    public void emptyStreamTest() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);
        base64OutputStream.write(new byte[0]);
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("");
        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("");
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws Exception exception in test.
     */
    @Test
    public void flushTest() throws Exception {
        FlushStream flushStream = new FlushStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(flushStream);
        base64OutputStream.write(123);

        Assertions.assertThat(flushStream.isFlushed()).isFalse();
        base64OutputStream.flush();
        Assertions.assertThat(flushStream.isFlushed()).isTrue();
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws Exception exception in test.
     */
    @Test
    public void writeToFlushedTest() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);

        base64OutputStream.write(new byte[]{(byte) 240, 120, 15});
        base64OutputStream.flush();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgP");

        base64OutputStream.write(new byte[]{17, 32});
        base64OutputStream.flush();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgP");

        base64OutputStream.write(176);
        base64OutputStream.flush();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgPESCw");

        base64OutputStream.write(new byte[]{30, (byte) 193, (byte) 201});
        base64OutputStream.flush();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgPESCwHsHJ");

        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgPESCwHsHJ");
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws Exception exception in test.
     */
    @Test
    public void closeTest() throws Exception {
        OutputStream outputStream = DataHelper.createOutputStreamBuilder().buildOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(outputStream);
        base64OutputStream.write(123);

        Assertions.assertThat(((IsCloseable) outputStream).isClosed()).isFalse();
        base64OutputStream.close();
        Assertions.assertThat(((IsCloseable) outputStream).isClosed()).isTrue();
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws Exception exception in test.
     */
    @Test
    public void writeToClosedTest() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);

        base64OutputStream.write(new byte[]{(byte) 240, 120, 15});
        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgP");

        base64OutputStream.write(new byte[]{17, 32});
        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgPESA=");

        base64OutputStream.write(176);
        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgPESA=sA==");

        base64OutputStream.write(new byte[]{30, (byte) 193, (byte) 201});
        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("8HgPESA=sA==HsHJ");
    }

    /**
     * {@link Base64OutputStream} class test.
     *
     * @throws Exception exception in test.
     */
    @Test
    public void closeClosedTest() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(baos);
        base64OutputStream.write(new byte[]{30, (byte) 193});
        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("HsE=");

        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("HsE=");

        base64OutputStream.close();
        Assertions.assertThat(new String(baos.toByteArray(), ENCODING)).isEqualTo("HsE=");
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class FlushStream extends OutputStream {

        private boolean _flushed;

        FlushStream() {
            super();
            _flushed = false;
        }

        @Override
        public void write(final int value) throws IOException {
            // Ignore
        }

        boolean isFlushed() {
            return _flushed;
        }

        @Override
        public void flush() throws IOException {
            super.flush();
            _flushed = true;
        }

    }

}
