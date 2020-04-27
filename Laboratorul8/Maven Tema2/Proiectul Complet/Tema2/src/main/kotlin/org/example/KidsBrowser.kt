package org.example

class KidsBrowser(private val cleanReq:CleanGetRequest, private val postRequest: PostRequest?) {
    fun start(){
        val response = cleanReq.getResponse()
        if(response != null)
        {
            println(response.text)
        }else
        {
            println("The content of the page is protected by parrental control!")
        }
    }
}