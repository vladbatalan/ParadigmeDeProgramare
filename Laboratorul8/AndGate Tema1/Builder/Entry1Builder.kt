package Builder

import AndGate

class Entry1Builder(val gate:AndGate):IBuilder {
    override fun buildPart(value : Boolean) {
        gate.entry1 = value
    }
}