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

public class Error extends RuntimeException {
    public Error(Location loc, String msg) {
        this(loc.toString() + ": " + msg);
    }

    public Error(String msg) {
        super(msg);
    }

    public Error(CharBuffer buf, String msg) {
        this(buf.getLocation(), msg);
    }

    public static Error unexpected(CharBuffer buf, String found, String expected) {
        return new Error(buf, "found " + found + " while expecting: '" + expected + "'");
    }

    public static Error unexpectedEOF(CharBuffer buf, String expected) {
        return unexpected(buf, "<EOF>", expected);
    }

    public static Error unexpectedEOF(CharBuffer buf, char expected) {
        return unexpectedEOF(buf, String.valueOf(expected));
    }

    public static Error unexpectedEOLN(CharBuffer buf, String expected) {
        return unexpected(buf, "<EOLN>", expected);
    }

    public static Error unexpectedEOLN(CharBuffer buf, char expected) {
        return unexpectedEOLN(buf, String.valueOf(expected));
    }

    public static void checkUnexpected(CharBuffer charBuffer, char close) {
        if (charBuffer.isEOF()) {
            invariant(0 != close);
            throw Error.unexpectedEOF(charBuffer, close);
        }
        if (charBuffer.isEOLN()) {
            invariant(0 != close);
            throw Error.unexpectedEOLN(charBuffer, close);
        }
    }
}
