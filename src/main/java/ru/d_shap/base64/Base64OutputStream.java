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
import java.io.OutputStream;

/**
 * Output stream to write base64 representation of bytes.
 *
 * @author Dmitry Shapovalov
 */
public final class Base64OutputStream extends OutputStream {

    private final OutputStream _outputStream;

    private AbstractState _currentState;

    private int _previousValue;

    /**
     * Create new object.
     *
     * @param outputStream output stream to write bytes.
     */
    public Base64OutputStream(final OutputStream outputStream) {
        super();
        _outputStream = outputStream;
        _currentState = State1.INSTANCE;
        _previousValue = 0;
    }

    @Override
    public void write(final int value) throws IOException {
        int currentValue = value & 0xFF;
        _currentState = _currentState.write(_outputStream, _previousValue, currentValue);
        _previousValue = currentValue;
    }

    @Override
    public void close() throws IOException {
        _currentState.close(_outputStream, _previousValue);
        _outputStream.close();
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

        abstract AbstractState write(OutputStream outputStream, int previousValue, int value) throws IOException;

        abstract void close(OutputStream outputStream, int previousValue) throws IOException;

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
        AbstractState write(final OutputStream outputStream, final int previousValue, final int value) throws IOException {
            outputStream.write(Base64Helper.getFirstBase64Symbol(value));
            return State2.INSTANCE;
        }

        @Override
        void close(final OutputStream outputStream, final int previousValue) throws IOException {
            // Ignore
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
        AbstractState write(final OutputStream outputStream, final int previousValue, final int value) throws IOException {
            outputStream.write(Base64Helper.getSecondBase64Symbol(previousValue, value));
            return State3.INSTANCE;
        }

        @Override
        void close(final OutputStream outputStream, final int previousValue) throws IOException {
            outputStream.write(Base64Helper.getSecondBase64Symbol(previousValue, 0));
            outputStream.write(Consts.PAD);
            outputStream.write(Consts.PAD);
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
        AbstractState write(final OutputStream outputStream, final int previousValue, final int value) throws IOException {
            outputStream.write(Base64Helper.getThirdBase64Symbol(previousValue, value));
            outputStream.write(Base64Helper.getFourthBase64Symbol(value));
            return State1.INSTANCE;
        }

        @Override
        void close(final OutputStream outputStream, final int previousValue) throws IOException {
            outputStream.write(Base64Helper.getThirdBase64Symbol(previousValue, 0));
            outputStream.write(Consts.PAD);
        }

    }

}
