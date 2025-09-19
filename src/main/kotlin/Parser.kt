package bbr

import kotlin.properties.Delegates.notNull


class Parser {

    init {
        scanNext()
    }

    fun expr() {
        term()

        while (true) {
            when (lookahead) {
                PLUS_CODE -> {
                    match(PLUS_CODE)
                    term()
                    print(PLUS)
                }

                MINUS_CODE -> {
                    match(MINUS_CODE)
                    term()
                    print(MINUS)
                }

                else -> break
            }
        }
    }

    fun term() {
        val char = lookahead.toChar()
        if (char.isDigit()) {
            print(char)
            match(lookahead)
        } else {
            throw Error(MSG_SYNTAX_ERROR)
        }
    }

    fun match(toMatch: Int) {
        if (lookahead == toMatch) {
            scanNext()
        } else {
            throw Error(MSG_SYNTAX_ERROR)
        }
    }

    private fun scanNext() {
        lookahead = System.`in`.read()
    }

    companion object {
        private var lookahead by notNull<Int>()

        private const val PLUS = '+'
        private const val PLUS_CODE = '+'.code
        private const val MINUS = '-'
        private const val MINUS_CODE = '-'.code
        private const val MSG_SYNTAX_ERROR = "syntax error"
    }
}