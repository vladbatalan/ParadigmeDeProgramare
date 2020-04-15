package Observable

import Momento.Momento

class Originator : Observable() {
    private var message = String()

    fun saveToMomento() : Momento{
        return Momento(message)
    }
    fun restoreFromMomento(momento : Momento){
        message = momento.getState();
    }
    fun setMessage(message: String){
        this.message = message;
    }

}