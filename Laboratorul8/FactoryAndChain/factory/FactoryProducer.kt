package factory

class FactoryProducer {
    fun getFactory(choice: String): AbstractFactory? {
        return when(choice.toUpperCase()){
            "ELITE" -> EliteFactory()
            "WORKER"-> HappyWorkerFactory()
            else -> {
                null
            }
        }
    }
}