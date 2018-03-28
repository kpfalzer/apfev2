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

/**
 *
 * @author kpfalzer
 */
public class CharBuffer {
    public static final int EOF = -1;
    public static final int EOLN = '\n';

    public CharBuffer(char buf[]) {
        this.buf = buf;
    }

    protected CharBuffer() {
    }

    protected void setBuf(char buf[]) {
        assert (null == this.buf);
        this.buf = buf;
    }

    public class Mark {
        public Mark() {
            this.pos = CharBuffer.this.pos;
            this.col = CharBuffer.this.col;
            this.lineNum = CharBuffer.this.lineNum;
        }

        public final int pos, lineNum, col;
    }

    public Mark getMark() {
        return new Mark();
    }

    public void setMark(Mark mark) {
        this.pos = mark.pos;
        this.col = mark.col;
        this.lineNum = mark.lineNum;
    }

    public boolean isEOF(int la) {
        final int ix = pos + la;
        assert (0 <= ix);
        return (ix >= buf.length);
    }

    public boolean isEOF() {
        return isEOF(0);
    }

    public int peek(int la) {
        return isEOF(la) ? EOF : buf[pos + la];
    }

    public int peek() {
        return peek(0);
    }

    /**
     * Return character at la-th (lookahead) position.
     * @param la lookahead (>= 0).
     * @return character at la-th lookahead.
     * Advance current position la+1 positions and update lineNum and col accordingly.
     */
    public int accept(int la) {
        assert (0 <= la);
        int rval = peek(la);
        for (la++; 0 < la; la--) {
            if (EOLN == buf[pos]) {
                lineNum++;
                col = 0;
            }
            col++;
            pos++;
        }
        return rval;
    }

    public int accept() {
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

    /**
     * Current position in buf.
     */
    private int pos = 0;
    private int lineNum = 1;
    private int col = 1;
    private char buf[] = null;
    
}
