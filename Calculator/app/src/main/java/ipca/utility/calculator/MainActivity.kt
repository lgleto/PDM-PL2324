package ipca.utility.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var textViewDisplay : TextView? = null

    val onNumPressed : (View)->Unit =  {
        val num = (it as Button).text.toString()
        var displayText = textViewDisplay?.text.toString()
        if (displayText == "0") {
            if (num  == ".") {
                displayText = "0."
            }else {
                displayText = num
            }
        }else{
            if (num  == ".") {
                if (!displayText.contains(".")){
                    displayText += num
                }
            }else{
                displayText += num
            }
        }
        textViewDisplay?.text = displayText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewDisplay = findViewById<TextView>(R.id.textViewDisplay)

        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val button0 = findViewById<Button>(R.id.button0)
        val buttonDot = findViewById<Button>(R.id.buttonDot)

        button1.setOnClickListener(onNumPressed)
        button2.setOnClickListener(onNumPressed)
        button3.setOnClickListener(onNumPressed)
        button4.setOnClickListener(onNumPressed)
        button5.setOnClickListener(onNumPressed)
        button6.setOnClickListener(onNumPressed)
        button7.setOnClickListener(onNumPressed)
        button8.setOnClickListener(onNumPressed)
        button9.setOnClickListener(onNumPressed)
        button0.setOnClickListener(onNumPressed)
        buttonDot.setOnClickListener(onNumPressed)
    }

}