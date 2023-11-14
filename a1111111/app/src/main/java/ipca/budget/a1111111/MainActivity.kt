package ipca.budget.a1111111

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.util.Date

class MainActivity : AppCompatActivity() {

    val budgetItems = arrayListOf<BudgetItem>(
        BudgetItem("Batatas 1kg", 10.0, Date()),
        BudgetItem("Cebolas 1kg", 15.0, Date()),
        BudgetItem("Bacalhau 1kg", 50.0, Date())
    )

    lateinit var listViewBudgetItem : ListView
    val adapter = BudgetAdapter()


    val resultLauncher =
        registerForActivityResult(
            ActivityResultContracts
            .StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK){
                it.data?.let {intent ->
                    val name = intent.extras?.getString(BudgetItemDetialActivity.DATA_NAME)?:""
                    val value = intent.extras?.getDouble(BudgetItemDetialActivity.DATA_VALUE)?:0.0
                    val date = Date()
                    val budgetItem = BudgetItem(name!!, value!!,date)
                    budgetItems.add(budgetItem)
                    adapter.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listViewBudgetItem = findViewById<ListView>(R.id.listViewBudgetItems)
        listViewBudgetItem.adapter = adapter

        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val intent = Intent(this,BudgetItemDetialActivity::class.java )
            resultLauncher.launch(intent)
        }

        findViewById<Button>(R.id.buttonSort).setOnClickListener {
            budgetItems.sortBy {
                it.value
            }
            adapter.notifyDataSetChanged()
        }
        findViewById<Button>(R.id.buttonTotal).setOnClickListener {

            var result = 0.0
            for(b in budgetItems){
                result += b.value
            }

            Toast.makeText(this, "Total:${result}", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this,BudgetItemDetialActivity::class.java )
                resultLauncher.launch(intent)
                true
            }
            R.id.action_sort -> {
                budgetItems.sortBy {
                    it.value
                }
                adapter.notifyDataSetChanged()
                true
            }
            R.id.action_total -> {
                var result = 0.0
                for(b in budgetItems){
                    result += b.value
                }

                Toast.makeText(this, "Total:${result}", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    inner class BudgetAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return budgetItems.size
        }

        override fun getItem(position: Int): Any {
            return budgetItems[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_budget_item,parent, false)
            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
            val textViewValue = rootView.findViewById<TextView>(R.id.textViewValue)
            val textViewDate = rootView.findViewById<TextView>(R.id.textViewDate)

            textViewDescription.text = budgetItems[position].description
            textViewValue.text = budgetItems[position].value.toString()
            textViewDate.text = budgetItems[position].date.toString()

            return rootView
        }

    }
}