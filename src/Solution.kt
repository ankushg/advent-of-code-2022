import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal

abstract class Solution<AnswerType>(
        private val dayNumber: String,
        private val part1ExpectedAnswer: AnswerType,
        private val part2ExpectedAnswer: AnswerType? = null
) {
    abstract fun part1(input: List<String>): AnswerType
    abstract fun part2(input: List<String>): AnswerType

    fun main() {
        val testInput = readInput("day$dayNumber", "Day${dayNumber}_test")
        val input = readInput("day$dayNumber", "Day$dayNumber")

        val testResult1 = test(testInput, expected = part1ExpectedAnswer, ::part1)
        val answer1 = part1(input)

        val testResult2 = test(testInput, expected = part2ExpectedAnswer, ::part2)
        val answer2 = part2(input)

        val table = table {
            header {
                style(bold = true)
                row {
                    align = TextAlign.CENTER
                    cell("Advent of Code: Day $dayNumber") {
                        columnSpan = 3
                    }
                }
                row("Part", "Test", "Answer")
            }

            body {
                row("1", testResult1, answer1)
                row("2", testResult2, answer2)
            }
        }
        Terminal().println(table)
    }

    private fun test(input: List<String>, expected: AnswerType?, block: (List<String>) -> AnswerType): String {
        val testResult = block(input)
        return when {
            expected == null -> "TODO 🚧"
            testResult == expected -> "Passed ✅"
            else -> "Failed ❌\n(got $testResult, expected $expected)"
        }
    }
}