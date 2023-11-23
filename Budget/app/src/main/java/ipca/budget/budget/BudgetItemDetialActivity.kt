package ipca.budget.budget

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class BudgetItemDetialActivity : AppCompatActivity() {

    var budgetItem : BudgetItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_item_detial)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextValue = findViewById<EditText>(R.id.editTextValue)

        intent.extras?.let {
            val id = it.getString("budget_id")
            id?.let { budgetId->
                lifecycleScope.launch(Dispatchers.IO) {
                    budgetItem = AppDatabase
                        .getDatabase(this@BudgetItemDetialActivity)
                        ?.budgetItemDao()
                        ?.loadById(budgetId)
                    lifecycleScope.launch(Dispatchers.IO) {
                        editTextName.setText(budgetItem?.description)
                        editTextValue.setText(budgetItem?.value.toString())
                    }
                }
            }
        }
        findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            if (budgetItem != null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    AppDatabase
                        .getDatabase(this@BudgetItemDetialActivity)
                        ?.budgetItemDao()?.delete(budgetItem!!)
                    lifecycleScope.launch(Dispatchers.Main) {
                        finish()
                    }
                }
            }
        }

        findViewById<Button>(R.id.buttonDone).setOnClickListener {
            if (budgetItem == null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    AppDatabase
                        .getDatabase(this@BudgetItemDetialActivity)
                        ?.budgetItemDao()
                        ?.insert(
                            BudgetItem(
                                UUID.randomUUID().toString(),
                                editTextName.text.toString(),
                                editTextValue.text.toString().toDouble(),
                                Date()
                            )
                        )
                    lifecycleScope.launch(Dispatchers.Main) {
                        finish()
                    }

                }
            }else{
                lifecycleScope.launch(Dispatchers.IO) {
                    AppDatabase
                        .getDatabase(this@BudgetItemDetialActivity)
                        ?.budgetItemDao()
                        ?.insert(
                            BudgetItem(
                                budgetItem!!.id,
                                editTextName.text.toString(),
                                editTextValue.text.toString().toDouble(),
                                Date()
                            )
                        )
                    lifecycleScope.launch(Dispatchers.Main) {
                        finish()
                    }
                }
            }

        }

    }

    companion object {
        const val DATA_NAME = "data_name"
        const val DATA_VALUE = "data_value"
    }
}