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
public class PrioritizedChoice implements Acceptor {

    public PrioritizedChoice(Acceptor... choices) {
        this.choices = choices;
    }

    @Override
    public Accepted accept(CharBuffer cbuf) {
        Accepted acci;
        //todo: use ParallelStream?
        for (int i = 0; i < choices.length; i++) {
            acci = choices[i].accept(cbuf);
            if (null != acci) {
                return new MyAccepted(i, acci);
            }
        }
        return null;
    }
    
    public static class MyAccepted implements Accepted {
        private MyAccepted(int i, Accepted accepted) {
            this.i = i;
            this.choice = accepted;
        }
        
        private final int i;
        private final Accepted choice;
    }
    
    private final Acceptor[] choices;
}
