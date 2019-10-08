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

import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream to write the base64 representation of the bytes.
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
     * @param outputStream output stream with the base64 representation of the bytes.
     */
    public Base64OutputStream(final OutputStream outputStream) {
        super();
        _outputStream = outputStream;
        _buffer = new int[3];
    }

    @Override
    public void write(final int value) throws IOException {
        _buffer[_bufferPosition] = value & 0xFF;
        _bufferPosition++;
        if (_bufferPosition == _buffer.length) {
            writeBufferToOutputStream();
            _bufferPosition = 0;
        }
    }

    @Override
    public void flush() throws IOException {
        _outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        writeBufferToOutputStream();
        _bufferPosition = 0;
        _outputStream.close();
    }

    private void writeBufferToOutputStream() throws IOException {
        if (_bufferPosition == 1) {
            _outputStream.write(Base64Helper.getFirstBase64Character(_buffer[0]));
            _outputStream.write(Base64Helper.getSecondBase64Character(_buffer[0]));
            _outputStream.write(Consts.PAD);
            _outputStream.write(Consts.PAD);
        }
        if (_bufferPosition == 2) {
            _outputStream.write(Base64Helper.getFirstBase64Character(_buffer[0]));
            _outputStream.write(Base64Helper.getSecondBase64Character(_buffer[0], _buffer[1]));
            _outputStream.write(Base64Helper.getThirdBase64Character(_buffer[1]));
            _outputStream.write(Consts.PAD);
        }
        if (_bufferPosition == 3) {
            _outputStream.write(Base64Helper.getFirstBase64Character(_buffer[0]));
            _outputStream.write(Base64Helper.getSecondBase64Character(_buffer[0], _buffer[1]));
            _outputStream.write(Base64Helper.getThirdBase64Character(_buffer[1], _buffer[2]));
            _outputStream.write(Base64Helper.getFourthBase64Character(_buffer[2]));
        }
    }

}
