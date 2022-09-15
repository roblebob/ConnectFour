
fun main() {
    val string = readln()
    // write here
    println(string.substringBeforeLast("u") + "u" + string.substringAfterLast("u").uppercase())
}