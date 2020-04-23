package State

import AndGate

class State6(val gate:AndGate) : IState {
    override fun nextState(): IState {
        if(gate.getType().getNumOfEntries() > getStateNumber())
            return State7(gate)
        else
            return State0(gate)
    }

    override fun getResult(currentResult: Boolean): Boolean {
        return currentResult.and(gate.entry6)
    }

    override fun getStateNumber(): Int {
        return 7
    }
}