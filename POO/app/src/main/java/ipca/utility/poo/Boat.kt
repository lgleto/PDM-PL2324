package ipca.utility.poo

class Boat : Vehicle {

    constructor(model : String) : super(model)
    override fun accelerate() {
        velocity += 2
    }

    override fun brake() {
        velocity -= 1
    }

    override fun showState (){
        println("This ${model} is running at $velocity Miles/h")
    }
}