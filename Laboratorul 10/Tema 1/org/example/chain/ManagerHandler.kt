package org.example.chain

import kotlinx.coroutines.*
import java.lang.ArithmeticException
import kotlin.random.Random

class ManagerHandler(var next1: Handler? = null, var next2: Handler? = null, var next3:Handler? = null): Handler {
    override suspend fun handleRequest(messageToBeProcessed: String) {
        val splitCommand = messageToBeProcessed.split(":")
        val priority = splitCommand[0].toInt()
        val message = splitCommand[1]

        // avem parte de un request
        if (message.contains("Request")) {
            println("[$message] recieved by Manager Handler - Level1...")
            if (priority == 3) {
                println("[$message] is being processed by Manager Handler...")
                //prelucrez cererea
                var responseMessage = "2:"

                CoroutineScope(Dispatchers.Default).launch {
                    responseMessage += processRequest(message)
                }.join()

                //trimit spre lantul de jos raspunsul
                next2?.handleRequest(responseMessage)

            } else {
                println("[$message] is being passed to next by Manager Handler...")
                //
                CoroutineScope(Dispatchers.Default).launch { next1?.handleRequest(messageToBeProcessed) }.join()
            }
        } else if (message.contains("Response")) {
            if (priority == 0) {
                println("[$message] recieved by Manager Handler - Level1...")
                println("[$message] was verifyed by Manager Handler - Level1...")
            } else {
                if (priority == 3) {
                    println("[$message] recieved by Manager Handler - Level2...")
                    CoroutineScope(Dispatchers.Default).launch {
                        //change priority to 0 -> Send to be verify by level 1
                        next2?.handleRequest("0:" + message)
                    }.join()
                } else {
                    println("[$message] recieved by Manager Handler - Level2...")
                    println("[$message] is being passed to next by Manager Handler - Level2...")
                    CoroutineScope(Dispatchers.Default).launch {
                        next3?.handleRequest(messageToBeProcessed)
                    }.join()
                }
            }
        }
    }

    override fun setLinks(link1: Handler?, link2: Handler?, link3: Handler?) {
        next1 = link1
        next2 = link2
        next3 = link3
    }

    private suspend fun processRequest(message : String) : String{
        val random = Random.nextInt(1000, 3000).toLong()
        println("\tProcessing for $random seconds at ManagerHandler...")

        delay(random)
        return message.replace("Request - ", "Response - ") + " processed by Executive Handler"
    }
}