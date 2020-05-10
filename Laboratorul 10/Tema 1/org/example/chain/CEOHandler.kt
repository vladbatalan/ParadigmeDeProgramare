package org.example.chain
import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.*

class CEOHandler(var next1: Handler? = null, var next2: Handler? = null): Handler {

    override suspend fun handleRequest(messageToBeProcessed: String) {
        val splitCommand = messageToBeProcessed.split(":")
        val priority = splitCommand[0].toInt()
        val message = splitCommand[1]

        // avem parte de un request
        if (message.contains("Request")) {
            println("[$message] recieved by CEO Handler - Level1...")
            if (priority == 1) {
                println("[$message] is being processed by CEO Handler - Level1...")
                //prelucrez cererea
                var responseMessage = "1:"
                val job = CoroutineScope(Dispatchers.Default).launch {
                    val msgProcessed = processRequest(message)
                    responseMessage += msgProcessed
                }
                job.join()
                //trimit spre lantul de jos raspunsul

                next2?.handleRequest(responseMessage)

            } else {
                println("[$message] is being passed to next by CEO Handler - Level1...")
                val job = CoroutineScope(Dispatchers.Default).launch { next1?.handleRequest(messageToBeProcessed) }
                job.join()
            }
        } else if (message.contains("Response")) {
            if (priority == 0) {
                println("[$message] recieved by CEO Handler - Level1...")
                println("[$message] was verifyed by CEO Handler - Level1...")
            } else {
                if (priority == 1) {
                    println("[$message] recieved by CEO Handler - Level2...")
                    CoroutineScope(Dispatchers.Default).launch {
                        //change priority to 0 -> Send to be verify by level 1
                        next2?.handleRequest("0:" + message)
                    }.join()
                } else {
                    println("\tERROR! [$message] could not be verified!")
                }
            }
        }
    }

    override fun setLinks(link1: Handler?, link2: Handler?, link3: Handler?) {
        next1 = link1
        next2 = link2
    }

    private suspend fun processRequest(message : String) : String{
        val random = Random.nextInt(1000, 3000).toLong()
        println("\tProcessing for $random seconds as CEO Handler...")
        delay(random)
        return message.replace("Request - ", "Response - ") + " processed by CEO Handler"
    }

}