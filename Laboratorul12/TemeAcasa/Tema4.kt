
class MyFunctor<K, V>(val myMap : MutableMap<K,V>){
    fun mapPair(function: ( Pair<K, V> ) -> Pair<K, V>) : MyFunctor<K,V>{
        val newMap = mutableMapOf<K,V>()
        myMap.forEach{
            val myPair = function(it.toPair())
            newMap.put(myPair.first, myPair.second)
        }
        return MyFunctor(newMap)
    }
    fun map(function: (V) -> V) : MyFunctor<K,V>{
        val newMap = mutableMapOf<K,V>()
        myMap.forEach{
            val result = function(it.value)
            newMap.put(it.key, result)
        }
        return MyFunctor(newMap)
    }

}

fun String.toPascalCase0(): String {
    val components = this.split(" ")
    var result: String = ""
    for(component in components) {
        result += component.capitalize()
    }
    return result
}

fun String.toPascalCase1(): String = this.split(" ").map{it.capitalize()}.joinToString(separator="")
fun String.toPascalCase2(): String = this.split(" ").joinToString(separator = "") { it.capitalize() }

fun main() {
    val myMap = mutableMapOf<Int, String>(
        1 to "value one",
        2 to "value two",
        3 to "value three",
        4 to "value four"
    )

    val firstOperation = MyFunctor<Int, String>(myMap).mapPair {
        Pair<Int, String>(it.first, "Test " + it.second )
    }.myMap
    println(firstOperation)

    val secondOperation = MyFunctor<Int, String>(myMap).map{
        it.toPascalCase0()
    }.myMap
    println(secondOperation)
}
