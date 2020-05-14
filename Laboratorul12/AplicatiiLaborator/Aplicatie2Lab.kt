import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun String.toDate(format : String) : LocalDate{
    val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
    val date = LocalDate.parse(this, formatter)

    return date
}

fun main(args:Array<String>){
    val toDay = "04/14/2020"
    val myDate = toDay.toDate("MM/dd/yyyy")

    println(myDate.toString())

}