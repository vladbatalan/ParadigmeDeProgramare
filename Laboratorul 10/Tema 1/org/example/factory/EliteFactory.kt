package org.example.factory

import org.example.chain.CEOHandler
import chain.ExecutiveHandler
import org.example.chain.Handler
import org.example.chain.ManagerHandler

class EliteFactory: AbstractFactory() {
    override fun getHandler(handler: String): Handler? {
        return when(handler.toUpperCase()){
            "CEO" -> CEOHandler()
            "EXECUTIVE" -> ExecutiveHandler()
            "MANAGER" -> ManagerHandler()
            else -> null
        }
    }
}