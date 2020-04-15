package Observer

import Momento.Caretacker

class LargeWordConsumer : Observer {
    private var careTacker: Caretacker = Caretacker()
    private var times = 0

    constructor(caretacker: Caretacker){
        this.careTacker = caretacker
    }
    override fun update(){
        val crrMomento = careTacker.getMySavedStates().get(careTacker.getMySavedStates().lastIndex)
        if(crrMomento.getState().length > 7){
            //se proceseaza aici
            times ++
            println("LargeWord processed: ${crrMomento.getState()}")
            if(times % 10 == 0)
            {
                careTacker.getMySavedStates().toMutableList()[careTacker.getMySavedStates().size % 10] = crrMomento
                println("\tcareTracker restored by LargeWord!")
                times = 0
            }
        }

    }



}