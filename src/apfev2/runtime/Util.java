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

public class Util {
    public static boolean isNull(Object obj) {
        return (null == obj);
    }

    public static boolean isNonNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isAlpha(char ch) {
        return (ch >= 'a' && ch <= 'z')
                || (ch >= 'A' && ch <= 'Z');
    }

    public static boolean isDigit(char ch) {
        return (ch >= '0' && ch <= '9');
    }

    public static void invariant(boolean cond) {
        if (!cond) {
            throw new RuntimeException("invariant failed");
        }
    }

    public static String toString(final Accepted accepted[]) {
        StringBuilder buf = new StringBuilder();
        for (Accepted acc : accepted) {
            buf.append(acc.toString());
        }
        return buf.toString();
    }

    public static class Pair<T1,T2> {
        public Pair() {}

        public Pair(T1 v1, T2 v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        public T1 v1() {
            return v1;
        }

        public T2 v2() {
            return v2;
        }

        public Pair v1(T1 v1) {
            this.v1 = v1;
            return this;
        }

        public Pair v2(T2 v2) {
            this.v2 = v2;
            return this;
        }

        private T1 v1 = null;
        private T2 v2 = null;
    }

    public static <T extends Accepted> T downcast(Accepted accepted) {
        return (T)accepted;
    }

    public static String toString(Accepted accepted) {
        return isNonNull(accepted) ? accepted.toString() : "";
    }
}
