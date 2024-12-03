fun main() {
    val regex = Regex("""mul\((\d+),(\d+)\)""")
    val instructionRegex = Regex("""do\(\)|don't\(\)|mul\(\d+,\d+\)""")

    fun evaluateInstructions(input: String): Int {
        var enableMul = true
        var sum = 0

        instructionRegex.findAll(input).forEach { matchResult ->
            when (val instruction = matchResult.value) {
                "do()" -> enableMul = true
                "don't()" -> enableMul = false
                else -> if (enableMul && instruction.startsWith("mul")) {
                    val (x, y) = regex.find(instruction)!!.destructured
                    sum += x.toInt() * y.toInt()
                }
            }
        }

        return sum
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            regex.findAll(line).sumOf { match ->
                val (x, y) = match.destructured
                x.toInt() * y.toInt()
            }
        }
    }

    fun part2(input: List<String>): Int {
        val concatenatedString = input.joinToString("")
        return evaluateInstructions(concatenatedString)
    }

    val testInput = readInput("input/Day03_test")
    val testInput2 = readInput("input/Day03_test2")
    check(part1(testInput) == 161)
    check(part2(testInput2) == 48)

    val input = readInput("input/Day03")
    val input2 = readInput("input/Day03")
    part1(input).println()
    part2(input2).println()
}