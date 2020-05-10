package org.example

import kotlinx.coroutines.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.random.Random

val sharedLock = ReentrantLock()
fun executeSum(myNumber: Int) : Int{
    println("\t${Thread.currentThread().name} is sleeping for 1000 ms")
    Thread.sleep(1000)
    var sum = 0
    try {
        sharedLock.lock()
        for (index in 1..myNumber) {
            sum += index
        }
    }finally {
        sharedLock.unlock()
    }
    return sum
}

fun main(args: Array<String>) {
    val nums : List<Int> = listOf<Int>(
        10,
        2,
        4,
        20
    )
    val threads = arrayListOf<Thread>()
    val result = arrayListOf<String>()

    nums.forEach {
        val thisThread : Thread = thread(name = "Worker-responsible-for($it)") {
            result.add("The result of computing the ${Thread.currentThread().name} got the following result: ${executeSum(it)}")
        }
        threads.add(thisThread)
    }
    threads.forEach{
        it.join()
    }

    result.forEach{
        println(it)
    }


}
