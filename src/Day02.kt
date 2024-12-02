fun isReportSafe(report: List<Int>): Boolean {
    val differences = report.zipWithNext { a, b -> b - a }

    val allIncreasing = differences.all { it in 1..3 }
    val allDecreasing = differences.all { it in -3..-1 }

    return allIncreasing || allDecreasing
}


fun isSafeWithProblemDampener(levels: List<Int>): Boolean {
    if (isReportSafe(levels)) return true

    return levels.indices.any { index ->
        val modifiedList = levels.toMutableList().apply { removeAt(index) }
        isReportSafe(modifiedList)
    }
}


fun main() {


    fun part1(input: List<String>): Int {
        return input.count { line ->
            val levels = line.split(" ").map { it.toInt() }
            isReportSafe(levels)
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val levels = line.split(" ").map { it.toInt() }
            isSafeWithProblemDampener(levels)
        }
    }

    val testInput = readInput("input/Day02_test")
    val testInput2 = readInput("input/Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput2) == 4)

    val input = readInput("input/Day02")
    val input2 = readInput("input/Day02")
    part1(input).println()
    part2(input2).println()


}
