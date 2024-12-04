fun main() {

    fun part1(input: List<String>): Int {
        val word = "XMAS"
        val directions = listOf(
            Pair(0, 1),
            Pair(1, 0),
            Pair(0, -1),
            Pair(-1, 0),
            Pair(1, 1),
            Pair(1, -1),
            Pair(-1, 1),
            Pair(-1, -1)
        )

        fun isWithinBounds(x: Int, y: Int): Boolean {
            return x in input.indices && y in input[x].indices
        }

        fun doesWordExistAt(x: Int, y: Int, dx: Int, dy: Int): Boolean {
            for (index in word.indices) {
                val newX = x + index * dx
                val newY = y + index * dy
                if (!isWithinBounds(newX, newY) || input[newX][newY] != word[index]) {
                    return false
                }
            }
            return true
        }

        var count = 0
        for (row in input.indices) {
            for (col in input[row].indices) {
                for ((dx, dy) in directions) {
                    if (doesWordExistAt(row, col, dx, dy)) {
                        count++
                    }
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val words = listOf("MAS", "SAM")

        fun isWithinBounds(x: Int, y: Int): Boolean {
            return x in input.indices && y in input[x].indices
        }

        fun checkForXPattern(centerX: Int, centerY: Int): Int {
            var matches = 0
            for (word1 in words) {
                for (word2 in words) {
                    val diagonal1Matches = word1.indices.all { offset ->
                        val x1 = centerX - 1 + offset
                        val y1 = centerY - 1 + offset
                        isWithinBounds(x1, y1) && input[x1][y1] == word1[offset]
                    }

                    val diagonal2Matches = word2.indices.all { offset ->
                        val x2 = centerX - 1 + offset
                        val y2 = centerY + 1 - offset
                        isWithinBounds(x2, y2) && input[x2][y2] == word2[offset]
                    }

                    if (diagonal1Matches && diagonal2Matches) {
                        matches++
                    }
                }
            }
            return matches
        }

        var count = 0

        for (row in input.indices) {
            for (col in input[row].indices) {
                count += checkForXPattern(row, col)
            }
        }

        return count
    }



    val testInput = readInput("input/Day04_test")
    val testInput2 = readInput("input/Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput2) == 9)

    val input = readInput("input/Day04")
    val input2 = readInput("input/Day04")
    part1(input).println()
    part2(input2).println()
}