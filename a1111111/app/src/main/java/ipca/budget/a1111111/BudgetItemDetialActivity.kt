package ipca.budget.a1111111

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class BudgetItemDetialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_item_detial)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextValue = findViewById<EditText>(R.id.editTextValue)

        findViewById<Button>(R.id.buttonDone).setOnClickListener {
            val intent = Intent()
            intent.putExtra(DATA_NAME,editTextName.text.toString())
            intent.putExtra(DATA_VALUE,editTextValue.text.toString().toDouble())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    companion object {
        const val DATA_NAME = "data_name"
        const val DATA_VALUE = "data_value"
    }
}