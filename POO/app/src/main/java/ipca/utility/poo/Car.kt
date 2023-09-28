package ipca.utility.poo

class Car : Vehicle, Wheels {

    var wheels : Int
    var cabinTemprature = 15
    constructor(model : String, wheels: Int) : super(model) {
        this.wheels = wheels

    }
    override fun accelerate() {
        velocity += 5
    }

    override fun brake() {
        velocity -= 2
    }

    fun heat(){
        cabinTemprature = 21
    }

    override fun showState (){
        super.showState()
        println("This is a car")
    }

    override fun checkHowManyWheelsAreRunning() {
        println("This vehicle has $wheels wheels.")
    }

    companion object{
        val FUEL_PRICE = 1.42
        fun pricePerKm (km: Double, literPer100Km : Double)  {
            var result = (km * FUEL_PRICE * literPer100Km ) / 100.0
            println("Your cost is $result ")
        }
    }

}