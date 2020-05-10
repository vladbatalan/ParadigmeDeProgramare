package org.example.factory

import org.example.chain.Handler

abstract class AbstractFactory {
    abstract fun getHandler(handler: String): Handler?
}