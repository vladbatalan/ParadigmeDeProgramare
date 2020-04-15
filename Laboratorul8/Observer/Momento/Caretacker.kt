package Momento

class Caretacker {
    var savedStates : MutableList<Momento> = mutableListOf<Momento>()

    fun addMomento(momento: Momento){
        savedStates.add(momento)
    }

    fun getMySavedStates() : MutableList<Momento>{
        return savedStates
    }

}