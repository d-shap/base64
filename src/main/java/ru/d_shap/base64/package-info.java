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
/**
 * <p>
 * Package contains classes to convert bytes to base64 representation and vice versa.
 * </p>
 * <p>
 * Base64 encoding is described in RFC 1421 and RFC 2045.
 * </p>
 * <p>
 * {@link ru.d_shap.base64.Base64Helper} class contains methods to convert byte array to base64 string, to convert
 * base64 string to byte array and to check, if the string is a base64 representation of bytes.
 * </p>
 * <p>
 * An example, how to convert bytes to base64 symbols:
 * </p>
 * <pre>{@code
 * String str1 = Base64Helper.toBase64(new byte[]{1, 5, (byte) 140, (byte) 250, -14, 25}); // str1 = "AQWM+vIZ"
 * String str2 = Base64Helper.toBase64(new byte[]{(byte) 255, (byte) 250, (byte) 180, 17, 94}); // str2 = "//q0EV4="
 * String str3 = Base64Helper.toBase64(new byte[]{17, 28, (byte) 179, -14}); // str3 = "ERyz8g=="
 * }</pre>
 * <p>
 * An example, how to convert base64 symbols to bytes:
 * </p>
 * <pre>{@code
 * byte[] bytes1 = Base64Helper.toBytes("a+Z/fF12"); // bytes1 = new byte[]{(107, (byte) 230, 127, 124, 93, 118}
 * byte[] bytes2 = Base64Helper.toBytes("12gE3JQ="); // bytes2 = new byte[]{(byte) 215, 104, 4, (byte) 220, (byte) 148}
 * byte[] bytes3 = Base64Helper.toBytes("0FFTyQ=="); // bytes3 = new byte[]{(byte) 208, 81, 83, (byte) 201}
 * }</pre>
 * <p>
 * {@link ru.d_shap.base64.Base64Helper} class contains all data in memory. For a large data (for example, big files)
 * this is not efficient. In such cases {@link ru.d_shap.base64.Base64InputStream} and {@link ru.d_shap.base64.Base64OutputStream}
 * classes could be used. {@link ru.d_shap.base64.Base64InputStream} read stream of base64 symbols and translates them to bytes.
 * {@link ru.d_shap.base64.Base64OutputStream} writes base64 symbols to the stream.
 * </p>
 * <p>
 * An example, how to write base64 symbols to the file:
 * </p>
 * <pre>{@code
 * try (FileInputStream inputStream = new FileInputStream("some input file");
 *      FileOutputStream outputStream = new FileOutputStream("some output file");
 *      Base64OutputStream base64OutputStream = new Base64OutputStream(outputStream);) {
 *     int read;
 *     while (true) {
 *         read = inputStream.read();
 *         if (read < 0) {
 *             break;
 *         }
 *         base64OutputStream.write(read);
 *     }
 * }
 * }</pre>
 * <p>
 * Each 3 original bytes are represented with 4 base64 symbols. Convertion to base64 increases the original size by 33 percent.
 * </p>
 */
package ru.d_shap.base64;
