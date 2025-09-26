package bbr.lexer

class Num(val num: String) : Token(Tag.NUM) {
    override fun toString(): String {
        return num
    }
}