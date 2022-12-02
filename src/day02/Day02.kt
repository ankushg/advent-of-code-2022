object Day02 : Solution<Int>(
    dayNumber = "02",
    part1ExpectedAnswer = 15,
    part2ExpectedAnswer = 12
) {
    // region helpers
    private fun <T> List<T>.asPair(): Pair<T, T> = this[0]!! to this[1]!!

    private fun scorePair(pair: Pair<Shape, Shape>): Int {
        return pair.second.score + determineResult(pair.first, pair.second).score
    }

    enum class Shape(val score: Int) {
        ROCK(1), PAPER(2), SCISSORS(3)
    }

    enum class GameResult(val score: Int) {
        LOSS(0), DRAW(3), WIN(6)
    }

    // gameResultsByOurShapeByOpponentShape[theirShape][ourshape] = result
    private val gameResultsByOurShapeByOpponentShape = mapOf(
        Shape.ROCK to mapOf(
            Shape.ROCK to GameResult.DRAW,
            Shape.PAPER to GameResult.WIN,
            Shape.SCISSORS to GameResult.LOSS
        ),
        Shape.PAPER to mapOf(
            Shape.ROCK to GameResult.LOSS,
            Shape.PAPER to GameResult.DRAW,
            Shape.SCISSORS to GameResult.WIN
        ),
        Shape.SCISSORS to mapOf(
            Shape.ROCK to GameResult.WIN,
            Shape.PAPER to GameResult.LOSS,
            Shape.SCISSORS to GameResult.DRAW
        )
    )

    private fun determineResult(theirShape: Shape, ourShape: Shape): GameResult {
        return gameResultsByOurShapeByOpponentShape[theirShape]!![ourShape]!!
    }
    // endregion

    // region parsing
    private val shapeCodeMap = mapOf(
        "A" to Shape.ROCK,
        "B" to Shape.PAPER,
        "C" to Shape.SCISSORS,

        // only used in Part 1
        "X" to Shape.ROCK,
        "Y" to Shape.PAPER,
        "Z" to Shape.SCISSORS
    )

    private val resultCodeMap = mapOf(
        "X" to GameResult.LOSS,
        "Y" to GameResult.DRAW,
        "Z" to GameResult.WIN
    )
    // endregion

    // region Part1
    override fun part1(input: List<String>): Int {
        return parseInputPart1(input)
            .sumOf(::scorePair)
    }

    private fun parseInputPart1(input: List<String>): List<Pair<Shape, Shape>> {
        return input.map { row ->
            row.split(" ").map { shapeCode -> shapeCodeMap[shapeCode]!! }.asPair()
        }
    }

    // endregion

    // region Part2
    override fun part2(input: List<String>): Int {
        return parseInputPart2(input)
            .map(::determineShapesFromResult)
            .sumOf(::scorePair)
    }

    private fun parseInputPart2(input: List<String>): List<Pair<Shape, GameResult>> {
        return input.map { it.split(" ").asPair() }
            .map { (them, desiredResult) ->
                shapeCodeMap[them]!! to resultCodeMap[desiredResult]!!
            }
    }

    private fun determineShapesFromResult(input: Pair<Shape, GameResult>): Pair<Shape, Shape> {
        val (them, desiredResult) = input
        return gameResultsByOurShapeByOpponentShape[them]!!
            .firstNotNullOf { (candidateShape, result) ->
                if (result == desiredResult) them to candidateShape else null
            }
    }
    //endregion
}

fun main() {
    Day02.main()
}
