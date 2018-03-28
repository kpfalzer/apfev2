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

/**
 *
 * @author kpfalzer
 */
public class Repetition implements Acceptor {

    public Repetition(Acceptor acceptor, boolean isOneOrMore) {
        this.acceptor = acceptor;
        this.isOneOrMore = isOneOrMore;
    }

    @Override
    public Accepted accept(CharBuffer cbuf) {
        Collection<Accepted> accepted = new ArrayList<>();
        Accepted acci;
        while (true) {
            acci = acceptor.accept(cbuf);
            if (null == acci) break;
            accepted.add(acci);
        }
        return (isOneOrMore && accepted.isEmpty()) ? null : new MyAccepted(accepted);
    }
    
    public static class MyAccepted implements Accepted {
        private MyAccepted(Collection<Accepted> accepted) {
            this.accepted = accepted.toArray(new Accepted[0]);
        }
        
        private final Accepted[] accepted;
    }
    
    private final Acceptor acceptor;
    private final boolean isOneOrMore;
}
