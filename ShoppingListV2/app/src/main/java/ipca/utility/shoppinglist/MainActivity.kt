package ipca.utility.shoppinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {

    var products = arrayListOf<Product>( )

    val productAdapter = ProductAdapter()

    val resultLauncher =
        registerForActivityResult(ActivityResultContracts
        .StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK){

            Backend.fetchProducts(lifecycleScope){
                this.products = it
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

        Backend.fetchProducts(lifecycleScope){
            this.products = it
            productAdapter.notifyDataSetChanged()
        }

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
            checkBox.isChecked = products[position].isChecked

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