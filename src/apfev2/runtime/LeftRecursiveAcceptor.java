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

import java.util.List;
import java.util.LinkedList;

import static apfev2.runtime.Util.isNonNull;
import static apfev2.runtime.Util.invariant;
import static apfev2.runtime.Util.isNull;


public class LeftRecursiveAcceptor implements Acceptor {
    /**
     * Create acceptor for Direct Left Recursion (DLR).
     * The order of prioritizedChoices has DLR Sequence first.
     *
     * @param firstNrIx          mark index of first choice (in prioritizedChoices) which is not DLR.
     * @param isDRR              true if Direct Right Recursive.
     * @param prioritizedChoices prioritized choices with DLR's first.
     */
    public LeftRecursiveAcceptor(int firstNrIx, boolean isDRR, Acceptor... prioritizedChoices) {
        this.firstNonRecursiveIx = firstNrIx;
        this.isDRR = isDRR;
        this.prioritizedChoices = prioritizedChoices;
    }

    private final int firstNonRecursiveIx;
    private final Acceptor[] prioritizedChoices;
    /**
     * True if definite right recursion
     */
    private final boolean isDRR;

    /**
     * A convenience class/struct to encapsulate state for left-recursive.
     * We encapsulate here, since this state is transient and our usage model will
     * create a singleton of LeftRecursiveAcceptor shaped for a nonterminal.
     * So, we'll instance within accept() to keep unique (per invocation).
     */
    private class Instance {
        /**
         * Track last acceptor choice, so if we accept seed, then we know which
         * choice was matched.
         */
        Accepted lastChoice = null;
        /**
         * Track last choice index, so if we accept the seed, then we know which
         * choice was selected.
         */
        int lastIx = -1;
        int lrCnt = 0;
        List<Accepted> items = null;

        private boolean hasSeed() {
            return 0 < lrCnt;
        }

        private boolean hasNoSeed() {
            return !hasSeed();
        }

        public Accepted accept(CharBuffer cbuf) {
            final CharBuffer.Mark start = cbuf.getMark();
            final Location loc = cbuf.getLocation();
            boolean ok = false;
            //iterate through non left-recursive the first time through and take first match.
            Accepted cand = null;
            for (int i = firstNonRecursiveIx; !ok; i++) {
                cand = prioritizedChoices[i].accept(cbuf);
                ok = isNonNull(cand);
                if (ok) {
                    lastChoice = cand;
                }
            }
            while (ok) {
                /**
                 * Upon entry, we have matched one of the non-left-recursive.
                 *
                 * We have also captured the essence of the match already in the
                 * subclass, so really just need subclass to grow seed.
                 */
                growSeed();
                if (isDRR) {
                    //only 1 pass on definite right recursion
                    invariant(1 == lrCnt);
                }
                /**
                 * Now we want to try the left recursive a <- a b ... / a c ...
                 */
                ok = false;
                for (int i = 0; !ok && (i < firstNonRecursiveIx); i++) {
                    cand = prioritizedChoices[i].accept(cbuf);
                    ok = isNonNull(cand);
                    if (ok) {
                        lastChoice = cand;
                    }
                }
            }
            if (isNull(items)) {
                cbuf.setMark(start);
                return null;
            }
            return new MyAccepted(loc, items);
        }

        private void growSeed() {
            if (isNull(items)) {
                items = new LinkedList<>();
            }
            invariant(isNonNull(lastChoice));
            items.add(lastChoice);
            lastChoice = null;
            lrCnt++;
        }
    }

    public static class MyAccepted extends Accepted {
        private MyAccepted(Location loc, List<Accepted> accepted) {
            super(loc);
            this.accepted = accepted.toArray(new Accepted[0]);
        }

        public final Accepted[] accepted;
    }

    @Override
    public Accepted accept(CharBuffer cbuf) {
        return (new Instance()).accept(cbuf);
    }
}
