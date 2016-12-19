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

    private final IntegerHolder _resultHolder;

    private final IntegerHolder _lastReadValueHolder;

    private AbstractState _currentState;

    /**
     * Create new object.
     *
     * @param inputStream input stream to read bytes.
     */
    public Base64InputStream(final InputStream inputStream) {
        super();
        _inputStream = inputStream;
        _resultHolder = new IntegerHolder();
        _lastReadValueHolder = new IntegerHolder();
        _currentState = State1.INSTANCE;
    }

    @Override
    public int read() throws IOException {
        if (_currentState == null) {
            return -1;
        } else {
            _currentState = _currentState.read(_inputStream, _resultHolder, _lastReadValueHolder);
            return _resultHolder.getValue();
        }
    }

    private static int getCharFromBase64Stream(final InputStream inputStream, final boolean checkEndOfInput, final boolean padIsValid) throws IOException {
        int symbol = inputStream.read();
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

    /**
     * Class for mutable integer.
     *
     * @author Dmitry Shapovalov
     */
    private static final class IntegerHolder {

        private int _value;

        IntegerHolder() {
            super();
            _value = 0;
        }

        int getValue() {
            return _value;
        }

        void setValue(final int value) {
            _value = value;
        }

    }

    /**
     * Base class for object state.
     *
     * @author Dmitry Shapovalov
     */
    private abstract static class AbstractState {

        AbstractState() {
            super();
        }

        abstract AbstractState read(InputStream inputStream, IntegerHolder resultHolder, IntegerHolder lastReadValueHolder) throws IOException;

    }

    /**
     * State to process the first byte.
     *
     * @author Dmitry Shapovalov
     */
    private static final class State1 extends AbstractState {

        static final AbstractState INSTANCE = new State1();

        private State1() {
            super();
        }

        @Override
        AbstractState read(final InputStream inputStream, final IntegerHolder resultHolder, final IntegerHolder lastReadValueHolder) throws IOException {
            int symbol1 = getCharFromBase64Stream(inputStream, false, false);
            if (symbol1 < 0) {
                resultHolder.setValue(-1);
                return null;
            }
            int symbol2 = getCharFromBase64Stream(inputStream, true, false);

            int byteRead = Base64Helper.getFirstBase64Byte(symbol1, symbol2);
            resultHolder.setValue(byteRead);
            lastReadValueHolder.setValue(symbol2);
            return State2.INSTANCE;
        }

    }

    /**
     * State to process the second byte.
     *
     * @author Dmitry Shapovalov
     */
    private static final class State2 extends AbstractState {

        static final AbstractState INSTANCE = new State2();

        private State2() {
            super();
        }

        @Override
        AbstractState read(final InputStream inputStream, final IntegerHolder resultHolder, final IntegerHolder lastReadValueHolder) throws IOException {
            int symbol3 = getCharFromBase64Stream(inputStream, true, true);
            if (symbol3 == Consts.PAD) {
                if (Base64Helper.isSecondBase64ByteZero(lastReadValueHolder.getValue())) {
                    int symbol4 = getCharFromBase64Stream(inputStream, true, true);
                    if (symbol4 == Consts.PAD) {
                        resultHolder.setValue(-1);
                        return null;
                    } else {
                        throw new IOException(ExceptionMessageHelper.createWrongBase64Symbol(symbol4));
                    }
                } else {
                    throw new IOException(ExceptionMessageHelper.createWrongBase64Symbol(lastReadValueHolder.getValue()));
                }
            }

            int byteRead = Base64Helper.getSecondBase64Byte(lastReadValueHolder.getValue(), symbol3);
            resultHolder.setValue(byteRead);
            lastReadValueHolder.setValue(symbol3);
            return State3.INSTANCE;
        }

    }

    /**
     * State to process the third byte.
     *
     * @author Dmitry Shapovalov
     */
    private static final class State3 extends AbstractState {

        static final AbstractState INSTANCE = new State3();

        private State3() {
            super();
        }

        @Override
        AbstractState read(final InputStream inputStream, final IntegerHolder resultHolder, final IntegerHolder lastReadValueHolder) throws IOException {
            int symbol4 = getCharFromBase64Stream(inputStream, true, true);
            if (symbol4 == Consts.PAD) {
                if (Base64Helper.isThirdBase64ByteZero(lastReadValueHolder.getValue())) {
                    resultHolder.setValue(-1);
                    return null;
                } else {
                    throw new IOException(ExceptionMessageHelper.createWrongBase64Symbol(lastReadValueHolder.getValue()));
                }
            }

            int byteRead = Base64Helper.getThirdBase64Byte(lastReadValueHolder.getValue(), symbol4);
            resultHolder.setValue(byteRead);
            return State1.INSTANCE;
        }

    }

}
