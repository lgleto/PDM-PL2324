package ipca.utility.poo

class Moto : Vehicle, Wheels {

    var wheels : Int
    constructor(model : String, wheels: Int) : super(model) {
        this.wheels = wheels
        hello()
    }
    override fun accelerate() {
        velocity += 8
    }
    override fun brake() {
        velocity -= 2
    }
    override fun showState (){
        println("This is a moto ${model} running at ${velocity}")
    }
    override fun checkHowManyWheelsAreRunning() {
        println("This vehicle has $wheels wheels.")
    }
}