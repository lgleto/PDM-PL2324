package ipca.utility.poo

class Car : Vehicle{

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
}