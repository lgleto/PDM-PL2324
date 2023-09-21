package ipca.utility.adder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val editTextOp1 = EditText(this)
        val editTextOp2 = EditText(this)

        val buttonAdd = Button(this)
        buttonAdd.text = "Soma"

        val textViewDisplay =  TextView(this)
        textViewDisplay.text = "Olá Mundo!"
        textViewDisplay.textSize = 32.0f

        val linearLayout = LinearLayout(this)
        linearLayout.addView(editTextOp1)
        linearLayout.addView(editTextOp2)
        linearLayout.addView(buttonAdd)
        linearLayout.addView(textViewDisplay)

        buttonAdd.setOnClickListener{
            val op1 : Int = editTextOp1.text.toString().toInt()
            val op2 : Int = editTextOp2.text.toString().toInt()

            val result = op1 + op2

            textViewDisplay.text = result.toString()

        }

        setContentView(linearLayout)
    }
}