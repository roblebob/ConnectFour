fun main() {
    val timerValue = readLine()!!.toInt()
    val timer = ByteTimer(timerValue)
    println(timer.time)
}

class ByteTimer(var time: Int) {
    init {
        time = if (time < -128) {
            -128
        } else if (time > 127) {
            127
        } else {
            time
        }
    }
}