package State

import AndGate

class State8(val gate:AndGate) : IState {
    override fun nextState(): IState {
        return State0(gate)
    }

    override fun getResult(currentResult: Boolean): Boolean {
        return currentResult.and(gate.entry8)
    }

    override fun getStateNumber(): Int {
        return 8
    }
}