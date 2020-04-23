package Builder

import AndGate
import Bridge.IImplementation

class ElectricEngineer(private val implementation : IImplementation) {
    private val andGate = AndGate(implementation)
    private val builderList = listOf<IBuilder>(
        Entry1Builder(andGate),
        Entry2Builder(andGate),
        Entry3Builder(andGate),
        Entry4Builder(andGate),
        Entry5Builder(andGate),
        Entry6Builder(andGate),
        Entry7Builder(andGate),
        Entry8Builder(andGate)
    )

    fun constructGate(entryList : ArrayList<Boolean>){
        if (entryList.size != implementation.getNumOfEntries())
        {
            println("Incorect number of inputs! The consctruction of the gate failed!")
            return
        }
        entryList.forEachIndexed { index, value ->
            builderList[index].buildPart(value)
        }
    }

    fun getProduct():AndGate{
        return andGate
    }
}