package Builder

import AndGate

class Entry3Builder(val gate:AndGate):IBuilder {
    override fun buildPart(value : Boolean) {
        gate.entry3 = value
    }
}