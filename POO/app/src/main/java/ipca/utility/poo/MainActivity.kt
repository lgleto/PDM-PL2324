package ipca.utility.poo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         //var vehicle = Vehicle("BMW")

        var car = Car("BMW", 4)
        var boat = Boat("Yamaha")

        car.m
        car.accelerate()
        boat.accelerate()

        var vehicles = arrayListOf(car, boat)

        for ( v in vehicles){
            v.showState()
            if (v is Car){
                v.heat()
            }
        }



    }
}