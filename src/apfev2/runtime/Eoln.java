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

import static apfev2.runtime.Util.invariant;
import static apfev2.runtime.Util.isNull;

public class Eoln implements Acceptor {

    /**
     * Accept (\r?\n)+
     * @param cbuf character buffer.
     * @return Accepted or null.  (Note: '\r' are dropped from matching text).
     */
    @Override
    public Accepted accept(CharBuffer cbuf) {
        char ch;
        StringBuilder buf = null;
        final Location loc = cbuf.getLocation();
        while (true) {
            ch = cbuf.peek();
            if ('\r' == ch) {
                ch = cbuf.accept(1);
                invariant('\n' == ch);
            } else if ('\n' == ch) {
                ch = cbuf.accept();
            } else {
                break;//while
            }
            if (isNull(buf)) {
                buf = new StringBuilder();
            }
            buf.append(ch);
        }
        return (isNull(buf)) ? null : new MyAccepted(loc, buf.toString());
    }

    public static class MyAccepted extends Accepted {
        private MyAccepted(Location loc, String text) {
            super(loc);
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        public final String text;
    }
}
