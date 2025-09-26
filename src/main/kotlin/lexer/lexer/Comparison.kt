package bbr.lexer.lexer

import bbr.lexer.Tag
import bbr.lexer.Token

class Comparison(tag: Int) : Token(tag) {
    override fun toString(): String {
        return when (tag) {
            Tag.LT -> "<"
            Tag.LE -> "<="
            Tag.EQ -> "=="
            Tag.NE -> "!="
            Tag.GT -> ">"
            Tag.GE -> ">="

            else -> throw Error("Unknown comparison tag $tag")
        }
    }
}