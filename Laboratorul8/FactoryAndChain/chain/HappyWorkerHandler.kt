package chain

class HappyWorkerHandler(var next1: Handler? = null, var next2: Handler? = null): Handler {
    override fun handleRequest(forwardDirection: String, messageToBeProcessed: String) {
        val splitCommand = messageToBeProcessed.split(":")
        val priority = splitCommand[0]
        val message = splitCommand[1]

        if(forwardDirection == "Up"){
            println("Sunt Worker si am inspectat terminarea prelucrarii mesajului -> $message")
            return
        }

        if(priority == "4"){

            println("Sunt Worker si prelucrez mesajul -> $message")
            next2?.handleRequest("Up", messageToBeProcessed)
            return
        }

        //nu stiu cine poate rezolva sunt in capatul listei
        println("Sunt Worker si nu pot prelucra mesajul -> $message: are prioritatea -> $priority")
    }

    override fun setLinks(link1: Handler?, link2: Handler?) {
        next1 = link1
        next2 = link2
    }
}