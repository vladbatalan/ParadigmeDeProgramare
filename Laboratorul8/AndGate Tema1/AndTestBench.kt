import Builder.ElectricEngineer
import Bridge.*

fun main(args:Array<String>){
    val engineer2 = ElectricEngineer(And2())
    val engineer3 = ElectricEngineer(And3())
    val engineer4 = ElectricEngineer(And4())
    val engineer8 = ElectricEngineer(And8())

    engineer2.constructGate(arrayListOf<Boolean>( true, true))
    engineer3.constructGate(arrayListOf<Boolean>( true, false, true))
    engineer4.constructGate(arrayListOf<Boolean>( true, false, true, true))
    engineer8.constructGate(arrayListOf<Boolean>( true, false, true, false, true, false, true, true))

    val gate1 = engineer2.getProduct()
    val gate2 = engineer3.getProduct()
    val gate3 = engineer4.getProduct()
    val gate4 = engineer8.getProduct()

    println("Result of gate1: " + gate1.getResult())
    println("Result of gate2: " + gate2.getResult())
    println("Result of gate3: " + gate3.getResult())
    println("Result of gate4: " + gate4.getResult())

}