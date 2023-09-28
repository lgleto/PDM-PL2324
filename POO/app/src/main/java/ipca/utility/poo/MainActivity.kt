package ipca.utility.poo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


fun hello(){
    print("hello world")
}
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         //var vehicle = Vehicle("BMW")

        var car = Car("BMW", 4)
        var boat = Boat("Yamaha")
        var moto = Moto("Honda",2)

        car.accelerate()
        boat.accelerate()

        var vehicles = arrayListOf(car, boat, moto)

        for ( v in vehicles){
            v.showState()
            if (v is Car){
                v.heat()
            }
            if (v is Wheels){
                v.checkHowManyWheelsAreRunning()
            }
        }

        Car.pricePerKm(10.0, 5.0)
        Utils.greetings()
        hello()
    }
}