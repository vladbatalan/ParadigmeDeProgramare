package org.example

import kotlinx.coroutines.*
import kotlin.system.*
import org.example.factory.FactoryProducer

fun main(args:Array<String>){

    // se creeaza 1xFactoryProducer, 1xEliteFactory,1xHappyWorkerFactory
    // ...
    val producerFact = FactoryProducer();
    val eliteFact = producerFact.getFactory("elite");
    val happyWorkerFact = producerFact.getFactory("worker");


    // crearea instantelor (prin intermediul celor 2 fabrici):
    // 2xCEOHandler, 2xExecutiveHandler, 2xManagerHandler,2xHappyWorkerHandler
    //...

    val ceo1 = eliteFact?.getHandler("ceo");
    val ceo2 = eliteFact?.getHandler("ceo");

    val executive1 = eliteFact?.getHandler("executive");
    val executive2 = eliteFact?.getHandler("executive");

    val manager1 = eliteFact?.getHandler("manager");
    val manager2 = eliteFact?.getHandler("manager");

    val worker1 = happyWorkerFact?.getHandler("worker");
    val worker2 = happyWorkerFact?.getHandler("worker");

    // se construieste lantul (se verifica intai diagrama de obiectesi se realizeaza legaturile)
    // ...

    ceo1?.setLinks(executive1, ceo2)
    ceo2?.setLinks(executive2, ceo1)

    executive1?.setLinks(manager1, executive2, null)
    executive2?.setLinks(manager2, executive1, ceo2)

    manager1?.setLinks(worker1, manager2, null)
    manager2?.setLinks(worker2, manager1, executive2)

    worker1?.setLinks(null, worker2)
    worker2?.setLinks(worker1, manager2)

    runBlocking {
        // se executa lantul utilizand atat mesaje de prioritate diferita,cat si directii diferite in lant
        val job1 = CoroutineScope(Dispatchers.Default).launch {
            async { ceo1?.handleRequest("1:Request - Test the Mango Project") }
            async { ceo1?.handleRequest("3:Request - Handle Ford's smart behavior project") }
            async { ceo1?.handleRequest("2:Request - Fire the bad manager") }
        }
        job1.join()
    }


}
