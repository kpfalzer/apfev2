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

import apfev2.runtime.Token;

public class Tokens {
    private static final Spacing SPACING = new Spacing();
    
    private static Token getTokenAcceptor(int code, String toMatch) {
        return new Token(code, toMatch, SPACING);
    }

    public static final Token EQ = getTokenAcceptor(2, "=");
    public static final Token LEFT_ARROW = getTokenAcceptor(3, "<-");
    public static final Token EXTOP = getTokenAcceptor(4, "<<");
    public static final Token SLASH = getTokenAcceptor(5, "/");
    public static final Token BAR = getTokenAcceptor(6, "|");
    public static final Token AND = getTokenAcceptor(7, "&");
    public static final Token NOT = getTokenAcceptor(8, "!");
    public static final Token QUESTION = getTokenAcceptor(9, "?");
    public static final Token STAR = getTokenAcceptor(10, "*");
    public static final Token PLUS = getTokenAcceptor(11, "+");
    public static final Token OPEN = getTokenAcceptor(12, "(");
    public static final Token CLOSE = getTokenAcceptor(13, ")");
    public static final Token DOT = getTokenAcceptor(14, ".");
    public static final Token SEMI = getTokenAcceptor(15, ";");
    public static final Token COLON = getTokenAcceptor(16, ":");
    public static final Token LCURLY2 = getTokenAcceptor(17, "{{");
    public static final Token RCURLY2 = getTokenAcceptor(18, "}}");
}
