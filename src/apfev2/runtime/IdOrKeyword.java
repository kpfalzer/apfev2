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

import java.util.Map;

import static apfev2.runtime.Util.isDigit;
import static apfev2.runtime.Util.isAlpha;
import static apfev2.runtime.Util.isNull;

public class IdOrKeyword implements Acceptor {
    public static final Integer IDENT = 1;

    public IdOrKeyword(Map<String, Integer> tokenCodeByKeyword) {
        this(tokenCodeByKeyword, defaultAcceptor);
    }

    public IdOrKeyword(Map<String, Integer> tokenCodeByKeyword, Acceptor acceptor) {
        this.tokenCodeByKeyword = tokenCodeByKeyword;
        this.acceptor = acceptor;
    }

    @Override
    public Accepted accept(CharBuffer cbuf) {
        final TokenAccepted tok = (TokenAccepted) acceptor.accept(cbuf);
        if (isNull(tok)) return null;
        int code = IDENT;
        if (tokenCodeByKeyword.containsKey(tok.text)) {
            code = tokenCodeByKeyword.get(tok.text);
        }
        return new TokenAccepted(tok, code);
    }

    /**
     * Default acceptor matches [a-ZA-Z_][a-zA-Z_0-9]*
     */
    private static class MyAcceptor implements Acceptor {

        @Override
        public Accepted accept(CharBuffer cbuf) {
            final Location start = cbuf.getLocation();
            StringBuilder buf = new StringBuilder();
            boolean gotOne = false;
            char la;
            while (true) {
                la = cbuf.peek();
                if (isAlpha(la) || ('_' == la) || (gotOne && isDigit(la))) {
                    gotOne = true;
                    buf.append(cbuf.accept());
                } else {
                    break;
                }
            }
            return (gotOne) ? new MyAccepted(start, buf.toString()) : null;
        }

        public static class MyAccepted extends TokenAccepted {
            private MyAccepted(Location loc, String text) {
                super(loc, text);
            }
        }
    }

    private static final Acceptor defaultAcceptor = new MyAcceptor();

    private final Map<String, Integer> tokenCodeByKeyword;
    private final Acceptor acceptor;
}
