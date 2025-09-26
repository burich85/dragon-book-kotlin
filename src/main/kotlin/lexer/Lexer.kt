package bbr.lexer

class Lexer {
    var line: Int = 1

    private var peek: Char = ' '
    private val words = mutableMapOf(
        "true" to Word(Tag.TRUE, "true"),
        "false" to Word(Tag.FALSE, "false")
    )

    init {
        //TODO: find the way to make input ready without this
        System.`in`.mark(0)
        System.`in`.read()
        System.`in`.reset()
    }

    private fun reserve(word: Word) {
        words.put(word.lexeme, word)
    }

    fun scan(): Token {
        while (System.`in`.available() > 0) {
            peekNext()

            when (peek) {
                ' ', '\t' -> continue
                '\n' -> line++
                else -> break
            }
        }

        if (peek.isDigit()) {
            var num = 0
            do {
                num = 10 * num + peek.digitToInt(10)
                peekNext()
            } while (peek.isDigit())
            return Num(num)
        }
        if (peek.isLetter()) {
            val lexemeBuilder = StringBuilder()
            do {
                lexemeBuilder.append(peek)
                peekNext()
            } while (peek.isLetterOrDigit())

            val lexeme = lexemeBuilder.toString()
            val word = words.getOrPut(lexeme) { Word(Tag.ID, lexeme) }
            return word
        }
        val token = Token(peek.code)
        peek = ' '
        return token
    }

    private fun peekNext() {
        peek = System.`in`.read().toChar()
    }
}