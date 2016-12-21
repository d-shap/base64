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

import java.io.IOException;
import java.io.InputStream;

/**
 * Input stream to read base64 representation of bytes.
 *
 * @author Dmitry Shapovalov
 */
public final class Base64InputStream extends InputStream {

    private final InputStream _inputStream;

    private final int[] _buffer;

    private int _bufferPosition;

    /**
     * Create new object.
     *
     * @param inputStream input stream to read bytes.
     */
    public Base64InputStream(final InputStream inputStream) {
        super();
        _inputStream = inputStream;
        _buffer = new int[3];
        _bufferPosition = _buffer.length;
    }

    @Override
    public int read() throws IOException {
        if (_bufferPosition < 0) {
            return -1;
        }

        if (_bufferPosition == _buffer.length) {
            updateBuffer();
            if (_bufferPosition < 0) {
                return -1;
            }
        }

        int result = _buffer[_bufferPosition];
        _bufferPosition++;
        return result;
    }

    private void updateBuffer() throws IOException {
        int symbol1 = readFromBase64Stream(false, false);
        if (symbol1 < 0) {
            _bufferPosition = -1;
            return;
        }
        int symbol2 = readFromBase64Stream(true, false);
        int symbol3 = readFromBase64Stream(true, true);
        int symbol4 = readFromBase64Stream(true, true);

        if (symbol4 == Consts.PAD) {
            if (symbol3 == Consts.PAD) {
                if (Base64Helper.isSecondBase64ByteZero(symbol2)) {
                    _buffer[2] = Base64Helper.getFirstBase64Byte(symbol1, symbol2);
                    _bufferPosition = 2;
                } else {
                    throw new IOException(ExceptionMessageHelper.createWrongBase64Symbol(symbol2));
                }
            } else {
                if (Base64Helper.isThirdBase64ByteZero(symbol3)) {
                    _buffer[1] = Base64Helper.getFirstBase64Byte(symbol1, symbol2);
                    _buffer[2] = Base64Helper.getSecondBase64Byte(symbol2, symbol3);
                    _bufferPosition = 1;
                } else {
                    throw new IOException(ExceptionMessageHelper.createWrongBase64Symbol(symbol3));
                }
            }
        } else {
            if (symbol3 == Consts.PAD) {
                throw new IOException(ExceptionMessageHelper.createWrongBase64Symbol(symbol4));
            } else {
                _buffer[0] = Base64Helper.getFirstBase64Byte(symbol1, symbol2);
                _buffer[1] = Base64Helper.getSecondBase64Byte(symbol2, symbol3);
                _buffer[2] = Base64Helper.getThirdBase64Byte(symbol3, symbol4);
                _bufferPosition = 0;
            }
        }
    }

    private int readFromBase64Stream(final boolean checkEndOfInput, final boolean padIsValid) throws IOException {
        int symbol = _inputStream.read();
        if (symbol < 0) {
            if (checkEndOfInput) {
                throw new IOException(ExceptionMessageHelper.createEndOfStreamMessage());
            } else {
                return -1;
            }
        }
        if (Base64Helper.isBase64SymbolValid(symbol) || padIsValid && symbol == Consts.PAD) {
            return symbol;
        } else {
            throw new IOException(ExceptionMessageHelper.createWrongBase64Symbol(symbol));
        }
    }

    @Override
    public void close() throws IOException {
        _inputStream.close();
    }

}
