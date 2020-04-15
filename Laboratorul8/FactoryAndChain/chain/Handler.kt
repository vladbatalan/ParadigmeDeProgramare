package chain

interface Handler {
    fun handleRequest(forwardDirection: String, messageToBeProcessed: String)
    fun setLinks(link1 : Handler?, link2 : Handler?)
}