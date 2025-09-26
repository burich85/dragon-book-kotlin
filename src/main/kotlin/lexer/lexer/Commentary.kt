package bbr.lexer.lexer

import bbr.lexer.Tag
import bbr.lexer.Token

class Commentary(val commentary: String) : Token(Tag.COMMENTARY) {
    override fun toString(): String {
        return commentary
    }
}