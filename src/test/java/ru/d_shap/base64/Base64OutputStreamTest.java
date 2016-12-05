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
        Base64OutputStream b64os = new Base64OutputStream(baos);
        b64os.write(new byte[]{(byte) 240, 120, 15, 30, (byte) 193, (byte) 201});
        b64os.close();
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
        Base64OutputStream b64os = new Base64OutputStream(baos);
        b64os.write(new byte[]{(byte) 240, 120, 15, 30, (byte) 193});
        b64os.close();
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
        Base64OutputStream b64os = new Base64OutputStream(baos);
        b64os.write(new byte[]{(byte) 240, 120, 15, 30});
        b64os.close();
        Assert.assertEquals("8HgPHg==", new String(baos.toByteArray(), ENCODING));
    }

}
