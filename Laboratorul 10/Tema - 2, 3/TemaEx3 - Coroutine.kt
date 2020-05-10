package org.example

import kotlinx.coroutines.*
import kotlin.random.Random

suspend fun getResult(myNumber : Int) : Int{
    val randomSeconds = Random.nextLong(100, 2000)
    println("Thread ${Thread.currentThread().name} execute getResult($myNumber)\n" +
            "\tThe execution is due to be completed in $randomSeconds miliseconds")
    delay(randomSeconds)
    var sum = 0
    for (index in 1 .. (myNumber+1)){
        sum += index;
    }
    println("Thread ${Thread.currentThread().name} finished getResult($myNumber) with $sum as a result.")
    return sum
}

fun main(args: Array<String>) {
    val nums : List<Int> = listOf<Int>(
        10,
        2,
        4,
        20
    )

    runBlocking {
        val jobs = arrayListOf<Job>()
        var results = mutableListOf<Int>(0,0,0,0)
        nums.forEachIndexed { index, num ->
            jobs.add(CoroutineScope(Dispatchers.Default).launch { results.set(index, getResult(num)) })
        }
        //println("Jobs before join, baby!")
        jobs.forEach{ job:Job ->
            //println("Job on join, Baby!")
            job.join()
        }
        //println("Job after join, Baby!")

        results.forEach{
            println("One result is $it")
        }
    }
}
