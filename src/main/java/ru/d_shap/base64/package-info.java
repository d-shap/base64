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
/**
 * <p>
 * Base64 library converts bytes to the base64 representation and vice versa.
 * </p>
 * <p>
 * Base64 encoding is described in RFC 1421 and RFC 2045.
 * </p>
 * <p>
 * {@link ru.d_shap.base64.Base64Helper} class contains methods to convert the byte array to the base64
 * string, to convert the base64 string to the byte array and to check, if the string is a base64
 * representation of bytes.
 * </p>
 * <p>
 * An example, how to convert the byte array to the base64 string:
 * </p>
 * <pre>{@code
 * String str1 = Base64Helper.toBase64(new byte[]{1, 5, (byte) 140, (byte) 250, -14, 25}); // str1 = "AQWM+vIZ"
 * String str2 = Base64Helper.toBase64(new byte[]{(byte) 255, (byte) 250, (byte) 180, 17, 94}); // str2 = "//q0EV4="
 * String str3 = Base64Helper.toBase64(new byte[]{17, 28, (byte) 179, -14}); // str3 = "ERyz8g=="
 * }</pre>
 * <p>
 * An example, how to convert the base64 string to the byte array:
 * </p>
 * <pre>{@code
 * byte[] bytes1 = Base64Helper.toBytes("a+Z/fF12"); // bytes1 = new byte[]{(107, (byte) 230, 127, 124, 93, 118}
 * byte[] bytes2 = Base64Helper.toBytes("12gE3JQ="); // bytes2 = new byte[]{(byte) 215, 104, 4, (byte) 220, (byte) 148}
 * byte[] bytes3 = Base64Helper.toBytes("0FFTyQ=="); // bytes3 = new byte[]{(byte) 208, 81, 83, (byte) 201}
 * }</pre>
 * <p>
 * {@link ru.d_shap.base64.Base64Helper} class contains all data in memory. For the large data (for
 * example, big files) this is not efficient. In this case  {@link ru.d_shap.base64.Base64InputStream}
 * and {@link ru.d_shap.base64.Base64OutputStream} classes can be used. {@link ru.d_shap.base64.Base64InputStream}
 * reads the stream of the base64 characters and translates them to the bytes. {@link ru.d_shap.base64.Base64OutputStream}
 * translates the bytes to the base64 characters and writes them to the stream.
 * </p>
 * <p>
 * An example, how to write the base64 characters to the file:
 * </p>
 * <pre>{@code
 * try (FileInputStream inputStream = new FileInputStream("input file");
 *      Base64OutputStream outputStream = new Base64OutputStream(new FileOutputStream("base64 output file"))) {
 *     int read;
 *     while (true) {
 *         read = inputStream.read();
 *         if (read < 0) {
 *             break;
 *         }
 *         outputStream.write(read);
 *     }
 * }
 * }</pre>
 * <p>
 * Each 3 original bytes are represented with 4 base64 characters. The base64 representation of bytes
 * increases the original size by 33 percent.
 * </p>
 */
package ru.d_shap.base64;
