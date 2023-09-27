package ipca.utility.adder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextOp1 = findViewById<EditText>(R.id.editTextOp1)
        val editTextOp2 = findViewById<EditText>(R.id.editTextOp2)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val textViewDisplay = findViewById<TextView>(R.id.textViewDisplay)

        buttonAdd.setOnClickListener {
            val op1: Int = if (editTextOp1.text.toString().isNotEmpty())
                editTextOp1.text.toString().toInt()
            else
                0
            val op2: Int = if (editTextOp2.text.toString().isNotEmpty())
                editTextOp2.text.toString().toInt()
            else
                0
            val result = op1 + op2
            textViewDisplay.text = result.toString()
        }

    }
}