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
    public static final int EQ = 2;
    public static final int LEFT_ARROW = 3;
    public static final int EXTOP = 4;
    public static final int SLASH = 5;
    public static final int BAR = 6;
    public static final int AND = 7;
    public static final int NOT = 8;
    public static final int QUESTION = 9;
    public static final int STAR = 10;
    public static final int PLUS = 11;
    public static final int OPEN = 12;
    public static final int CLOSE = 13;
    public static final int DOT = 14;
    public static final int SEMI = 15;
    public static final int COLON = 16;
    public static final int LCURLY2 = 17;
    public static final int RCURLY2 = 18;
    public static final int SL_COMMENT = 19;
    public static final int ML_COMMENT = 20;
    public static final int EOLN = 21;

    public static class MyAcceptor implements Acceptor {
        public MyAcceptor(int code) {
            this(new int[]{code});
        }

        public MyAcceptor(int[] prioritizedCodes) {
            this.prioritizedCodes = prioritizedCodes;
        }

        private int[] prioritizedCodes;

        @Override
        public Accepted accept(CharBuffer cbuf) {
            for (int code : prioritizedCodes) {

            }
        }

        private static Acceptor accept(int code, CharBuffer cbuf) {
            final Location loc = cbuf.getLocation();
            switch(code) {

            }
        }
    }

    public static class MyAccepted extends TokenAccepted {
        private MyAccepted(Location loc, int code, String text) {
            super(loc, text, code);
        }
    }
}
