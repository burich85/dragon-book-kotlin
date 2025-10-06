package bbr.lexer.symbols

open class Env(previous: Env?) {
    private val table = mutableMapOf<String, Symbol>()
    protected var prev: Env? = previous

    fun put(s: String, symbol: Symbol) {
        table[s] = symbol
    }

    fun get(s: String): Symbol? {
        var env: Env? = this

        while (env != null) {
            val found = env.table[s]
            if (found != null) {
                return found
            }

            env = env.prev
        }

        return null
    }
}

//TODO: Symbol class
typealias Symbol = Unit