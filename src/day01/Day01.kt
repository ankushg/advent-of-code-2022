import java.util.PriorityQueue

@JvmInline
private value class Elf(private val inventory: List<Int>) {
    val total get() = inventory.sum()
}

object Day01 : Solution<Int>(
        dayNumber = "01",
        part1ExpectedAnswer = 24000,
        part2ExpectedAnswer = 45000
) {
    private fun buildElfList(input: List<String>): List<Elf> {
        val elves = mutableListOf<Elf>()

        var currentElfInventory = mutableListOf<Int>()
        input.forEach {
            if (it.isBlank()) {
                elves.add(Elf(currentElfInventory))
                currentElfInventory = mutableListOf()
            } else {
                currentElfInventory.add(it.toInt())
            }
        }
        if (currentElfInventory.isNotEmpty()) {
            elves.add(Elf(currentElfInventory))
        }

        return elves
    }

    override fun part1(input: List<String>): Int {
        val elves = buildElfList(input)
        return elves.maxOf { it.total }
    }

    override fun part2(input: List<String>): Int {
        val elves = buildElfList(input)

        // simple stdlib
        return elves.map { it.total }.sortedDescending().take(3).sum()

//        // Using MinHeap
//        val comparator = compareBy<Elf> { it.total }
//        val top3Heap = PriorityQueue<Elf>(3, comparator)
//
//        elves.take(3).forEach(top3Heap::add)
//
//        elves.drop(3).forEach { newElf ->
//            if (newElf.total > (top3Heap.peek()?.total ?: Int.MIN_VALUE)) {
//                top3Heap.poll()
//                top3Heap.add(newElf)
//            }
//        }
//
//        return top3Heap.sumOf { it.total }
    }
}

fun main() {
    Day01.main()
}

