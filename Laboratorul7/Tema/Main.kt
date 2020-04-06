import java.io.File
import java.sql.Timestamp

fun <T:Comparable<T>> getMax(obj1: T, obj2:T): T{
    if(obj1.compareTo(obj2) >= 0) return obj1
    return obj2
}

fun <K:Timestamp,T:HistoryLogRecord>replace(for_val:T, replace_with:T, outHashMap: HashMap<K, MutableList<T>>){
    var ok:Boolean = false
    outHashMap.forEach {
        if(!ok && it.component2().contains(for_val)){
            it.component2().remove(for_val)
            ok = true
            if(it.component2().size == 0){
                outHashMap.remove(it.key)
            }
        }
    }
    if(!outHashMap.containsKey(replace_with.startDate)) {
        val tmp = mutableListOf<T>()
        outHashMap.put(replace_with.startDate as K, tmp)
    }
    outHashMap.get(replace_with.startDate)?.add(replace_with)
}

fun main(args:Array<String>){
    val f = File("history.log")
    val content = f.readText()

    print(content)

    //index for counting the lines
    var index = 0
    var str = ""

    //the list
    var list = mutableListOf<HistoryLogRecord>()


    //foreach line add 5 lines and make an history log based on them
    for(line in content.lines()){
        if(line.isEmpty())
            continue
        if(index == 4) {
            val log = HistoryLogRecord(str)
            list.add(log)
            str = ""
        }
        else{
            str = str + line + '\n'
        }
        index = (index + 1)%5

    }

    val historyMap = hashMapOf<Timestamp, MutableList<HistoryLogRecord>>()

    list.forEach{
        if(!historyMap.containsKey(it.startDate)) {
            val tmpSeq = mutableListOf<HistoryLogRecord>()
            historyMap.put(it.startDate, tmpSeq)
        }
        historyMap[it.startDate]?.add(it)
    }

    var elemToReplaceWith:HistoryLogRecord = HistoryLogRecord("")
    print("########## Current map: ##########\n")
    historyMap.forEach { itPrimary ->
        print("->\t\t" + itPrimary.key.toString() + "\n")
        itPrimary.value.forEach {
            print( it.toString() + '\n')
            elemToReplaceWith = it
        }
    }

    print("######### Replace element from map: #########\n")
    val replace_History = HistoryLogRecord("Start-Date: 2020-04-01  23:17:48\n" +
            "Commandline: apt remove --purge jeex second command replaced\n")

    replace(elemToReplaceWith, replace_History, historyMap)
    print("######### Changed map: ########\n")
    historyMap.forEach { itPrimary ->
        print("->\t\t" + itPrimary.key.toString() + "\n")
        itPrimary.value.forEach {
            print( it.toString() + '\n')
        }
    }

}