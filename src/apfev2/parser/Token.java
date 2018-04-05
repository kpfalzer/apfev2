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

import apfev2.runtime.*;

public class Token {
    private static Tokens.Code getCode(int code, String toMatch) {
        return Tokens.getCode(code, toMatch);
    }

    public static final Tokens.Code EQ = getCode(2, "=");
    public static final Tokens.Code LEFT_ARROW = getCode(3, "<-");
    public static final Tokens.Code EXTOP = getCode(4, "<<");
    public static final Tokens.Code SLASH = getCode(5, "/");
    public static final Tokens.Code BAR = getCode(6, "|");
    public static final Tokens.Code AND = getCode(7, "&");
    public static final Tokens.Code NOT = getCode(8, "!");
    public static final Tokens.Code QUESTION = getCode(9, "?");
    public static final Tokens.Code STAR = getCode(10, "*");
    public static final Tokens.Code PLUS = getCode(11, "+");
    public static final Tokens.Code OPEN = getCode(12, "(");
    public static final Tokens.Code CLOSE = getCode(13, ")");
    public static final Tokens.Code DOT = getCode(14, ".");
    public static final Tokens.Code SEMI = getCode(15, ";");
    public static final Tokens.Code COLON = getCode(16, ":");
    public static final Tokens.Code LCURLY2 = getCode(17, "{{");
    public static final Tokens.Code RCURLY2 = getCode(18, "}}");
    public static final Tokens.Code SL_COMMENT = getCode(19, "unused");
    public static final Tokens.Code ML_COMMENT = getCode(20, "unused");
    public static final Tokens.Code EOLN = getCode(21, "unused");

}
