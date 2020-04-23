package State

import AndGate

interface IState {
    fun nextState() : IState
    fun getResult(currentResult : Boolean) : Boolean
    fun getStateNumber() : Int
}