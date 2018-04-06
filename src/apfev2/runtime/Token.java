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

import static apfev2.runtime.Util.isNonNull;
import static apfev2.runtime.Util.isNull;

public class Token implements Acceptor {
    public Token(int code, String text, Acceptor trailing) {
        this.code  = code;
        this.text = new CharSequence(text);
        this.trailing = trailing;
    }

    public Token(int code, String text) {
        this(code, text, null);
    }

    public final int code;
    public final CharSequence text;
    public final Acceptor trailing;

    @Override
    public Accepted accept(CharBuffer cbuf) {
        final Location loc = cbuf.getLocation();
        Accepted accepted = text.accept(cbuf);
        if (isNull(accepted)) {
            return null;
        }
        accepted = isNonNull(trailing) ? trailing.accept(cbuf) : null;
        return getAccepted(loc, accepted);
    }

    private TokenAccepted getAccepted(Location loc, Accepted trailing) {
        String text = Token.this.toString();;
        if (isNonNull(trailing)) {
            text += trailing.toString();
        }
        return new TokenAccepted(loc, text, code);
    }
}
