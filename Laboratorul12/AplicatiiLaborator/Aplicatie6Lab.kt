import java.util.*

fun main(args:Array<String>){
    val reader = Scanner(System.`in`)
    val myString = reader.nextLine().toCharArray()

    var index = -1

    val mySequence = generateSequence {
        if(index < myString.size) {
            index ++
            myString[index]
        }else
            null
    }.take(myString.size).toList().distinct().toCharArray()

    println(mySequence)
}