package factory

import chain.Handler
import chain.HappyWorkerHandler

class HappyWorkerFactory: AbstractFactory() {
    override fun getHandler(handler: String): Handler? {
        return when(handler.toUpperCase()){
            "WORKER" -> HappyWorkerHandler()
            else -> null
        }
    }
}