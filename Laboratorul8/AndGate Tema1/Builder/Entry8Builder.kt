package Builder

import AndGate

class Entry8Builder(val gate:AndGate):IBuilder {
    override fun buildPart(value : Boolean) {
        gate.entry8 = value
    }
}