class SyslogRecord{
    private var timestamp = CustomTimeStamp("","","")
    private var hostname:String = ""
    private var aplicationName:String = ""
    private var pid : Int = 0
    private var logEntry:String = ""

    constructor(content: String){

        //split staff
        val splitRecord = content.split(' ')
        if(splitRecord.size <= 5)
            return


        //get point from where to search for ':'
        val time = "(?:[012]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)".toRegex()
        var getTime = ""
        if(time.find(content) != null)
            getTime = time.find(content)!!.value as String

        //gasim secventa de unde incepe logEntry
        val columnReg = ": ".toRegex()
        val startColumn = columnReg.find(content, time.find(content)!!.range.last)!!.range.last


        //get pid if exist
        val getPidRegex = "\\[[0-9]+\\]".toRegex()
        val isPid = getPidRegex.find(splitRecord[5])
        if(isPid != null) {
            val pidString = isPid.value.substring(1, isPid.value.length - 1)
            pid =  pidString.toInt()
            aplicationName = splitRecord[5].substring(0, isPid.range.first)
        }
        else {
            pid = -1
            aplicationName = splitRecord[5].substring(0, splitRecord[5].length-1)
        }
        timestamp = CustomTimeStamp(splitRecord[0],splitRecord[2],getTime)
        hostname = splitRecord[4]
        logEntry = (content.substring(startColumn) as String).trim()
    }

    fun monthToIntDate(month:String) : String{
        val monthArray = mutableListOf<String>(
            "Ian", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        var pos = monthArray.indexOf(month).toString()
        return pos
    }

    override fun toString():String{
        if(pid != -1)
            return "${timestamp.toString()} $hostname $aplicationName[$pid]:  $logEntry\n"
        return "${timestamp.toString()} $hostname $aplicationName:  $logEntry\n"
    }

    fun getTimeStamp() : CustomTimeStamp{
        return timestamp
    }

    fun getAplicationName():String{
        return aplicationName
    }

    fun getLogEntry():String{
        return logEntry
    }

    fun getPid():Int{
        return pid
    }

}

