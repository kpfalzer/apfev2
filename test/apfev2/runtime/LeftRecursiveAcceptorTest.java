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

import static org.junit.jupiter.api.Assertions.*;

class LeftRecursiveAcceptorTest {

    @org.junit.jupiter.api.Test
    void accept() {
        final String DATA =
                "aabbbcccCCbbcc";
        final CharBuffer cbuf = new CharBuffer(DATA.toCharArray());
        Accepted accepted = a.accept(cbuf);
        assertNotNull(accepted);
        assertTrue(DATA.equals(accepted.toString()));
    }

    /*
     *  a:  a b
     *   |  a c
     *   |  'a'
     *
     *  b: b c
     *   | b d
     *   | 'b'
     *
     *  c: c 'CC'
     *   : 'c'
     */
    static final LeftRecursiveAcceptor a = new LeftRecursiveAcceptor(2, false);
    static final LeftRecursiveAcceptor b = new LeftRecursiveAcceptor(2, false);
    static final LeftRecursiveAcceptor c = new LeftRecursiveAcceptor(1, false);
    static final Acceptor d = new CharSequence("d");

    static {
        a.initialize(
                new Sequence(a, b),
                new Sequence(a, c),
                new CharSequence("a")
        );
        b.initialize(
                new Sequence(b, c),
                new Sequence(b, d),
                new CharSequence("b")
        );
        c.initialize(
          new Sequence(c, new CharSequence("CC")),
          new CharSequence("c")
        );
    }
}