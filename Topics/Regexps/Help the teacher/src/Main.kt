fun main() {
    val report = readLine()!!
    //write your code here.
    println(report.matches("[0-9] wrong answers?".toRegex()))
}