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
 * @author kpfalzer
 */
public class Repetition implements Acceptor {

    public enum EType {eZeroOrOne, eZeroOrMore, eOneOrMore}

    ;

    public Repetition(Acceptor acceptor, EType type) {
        this.acceptor = acceptor;
        this.type = type;
    }

    @Override
    public Accepted accept(CharBuffer cbuf) {
        Collection<Accepted> accepted = new ArrayList<>();
        Accepted acci;
        final Location loc = cbuf.getLocation();
        while (true) {
            acci = acceptor.accept(cbuf);
            if (isNull(acci)) break;
            accepted.add(acci);
            if (EType.eZeroOrOne == type) break;
        }
        return (isOneOrMore() && accepted.isEmpty()) ? null : new MyAccepted(loc, accepted);
    }

    public boolean isOneOrMore() {
        return EType.eOneOrMore == type;
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

    private final Acceptor acceptor;
    private final EType type;
}
