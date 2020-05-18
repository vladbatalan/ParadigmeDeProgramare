
import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val reader = Scanner(System.`in`)

    //read offset
    println("Se introduce offset-ul pentru Caesar: ")
    val offsetCaesar = reader.nextInt()

    //read content
    val myFile = File("text_to_caesar.txt")
    val content = myFile.readText()
    println(content)

    //creare text
    val myText = content.split(' ').map { s ->
        if(s.length in 4..7)
        {
            var newString = s.map {
                if(it.toInt() in 'a'.toInt()..'z'.toInt()){
                    (((it.toInt() - 'a'.toInt() + offsetCaesar) % 26) + 'a'.toInt()).toChar()
                }else{
                    if(it.toInt() in 'A'.toInt()..'Z'.toInt()){
                        (((it.toInt() - 'A'.toInt() + offsetCaesar) % 26) + 'A'.toInt()).toChar()
                    }else{
                        it
                    }
                }
            }.joinToString("")
            newString
        }else {
            s
        }
    }.joinToString(" ")

    //afisare text
    println(myText)
}