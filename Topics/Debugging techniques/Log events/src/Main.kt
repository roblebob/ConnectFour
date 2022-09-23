fun String?.capitalize(): String? {
    println("Before: $this")
    return when {
        isNullOrBlank() -> this
        length == 1 -> uppercase()
        else -> this[0].uppercase() + substring(1)
    }.also { println("After: $it") }
}