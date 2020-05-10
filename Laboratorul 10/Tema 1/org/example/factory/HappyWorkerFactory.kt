package org.example.factory

import org.example.chain.Handler
import org.example.chain.HappyWorkerHandler

class HappyWorkerFactory: AbstractFactory() {
    override fun getHandler(handler: String): Handler? {
        return when(handler.toUpperCase()){
            "WORKER" -> HappyWorkerHandler()
            else -> null
        }
    }
}