package bbr.lexer

fun main() {
    val lexer = Lexer()

    val tokens = mutableListOf<Token>()

    while (System.`in`.available() > 0) {
        tokens.add(lexer.scan())
    }

    println()
    for (token in tokens) {
        println(token.view())
    }
}
