package bbr.lexer

class Num(val num: Int) : Token(Tag.NUM) {
    override fun toString(): String {
        return num.toString()
    }
}