import java.io.File
import kotlin.collections.ArrayList

fun main(){
    var list = mutableListOf<SyslogRecord>()


    val logContent = File("/var/log/syslog").readText();
    for(line in logContent.lines()){
        if(!line.isEmpty()) {
            val record = SyslogRecord(line)
            list.add(record)
        }
    }
    val seq = list.asSequence().filter { it.getTimeStamp().currentTimeDifferenceInMinutes() < 30 }.asSequence()

    val myMap = mutableMapOf<String, ArrayList<SyslogRecord>>()

    for (elem in seq){
        if(myMap.containsKey(elem.getAplicationName()))
            myMap[elem.getAplicationName()]?.add(elem)
        else {
            val arr = ArrayList<SyslogRecord>()
            arr.add(elem)
            myMap.put(elem.getAplicationName(), arr)
        }
    }

    for(pair in myMap){
        pair.value.sortBy { it.getLogEntry().toUpperCase() }
    }

    for (pair in myMap){
        print(pair.key + ":\n")
        val sqOfSysLog = pair.value.asSequence().filter { it.getPid() != -1 }.asSequence()
        for(elem in sqOfSysLog){
            print("\t${elem.toString()}")
        }
    }
}