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
import apfev2.runtime.Location;

public class Comment implements Acceptor {
    @Override
    public Accepted accept(CharBuffer cbuf) {
        final Location loc = cbuf.getLocation();
        char ch = cbuf.peek();
        if (('/' == ch) && ('/' == cbuf.peek(1))) {
            return lineComment(loc, cbuf);
        } else if (('/' == ch) && ('*' == cbuf.peek(1))) {
            return blockComment(loc, cbuf);
        }
        return null;
    }

    private MyAccepted blockComment(Location loc, CharBuffer cbuf) {
        cbuf.accept(1);
        StringBuilder buf = new StringBuilder("/*");
        char ch;
        while (!cbuf.isEOF()) {
            ch = cbuf.accept();
            if (('*' == ch) && ('/' == cbuf.peek())) {
                buf.append(ch).append(cbuf.accept());
                break; //while
            } else {
                buf.append(ch);
            }
        }
        if (cbuf.isEOF()) {
            throw new RuntimeException(
                    cbuf.getLocation().toString()
                            + ": Unexpected EOF (missing '*/' in block comment)"
            );
        }
        return new MyAccepted(loc, buf.toString());
    }

    private MyAccepted lineComment(Location loc, CharBuffer cbuf) {
        cbuf.accept(1);
        StringBuilder buf = new StringBuilder("//");
        char ch;
        while (!cbuf.isEOF()) {
            ch = cbuf.accept();
            if ('\r' != ch) {
                buf.append(ch);
            }
            if (CharBuffer.EOLN == ch) {
                break; //while
            }
        }
        return new MyAccepted(loc, buf.toString());
    }

    public static class MyAccepted extends Accepted {
        private MyAccepted(Location loc, String text) {
            super(loc);
            this.text = text;
        }

        public final String text;
    }
}
