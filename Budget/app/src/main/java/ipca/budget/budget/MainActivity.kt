package ipca.budget.budget

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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {

    var budgetItems : List<BudgetItem> = arrayListOf<BudgetItem>()

    lateinit var listViewBudgetItem : ListView
    val adapter = BudgetAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listViewBudgetItem = findViewById<ListView>(R.id.listViewBudgetItems)
        listViewBudgetItem.adapter = adapter


        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val intent = Intent(this,BudgetItemDetialActivity::class.java )
            startActivity(intent)
        }

        AppDatabase
            .getDatabase(this@MainActivity)
            ?.budgetItemDao()
            ?.getAll()?.observe(this@MainActivity, Observer {
                budgetItems = it
                adapter.notifyDataSetChanged()
            })

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
                startActivity(intent)
                true
            }
            R.id.action_sort -> {

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


            rootView.setOnClickListener {
                val intent = Intent(this@MainActivity, BudgetItemDetialActivity::class.java)
                intent.putExtra("budget_id", budgetItems[position].id)
                startActivity(intent)
            }
            return rootView
        }

    }
}