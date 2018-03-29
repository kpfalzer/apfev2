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
 * Accept single char: in range or oneOf.
 */
public class CharClass implements Acceptor {

    public CharClass(char lo, char hi) {
        assert (lo <= hi);
        this.lo = lo;
        this.hi = hi;
    }

    public CharClass(String oneOf) {
        this.oneOf = oneOf;
    }

    private char lo = 0, hi = 0;
    private String oneOf = null;

    @Override
    public Accepted accept(CharBuffer cbuf) {
        final Location loc = cbuf.getLocation();
        char ch = (char) cbuf.peek();
        boolean ok;
        if (isNonNull(oneOf)) {
            ok = (0 <= oneOf.indexOf(ch));
        } else {
            ok = (lo <= ch) && (hi >= ch);
        }
        if (ok) {
            cbuf.accept();
            return new MyAccepted(loc, ch);
        }
        return null;
    }

    public static class MyAccepted extends Accepted {
        private MyAccepted(Location loc, char ch) {
            super(loc);
            this.ch = ch;
        }

        public final char ch;

    }
}
