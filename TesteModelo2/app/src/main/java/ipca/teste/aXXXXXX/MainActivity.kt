package ipca.teste.aXXXXXX

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val sliderView = findViewById<SliderView>(R.id.sliderView)
        val buttonReset = findViewById<Button>(R.id.buttonReset)

        sliderView.setPercentageChanged {
            if (it>80){
                textView.text = "O valor não deve exceder os 80%"
            }else{
                textView.text = ""
            }
        }

        buttonReset.setOnClickListener {
            sliderView.value = 50
        }



    }
}