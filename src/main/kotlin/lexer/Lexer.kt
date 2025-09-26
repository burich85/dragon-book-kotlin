package bbr.lexer

import bbr.lexer.lexer.Commentary
import bbr.lexer.lexer.Comparison

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
                    ' ', '\t', '\n' -> continue
                    else -> break
                }
            }
        }

        if (peek.isDigit())
            return buildNumber()

        if (peek.isLetter())
            return buildWord()

        if (peek == '/') {
            if (isEmpty()) {
                return buildTokenAndClean()
            }
            peekNext()

            return when (peek) {
                '/' -> buildOneLineCommentary()
                '*' -> buildMultiLineCommentary()
                else -> buildToken('/')
            }
        }

        if (peek == '<') {
            if (isEmpty()) {
                return Comparison(Tag.LT)
            }
            peekNext()
            if (peek == '=') {
                clean()
                return Comparison(Tag.LE)
            } else {
                return Comparison(Tag.LT)
            }
        }

        if (peek == '>') {
            if (isEmpty()) {
                return Comparison(Tag.GT)
            }
            peekNext()
            if (peek == '=') {
                clean()
                return Comparison(Tag.GE)
            } else {
                return Comparison(Tag.GT)
            }
        }

        if (peek == '!') {
            if (isEmpty()) {
                return buildTokenAndClean()
            }
            peekNext()
            if (peek == '=') {
                clean()
                return Comparison(Tag.NE)
            } else {
                return buildToken('!')
            }
        }

        if (peek == '=') {
            if (isEmpty()) {
                return buildTokenAndClean()
            }
            peekNext()
            if (peek == '=') {
                clean()
                return Comparison(Tag.EQ)
            } else {
                return buildToken('=')
            }
        }

        return buildTokenAndClean()
    }

    private fun isEmpty(): Boolean {
        return System.`in`.available() == 0
    }

    private fun clean() {
        peek = ' '
    }

    private fun buildToken(char: Char = peek): Token {
        return Token(char.code)
    }

    private fun buildTokenAndClean(char: Char = peek): Token {
        val token = Token(char.code)
        clean()
        return token
    }

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
                break
            }
            commentaryBuilder.append(peek)
        }
        clean()

        return Commentary(commentaryBuilder.toString())
    }

    private fun buildMultiLineCommentary(): Token {
        var prev = ' '
        val commentaryBuilder = StringBuilder()

        while (!isEmpty()) {
            peekNext()

            if (prev == '*' && peek == '/') {
                commentaryBuilder.deleteCharAt(commentaryBuilder.length - 1)
                break
            }

            commentaryBuilder.append(peek)
            prev = peek
        }
        clean()

        return Commentary(commentaryBuilder.toString())
    }

    private fun peekNext() {
        peek = System.`in`.read().toChar()

        if (peek == '\n') { //FIXME: potential problem, because it is the next symbol at the moment
            line++
        }
    }
}