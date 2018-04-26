/*
 * The MIT License
 *
 * Copyright 2018 kpfalzer.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package apfev2.parser;

import apfev2.runtime.Accepted;
import apfev2.runtime.Acceptor;
import apfev2.runtime.CharBuffer;

import static apfev2.runtime.Util.isNonNull;
import static apfev2.runtime.Util.isNull;

public class WithSpacing<T extends Acceptor> implements Acceptor {
    public WithSpacing(T baseAcceptor) {
        this.baseAcceptor = baseAcceptor;
    }

    private final T baseAcceptor;

    @Override
    public Accepted accept(CharBuffer cbuf) {
        final Accepted baseAccepted = baseAcceptor.accept(cbuf);
        if (isNull(baseAccepted)) {
            return null;
        }
        final Accepted spacingAccepted = Spacing.THE_ONE.accept(cbuf);
        return new MyAccepted(baseAccepted, spacingAccepted);
    }

    public static class MyAccepted extends Accepted {

        private MyAccepted(Accepted baseAccepted, Accepted spacingAccepted) {
            super(baseAccepted.location);
            this.baseAccepted = baseAccepted;
            this.spacingAccepted = spacingAccepted;
        }

        public boolean hasSpacing() {
            return isNonNull(spacingAccepted);
        }

        public final Accepted baseAccepted, spacingAccepted;
    }
}
