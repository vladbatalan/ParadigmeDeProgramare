import Momento.Caretacker
import Observable.Originator
import Observer.LargeWordConsumer
import Observer.SmallWordConsumer
import java.io.File

fun main(args : Array<String>){
    val originator = Originator()
    val caretacker = Caretacker()


    val obs1 = LargeWordConsumer(caretacker)
    val obs2 = SmallWordConsumer(caretacker)
    originator.add(obs1)
    originator.add(obs2)


    val f = File("out/production/Observer/words.txt")
    val content = f.readText()

    content.split(" ").forEach{
        //println("\t$it")
        originator.setMessage(it)
        caretacker.addMomento(originator.saveToMomento())
        originator.notifyAllObs()
    }

    caretacker.getMySavedStates().forEach{
        print("${it.getState()} ")
    }



}