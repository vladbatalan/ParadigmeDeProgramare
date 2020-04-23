package State

import AndGate

class State5(val gate:AndGate) : IState {
    override fun nextState(): IState {
        if(gate.getType().getNumOfEntries() > getStateNumber())
            return State6(gate)
        else
            return State0(gate)
    }

    override fun getResult(currentResult: Boolean): Boolean {
        return currentResult.and(gate.entry5)
    }

    override fun getStateNumber(): Int {
        return 5
    }
}