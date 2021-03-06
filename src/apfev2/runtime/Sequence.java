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

import java.util.ArrayList;
import java.util.Collection;
import static apfev2.runtime.Util.isNull;

/**
 *
 * @author kpfalzer
 */
public class Sequence implements Acceptor {

    public Sequence(Acceptor... acceptors) {
        this.acceptors = acceptors;
    }

    @Override
    public Accepted accept(CharBuffer cbuf) {
        Collection<Accepted> accepted = null;
        Accepted acci;
        final Location loc = cbuf.getLocation();
        final CharBuffer.Mark start = cbuf.getMark();
        for (Acceptor acceptor : acceptors) {
            acci = acceptor.accept(cbuf);
            if (isNull(acci)) {
                cbuf.setMark(start);
                return null;
            }
            if (isNull(accepted)) {
                accepted = new ArrayList<>(acceptors.length);
            }
            accepted.add(acci);
        }
        return new MyAccepted(loc, accepted);
    }
    
    public static class MyAccepted extends Accepted {
        private MyAccepted(Location loc, Collection<Accepted> accepted) {
            super(loc);
            this.accepted = accepted.toArray(new Accepted[0]);
        }

        @Override
        public String toString() {
            return Util.toString(accepted);
        }

        public final Accepted[] accepted;
    }

    private final Acceptor[] acceptors;
}
