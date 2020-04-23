package State

import AndGate

class State4(val gate:AndGate) : IState {
    override fun nextState(): IState {
        if(gate.getType().getNumOfEntries() > getStateNumber())
            return State5(gate)
        else
            return State0(gate)
    }

    override fun getResult(currentResult: Boolean): Boolean {
        return currentResult.and(gate.entry4)
    }

    override fun getStateNumber(): Int {
        return 4
    }
}