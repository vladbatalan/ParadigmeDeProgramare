package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

fun alfaMultiply(myList : ArrayList<Int>, alfa : Int) : ArrayList<Int> {
    myList.forEachIndexed { index, elem ->
        myList[index] = elem * alfa
    }
    return myList
}

fun orderList(myList: ArrayList<Int>) : ArrayList<Int>{
    Collections.sort(myList)
    return myList
}

fun showList(myList : ArrayList<Int> ) : ArrayList<Int>{
    println(myList)
    return myList
}

fun main(args: Array<String>) {
    val myList : ArrayList<Int> = arrayListOf(
        10, 32, 19 ,5 ,6 ,7
    )

    runBlocking {
        launch {
            val channel1 = Channel<ArrayList<Int>>()
            launch {
                val channel2 = Channel<ArrayList<Int>>()
                launch {channel2.send(alfaMultiply(myList, 3)) }
                channel1.send(orderList(channel2.receive()))
            }
            showList(channel1.receive())
        }
    }

}
