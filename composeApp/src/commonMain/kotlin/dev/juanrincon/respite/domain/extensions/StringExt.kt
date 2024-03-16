package dev.juanrincon.respite.domain.extensions

fun String.getCityAbbreviation(): String {
    val words = this.split(" ")
    return when (words.size) {
        1 -> {
            words.first().replace("[AEIOUaeiou]", "")
        }
        2 -> {
            val firstWord = words.first()
            val secondWord = words.last()
            return if (firstWord.length > 2) {
                "${firstWord.first()}${secondWord.first()}"
            } else {
                firstWord + secondWord.first()
            }
        }
        else -> {
            val abbr = words.fold("") { acc: String, s: String ->
                acc + s.first()
            }
            abbr
        }
    }
}
