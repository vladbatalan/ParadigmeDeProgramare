import factory.FactoryProducer
import javafx.concurrent.Worker

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

    executive1?.setLinks(manager1, executive2)
    executive2?.setLinks(manager2, executive1)

    manager1?.setLinks(worker1, manager2)
    manager2?.setLinks(worker2, manager1)

    worker1?.setLinks(null, worker2)
    worker2?.setLinks(null, worker1)

    // se executa lantul utilizand atat mesaje de prioritate diferita,cat si directii diferite in lant
    // ...

    ceo1?.handleRequest("Right", "2:Curatenie printre manageri")
    ceo2?.handleRequest("Right", "1:Da-l afara pe Popescu")

    ceo1?.handleRequest("Right", "3:Fa chestii de manager")
    ceo2?.handleRequest("Right", "4:Fa cod de babuin")

    manager1?.handleRequest("Down", "4:Cauciuce pentru toti")
    executive1?.handleRequest("Right", "3:Prezentare de proiect")

    executive2?.handleRequest("Right", "5:Sterge praful")
    worker1?.handleRequest("Down", "4:Fa niste apeluri catre indieni")







}