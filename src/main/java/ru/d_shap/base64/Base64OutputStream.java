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

import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream to write base64 representation of bytes.
 *
 * @author Dmitry Shapovalov
 */
public final class Base64OutputStream extends OutputStream {

    private final OutputStream _outputStream;

    private final int[] _buffer;

    private int _bufferPosition;

    /**
     * Create new object.
     *
     * @param outputStream output stream to write bytes.
     */
    public Base64OutputStream(final OutputStream outputStream) {
        super();
        _outputStream = outputStream;
        _buffer = new int[3];
        _bufferPosition = 0;
    }

    @Override
    public void write(final int value) throws IOException {
        _buffer[_bufferPosition] = value & 0xFF;
        _bufferPosition++;
        if (_bufferPosition == _buffer.length) {
            flushBuffer();
            _bufferPosition = 0;
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        flushBuffer();
        _bufferPosition = 0;
        _outputStream.close();
    }

    private void flushBuffer() throws IOException {
        if (_bufferPosition == 1) {
            _outputStream.write(Base64Helper.getFirstBase64Symbol(_buffer[0]));
            _outputStream.write(Base64Helper.getSecondBase64Symbol(_buffer[0]));
            _outputStream.write(Consts.PAD);
            _outputStream.write(Consts.PAD);
        } else if (_bufferPosition == 2) {
            _outputStream.write(Base64Helper.getFirstBase64Symbol(_buffer[0]));
            _outputStream.write(Base64Helper.getSecondBase64Symbol(_buffer[0], _buffer[1]));
            _outputStream.write(Base64Helper.getThirdBase64Symbol(_buffer[1]));
            _outputStream.write(Consts.PAD);
        } else if (_bufferPosition == 3) {
            _outputStream.write(Base64Helper.getFirstBase64Symbol(_buffer[0]));
            _outputStream.write(Base64Helper.getSecondBase64Symbol(_buffer[0], _buffer[1]));
            _outputStream.write(Base64Helper.getThirdBase64Symbol(_buffer[1], _buffer[2]));
            _outputStream.write(Base64Helper.getFourthBase64Symbol(_buffer[2]));
        }
    }

}
