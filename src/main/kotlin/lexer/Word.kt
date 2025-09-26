package bbr.lexer

class Word(tag: Int, val lexeme: String) : Token(tag) {
    override fun toString(): String {
        return lexeme
    }
}