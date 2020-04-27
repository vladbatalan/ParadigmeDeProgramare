package org.example

import khttp.post
import khttp.responses.Response

class PostRequest(private val url:String, private val params:Map<String, String>) {
    private val genericReq:GenericRequest = GenericRequest(url, params)

    fun postData() : Response{
        return post(url, params = params)
    }
}