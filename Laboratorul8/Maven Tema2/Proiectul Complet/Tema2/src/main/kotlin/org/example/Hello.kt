package org.example

@ExperimentalUnsignedTypes
fun main(args: Array<String>) {

    val cleanGetRequest1 : CleanGetRequest = CleanGetRequest(GetRequest("https://en.wikipedia.org/wiki/Kotlin_(programming_language)", mapOf(), 5.0))
    val cleanGetRequest2 : CleanGetRequest = CleanGetRequest(GetRequest("https://ro.wikipedia.org/wiki/Programare_func%C8%9Bional%C4%83", mapOf(), 5.0))
    val cleanGetRequest3 : CleanGetRequest = CleanGetRequest(GetRequest("https://www.random.org/", mapOf(), 5.0))
    val cleanGetRequest4 : CleanGetRequest = CleanGetRequest(GetRequest("https://jamilacuisine.ro/mancare-de-mazare-cu-pui-reteta-video/", mapOf(), 5.0))

    var browser : KidsBrowser

    println("Accessing: https://ro.wikipedia.org/wiki/Programare_func%C8%9Bional%C4%83 ...")
    browser = KidsBrowser(cleanGetRequest2, null)
    browser.start()

    println("Accessing: https://en.wikipedia.org/wiki/Kotlin_(programming_language) ...")
    browser = KidsBrowser(cleanGetRequest1, null)
    browser.start()

    println("Accessing: https://www.random.org/ ...")
    browser = KidsBrowser(cleanGetRequest3, null)
    browser.start()


    println("Accessing: https://jamilacuisine.ro/mancare-de-mazare-cu-pui-reteta-video/ ...")
    browser = KidsBrowser(cleanGetRequest4, null)
    browser.start()

}

