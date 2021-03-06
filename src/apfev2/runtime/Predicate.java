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
 */
package apfev2.runtime;
import static apfev2.runtime.Util.isNonNull;
import static apfev2.runtime.Util.isNull;

/**
 * Test if acceptor would be accepted.
 * No chars are actually consumed.
 */
public class Predicate implements Acceptor {
    public Predicate(Acceptor acceptor, boolean isNot) {
        this.acceptor = acceptor;
        this.isNot = isNot;
    }

    public Predicate(Acceptor acceptor) {
        this(acceptor, false);
    }

    private final Acceptor acceptor;
    private final boolean isNot;

    @Override
    public Accepted accept(CharBuffer cbuf) {
        final CharBuffer.Mark start = cbuf.getMark();
        final Accepted test = acceptor.accept(cbuf);
        cbuf.setMark(start);
        return ((!isNot && isNonNull(test)) || (isNot && isNull(test))) ? new MyAccepted(cbuf) : null;
    }

    public static class MyAccepted extends Accepted {
        private MyAccepted(CharBuffer cbuf) {
            super(cbuf.getLocation());
        }
    }
}
