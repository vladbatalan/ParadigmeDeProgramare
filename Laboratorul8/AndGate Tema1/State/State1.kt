package State

import AndGate

class State1(val gate:AndGate) : IState {
    override fun nextState(): IState {
        if(gate.getType().getNumOfEntries() > getStateNumber())
            return State2(gate)
        else
            return State0(gate)
    }

    override fun getResult(currentResult: Boolean): Boolean {
        return currentResult.and(gate.entry1)
    }

    override fun getStateNumber(): Int {
        return 1
    }
}