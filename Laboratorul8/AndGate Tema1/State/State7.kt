package State

import AndGate

class State7(val gate:AndGate) : IState {
    override fun nextState(): IState {
        if(gate.getType().getNumOfEntries() > getStateNumber())
            return State8(gate)
        else
            return State0(gate)
    }

    override fun getResult(currentResult: Boolean): Boolean {
        return currentResult.and(gate.entry7)
    }

    override fun getStateNumber(): Int {
        return 1
    }
}