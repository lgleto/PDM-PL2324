package ipca.utility.shoppinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import io.swagger.client.models.Product

const val TAG = "shoppinglist"

class MainActivity : AppCompatActivity() {

    var products : List<Product>  = arrayListOf<Product>()

    val productAdapter = ProductAdapter()

    val resultLauncher =
        registerForActivityResult(ActivityResultContracts
        .StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK){

            getProducts()
        }
    }

    fun getProducts(){
        Backend.fetchProducts().observe(this) {
            it.onError {error ->
                Toast.makeText(
                    this@MainActivity,
                    "Erro:${error.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
            it.onNetworkError {
                Toast.makeText(
                    this@MainActivity,
                    "Erro${2}",
                    Toast.LENGTH_LONG
                ).show()
            }
            it.onSuccess {
                for ( p in it) {
                    Log.d(TAG, p.name?:"")
                }
                this.products = it.asList()
                productAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewProducts = findViewById<ListView>(R.id.listViewProducts)
        listViewProducts.adapter = productAdapter

        val buttonAdd = findViewById<Button>(R.id.buttonAddProduct)
        buttonAdd.setOnClickListener {
            val intent = Intent(this,ProductDetailActivity::class.java )
            resultLauncher.launch(intent)
        }

        getProducts()

    }



    inner class ProductAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return products.size
        }

        override fun getItem(position: Int): Any {
            return products[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_product,parent, false)
            val textViewProductName = rootView.findViewById<TextView>(R.id.textViewProductName)
            val textViewProductQtt = rootView.findViewById<TextView>(R.id.textViewProductQtt)
            val checkBox = rootView.findViewById<CheckBox>(R.id.checkBox)

            textViewProductName.text = products[position].name
            textViewProductQtt.text = products[position].qtt.toString()
            checkBox.isChecked = products[position].isChecked?:false

            rootView.setOnClickListener {
                val intent = Intent(this@MainActivity,ProductDetailActivity::class.java )
                intent.putExtra(ProductDetailActivity.DATA_PRODUCT_NAME, products[position].name)
                intent.putExtra(ProductDetailActivity.DATA_QTT, products[position].qtt)
                intent.putExtra(ProductDetailActivity.DATA_POSITION, position)
                resultLauncher.launch(intent)
            }

            return rootView
        }

    }
}