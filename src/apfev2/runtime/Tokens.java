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

package apfev2.runtime;
import static apfev2.runtime.Util.isNonNull;

/**
 * Manage tokens.
 */
public class Tokens {
    public static class Code extends Util.Pair<Integer, CharSequence> {
        public Code(int code, String text) {
            super(code, new CharSequence(text));
        }

        public int code() {
            return super.v1();
        }

        public CharSequence acceptor() {
            return super.v2();
        }

        public boolean equals(Code other) {
            return (other.code() == this.code());
        }
    }

    public static Code getCode(int code, String text) {
        return new Code(code, text);
    }

    public static class MyAcceptor implements Acceptor {
        public MyAcceptor(Code code) {
            this(code, null);
        }

        public MyAcceptor(Code code, Acceptor trailing) {
            this(trailing, code);
        }

        public MyAcceptor(Code... prioritizedCodes) {
            this(null, prioritizedCodes);
        }

        public MyAcceptor(Acceptor trailing, Code... prioritizedCodes) {
            this.prioritizedCodes = prioritizedCodes;
            this.trailing = trailing;
        }

        private final Code[] prioritizedCodes;
        private final Acceptor trailing;

        @Override
        public Accepted accept(CharBuffer cbuf) {
            final Location loc = cbuf.getLocation();
            Accepted accepted;
            for (Code code : prioritizedCodes) {
                accepted = code.acceptor().accept(cbuf);
                if (isNonNull(accepted)) {
                    StringBuilder buf = new StringBuilder(accepted.toString());
                    if (isNonNull(trailing)) {
                        accepted = trailing.accept(cbuf);
                        if (isNonNull(accepted)) {
                            buf.append(accepted.toString());
                        }
                    }
                    return new MyAccepted(loc, code.code(), buf.toString());
                }
            }
            return null;
        }

    }

    public static class MyAccepted extends TokenAccepted {
        private MyAccepted(Location loc, int code, String text) {
            super(loc, text, code);
        }
    }

}
