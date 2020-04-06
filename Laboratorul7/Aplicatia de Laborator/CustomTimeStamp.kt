import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomTimeStamp(month: String, day: String, hhmmss: String) {
    var timestamp = String()
    init {
        timestamp = "$month $day $hhmmss"
    }

    fun currentTimeDifferenceInMinutes():Int{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val formatted = current.format(formatter) as String

        //get current time splited format
        val currentSplit = formatted.split(":")

        val hh0 = currentSplit[0].toInt()
        val mm0 = currentSplit[1].toInt()
        val ss0 = currentSplit[2].toInt()

        //get this splited format
        val splitTime = timestamp.split(' ')[2].split(':')
        val ss = splitTime[2].toInt()
        val mm = splitTime[1].toInt()
        val hh = splitTime[0].toInt()


        val secSum0 = hh0*3600 + mm0*60 + ss0
        val secSum1 = hh*3600 + mm*60 + ss

        //return minutes
        return (secSum0 - secSum1)/60
    }

    override fun toString() : String{
        return timestamp
    }


}