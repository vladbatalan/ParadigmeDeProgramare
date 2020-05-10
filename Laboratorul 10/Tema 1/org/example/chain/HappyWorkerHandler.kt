package org.example.chain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ArithmeticException
import kotlin.random.Random

class HappyWorkerHandler(var next1: Handler? = null, var next2: Handler? = null): Handler {
    override suspend fun handleRequest(messageToBeProcessed: String) {
        val splitCommand = messageToBeProcessed.split(":")
        val priority = splitCommand[0].toInt()
        val message = splitCommand[1]


        // avem parte de un request
        if (message.contains("Request")) {
            println("[$message] recieved by HappyWorker Handler - Level1...")
            if (priority == 4) {
                println("[$message] is being processed by HappyWorker Handler - Level1...")
                //prelucrez cererea
                var responseMessage = "3:"
                val job = CoroutineScope(Dispatchers.Default).launch {
                    val msgProcessed = processRequest(message)
                    responseMessage += msgProcessed
                }
                //trimit spre lantul de jos raspunsul
                job.join()
                next2?.handleRequest(responseMessage)

            } else {
                // creating a new message of error
                var errMessage = "1:"
                val job = CoroutineScope(Dispatchers.Default).launch {
                    errMessage += failedProcessRequest(message)
                    next2?.handleRequest(errMessage)
                }
                job.join()
            }
        } else if (message.contains("Response")) {
            if (priority == 0) {
                println("[$message] recieved by HappyWorker Handler - Level1...")
                println("[$message] was verifyed by HappyWorker Handler - Level1...")
            } else {
                if (priority == 4) {
                    println("[$message] recieved by HappyWorker Handler - Level2...")
                    val job = CoroutineScope(Dispatchers.Default).launch {
                        //change priority to 0 -> Send to be verify by level 1
                        next1?.handleRequest("0:" + message)
                    }
                    job.join()
                } else {
                    println("[$message] recieved by HappyWorker Handler - Level2...")
                    println("[$message] is being passed to next by HappyWorker Handler - Level2...")
                    val job = CoroutineScope(Dispatchers.Default).launch {
                        next2?.handleRequest(messageToBeProcessed)
                    }
                    job.join()
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
        println("\tProcessing for $random seconds at HappyWorker Handler...")
        delay(random)
        return message.replace("Request - ", "Response - ") + " processed by HappyWorker Handler"
    }

    private suspend fun failedProcessRequest(message: String) : String{
        val random = Random.nextInt(1000, 3000).toLong()
        println("\tProcessing for $random seconds HappyWorker Handler...")
        delay(random)
        return "Response - [${message.replace("Request - ","")}] could not be processed! The HappyWorker is overwhelmed!"
    }
}