package connectfour

fun main() {
    println("Connect Four")

    val setup = Setup()

    val turn2name = { turn: Boolean ->  if (turn) { setup.name1 } else { setup.name2 } }
    val turn2discColor = { turn: Boolean ->  if (turn) { "o" } else { "*" } }
    val winString = { turn: Boolean -> turn2discColor(turn).repeat(4)}

    val score = mutableMapOf<String, Int>()
    score[setup.name1] = 0
    score[setup.name2] = 0

    var mTurn = true

    outerloop@ for (round in 1..setup.rounds) {

        if (setup.rounds > 1) {
            println("Game #$round")
        }

        var mBoard = getBoard(setup.rows, setup.cols)
        println(mBoard)

        loop@ while (true) {
            println( turn2name(mTurn) + "'s turn:")

            val s = readln()
            if (s == "end") {
                break@outerloop
            }
            val targetCol = try { s.toInt() } catch (e: Exception) {
                println("Incorrect column number")
                continue
            }
            if (targetCol !in 1..setup.cols) {
                println("The column number is out of range (1 - ${setup.cols})")
                continue
            }

            var targetRow = -1


            // put disc in targetCol
            for (row in 1..setup.rows) {

                val idx = (setup.rows - row +     1 /* heading */ ) *
                        (2 * setup.cols + 1 +     1 /* newline */ ) +
                        (2 * targetCol - 1)

                if (mBoard[idx] == ' ') {
                    mBoard = mBoard.substring(0 until idx) +
                            turn2discColor(mTurn) +
                            mBoard.substring(idx +1 until mBoard.length)

                    targetRow = row
                    break

                } else if (row == setup.rows) {
                    println("Column $targetCol is full")
                    continue@loop
                }
            }

            println(mBoard)

            if (getLine(targetRow=targetRow, targetCol=targetCol, type="horizontal", board=mBoard, setup=setup)!!.contains(winString(mTurn)) ||
                getLine(targetRow=targetRow, targetCol=targetCol, type="vertical", board=mBoard, setup=setup)!!.contains(winString(mTurn)) ||
                getLine(targetRow=targetRow, targetCol=targetCol, type="diagonal", board=mBoard, setup=setup)!!.contains(winString(mTurn)) ||
                getLine(targetRow=targetRow, targetCol=targetCol, type="antidiagonal", board=mBoard, setup=setup)!!.contains(winString(mTurn))
            ){
                println("Player ${turn2name(mTurn)} won")
                score[turn2name(mTurn)] = score[turn2name(mTurn)]!! + 2
                break

            } else if (!mBoard.substringAfter("\n").contains(" ")) {
                println("It is a draw")
                score[setup.name1] = score[setup.name1]!! + 1
                score[setup.name2] = score[setup.name2]!! + 1
                break
            }

            mTurn = !mTurn  // alternate between moves
        }
        if (setup.rounds > 1) {
            println("Score\n" +
                    "${setup.name1}: ${score[setup.name1]} ${setup.name2}: ${score[setup.name2]}")
        }

        mTurn = !mTurn // alternate between games/rounds (who begins)
    }
    println("Game over!")
}




fun getLine(targetRow: Int, targetCol: Int, type: String, board: String, setup: Setup): String? {
    val temp = board
        .substringAfter("\n")
        .substringBeforeLast("\n")
        .replace("[╝║═╚]".toRegex(), "")

    val idx = (setup.rows - targetRow) * (setup.cols + 1) + targetCol - 1

    return when (type) {
        "horizontal" -> temp.substring(0 until idx).substringAfterLast("\n") +
                        temp.substring(idx until temp.length).substringBefore("\n")

        "vertical" -> {
            var line = ""
            for (row in 0 until setup.rows) {
                line += temp[targetCol - 1 + row * (setup.cols + 1)]
            }
            line
        }

        "diagonal" -> {
            var line = temp[idx].toString()
            var idx1 = idx - setup.cols
            var idx2 = idx + setup.cols
            while (idx1 >= 0 || idx2 < temp.length) {
                if (idx1 >= 0) {
                    line += temp[idx1].toString()
                }
                if (idx2 < temp.length) {
                    line = temp[idx2].toString() + line
                }

                idx1 += -setup.cols
                idx2 += setup.cols

                if (idx1 >= 0 && temp[idx1] == '\n') {
                    idx1 = -1
                }
                if (idx2 < temp.length && temp[idx2] == '\n') {
                    idx2 = temp.length
                }
            }
            line
        }

        "antidiagonal" -> {
            var line = temp[idx].toString()
            var idx1 = idx - setup.cols - 2
            var idx2 = idx + setup.cols + 2
            while (idx1 >= 0 || idx2 < temp.length) {
                if (idx1 >= 0) {
                    line += temp[idx1].toString()
                }
                if (idx2 < temp.length) {
                    line = temp[idx2].toString() + line
                }

                idx1 += -setup.cols - 2
                idx2 += setup.cols + 2

                if (idx1 >= 0 && temp[idx1] == '\n') {
                    idx1 = -1
                }
                if (idx2 < temp.length && temp[idx2] == '\n') {
                    idx2 = temp.length
                }
            }
            line
        }

        else -> null
    }
}


class Setup(val name1: String = run { println("First player's name:"); readln() },
            val name2: String = run { println("Second player's name:"); readln() },
            var rows: Int = 6, var cols: Int = 7, var rounds: Int = 1) {

    init {
        // getting rows and cols
        while (true) {
            println("Set the board dimensions (Rows x Columns)\n" +
                    "Press Enter for default (6 x 7)")
            val s = readln()

            if (s.isEmpty()) { // ... defaults
                break
            } else if (!s.matches("[\\s]*[0-9]+[\\s]*[xX][\\s]*[0-9]+[\\s]*".toRegex())) {
                println("Invalid input")
                continue
            }

            rows = s.replace("[\\s]*".toRegex(), "").replace("[xX][0-9]+".toRegex(), "").toInt()
            cols = s.replace("[\\s]*".toRegex(), "").replace("[0-9]+[xX]".toRegex(), "").toInt()

            if (rows !in 5..9) {
                println("Board rows should be from 5 to 9")
                continue
            }
            if (cols !in 5..9) {
                println("Board columns should be from 5 to 9")
                continue
            }

            break
        }

        // getting the number of games (rounds)
        while (true) {
            println("Do you want to play single or multiple games?\n" +
                    "For a single game, input 1 or press Enter\n" +
                    "Input a number of games:")

            val s = readln()

            rounds = if (s.isEmpty()) {
                1
            } else if (!s.matches("[1-9]+[0-9]*".toRegex())) {
                println("Invalid input")
                continue
            } else {
                s.toInt()
            }

            break
        }

        // printing the resulting setup
        println("$name1 VS $name2\n" +
                "$rows X $cols board\n" +
                if (rounds == 1) { "Single game" } else { "Total $rounds games" })
    }
}


fun getBoard(rows: Int, cols: Int): String {

    var s = ""
    for (col in 1..cols * 2 + 1) {
        s += if (col % 2 == 0) { "${col / 2}" } else { " " }
    }
    for (row in 1..rows) {
        s += "\n"
        for (col in 1..cols * 2 + 1) {
            s += if (col % 2 == 1) { "║" } else { " " }
        }
    }
    s += "\n"
    for (col in 1..cols * 2 + 1) {
        s += when {
            col == 1 -> "╚"
            col == cols * 2 + 1 -> "╝"
            col % 2 == 0 -> "═"
            else -> "╩"
        }
    }
    return s
}


