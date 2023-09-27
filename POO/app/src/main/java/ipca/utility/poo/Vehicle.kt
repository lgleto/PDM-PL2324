package ipca.utility.poo

abstract class Vehicle {

    var velocity = 0
     var model : String

    constructor( model : String){
        this.model = model
    }

    abstract fun accelerate()
    abstract fun brake()

    open fun showState (){
        println("This ${model} is running at $velocity Km/h")
    }
}