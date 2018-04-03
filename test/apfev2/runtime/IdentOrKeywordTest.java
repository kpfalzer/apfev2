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

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IdentOrKeywordTest {

    @Test
    void accept() {
        final String DATA =
                "ident_2 keyword1 anotherkwrd2 __anIdent";
        final CharBuffer cbuf = new CharBuffer(DATA.toCharArray());
        final Map<String,Integer> codeByKeyword = new HashMap<>();
        codeByKeyword.put("keyword1", 2);
        codeByKeyword.put("anotherkwrd2", 3);
        final Acceptor acceptor = new IdentOrKeyword(codeByKeyword);

        TokenAccepted token = null;

        token = (TokenAccepted) acceptor.accept(cbuf);
        assertNotNull(token);
        assertTrue(IdentOrKeyword.IDENT == token.code);

        assertNotNull(SPACING.accept(cbuf));

        token = (TokenAccepted) acceptor.accept(cbuf);
        assertNotNull(token);
        assertTrue(codeByKeyword.get("keyword1") == token.code);

        assertNotNull(SPACING.accept(cbuf));

        token = (TokenAccepted) acceptor.accept(cbuf);
        assertNotNull(token);
        assertTrue(codeByKeyword.get("anotherkwrd2") == token.code);

        assertNotNull(SPACING.accept(cbuf));

        token = (TokenAccepted) acceptor.accept(cbuf);
        assertNotNull(token);
        assertTrue(IdentOrKeyword.IDENT == token.code);

        assertNull(SPACING.accept(cbuf));
    }

    private static final Acceptor SPACING = new Repetition(new CharClass(" \t"), Repetition.EType.eOneOrMore);
}