package Observer

import Momento.Caretacker

class SmallWordConsumer : Observer {
    private var careTacker: Caretacker = Caretacker()
    private var times = 0
    constructor(caretacker: Caretacker){
        this.careTacker = caretacker
    }


    override fun update(){
        val crrMomento = careTacker.getMySavedStates().get(careTacker.getMySavedStates().lastIndex)
        if(crrMomento.getState().length <= 7){
            //se proceseaza aici
            times ++
            println("SmallWord processed: ${crrMomento.getState()}")
            if(times % 7 == 0)
            {
                careTacker.getMySavedStates().toMutableList()[careTacker.getMySavedStates().size % 7] = crrMomento
                println("careTracker restored by LargeWord!")
                times = 0
            }
        }

    }



}