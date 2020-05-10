package chain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.chain.Handler
import kotlin.random.Random

class ExecutiveHandler(var next1: Handler? = null, var next2: Handler? = null, var next3:Handler? = null): Handler {

    override suspend fun handleRequest(messageToBeProcessed: String) {
        val splitCommand = messageToBeProcessed.split(":")
        val priority = splitCommand[0].toInt()
        val message = splitCommand[1]


        // avem parte de un request
        if (message.contains("Request")) {
            println("[$message] recieved by Executive Handler - Level1...")
            if (priority == 2) {
                println("[$message] is being processed by Executive Handler - Level1...")
                //prelucrez cererea
                var responseMessage = "1:"
                val job = CoroutineScope(Dispatchers.Default).launch {
                    val msgProcessed = processRequest(message)
                    responseMessage += msgProcessed
                }
                //trimit spre lantul de jos raspunsul
                job.join()
                next2?.handleRequest(responseMessage)

            } else {
                println("[$message] is being passed to next by Executive Handler - Level1...")
                //
                CoroutineScope(Dispatchers.Default).launch { next1?.handleRequest(messageToBeProcessed) }.join()
            }
        } else if (message.contains("Response")) {
            if (priority == 0) {
                println("[$message] recieved by Executive Handler - Level1...")
                println("[$message] was verifyed by Executive Handler - Level1...")
            } else {
                if (priority == 2) {
                    println("[$message] recieved by Executive Handler - Level2...")
                    CoroutineScope(Dispatchers.Default).launch {
                        //change priority to 0 -> Send to be verify by level 1
                        next2?.handleRequest("0:" + message)
                    }.join()
                } else {
                    println("[$message] recieved by Executive Handler - Level2...")
                    println("[$message] is being passed to next by Executive Handler - Level2...")
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
        println("\tProcessing for $random seconds as Executive Handler...")
        delay(random)
        return message.replace("Request - ", "Response - ") + " processed by Executive Handler"
    }

}