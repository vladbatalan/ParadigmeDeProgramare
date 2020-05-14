

fun main(args:Array<String>){
    val myOriginalMap = mapOf<Int, String>(
        1 to "abc",
        2 to "def",
        3 to "ghi"
    )

    val myInvertedMap = myOriginalMap.map {
       it.value to it.key
    }
    println(myInvertedMap)
}