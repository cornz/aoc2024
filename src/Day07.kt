fun main() {

    fun canBeTrue(testValue: Long, numbers: List<Long>, variants: List<String>): Boolean {
        fun evaluateExpression(numbers: List<Long>, operators: List<String>): Long {
            var result = numbers[0]
            for (i in operators.indices) {
                when (operators[i]) {
                    "+" -> result += numbers[i + 1]
                    "*" -> result *= numbers[i + 1]
                    "||" -> result = "$result${numbers[i + 1]}".toLong()
                }
            }
            return result
        }

        fun generateCombinations(length: Long, operators: List<String>): List<List<String>> {
            if (length == 0L)  {
                return listOf(emptyList())
            }
            val smaller = generateCombinations(length - 1, operators)
            return operators.flatMap { op -> smaller.map { it + op } }
        }


        val combinations = generateCombinations(numbers.size - 1L, variants)

        for (operators in combinations) {
            if (evaluateExpression(numbers, operators) == testValue) {
                return true
            }
        }

        return false
    }


    fun calculateTotalCalibrationResult(equations: List<String>, variants: List<String>): Long {
        var total = 0L

        for (equation in equations) {
            val parts = equation.split(": ")
            val testValue = parts[0].toLong()
            val numbers = parts[1].split(" ").map { it.toLong() }

            if (canBeTrue(testValue, numbers, variants)) {
                total += testValue
            }
        }

        return total
    }

    fun part1(input: List<String>): Long {
        val variants = listOf("+", "*")
        return calculateTotalCalibrationResult(input, variants)
    }

    fun part2(input: List<String>): Long {
        val variants = listOf("+", "*", "||")
        return calculateTotalCalibrationResult(input, variants)
    }

    val testInput = readInput("input/Day07_test")
    val testInput2 = readInput("input/Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput2) == 11387L)

    val input = readInput("input/Day07")
    val input2 = readInput("input/Day07")
    part1(input).println()
    part2(input2).println()
}