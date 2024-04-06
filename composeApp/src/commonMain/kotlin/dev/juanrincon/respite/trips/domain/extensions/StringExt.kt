package dev.juanrincon.respite.trips.domain.extensions

fun String.getCityAbbreviation(): String {
    val words = this.split(" ")
    return when (words.size) {
        1 -> {
            val noVowels = words.first().replace("[AEIOUaeiou]".toRegex(), "")
            if (noVowels.length > 3) {
                "${noVowels.first()}${noVowels.get(noVowels.length / 2)}${noVowels.last()}"
            } else {
                noVowels
            }
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
