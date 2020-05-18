
fun main(args: Array<String>) {
    val myList = listOf(1, 25, 75, 39, 7, 2, 35, 3, 31, 7, 8)

    val lessThanFive = myList.filter { it > 5 }
    println(lessThanFive)

    val pairList = lessThanFive.zipWithNext().filterIndexed { index, pair ->
        index % 2 == 0
    }
    println(pairList)

    val multPair = pairList.flatMap { pair ->
        listOf(pair.first * pair.second)
    }
    println(multPair)

    val finalSum = multPair.sum()
    println(finalSum)



}