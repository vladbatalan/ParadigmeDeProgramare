package Builder

import AndGate

class Entry7Builder(val gate:AndGate):IBuilder {
    override fun buildPart(value : Boolean) {
        gate.entry7 = value
    }
}