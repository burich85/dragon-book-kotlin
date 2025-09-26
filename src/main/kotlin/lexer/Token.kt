package bbr.lexer

import java.lang.reflect.Modifier

open class Token(val tag: Int) {
    override fun toString(): String {
        return tag.toChar().toString()
    }

    fun view(): String {
        return "${tagView()}(${toString()})"
    }

    private fun tagView(): String {
        return tagNamesMap[tag] ?: "TOKEN"
    }

    companion object {
        private val tagNamesMap: Map<Int, String> by lazy {
            val keys = mutableSetOf<Int>()

            Tag.javaClass
                .declaredFields
                .filter { Modifier.isStatic(it.modifiers) && Modifier.isFinal(it.modifiers) && Modifier.isPublic(it.modifiers) }
                .mapNotNull { declaredConst ->
                    declaredConst.isAccessible = true
                    val key = declaredConst.get(null) as? Int ?: return@mapNotNull null
                    if (keys.contains(key)) throw Error("Tag contains duplicated const value: $key")
                    keys.add(key)

                    key to declaredConst.name
                }
                .toMap()
        }
    }
}

