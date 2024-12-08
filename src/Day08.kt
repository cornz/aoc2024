import kotlin.math.abs

fun main() {

    fun getAntennas(map: List<String>): Map<Char, List<Pair<Int, Int>>> {
        val antennaPositions = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        for (row in map.indices) {
            for (col in map[row].indices) {
                val char = map[row][col]
                if (char != '.') {
                    antennaPositions.computeIfAbsent(char) { mutableListOf() }
                        .add(row to col)
                }
            }
        }

        return antennaPositions
    }

    fun getNodes(
        map: List<String>,
        antennasByFreq: Map<Char, List<Pair<Int, Int>>>
    ): Set<Pair<Int, Int>> {

        fun isNodeInBounds(pos: Pair<Int, Int>, height: Int, width: Int): Boolean {
            val (x, y) = pos
            return x in 0 until height && y in 0 until width
        }

        fun calculateAntinodes(coords: List<Pair<Int, Int>>, height: Int, width: Int): Set<Pair<Int, Int>> {
            val antinodes = mutableSetOf<Pair<Int, Int>>()
            for (i in coords.indices) {
                val (firstX, firstY) = coords[i]
                for (j in i + 1 until coords.size) {
                    val (secondX, secondY) = coords[j]
                    val antinode1 = Pair(2 * firstX - secondX, 2 * firstY - secondY)
                    val antinode2 = Pair(2 * secondX - firstX, 2 * secondY - firstY)
                    if (isNodeInBounds(antinode1, height, width)) {
                        antinodes.add(antinode1)
                    }
                    if (isNodeInBounds(antinode2, height, width)) {
                        antinodes.add(antinode2)
                    }
                }
            }
            return antinodes
        }

        val height = map.size
        val width = if (height > 0) map[0].length else 0
        val antinodes = mutableSetOf<Pair<Int, Int>>()

        for ((_, coords) in antennasByFreq) {
            if (coords.size >= 2) {
                antinodes.addAll(calculateAntinodes(coords, height, width))
            }
        }

        return antinodes
    }

    fun countNodesWithHarmonics(
        map: List<String>,
        antennasByFrequency: Map<Char, List<Pair<Int, Int>>>
    ): Int {
        fun gcd(a: Int, b: Int): Int {
            return if (b == 0) a else gcd(b, a % b)
        }

        val height = map.size
        val width = map[0].length

        val antinodeLocations = mutableSetOf<Pair<Int, Int>>()

        for ((_, antennaPositions) in antennasByFrequency) {
            if (antennaPositions.size < 2) {
                continue
            }

            for (i in antennaPositions.indices) {
                for (j in i + 1 until antennaPositions.size) {

                    val (startX, startY) = antennaPositions[i]
                    val (endX, endY) = antennaPositions[j]

                    val deltaX = endX - startX
                    val deltaY = endY - startY
                    val greatestCommonDivisor = gcd(abs(deltaX), abs(deltaY))

                    val stepX = deltaX / greatestCommonDivisor
                    val stepY = deltaY / greatestCommonDivisor

                    fun calculateAntinodePath(x: Int, y: Int, stepX: Int, stepY: Int, forward: Boolean) {
                        var currentX = x
                        var currentY = y
                        while (currentX in 0 until height && currentY in 0 until width) {
                            antinodeLocations.add(currentX to currentY)
                            if (forward) {
                                currentX += stepX
                                currentY += stepY
                            } else {
                                currentX -= stepX
                                currentY -= stepY
                            }
                        }
                    }

                    calculateAntinodePath(startX, startY, stepX, stepY, forward = true)
                    calculateAntinodePath(startX, startY, stepX, stepY, forward = false)
                }
            }
        }

        return antinodeLocations.size
    }



    fun part1(input: List<String>): Int {
        val antennaPositions = getAntennas(input)
        val antinodes = getNodes(input, antennaPositions)

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val antennaPositions = getAntennas(input)
        val antinodesWithHarmonics = countNodesWithHarmonics(input, antennaPositions)
        return antinodesWithHarmonics
    }

    val testInput = readInput("input/Day08_test")
    val testInput2 = readInput("input/Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput2) == 34)

    val input = readInput("input/Day08")
    val input2 = readInput("input/Day08")
    part1(input).println()
    part2(input2).println()
}