package Builder

import AndGate

class Entry4Builder(val gate:AndGate):IBuilder {
    override fun buildPart(value : Boolean) {
        gate.entry4 = value
    }
}