import java.util.*

fun main(args:Array<String>){
    val reader = Scanner(System.`in`)

    print("Numar de multiplicari: ")
    var n = reader.nextInt()

    val myList = listOf<Int>(1, 2, 3)

    val duplicatedList = myList.flatMap {
        val myInternalList = mutableListOf<Int>()
        for( index in 0 .. n-1)
            myInternalList.add(it)
        myInternalList.toList()
    }

    println(duplicatedList)

}