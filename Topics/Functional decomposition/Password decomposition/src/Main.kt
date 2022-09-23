fun passwordLessThan5(): String {
    // write here
    var password = ""//readln()
    return if (password.length < 5) {
        println("Your password should be longer than five characters, please write a new password")
        List(16) {
            (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
        }.joinToString("")
    }
    else {
        password
    }
}

fun passwordLongerThan5(password: String) {
    // write here
    println("Your password is correct, please enter your name")
    val name = "John"
    println("Congratulations! Password for $name is $password")
}