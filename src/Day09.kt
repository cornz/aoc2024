fun main() {

    fun parseDiskMap(input: String): MutableList<Int> {
        val blocks = mutableListOf<Int>()
        var isFile = true
        var fileId = 0

        for (ch in input) {
            val length = ch.digitToInt()
            if (length == 0) {
                isFile = !isFile
                continue
            }

            if (isFile) {
                repeat(length) {
                    blocks.add(fileId)
                }
                fileId++
            } else {
                repeat(length) {
                    blocks.add(-1)
                }
            }
            isFile = !isFile
        }

        return blocks
    }

    fun compactDisk(blocks: MutableList<Int>) {
        while (true) {
            val firstFree = blocks.indexOf(-1)
            if (firstFree == -1) {
                break
            }

            val anyFileAfter = (firstFree + 1 until blocks.size).any { blocks[it] != -1 }
            if (!anyFileAfter) {
                break
            }

            val rightmostFileIndex = (blocks.size - 1 downTo 0).firstOrNull { blocks[it] != -1 } ?: break

            val temp = blocks[firstFree]
            blocks[firstFree] = blocks[rightmostFileIndex]
            blocks[rightmostFileIndex] = temp
        }
    }

    fun computeChecksum(blocks: List<Int>): Long {
        var checksum = 0L
        for ((pos, id) in blocks.withIndex()) {
            if (id != -1) {
                checksum += pos.toLong() * id.toLong()
            }
        }
        return checksum
    }


    fun moveFile(blocks: MutableList<Int>, fileId: Int) {
        val filePositions = blocks.withIndex().filter { it.value == fileId }.map { it.index }
        val fileStart = filePositions.first()
        val fileEnd = filePositions.last()
        val fileLength = filePositions.size

        var currentFreeStart = -1
        var currentFreeLength = 0
        var chosenStart = -1

        for (i in 0 until fileStart) {
            if (blocks[i] == -1) {
                if (currentFreeStart == -1) {
                    currentFreeStart = i
                    currentFreeLength = 1
                } else {
                    currentFreeLength++
                }

                if (currentFreeLength >= fileLength) {
                    chosenStart = currentFreeStart
                    break
                }
            } else {
                currentFreeStart = -1
                currentFreeLength = 0
            }
        }

        if (chosenStart == -1) {
            return
        }

        for (i in fileStart..fileEnd) {
            blocks[i] = -1
        }

        for (i in 0 until fileLength) {
            blocks[chosenStart + i] = fileId
        }
    }

    fun part1(input: List<String>): Long {
        val blocks = parseDiskMap(input[0])
        compactDisk(blocks)
        val checksum = computeChecksum(blocks)

        return checksum
    }

    fun part2(input: List<String>): Long {
        val blocks = parseDiskMap(input[0])

        val maxFileId = blocks.maxOrNull()?.takeIf { it != -1 } ?: -1

        for (fileId in maxFileId downTo 0) {
            moveFile(blocks, fileId)
        }

        val checksum = computeChecksum(blocks)
        return checksum
    }

    val testInput = readInput("input/Day09_test")
    val testInput2 = readInput("input/Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput2) == 2858L)

    val input = readInput("input/Day09")
    val input2 = readInput("input/Day09")
    part1(input).println()
    part2(input2).println()
}

