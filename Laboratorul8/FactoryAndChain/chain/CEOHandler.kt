package chain

class CEOHandler(var next1: Handler? = null, var next2: Handler? = null): Handler {
    override fun handleRequest(forwardDirection: String, messageToBeProcessed: String) {
        val splitCommand = messageToBeProcessed.split(":")
        val priority = splitCommand[0]
        val message = splitCommand[1]


        if(forwardDirection == "Up"){
            println("Sunt CEO si am inspectat terminarea prelucrarii mesajului -> $message")
            return
        }

        if(priority == "1"){

            println("Sunt CEO si prelucrez mesajul -> $message")
            next2?.handleRequest("Up", messageToBeProcessed)
            return
        }

        next1?.handleRequest("Right", messageToBeProcessed)
        //next2?.handleRequest("Down", messageToBeProcessed)
    }

    override fun setLinks(link1 : Handler?, link2 : Handler?){
        next1 = link1
        next2 = link2
    }

}