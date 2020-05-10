package org.example.chain

interface Handler {
    suspend fun handleRequest(messageToBeProcessed: String)
    fun setLinks(link1 : Handler?, link2 : Handler?, link3 : Handler? = null)
}