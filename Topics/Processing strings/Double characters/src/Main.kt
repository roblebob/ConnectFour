fun main() {
    // write your code here
    val string = readLine()!!
    val mutableList = mutableListOf<String>()
    for (char in string) {
        repeat(2) {
            mutableList.add(char.toString())
        }
    }

    println(mutableList.joinToString(""))
}