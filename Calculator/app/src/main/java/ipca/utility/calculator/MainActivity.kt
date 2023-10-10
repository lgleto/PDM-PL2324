package ipca.utility.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var calculatorBrain = CalculatorBrain()
    var textViewDisplay : TextView? = null
    var userIsInTheMiddleOfIntroduction = true

    var display : Double
        get() {
            return textViewDisplay?.text.toString().toDouble()
        }
        set(value) {
            var intResult = value.toInt()
            var resultTxt = ""
            if (value == intResult.toDouble() ) {
                resultTxt = intResult.toString()
            }else{
                resultTxt = value.toString()
            }
            textViewDisplay?.text = resultTxt
        }

    val onNumPressed : (View)->Unit =  {
        val num = (it as Button).text.toString()
        var displayText = textViewDisplay?.text.toString()
        if (userIsInTheMiddleOfIntroduction) {
            if (displayText == "0") {
                if (num == ".") {
                    displayText = "0."
                } else {
                    displayText = num
                }
            } else {
                if (num == ".") {
                    if (!displayText.contains(".")) {
                        displayText += num
                    }
                } else {
                    displayText += num
                }
            }
        }else {
            displayText = num
        }
        textViewDisplay?.text = displayText

        userIsInTheMiddleOfIntroduction = true
    }

    val onOperationPressed : (View) -> Unit = {

        calculatorBrain.operation?.let{
            display = calculatorBrain.doOperation(display)
        }
        calculatorBrain.operation = when ((it as Button).text.toString()){
            "+" -> CalculatorBrain.Operation.SUM
            "-" -> CalculatorBrain.Operation.SUB
            "*" -> CalculatorBrain.Operation.MUL
            "/" -> CalculatorBrain.Operation.DIV
            else -> null
        }
        calculatorBrain.accumulator = display
        userIsInTheMiddleOfIntroduction = false



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


        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonAdd    = findViewById<Button>(R.id.buttonAdd)
        val buttonMinus  = findViewById<Button>(R.id.buttonMinus)
        val buttonMulti  = findViewById<Button>(R.id.buttonMulti)

        val buttonEqual   = findViewById<Button>(R.id.buttonEqual)
        val buttonAC      = findViewById<Button>(R.id.buttonAC)


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

        buttonDivide.setOnClickListener(onOperationPressed)
        buttonAdd.setOnClickListener(onOperationPressed)
        buttonMinus.setOnClickListener(onOperationPressed)
        buttonMulti.setOnClickListener(onOperationPressed)

        buttonEqual.setOnClickListener {
            display = calculatorBrain.doOperation(display)
        }

        buttonAC.setOnClickListener {
            calculatorBrain.accumulator = 0.0
            calculatorBrain.operation = null
            display = 0.0
            userIsInTheMiddleOfIntroduction = true
        }

    }



}