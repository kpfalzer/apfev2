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
import static apfev2.runtime.Util.invariant;

public class CharSequence implements Acceptor {

    public CharSequence(String toMatch) {
        invariant(!toMatch.isEmpty());
        this.toMatch = toMatch;
    }

    private final String toMatch;

    @Override
    public String toString() {
        return toMatch;
    }

    @Override
    public Accepted accept(CharBuffer cbuf) {
        if (cbuf.match(toMatch)) {
            final Location loc = cbuf.getLocation();
            cbuf.accept(toMatch.length()-1);
            return new MyAccepted(loc);
        }
        return null;
    }

    public class MyAccepted extends Accepted {
        public MyAccepted(Location loc) {
            super(loc);
        }

        @Override
        public String toString() {
            return CharSequence.this.toString();
        }
    }
}
