package bbr.lexer

import bbr.lexer.lexer.Commentary

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
        if (peek == ' ') {
            while (!isEmpty()) {
                peekNext()

                when (peek) {
                    ' ', '\t' -> continue
                    '\n' -> line++
                    else -> break
                }
            }
        }

        if (peek.isDigit())
            return buildNumber()

        if (peek.isLetter())
            return buildWord()

        if (peek == '/') {
            val slashToken = buildToken()
            if (isEmpty()) {
                peek = ' '
                return slashToken
            }
            peekNext()

            return if (peek == '/')
                buildOneLineCommentary()
            else
                slashToken
        }

        val token = buildToken()
        peek = ' '
        return token
    }

    private fun isEmpty(): Boolean = System.`in`.available() == 0

    private fun buildToken(): Token = Token(peek.code)

    private fun buildWord(): Word {
        val lexemeBuilder = StringBuilder()
        do {
            lexemeBuilder.append(peek)
            peekNext()
        } while (peek.isLetterOrDigit())

        val lexeme = lexemeBuilder.toString()
        val word = words.getOrPut(lexeme) { Word(Tag.ID, lexeme) }
        return word
    }

    private fun buildNumber(): Num {
        var num = 0
        do {
            num = 10 * num + peek.digitToInt(10)
            peekNext()
        } while (peek.isDigit())
        return Num(num)
    }

    private fun buildOneLineCommentary(): Token {
        val commentaryBuilder = StringBuilder()
        while (!isEmpty()) {
            peekNext()
            if (peek == '\n') {
                line++
                break
            }
            commentaryBuilder.append(peek)
        }
        peek = ' '

        return Commentary(commentaryBuilder.toString())
    }

    private fun peekNext() {
        peek = System.`in`.read().toChar()
    }
}