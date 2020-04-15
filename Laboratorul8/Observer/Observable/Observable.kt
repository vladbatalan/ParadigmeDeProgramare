package Observable
import Observer.Observer

open class Observable {
    private var observers:MutableList<Observer> = mutableListOf()


    fun add(observer: Observer){
        observers.add(observer)
    }

    fun remove(observer: Observer) {
        observers.remove(observer)
    }

    fun notifyAllObs(){
        observers.forEach{
            it.update()
        }
    }

}