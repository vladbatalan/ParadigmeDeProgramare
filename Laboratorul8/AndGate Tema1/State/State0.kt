package State

import AndGate

class State0(val gate:AndGate) : IState {
    override fun nextState(): IState {
        if(gate.getType().getNumOfEntries() > getStateNumber())
            return State1(gate)
        else
            return this
    }

    override fun getResult(currentResult: Boolean): Boolean {
        return true
    }

    override fun getStateNumber(): Int {
        return 0
    }
}