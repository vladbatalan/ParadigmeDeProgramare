package Builder

import AndGate

class Entry2Builder(val gate:AndGate):IBuilder {
    override fun buildPart(value : Boolean) {
        gate.entry2 = value
    }
}