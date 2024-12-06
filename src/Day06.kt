fun main() {

    fun findGuardStartPosition(map: List<String>): Pair<Pair<Int, Int>, Int>? {
        for (i in map.indices) {
            for (j in map[i].indices) {
                when (map[i][j]) {
                    '^' -> return Pair(i to j, 0)
                }
            }
        }
        return null
    }


    fun simulateGuardPath(map: List<String>): Set<Pair<Int, Int>> {
        val guardStart = findGuardStartPosition(map)
        val (startPosition, startDirection) = guardStart!!
        val visitedPositions = mutableSetOf<Pair<Int, Int>>()
        val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
        var currentDirection = startDirection
        var (x, y) = startPosition

        visitedPositions.add(x to y)

        while (true) {
            val (dx, dy) = directions[currentDirection]
            val newX = x + dx
            val newY = y + dy

            if (newX !in map.indices || newY !in map[0].indices) {
                break
            }

            if (map[newX][newY] == '#') {
                currentDirection = (currentDirection + 1) % 4
            } else {
                x = newX
                y = newY
                visitedPositions.add(x to y)
            }
        }

        return visitedPositions
    }

    fun checkForLoop(map: List<String>): Boolean {
        val guardStart = findGuardStartPosition(map)
        val (startPosition, startDirection) = guardStart!!
        var currentDirection = startDirection
        var (x, y) = startPosition
        val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))

        val visitedStates = mutableSetOf<Triple<Int, Int, Int>>()
        visitedStates.add(Triple(x, y, currentDirection))

        while (true) {
            val (dx, dy) = directions[currentDirection]
            val newX = x + dx
            val newY = y + dy

            if (newX !in map.indices || newY !in map[0].indices) {
                return true
            }

            val cell = map[newX][newY]
            if (cell == '#') {
                currentDirection = (currentDirection + 1) % 4
            } else {
                x = newX
                y = newY
            }

            val state = Triple(x, y, currentDirection)
            if (state in visitedStates) {
                return false
            }
            visitedStates.add(state)
        }
    }


    fun part1(input: List<String>): Int {
        return simulateGuardPath(input).size
    }

    fun part2(input: List<String>): Int {
        val visitedPositions = simulateGuardPath(input)
        val (startPos, _) = findGuardStartPosition(input)!!
        val startX = startPos.first
        val startY = startPos.second

        var count = 0


        for ((x, y) in visitedPositions) {
            if (x == startX && y == startY) {
                continue
            }
            if (input[x][y] == '#') {
                continue
            }

            val newMap = input.toMutableList().map { it.toCharArray() }.toTypedArray()
            newMap[x][y] = '#'
            val newMapStrings = newMap.map { String(it) }

            val noLoop = checkForLoop(newMapStrings)
            if (!noLoop) {
                count++
            }
        }

        return count
    }

    val testInput = readInput("input/Day06_test")
    val testInput2 = readInput("input/Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput2) == 6)

    val input = readInput("input/Day06")
    val input2 = readInput("input/Day06")
    part1(input).println()
    part2(input2).println()
}