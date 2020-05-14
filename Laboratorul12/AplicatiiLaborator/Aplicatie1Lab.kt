

fun Int.isPrime() : Boolean{
    if(this == 2)
        return true

    if(this % 2 == 0 || this == 1)
        return false

    val number = Math.abs(this)
    var index = 3

    while(index * index < number){
        if(number % index == 0)
            return false;
        index += 2;
    }
    return true;
}


fun main(args:Array<String>){
    val myList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ,11, 12, 13)

    myList.forEach {
        if (it.isPrime()){
            println("$it is prime!")
        }
    }

}