import kotlin.properties.Delegates



var primeNumber : Int by Delegates.vetoable(2){
        property, oldValue, newValue ->
    println("${property.name} $oldValue -> $newValue")

    // isPrime este implementat in Aplicatia1Lab.kt
    newValue.isPrime()
}


fun main(args:Array<String>){
    primeNumber = 1
    println("First assignation to primeNumber: $primeNumber")
    primeNumber = 13
    println("Second assignation to primeNumber: $primeNumber")
    primeNumber = 42
    println("Third assignation to primeNumber: $primeNumber")



}