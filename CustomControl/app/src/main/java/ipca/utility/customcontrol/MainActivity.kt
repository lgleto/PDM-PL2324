package ipca.utility.customcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClear = findViewById<Button>(R.id.buttonClear)
        val jogoGaloView = findViewById<JogoGaloView>(R.id.jogoGaloView)
        val textViewTurn = findViewById<TextView>(R.id.textViewTurn)

        buttonClear.setOnClickListener {
            jogoGaloView.clear()
        }

        jogoGaloView.setOnTurnChanged {
            if (it) {
                textViewTurn.text = "Circles is your turn"
            }else{
                textViewTurn.text = "Crosses is your turn"
            }
        }

    }
}