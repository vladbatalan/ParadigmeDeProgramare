package Builder

import AndGate

class Entry6Builder(val gate:AndGate):IBuilder {
    override fun buildPart(value : Boolean) {
        gate.entry6 = value
    }
}