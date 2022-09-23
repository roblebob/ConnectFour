// complete this function, add default values
fun carPrice(old: Int = 5, kilometers: Int = 100_000, maximumSpeed: Int = 120, automatic: Boolean = false){
    var price = 20_000
    price += - old * 2_000
    price += (maximumSpeed - 120) * 100
    price += - (kilometers / 10_000) * 200
    price += if (automatic) { 1_500 } else { 0 }

    println(price)
}