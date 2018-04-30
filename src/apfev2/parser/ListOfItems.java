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

import apfev2.runtime.Accepted;
import apfev2.runtime.Acceptor;
import apfev2.runtime.CharBuffer;
import apfev2.runtime.Location;
import apfev2.runtime.Predicate;

import java.util.LinkedList;
import java.util.List;

import static apfev2.runtime.Util.isNull;

/**
 * Convenience class to process list of items.
 */
public class ListOfItems implements Acceptor {
    public ListOfItems(Acceptor item, Acceptor sep) {
        this.itemType = item;
        this.sepType = sep;
    }

    public final Acceptor itemType, sepType;

    @Override
    public Accepted accept(CharBuffer cbuf) {
        List<Accepted> items = null;
        Accepted item;
        CharBuffer.Mark mark = cbuf.getMark();
        while (!cbuf.isEOF()) {
            item = itemType.accept(cbuf);
            if (isNull(item)) {
                break;
            } else if (isNull(items)) {
                items = new LinkedList<>();
            }
            items.add(item);
            mark = cbuf.getMark();
            item = sepType.accept(cbuf);
            if (isNull(item)) {
                break;
            }
        }
        cbuf.setMark(mark);
        return (isNull(items)) ? null : new MyAccepted(cbuf.getLocation(), items);
    }

    public static class MyAccepted extends Accepted {

        protected MyAccepted(Location loc, List<Accepted> items) {
            super(loc);
            this.items = items;
        }

        public final List<Accepted> items;
    }
}
