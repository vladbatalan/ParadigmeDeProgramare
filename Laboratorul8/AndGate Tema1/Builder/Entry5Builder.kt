package Builder

import AndGate

class Entry5Builder(val gate:AndGate):IBuilder {
    override fun buildPart(value : Boolean) {
        gate.entry5 = value
    }
}