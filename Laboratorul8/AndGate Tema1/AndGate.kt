import Bridge.*
import State.*

class AndGate(val gateType : IImplementation){
    var entry1 : Boolean = false
    var entry2 : Boolean = false
    var entry3 : Boolean = false
    var entry4 : Boolean = false
    var entry5 : Boolean = false
    var entry6 : Boolean = false
    var entry7 : Boolean = false
    var entry8 : Boolean = false

    private var currentState : IState = State0(this)

    fun getResult():Boolean{
        var result = true
        currentState = State0(this)
        currentState = currentState.nextState()

        while(currentState.getStateNumber() != 0){
            result = currentState.getResult(result)
            currentState = currentState.nextState()
        }
        return result
    }


    fun getType():IImplementation{
        return gateType
    }

}