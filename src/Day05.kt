fun main() {

    fun parseInput(input: List<String>): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        val rules = mutableListOf<Pair<Int, Int>>()
        val updates = mutableListOf<List<Int>>()

        for (line in input) {
            if (line.contains("|")) {
                val (x, y) = line.split("|").map { it.toInt() }
                rules.add(x to y)
            } else {
                updates.add(line.split(",").map { it.toInt() })
            }
        }

        return rules to updates
    }

    fun isOrderCorrect(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
        val indexMap = update.withIndex().associate { it.value to it.index }

        for ((before, after) in rules) {
            val beforeIndex = indexMap.getOrDefault(before, -1)
            val afterIndex = indexMap.getOrDefault(after, -1)

            if (beforeIndex != -1 && afterIndex != -1 && beforeIndex >= afterIndex) {
                return false
            }
        }
        return true
    }

    fun orderUpdate(update: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
        val priorityMap = update.associateWith { 0 }.toMutableMap()

        for ((before, after) in rules) {
            if (before in priorityMap && after in priorityMap) {
                priorityMap[before] = priorityMap.getValue(before) - 1
                priorityMap[after] = priorityMap.getValue(after) + 1
            }
        }

        return update.sortedBy { priorityMap[it] }
    }


    fun getMiddleNumber(arr: List<Int>): Int {
        return arr[arr.size / 2]
    }

    fun part1(input: List<String>): Int {
        val (rules, updates) = parseInput(input)
        val correctlyOrderedUpdates = updates.filter { isOrderCorrect(it, rules) }

        return correctlyOrderedUpdates.sumOf { update -> getMiddleNumber(update) }
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = parseInput(input)
        val incorrectlyOrderedUpdates = updates.filter { !isOrderCorrect(it, rules) }
        val correctedMiddleNumbers = incorrectlyOrderedUpdates
            .map { orderUpdate(it, rules) }
            .map { getMiddleNumber(it) }

        return correctedMiddleNumbers.sum()
    }

    val testInput = readInput("input/Day05_test")
    val testInput2 = readInput("input/Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput2) == 123)

    val input = readInput("input/Day05")
    val input2 = readInput("input/Day05")
    part1(input).println()
    part2(input2).println()
}