import kotlin.math.pow

fun main() {
    // write your code here
    val name = readLine()!!
    val value = readln().toInt()

    println(
        when (name) {
            "amount" -> compoundInterest(amount = value.toDouble())
            "percent" -> compoundInterest(percent = value.toDouble())
            "years" -> compoundInterest(years = value.toDouble())
            else -> compoundInterest()
        }.toInt()
    )
}

fun compoundInterest(amount: Double = 1000.0, percent: Double = 5.0, years: Double = 10.0): Double =
    amount * (1.0 + percent / 100.0).pow(years)