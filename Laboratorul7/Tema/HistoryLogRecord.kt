import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

class HistoryLogRecord(logBlock: String) : Comparable<HistoryLogRecord>
{
    var startDate : Timestamp = Timestamp(0)
    var commandLine : String = "";

    init {
        //print("\t\tTested Log:\n$logBlock\n")
        var ok:Boolean = true
        val logLines = logBlock.lines()
        if(logLines[0].contains("Start-Date: ")){
            //get the string
            val regStart = "Start-Date: ".toRegex()
            val result = regStart.find(logLines[0])!!
            val dateString = logLines[0].subSequence(result.range.last + 1, logLines[0].length).toString()

            //create timestamp
            val dateFormat = SimpleDateFormat("yyyy-MM-dd  hh:mm:ss")
            val parsedData = dateFormat.parse(dateString)
            startDate = Timestamp(parsedData.time)
        }
        else
            ok = false
        if(logLines.size >= 2 && logLines[1].contains("Commandline: ")){
            //get the command line string
            val regCommand = "Commandline: ".toRegex()
            val result = regCommand.find(logLines[1])!!
            commandLine = logLines[1].substring(result.range.last + 1, logLines[1].length)
        }
        else
            ok = false
        if(!ok){
            startDate = Timestamp(0)
            commandLine = ""
        }
    }

    override fun toString():String{
        return "StartDate: $startDate\nCommandLine: $commandLine\n"
    }

    override fun compareTo(other: HistoryLogRecord): Int {
        return startDate.compareTo(other.startDate)
    }


}