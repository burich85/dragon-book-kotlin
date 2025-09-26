package bbr.lexer

fun main() {
    val lexer = Lexer()

    while (System.`in`.available() > 0) {
        val token = lexer.scan()
        println(token.view())
    }
}
