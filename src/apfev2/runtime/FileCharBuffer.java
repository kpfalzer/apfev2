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

import java.io.*;

/**
 * Encapsulate text file as CharBuffer and drop any linefeed ('\r').
 */
public class FileCharBuffer extends CharBuffer {
    public FileCharBuffer(String filename) throws IOException {
        this.filename = filename;
        initialize();
    }

    public class Mark extends CharBuffer.Mark {
        public Mark(String filename, CharBuffer.Mark mark) {
            super(mark);
            this.filename = filename;
        }
        public final String filename;
    }

    public Mark getMark() {
        return new Mark(filename, super.getMark());
    }

    @Override
    public Location getLocation() {
        return new Location(filename, getLine(), getCol());
    }

    private static final int LF = '\r';

    private void initialize() throws IOException {
        char fbuf[] = null;
        int pos = 0;
        char[] cbuf = new char[1024];
        for (int loop = 0; loop < 2; loop++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            int charCount = 0;
            int nchars;
            while ((nchars = reader.read(cbuf)) > -1) {
                for (int i = 0; i < nchars; i++) {
                    if (LF != cbuf[i]) {
                        if (0 == loop) {
                            nchars++;
                        } else {
                            fbuf[pos++] = cbuf[i];
                        }
                    }
                }
            }
            reader.close();
            if (0 == loop) {
                fbuf = new char[charCount];
            }
        }
        super.setBuf(fbuf);
    }

    private final String filename;
}
