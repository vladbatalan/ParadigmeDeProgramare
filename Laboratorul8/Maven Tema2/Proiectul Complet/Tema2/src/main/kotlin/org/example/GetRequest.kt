package org.example

import khttp.responses.Response
import khttp.get

class GetRequest(private val url:String, private val params:Map<String,String>, private val timeout:Double) : HTTPGet {
    private val genericReq:GenericRequest = GenericRequest(url, params)

    override fun getResponse(): Response? {
        return get(url, timeout = this.timeout)
    }
}