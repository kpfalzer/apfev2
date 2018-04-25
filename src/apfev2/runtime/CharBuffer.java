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
import static apfev2.runtime.Util.isNull;


/**
 *
 * @author kpfalzer
 */
public class CharBuffer {
    private static final char EOF = 0xff;
    private static final char EOLN = '\n';

    public CharBuffer(char buf[]) {
        this.buf = buf;
    }

    protected CharBuffer() {
    }

    protected void setBuf(char buf[]) {
        assert (isNull(this.buf));
        this.buf = buf;
    }

    public Location getLocation() {
        return new Location(line, col);
    }

    public static class Mark {
        public Mark(int pos, int line, int col) {
            this.pos = pos;
            this.col = col;
            this.line = line;
        }

        public Mark(Mark mark) {
            this(mark.pos, mark.line, mark.col);
        }

        public final int pos, line, col;
    }

    public Mark getMark() {
        return new Mark(pos, line, col);
    }

    public void setMark(Mark mark) {
        this.pos = mark.pos;
        this.col = mark.col;
        this.line = mark.line;
    }

    public boolean isEOF(int la) {
        final int ix = pos + la;
        assert (0 <= ix);
        return (ix >= buf.length);
    }

    public boolean isEOF() {
        return isEOF(0);
    }

    public char peek(int la) {
        return isEOF(la) ? EOF : buf[pos + la];
    }

    public char peek() {
        return peek(0);
    }

    public char peekAndCheckUnexpected(char close) {
        Error.checkUnexpected(this, close);
        return peek();
    }

    public char acceptUnlessUnexpected(char close) {
        peekAndCheckUnexpected(close);
        return accept();
    }

    public boolean isEOLN() {
        return isEOLN(peek());
    }

    public static boolean isEOLN(char ch) {
        return EOLN == ch;
    }

    /**
     * Return character at la-th (lookahead) position.
     * @param la lookahead (>= 0).
     * @return character at la-th lookahead.
     * Advance current position la+1 positions and update lineNum and col accordingly.
     */
    public char accept(int la) {
        assert (0 <= la);
        char rval = peek(la);
        for (la++; 0 < la; la--) {
            if (isEOLN(buf[pos])) {
                line++;
                col = 0;
            }
            col++;
            pos++;
        }
        return rval;
    }

    public char accept() {
        return accept(0);
    }

    /**
     * Check if current position matches string.
     * @param s string to match.
     * @return true if match, else false.
     */
    public boolean match(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != peek(i)) {
                return false;
            }
        }
        return true;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    /**
     * Current position in buf.
     */
    private int pos = 0;
    private int line = 1;
    private int col = 1;
    private char buf[] = null;
    
}
