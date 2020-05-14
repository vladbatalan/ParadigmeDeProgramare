import java.lang.NullPointerException
import java.util.*

fun main(args:Array<String>){
    val reader = Scanner(System.`in`)
    var myString = reader.nextLine()

    val mySequence = generateSequence {
        if(!myString.isEmpty())
        {
            val firstChar = myString[0]
            val found = myString.indexOfFirst {
               it != firstChar
            }
            if( found >= 0 )
            {
                myString = myString.substring(found)
                if(found != 1)
                    firstChar + found.toString()
                else
                    firstChar
            }
            else {
                val len = myString.length
                myString = ""
                if(len > 1)
                    firstChar + len.toString()
                else
                    firstChar
            }
        }
        else
            null
    }.toList().joinToString(separator = "")

    println(mySequence)
}