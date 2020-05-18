import java.util.*

fun main(args: Array<String>) {
    val reader = Scanner(System.`in`)

    val myPointList = mutableListOf< List<Int> >()

    print("n = ")
    val n = reader.nextInt()


    for(index in 1..n){
        print("x = ")
        val x = reader.nextInt()
        print("y = ")
        val y = reader.nextInt()

        myPointList.add(listOf(x, y))
    }

    myPointList.add(myPointList[0]) // pentru legatura dintre primul si ultimul element

    var perimetru:Double = 0.0
    myPointList.zipWithNext().forEach {
        val p1 = it.first
        val p2 = it.second

        val distance = Math.sqrt(((p1[0]-p2[0])*(p1[0]-p2[0]) + (p1[1] - p2[1])*(p1[1] - p2[1]).toDouble()))

        perimetru += distance
    }

    println("Perimetrul poligonului este: $perimetru")
}