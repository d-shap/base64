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
import java.io.InputStream;

/**
 * Input stream to read the base64 representation of the bytes.
 *
 * @author Dmitry Shapovalov
 */
public final class Base64InputStream extends InputStream {

    private static final int END_OF_STREAM = -1;

    private static final int NEGATIVE_SKIP_RESULT = -1;

    private final InputStream _inputStream;

    private final int[] _buffer;

    private int _bufferPosition;

    /**
     * Create new object.
     *
     * @param inputStream input stream with the base64 representation of the bytes.
     */
    public Base64InputStream(final InputStream inputStream) {
        super();
        _inputStream = inputStream;
        _buffer = new int[3];
        _bufferPosition = _buffer.length - 1;
    }

    @Override
    public int read() throws IOException {
        if (_bufferPosition == END_OF_STREAM) {
            return END_OF_STREAM;
        }
        _bufferPosition++;
        if (_bufferPosition == _buffer.length) {
            _bufferPosition = updateBuffer();
            if (_bufferPosition == END_OF_STREAM) {
                return END_OF_STREAM;
            }
        }
        return _buffer[_bufferPosition];
    }

    private int updateBuffer() throws IOException {
        int character1 = readCharacterFromStream(false, false);
        if (character1 == END_OF_STREAM) {
            return END_OF_STREAM;
        }
        int character2 = readCharacterFromStream(true, false);
        int character3 = readCharacterFromStream(true, true);
        int character4 = readCharacterFromStream(true, true);

        if (character4 == Consts.PAD) {
            if (character3 == Consts.PAD) {
                if (Base64Helper.isSecondBase64ByteZero(character2)) {
                    _buffer[2] = Base64Helper.getFirstBase64Byte(character1, character2);
                    return 2;
                } else {
                    throw new IOException(ExceptionMessageHelper.createWrongBase64StringCharacterMessage(character2));
                }
            } else {
                if (Base64Helper.isThirdBase64ByteZero(character3)) {
                    _buffer[1] = Base64Helper.getFirstBase64Byte(character1, character2);
                    _buffer[2] = Base64Helper.getSecondBase64Byte(character2, character3);
                    return 1;
                } else {
                    throw new IOException(ExceptionMessageHelper.createWrongBase64StringCharacterMessage(character3));
                }
            }
        } else {
            if (character3 == Consts.PAD) {
                throw new IOException(ExceptionMessageHelper.createWrongBase64StringCharacterMessage(character4));
            } else {
                _buffer[0] = Base64Helper.getFirstBase64Byte(character1, character2);
                _buffer[1] = Base64Helper.getSecondBase64Byte(character2, character3);
                _buffer[2] = Base64Helper.getThirdBase64Byte(character3, character4);
                return 0;
            }
        }
    }

    private int readCharacterFromStream(final boolean checkEndOfInput, final boolean padIsValid) throws IOException {
        int character = _inputStream.read();
        if (character < 0) {
            if (checkEndOfInput) {
                throw new IOException(ExceptionMessageHelper.createEndOfStreamMessage());
            } else {
                return END_OF_STREAM;
            }
        }
        if (Base64Helper.isBase64CharacterValid(character) || padIsValid && character == Consts.PAD) {
            return character;
        } else {
            throw new IOException(ExceptionMessageHelper.createWrongBase64StringCharacterMessage(character));
        }
    }

    @Override
    public long skip(final long count) throws IOException {
        if (count < 0) {
            return NEGATIVE_SKIP_RESULT;
        }
        long skipped = skipInCurrentBuffer(count);
        skipped += skipInInputStream(count - skipped);
        if (skipped == count) {
            return skipped;
        }
        _bufferPosition = updateBuffer();
        if (_bufferPosition < 0) {
            return skipped;
        }
        skipped += skipInUpdatedBuffer(count - skipped);
        return skipped;
    }

    private long skipInCurrentBuffer(final long count) {
        int unreadBytesInBuffer = _buffer.length - _bufferPosition - 1;
        _bufferPosition = Math.min(_buffer.length - 1, _bufferPosition + (int) count);
        return Math.min(unreadBytesInBuffer, count);
    }

    private long skipInInputStream(final long count) throws IOException {
        long countCharacters = count / 3L * 4L;
        long skippedCharacters = _inputStream.skip(countCharacters);
        return skippedCharacters / 4L * 3L;
    }

    private long skipInUpdatedBuffer(final long count) {
        int unreadBytesInBuffer = _buffer.length - _bufferPosition;
        _bufferPosition = Math.min(_buffer.length - 1, _bufferPosition + (int) count - 1);
        return Math.min(unreadBytesInBuffer, count);
    }

    @Override
    public int available() throws IOException {
        int availableCharacters = _inputStream.available();
        int unreadBytesInBuffer = _buffer.length - _bufferPosition - 1;
        return unreadBytesInBuffer + availableCharacters / 4 * 3;
    }

    @Override
    public void close() throws IOException {
        _inputStream.close();
    }

}
