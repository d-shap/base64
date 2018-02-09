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
        if (_bufferPosition < 0) {
            return -1;
        }
        _bufferPosition++;
        if (_bufferPosition == _buffer.length) {
            _bufferPosition = updateBuffer();
            if (_bufferPosition < 0) {
                return -1;
            }
        }
        return _buffer[_bufferPosition];
    }

    private int updateBuffer() throws IOException {
        int character1 = readCharacterFromStream(false, false);
        if (character1 < 0) {
            return -1;
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
                    throw new IOException(ExceptionMessageHelper.createWrongBase64CharacterMessage(character2));
                }
            } else {
                if (Base64Helper.isThirdBase64ByteZero(character3)) {
                    _buffer[1] = Base64Helper.getFirstBase64Byte(character1, character2);
                    _buffer[2] = Base64Helper.getSecondBase64Byte(character2, character3);
                    return 1;
                } else {
                    throw new IOException(ExceptionMessageHelper.createWrongBase64CharacterMessage(character3));
                }
            }
        } else {
            if (character3 == Consts.PAD) {
                throw new IOException(ExceptionMessageHelper.createWrongBase64CharacterMessage(character4));
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
                return -1;
            }
        }
        if (Base64Helper.isBase64CharacterValid(character) || padIsValid && character == Consts.PAD) {
            return character;
        } else {
            throw new IOException(ExceptionMessageHelper.createWrongBase64CharacterMessage(character));
        }
    }

    @Override
    public long skip(final long count) throws IOException {
        if (count < 0) {
            return -1;
        }
        long skipped = skipInCurrentBuffer(count);
        if (skipped == count) {
            return skipped;
        }
        skipped += skipInInputStream(count - skipped);
        if (skipped == count) {
            return skipped;
        }
        _bufferPosition = updateBuffer();
        skipped += skipInCurrentBuffer(count - skipped);
        return skipped;
    }

    private long skipInCurrentBuffer(final long count) {
        int unreadBytesInBuffer = _buffer.length - _bufferPosition - 1;
        if (count > unreadBytesInBuffer) {
            _bufferPosition = _buffer.length - 1;
            return unreadBytesInBuffer;
        } else {
            _bufferPosition = _bufferPosition + (int) count;
            return count;
        }
    }

    private long skipInInputStream(final long count) throws IOException {
        long countBytesD3 = count / 3L;
        long countCharacters = countBytesD3 * 4L;
        long skippedCharacters = _inputStream.skip(countCharacters);
        if (skippedCharacters == countCharacters) {
            return countBytesD3 * 3L;
        } else {
            return skippedCharacters / 4L * 3L;
        }
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
