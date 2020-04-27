package org.example

class GenericRequest(private val url:String, private val params:Map<String, String>?):Cloneable {
    override fun clone(): GenericRequest {
        return this
    }
}